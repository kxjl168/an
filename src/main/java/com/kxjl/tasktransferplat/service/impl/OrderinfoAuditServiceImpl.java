package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.controller.mongo.MongoGeoDao;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.annotation.OrderNeedLog;
import com.kxjl.tasktransferplat.dao.OrderInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAttachMoneyDetailMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAuditMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.OrderinfoAuditService;
import com.kxjl.tasktransferplat.service.PriceLockCompanyService;
import com.kxjl.tasktransferplat.service.UserMessageService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.service.mongo.MongoOrderInfoService;
import com.kxjl.tasktransferplat.util.ParamUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static com.kxjl.tasktransferplat.util.DateUtil.getLastWeek;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.*;

/**
 *
 */

@Service
public class OrderinfoAuditServiceImpl implements OrderinfoAuditService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderinfoAuditMapper itemMapper;

	@Autowired
	private OrderinfoAuditMapper orderInfoAuditMapper;

	@Autowired
	UserinfoService userinfoService;

	@Autowired
	UserMessageMapper userMessageMapper;

	@Autowired
	private OrderinfoAttachMoneyDetailMapper detailMapper;

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderinfoMapper orderinfoMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ManagerMessageService managerMessageService;

	@Autowired
	private PriceLockCompanyService priceLockCompanyService;

	@Autowired
	private OrderinfoOperateLogMapper orderinfoOperateLogMapper;

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveOrderinfoAudit(OrderinfoAudit item) {
		JSONObject rtn = new JSONObject();

		try {

			item.setId(UUIDUtil.getLongUUID(redisTemplate));

			itemMapper.insertSelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrderinfoAudit(OrderinfoAudit item) {
		JSONObject rtn = new JSONObject();

		if (item.getId() == null) {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			itemMapper.updateByPrimaryKeySelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新出错", e);
			rtn.put("bol", false);
			rtn.put("message", "更新出错");
			return rtn;
		}
	}

	@Override
	public List<OrderinfoAudit> selectOrderinfoAuditList(OrderinfoAudit item) {
		List<OrderinfoAudit> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectOwnList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteOrderinfoAudit(OrderinfoAudit item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override

	public OrderinfoAudit selectOrderinfoAuditById(Long id) {
		OrderinfoAudit data = null;

		OrderinfoAudit query = new OrderinfoAudit();
		query.setId(id);

		List<OrderinfoAudit> datas = selectOrderinfoAuditList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

	/**
	 * 锁企和平台查询待审核的信息
	 *
	 * @param item
	 * @param manager
	 * @return
	 */
	@Override
	public List<OrderinfoAudit> selectAuditByOrderId(Orderinfo item, Manager manager) {
		String orderInfoId = item.getId();
		Orderinfo orderinfo = orderInfoMapper.selectById(orderInfoId);
		long proposer = orderinfo.getLockerId();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("OrderInfoId", item.getId());
		queryWrapper.orderByDesc("ProposTime");

		List<OrderinfoAudit> orderinfoAudits = new ArrayList<>();
		int type = manager.getType();
		if (type == 0) {
			// 源匠查看
			// 待源匠审核通过
			if (orderinfo.getOrderState() == 1) {
				queryWrapper.in("auditStates", 0, 5);
				queryWrapper.eq("Proposer", proposer);
				queryWrapper.eq("ProposType", 1);

				orderinfoAudits = orderInfoAuditMapper.selectList(queryWrapper);
			}

			// 待琐企审核
			if (orderinfo.getOrderState() == 4) {
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("OrderInfoId", item.getId());
				queryWrapper2.orderByDesc("ProposTime");
				queryWrapper2.in("auditStates", 0, 3, 4);
				// queryWrapper2.eq("Proposer", proposer);
				queryWrapper2.eq("ProposType", 3);

				orderinfoAudits = orderInfoAuditMapper.selectList(queryWrapper2);
			}
			// 琐企审核不通过
			if (orderinfo.getOrderState() == 5) {
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("OrderInfoId", item.getId());
				queryWrapper2.orderByDesc("ProposTime");
				queryWrapper2.in("auditStates", 4);
				queryWrapper2.in("doneflag", 0);
				// queryWrapper2.eq("Proposer", proposer);
				queryWrapper2.eq("ProposType", 3);
				queryWrapper2.orderByDesc("ProposTime");
				

				orderinfoAudits = orderInfoAuditMapper.selectList(queryWrapper2);

				// 合并显示之前锁匠提交的数据 zj 190624
				QueryWrapper queryWrapper3 = new QueryWrapper();
				queryWrapper3.eq("OrderInfoId", item.getId());
				queryWrapper3.orderByDesc("ProposTime");
				queryWrapper3.in("auditStates", 0);
				queryWrapper3.in("doneflag", 0);
				// queryWrapper2.eq("Proposer", proposer);
				queryWrapper3.eq("ProposType", 1);
				List<OrderinfoAudit> orderinfoAudit2s = orderInfoAuditMapper.selectList(queryWrapper3);

				if (orderinfoAudit2s != null && orderinfoAudit2s.size() > 0) {
					orderinfoAudits.add(0, orderinfoAudit2s.get(0));
				}
			}

			// 待合伙人审核
			if (orderinfo.getOrderState() == 6) {
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("OrderInfoId", item.getId());
				queryWrapper2.orderByDesc("ProposTime");
				queryWrapper2.in("auditStates", 0, 6);
				queryWrapper2.eq("Proposer", proposer);
				queryWrapper2.eq("ProposType", 4);

				orderinfoAudits = orderInfoAuditMapper.selectList(queryWrapper2);
			}

		} else {
			// 琐企查看
			queryWrapper.eq("auditStates", 0);
			queryWrapper.eq("ProposType", 3);

			List<OrderinfoAudit> orderinfoAudits1 = orderInfoAuditMapper.selectList(queryWrapper);

			orderinfoAudits.addAll(orderinfoAudits1);
		}

		return orderinfoAudits;

	}

	/**
	 * 查询当前工单当前锁匠是否存在本次申请类型的未完成的审核
	 *
	 * @param orderinfo
	 * @param operateType
	 * @return
	 */
	@Override
	public OrderinfoAudit selectUndoneAudit(Orderinfo orderinfo, Integer operateType) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("OrderInfoId", orderinfo.getId());
		queryWrapper.eq("Proposer", orderinfo.getLockerId());
		queryWrapper.eq("ProposType", operateType);
		queryWrapper.in("AuditStates", 0, 5);
		OrderinfoAudit orderinfoAudit = orderInfoAuditMapper.selectOne(queryWrapper);
		return orderinfoAudit;
	}

	@Override
	public List<Map<String, Object>> countPlusOrder(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(3);
			item.setAuditStates(null);
			map = itemMapper.selectOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询申请加钱出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countPlusOrderPass(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(3);
			item.setAuditStates(3);
			map = itemMapper.selectOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询申请加钱通过出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public int countNumPlusOrder(Long companyId) {
		OrderinfoAudit orderinfoAudit = new OrderinfoAudit();
		orderinfoAudit.setCompanyId(companyId);
		orderinfoAudit.setProposType(3);
		orderinfoAudit.setAuditStates(0);
		return itemMapper.selectNumCount(orderinfoAudit);
	}

	@Override
	public int countOrderCompany(OrderinfoAudit item) {
		item.setAuditStates(0);
		item.setAuditor(null);
		item.setType(2);
		return itemMapper.selectCompanyAllCount(item);
	}

	@Override
	public int countCompanyOrderOne(OrderinfoAudit item) {
		item.setType(2);
		item.setAuditStates(null);
		return itemMapper.selectCompanyAllCount(item);
	}

	@Override
	public int countUserInfo(OrderinfoAudit item) {
		item.setProposType(1);
		item.setAuditStates(0);
		item.setAuditor(null);
		int t1 = itemMapper.selectUserInfoAllCount(item);
		QueryWrapper<Orderinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("OrderState", 5);
		int t2 = orderinfoMapper.selectCount(queryWrapper);
		return t1 + t2;
	}

	@Override
	public int countUserInfoPass(OrderinfoAudit item) {
		item.setProposType(1);

		//

		return itemMapper.selectUserInfoAllCount(item);
	}

	@Override
	public List<Map<String, Object>> countEnterpriseOrderAll(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setAuditor(null);
			item.setType(2);
			map = itemMapper.selectEnterpriseOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询源匠处理的工单统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countEnterpriseOrderAllPass(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setType(2);
			item.setAuditStates(1);
			map = itemMapper.selectEnterpriseOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询源匠已处理的工单统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countUserInfoOrder(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(1);
			item.setAuditor(null);
			item.setAuditStates(null);
			map = itemMapper.selectUserInfoOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询源匠处理的订单数统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countUserInfoOrderPass(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(1);
			item.setAuditStates(1);
			map = itemMapper.selectUserInfoOrderCountPass(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询源匠已处理的订单数统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public int countOrderEnterprise(OrderinfoAudit item) {
		item.setAuditStates(0);
		item.setAuditor(null);
		item.setType(0);
		return itemMapper.selectCompanyAllCount(item);
	}

	@Override
	public int countEnterpriseOrderOne(OrderinfoAudit item) {
		item.setType(0);
		item.setAuditStates(null);
		return itemMapper.selectCompanyAllCount(item);
	}

	@Override
	public int countEpUserInfo(OrderinfoAudit item) {
		item.setProposType(3);
		item.setAuditStates(0);
		item.setAuditor(null);
		return itemMapper.selectUserInfoAllCount(item);
	}

	@Override
	public int countEpUserInfoPass(OrderinfoAudit item) {

		item.setProposType(3);
		return itemMapper.selectUserInfoAllCount(item);
	}

	@Override
	public List<Map<String, Object>> countNormalOrderAll(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setAuditor(null);
			item.setType(0);
			item.setAuditStates(null);
			map = itemMapper.selectEnterpriseOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企处理的工单统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countNormalOrderAllPass(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setType(0);
			item.setAuditStates(3);
			map = itemMapper.selectEnterpriseOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企已处理的工单统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countNUserInfoOrder(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(1);
			item.setAuditor(null);
			map = itemMapper.selectUserInfoOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企处理的订单数统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countNUserInfoOrderPass(OrderinfoAudit item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setProposType(1);
			item.setAuditStates(null);
			map = itemMapper.selectUserInfoOrderCountPass(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企已处理的订单数统计出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	/**
	 * 查询通过的审核
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public List<OrderinfoAudit> selectOrderInfoAudit(OrderinfoAudit item) {
		return itemMapper.selectOrderInfoAudit(item);
	}

	/**
	 * 包装工单审核对象
	 *
	 * @param orderinfo
	 * @param proposType
	 * @param auditStates
	 * @return
	 */
	@Override
	public OrderinfoAudit packageOrderInfoAudit(Orderinfo orderinfo, int proposType, int auditStates) {
		OrderinfoAudit orderinfoAudit = new OrderinfoAudit();
		orderinfoAudit.setId(snowflakeIdWorker.nextId());
		orderinfoAudit.setOrderInfoId(orderinfo.getId());
		orderinfoAudit.setProposer(orderinfo.getCreateUserId());
		orderinfoAudit.setProposType(proposType);
		orderinfoAudit.setAuditStates(auditStates);
		return orderinfoAudit;
	}

	/**
	 * 查找创建审核
	 *
	 * @param orderinfo
	 * @return
	 */
	@Override
	public List<OrderinfoAudit> selectCreateAudit(Orderinfo orderinfo) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("OrderInfoId", orderinfo.getId());
		List<OrderinfoAudit> auditList = orderInfoAuditMapper.selectList(queryWrapper);
		return auditList;
	}

	/**
	 * 查询公司加钱金额
	 *
	 * @param item
	 * @return
	 */
	@Override
	public BigDecimal selectCompanyProposMoney(OrderinfoAudit item) {
		// 根据审核记录id查询审核记录
		OrderinfoAudit orderinfoAudit = orderInfoAuditMapper.selectById(item.getId());

		// 根据工单id查询工单
		String orderId = orderinfoAudit.getOrderInfoId();
		Orderinfo orderinfo = orderInfoMapper.selectById(orderId);

		// 查询锁企的对应价格
		long sellerId = orderinfo.getSellerId();
		int subType = orderinfoAudit.getSubType();
		PriceLockCompany companyPrice = priceLockCompanyService.selectByTypeAndId(sellerId, String.valueOf(subType), 2);
		BigDecimal proposMoney;
		if (companyPrice == null) {
			proposMoney = new BigDecimal(0).setScale(2);
		} else {
			proposMoney = companyPrice.getPrice().setScale(2);
		}
		return proposMoney;
	}

	/**
	 * 锁企审核
	 *
	 * @param managerId
	 * @param orderinfoAudit
	 * @param orderinfo
	 * @param passState
	 * @param reason
	 */
	@Override
	// @OrderNeedLog(type = 0, subType = 2)
	@Transactional
	public void lockCompanyAudit(String managerId, BaseRequestDto requestDto, OrderinfoAudit orderinfoAudit,
			Orderinfo orderinfo, Integer passState, String reason) {
		if (passState == 1) {
			auditPass(managerId, orderinfoAudit, orderinfo);
		} else {
			auditFail(managerId, reason, orderinfoAudit, orderinfo);
		}

		// 插入工单日志表
		OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
		orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
		orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
		orderinfoOperateLog.setType(0);
		orderinfoOperateLog.setSubType(2);
		orderinfoOperateLog.setContent(passState == 1 ? "通过" : "不通过："+reason);
		orderinfoOperateLog.setOperateUserId(managerId);
		orderinfoOperateLog.setAuditId(orderinfoAudit.getId());

		orderinfoOperateLogMapper.insert(orderinfoOperateLog);
	}

	/**
	 * 平台审核
	 *
	 * @param managerId
	 * @param orderinfoAudit
	 * @param orderinfo
	 * @param passState
	 * @param reason
	 * @param proposMoney
	 */
	@Override
	@Transactional
	// @OrderNeedLog(type = 1, subType = 2)
	public void platAudit(String managerId, BaseRequestDto requestDto, OrderinfoAudit orderinfoAudit,
			Orderinfo orderinfo, Integer passState, String reason, String proposMoney) {

		if (passState == 1 || passState == 2) {
			// 插入工单日志表
			OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
			orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
			orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
			orderinfoOperateLog.setType(1);
			orderinfoOperateLog.setSubType(2);
			orderinfoOperateLog.setContent("加钱审核" + (passState == 1 ? "通过" : "不通过"+reason));
			orderinfoOperateLog.setOperateUserId(managerId);
			orderinfoOperateLog.setAuditId(orderinfoAudit.getId());

			orderinfoOperateLogMapper.insert(orderinfoOperateLog);
		}
		if (passState == 1) {
			auditPass(managerId, orderinfoAudit, orderinfo);
		} else if (passState == 2) {
			auditFail(managerId, reason, orderinfoAudit, orderinfo);
		} else {

			// 源匠向锁企申请
			applyToLockCompany(managerId, requestDto, proposMoney, reason, orderinfo, orderinfoAudit);
		}

	}

	/**
	 * 源匠向锁企申请加钱
	 *
	 * @param managerId
	 * @param proposMoney
	 * @param reason
	 * @param orderinfo
	 * @param orderinfoAudit
	 */
	// @OrderNeedLog(type = 1, subType = 7) // 向锁企提交审核记录
	private void applyToLockCompany(String managerId, BaseRequestDto requestDto, String proposMoney, String reason,
			Orderinfo orderinfo, OrderinfoAudit orderinfoAudit) {

		// 将工单状态更新为待锁企审核
		orderinfo.setOrderState(4);

		// 更新原本锁匠的申请为待锁企审核
		orderinfoAudit.setAuditStates(5);

		// 插入源匠申请记录
		OrderinfoAudit insertAudit = new OrderinfoAudit();
		insertAudit.setId(snowflakeIdWorker.nextId());
		insertAudit.setOrderInfoId(orderinfo.getId());
		insertAudit.setProposType(3);
		insertAudit.setSubType(orderinfoAudit.getSubType());
		insertAudit.setProposMoney(new BigDecimal(proposMoney));
		insertAudit.setProposReason(reason);
		insertAudit.setProposer(managerId);
		insertAudit.setAuditStates(0);
		insertAudit.setIcons(orderinfoAudit.getIcons());
		orderInfoAuditMapper.insert(insertAudit);

		orderInfoMapper.updateById(orderinfo);

		orderInfoAuditMapper.updateById(orderinfoAudit);

		// 插入工单日志表
		OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
		orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
		orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
		orderinfoOperateLog.setType(1);
		orderinfoOperateLog.setSubType(7);
		orderinfoOperateLog.setContent(insertAudit.getProposReason());
		orderinfoOperateLog.setOperateUserId(managerId);
		orderinfoOperateLog.setAuditId(insertAudit.getId());

		orderinfoOperateLogMapper.insert(orderinfoOperateLog);

		/*
		 * //推送锁企消息 try { List<Map<String, String>> managerIdList =
		 * managerMessageService.selectManagerMessageInsertUserList(3 + ""); for
		 * (Map<String, String> map : managerIdList) { String userId =
		 * map.get("managerId"); ManagerMessage managerMessage = new ManagerMessage();
		 * managerMessage.setUserId(userId); managerMessage.setType(3 + "");
		 * managerMessage.setTitle("加钱审核"); managerMessage.setContent("等待客服审核");
		 * managerMessage.setIsRead(0); managerMessage.setId(UUIDUtil.getUUID());
		 * managerMessageService.insert(managerMessage); List<Map<String, String>> list
		 * = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
		 * MyWebSocket.sendMessage(JSON.toJSONString(list), userId); } } catch
		 * (IOException e) { e.printStackTrace(); log.error("websocket发送消息报错"); }
		 */
	}

	/**
	 * 审核通过
	 *
	 * @param managerId
	 * @param orderinfoAudit
	 * @param orderinfo
	 */
	@Transactional
	private void auditPass(String managerId, OrderinfoAudit orderinfoAudit, Orderinfo orderinfo) {
		orderinfoAudit.setAuditor(managerId);
		orderinfoAudit.setAuditTime(new Timestamp(System.currentTimeMillis()).toString());
		// 1、更新审核状态

		// 1：锁匠申请加钱待源匠审核，2：审核退单，3：待源匠确认,4：源匠加钱申请待锁企审核，5：锁企不通过待源匠处理
		int orderState = orderinfo.getOrderState();
		if (orderState == 1 || orderState == 5 || orderState == 4) {
			orderinfoAudit.setAuditStates(1);
			// 加钱通过
			addMoneyPass(managerId, orderinfoAudit, orderinfo);
		} else if (orderState == 2) {
			// 将审核状态变更为源匠审核通过
			orderinfoAudit.setAuditStates(1);
			// 取消工单通过
			cancelOrderPass(orderinfoAudit, orderinfo);
		}
		//orderinfoAudit.setDoneflag("1"); //完成
		orderInfoAuditMapper.updateById(orderinfoAudit);
	}

	/**
	 * 取消工单通过
	 *
	 * @param orderinfoAudit
	 * @param orderinfo
	 */
	private void cancelOrderPass(OrderinfoAudit orderinfoAudit, Orderinfo orderinfo) {
		// 2、更新工单状态为待分配，更新工单锁匠为空
		orderinfo.setOrderState(101);
		orderinfo.setLockerId(null);
		orderInfoMapper.updateById(orderinfo);

		// todo 3、推送
	}

	/**
	 * 申请加钱通过
	 *
	 * @param orderinfoAudit
	 * @param orderinfo
	 */
	@Transactional
	private void addMoneyPass(String managerId, OrderinfoAudit orderinfoAudit, Orderinfo orderinfo) {
		// 公共部分
		// 3、插入工单加钱记录
		OrderinfoAttachMoneyDetail detail = new OrderinfoAttachMoneyDetail();
		detail.setId(snowflakeIdWorker.nextId());
		detail.setAuditId(orderinfoAudit.getId());
		detail.setOperateTime(new Timestamp(System.currentTimeMillis()).toString());
		detail.setOperateUserId(orderinfoAudit.getAuditor());
		detail.setOrderinfoId(orderinfo.getId());
		detail.setReasonType("2" + String.valueOf(orderinfoAudit.getSubType()));

		Manager m = managerService.selectByPrimaryKey(managerId);

		detail.setReasonDes("加钱申请," + m.getNickname() + "审核通过:" + orderinfoAudit.getProposReason());

		// 如果工单状态是1，且通过，则是源匠通过，锁企无需支付加钱费用
		// 如果工单状态是4，且通过，则是锁企通过，那么由锁企支付加钱费用

		// 1：锁匠申请加钱待源匠审核，2：审核退单，3：待源匠确认,4：源匠加钱申请待锁企审核，5：锁企不通过待源匠处理（如果锁企通过源匠无需再次处理）， 6:
		// 合伙人下锁匠申请加钱待合伙人审核。
		int orderState = orderinfo.getOrderState();
		if (orderState == 4) { // 锁企审核通过
			BigDecimal platProposMoney = orderinfoAudit.getProposMoney();

			// 锁企价格变化
			detail.setChangeValueSeller(platProposMoney);

			// 查询出锁匠的加钱申请，并为锁匠的工单加钱
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("OrderinfoId", orderinfo.getId());
			queryWrapper.eq("ProposType", 1);
			queryWrapper.eq("AuditStates", 5); // '审核状态，0：未审核，1：源匠审核通过，2：源匠审核不通过，3：锁企审核通过，4：锁企审核不通过，5：源匠正在向锁企申请,
												// 6:锁匠向合伙人申请,7：合伙人审核不通过',
			List<OrderinfoAudit> applyList = orderInfoAuditMapper.selectList(queryWrapper);
			OrderinfoAudit lockerApply = applyList.get(0);
			// 将锁匠的申请状态修改为3
			lockerApply.setAuditStates(3);
			lockerApply.setAuditor(managerId);
			lockerApply.setAuditTime(null);
			lockerApply.setDoneflag("1"); //完成

			// 锁匠价格变化
			detail.setChangeValueLocker(lockerApply.getProposMoney());
			orderInfoAuditMapper.updateById(lockerApply);
			
		

		} else {

			// 查询出锁匠的加钱申请，
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("OrderinfoId", orderinfo.getId());
			queryWrapper.eq("ProposType", 1);
			queryWrapper.in("AuditStates", 0, 4); // 待源匠审核或者 锁企审核不通过
			List<OrderinfoAudit> applyList = orderInfoAuditMapper.selectList(queryWrapper);
			if (applyList != null && applyList.size() > 0) {
				OrderinfoAudit lockerApply = applyList.get(0);

				// 将锁匠的申请状态修改为1，审核通过
				lockerApply.setAuditStates(1);
				lockerApply.setAuditor(managerId);
				lockerApply.setAuditTime(null);
				lockerApply.setDoneflag("1"); //完成
				orderInfoAuditMapper.updateById(lockerApply);
			}

			// 如果是源匠直接通过
			detail.setChangeValueLocker(orderinfoAudit.getProposMoney());

		}

		// 插入工单价格变动
		detailMapper.insert(detail);

		// 更新工单价格
		OrderinfoAttachMoneyDetail dquery = new OrderinfoAttachMoneyDetail();
		dquery.setOrderinfoId(orderinfo.getId());
		// 更新工单锁匠总价，及琐企总价 zj 190527
		OrderinfoAttachMoneyDetail totalData = detailMapper.GetOrderTotalPrice(dquery);
		orderinfo.setLockerTotalPrice(totalData.getChangeValueLocker());
		orderinfo.setSellerTotalPrice(totalData.getChangeValueSeller());
		// 2、更新工单状态，更新工单金额
		orderinfo.setOrderState(210); // zj 190522 有审核的，都认为都确认过了，都不能再重新指派
		// 进行更新和插入操作
		orderInfoMapper.updateById(orderinfo);

		// 消息

		Orderinfo oinfo = orderinfoMapper.selectById(orderinfo.getId());
		Userinfo Locker = userinfoService.selectUserinfoById(oinfo.getLockerId());
		String msg = orderinfo.getOrderNo();
		// todo 3、推送
		AliSendSMS.sendMessageNoraml(Locker.getPhone(), msg);

		String messageStr = "您的工单" + orderinfo.getOrderNo() + "加钱通过审核,金额增加" + detail.getChangeValueLocker().toString()
				+ ",当前服务费" + totalData.getChangeValueLocker().toString();
		// 插入消息
		UserMessage um = new UserMessage();
		um.setId(snowflakeIdWorker.nextId());
		um.setMessageTitle("审核通过");
		um.setMessageContent(messageStr);
		um.setMessageType(2L);
		um.setReceiver(Locker.getId());
		um.setOrderInfoId(orderinfo.getId());

		userMessageMapper.insert(um);

		
		//审核完成
		orderInfoAuditMapper.updateAllAuditDone( orderinfo.getId());
	}

	/**
	 * 审核不通过
	 *
	 * @param managerId
	 * @param failReason
	 * @param orderinfoAudit
	 * @param orderinfo
	 */
	@Transactional
	private void auditFail(String managerId, String failReason, OrderinfoAudit orderinfoAudit, Orderinfo orderinfo) {
		orderinfoAudit.setAuditor(managerId);
		orderinfoAudit.setAuditFailReason(failReason);
		orderinfoAudit.setAuditTime(new Timestamp(System.currentTimeMillis()).toString());

		// 1、更新审核状态
		int orderState = orderinfo.getOrderState();
		if (orderState == 1 || orderState == 5) {
			// 将审核状态变更为源匠审核不通过
			orderinfoAudit.setAuditStates(2);
			// 平台不通过申请加钱
			addMoneyFail(managerId, orderinfo, failReason);
		} else if (orderState == 2) {
			// 将审核状态变更为源匠审核不通过
			orderinfoAudit.setAuditStates(2);
			// 取消工单不通过
			cancelOrderFail(orderinfoAudit, orderinfo);
		} else if (orderState == 4) {
			// 将审核状态变更为锁企审核不通过
			orderinfoAudit.setAuditStates(4);
			// 锁企不通过申请加钱
			addMoneyFail(managerId, orderinfo, failReason);
		}
		//orderinfoAudit.setDoneflag("1"); //完成
		orderInfoAuditMapper.updateById(orderinfoAudit);
	}

	/**
	 * 取消工单不通过
	 *
	 * @param orderinfoAudit
	 * @param orderinfo
	 */
	private void cancelOrderFail(OrderinfoAudit orderinfoAudit, Orderinfo orderinfo) {
		// 2、查询是否存在未审核的加钱申请，若存在，则状态变更为1，若不存在，则状态变更为201，
		// 工单状态不变，直接推送
		OrderinfoAudit undoneAudit = selectUndoneAudit(orderinfo, 1);
		if (undoneAudit != null) {
			orderinfo.setOrderState(1);
		} else {
			orderinfo.setOrderState(201);
		}
		orderInfoMapper.updateById(orderinfo);
	}

	/**
	 * 申请加钱不通过
	 *
	 * @param orderinfo
	 */
	@Transactional
	private void addMoneyFail(String managerId, Orderinfo orderinfo, String failReason) {
		int orderState = orderinfo.getOrderState();

		// 修改工单状态

		// 如果工单状态为1，说明源匠拒绝加钱申请，锁匠继续执行工单或取消 ，
		if (orderState == 1 || orderState == 5) {

			// 查询出锁匠的加钱申请， 修改审核状态
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("OrderinfoId", orderinfo.getId());
			queryWrapper.eq("ProposType", 1);
			queryWrapper.eq("DoneFlag", 0);
			queryWrapper.in("AuditStates", 0, 5);// 待源匠审核的
			List<OrderinfoAudit> applyList = orderInfoAuditMapper.selectList(queryWrapper);

			Userinfo Locker = new Userinfo();
			OrderinfoAudit lockerApply = new OrderinfoAudit();
			if (applyList != null && applyList.size() > 0) {
				lockerApply = applyList.get(0);

				lockerApply.setAuditStates(2); // 必须要，外面的修改状态为锁企审核的记录

				lockerApply.setAuditor(managerId);
				orderInfoAuditMapper.updateById(lockerApply);

				Locker = userinfoService.selectUserinfoById(Long.parseLong(lockerApply.getProposer()));

			} else
				return;

			orderinfo.setOrderState(210);

			String msg = orderinfo.getOrderNo();
			// todo 3、推送
			AliSendSMS.sendMessageSms(Locker.getPhone(), msg);

			String messageStr = "您的工单" + orderinfo.getOrderNo() + "申请未通过审核：" + failReason + ",请及时处理";
			// 插入消息
			UserMessage um = new UserMessage();
			um.setId(snowflakeIdWorker.nextId());
			um.setMessageContent(messageStr);
			um.setMessageType(2L);
			um.setReceiver(Locker.getId());
			um.setOrderInfoId(orderinfo.getId());
			um.setMessageTitle("审核未通过");
			userMessageMapper.insert(um);
			
			
			//审核完成
			orderInfoAuditMapper.updateAllAuditDone( orderinfo.getId());

		}
		// 如果工单状态为4，说明锁企拒绝加钱申请，则工单回到源匠处理
		else {

			orderinfo.setOrderState(5);

			// 查询出锁匠的加钱申请， 修改审核状态
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("OrderinfoId", orderinfo.getId());
			queryWrapper.eq("ProposType", 1);
			queryWrapper.in("AuditStates", 5);// 待锁企审核的
			queryWrapper.orderByDesc("ProposTime");
			List<OrderinfoAudit> applyList = orderInfoAuditMapper.selectList(queryWrapper);

			Userinfo Locker = new Userinfo();
			OrderinfoAudit lockerApply = new OrderinfoAudit();
			if (applyList != null && applyList.size() > 0) {
				lockerApply = applyList.get(0);

				lockerApply.setAuditStates(0); // 改为待处理
				orderInfoAuditMapper.updateById(lockerApply);

			}

			try {
				List<Map<String, String>> managerIdList = managerMessageService
						.selectManagerMessageInsertUserList(3 + "");
				for (Map<String, String> map : managerIdList) {
					String userId = map.get("managerId");
					ManagerMessage managerMessage = new ManagerMessage();
					managerMessage.setUserId(userId);
					managerMessage.setType(3 + "");
					managerMessage.setTitle("加钱审核");
					managerMessage.setContent("等待客服审核");
					managerMessage.setIsRead(0);
					managerMessage.setId(UUIDUtil.getUUID());
					managerMessageService.insert(managerMessage);
					List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
					MyWebSocket.sendMessage(JSON.toJSONString(list), userId);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("websocket发送消息报错");
			}

		}
		orderinfoMapper.updateByPrimaryKeySelective(orderinfo);

	}

	/**
	 * 合伙人查询锁匠加钱审核列表
	 * 
	 * @param partnerId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> partnerAuditLockerAddPriceOrderList(Long partnerId) {
		try {
			return itemMapper.partnerAuditLockerAddPriceOrderList(partnerId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
