package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.dao.ManagerMapper;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.pojo.ManagerRole;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PropertiesUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.tasktransferplat.dao.plus.AppSmsMapper;
import com.kxjl.tasktransferplat.dao.plus.AreaDistrictMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoAuditAttachMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoAuditMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoJobAreaMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoOperateLogMapper;
import com.kxjl.tasktransferplat.pojo.AppSms;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.pojo.UserMessage;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;
import com.kxjl.tasktransferplat.pojo.UserinfoAuditAttach;
import com.kxjl.tasktransferplat.pojo.UserinfoJobArea;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;
import com.kxjl.tasktransferplat.service.AreaService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static com.kxjl.base.util.DateUtil.getEndString;
import static com.kxjl.base.util.DateUtil.getStartString;
import static com.kxjl.tasktransferplat.util.DateUtil.getFirstDayOfWeek;
import static com.kxjl.tasktransferplat.util.DateUtil.nowDateFormatString;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserinfoServiceImpl implements UserinfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserinfoMapper itemMapper;

	@Autowired
	UserinfoOperateLogMapper userinfoOperateLogMapper;

	@Autowired
	UserinfoAuditMapper userinfoAuditMapper;

	@Autowired
	UserinfoAuditAttachMapper userinfoAuditAttachMapper;

	@Autowired
	private UserMessageMapper userMessageMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ManagerMapper managerMapper;

	@Autowired
	private OrderinfoMapper orderinfoMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private AreaService areaService;

	@Autowired
	private AreaDistrictMapper areaMapper;

	@Autowired
	private OrderinfoService orderinfoService;

	@Autowired
	private UserinfoJobAreaMapper userinfoJobAreaMapper;

	@Autowired
	private AppSmsMapper smsMapper;

	/**
	 * 更新锁匠为自由锁匠
	 * 
	 * @param lockerId
	 * @return
	 * @author zj
	 * @date 2019年5月16日
	 */
	@Override
	public Message updateLockerParterNull(Long lockerId) {

		Message message = new Message();
		int rst = itemMapper.updateLockerParterNull(lockerId);

		message.setBol(rst == 1 ? true : false);
		message.setMessage("更新成功");

		return message;
	}

	/**
	 * method with xml
	 * 
	 * @param
	 * @return
	 * @author KAutoGenerator
	 * @date 2019-01-29 10:29:15
	 */
	@Override
	public int updateByPrimaryKeySelective(Userinfo record) {
		return itemMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 接口 token
	 *
	 * @param token
	 * @return
	 * @author zj
	 * @date 2018年6月20日
	 */
	@Override
	public Userinfo getUserByToken(String token) {
		if (token == null || token.length() == 0) {
			return null;
		}
		return itemMapper.getUserByToken(token);

	}

	/**
	 * 待审核锁匠
	 *
	 * @param item
	 * @return
	 */
	@Override
	public List<Userinfo> selectUnAuditUserinfoList(Userinfo item) {
		List<Userinfo> itemList = new ArrayList<>();
		try {

			item.setAuditFlag("0");// 待审核
			item.setUserType("1"); // 普通锁匠

			itemList = itemMapper.selectList(item);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	/**
	 * 评分管理查询
	 *
	 * @param item
	 * @return
	 */
	@Override
	public List<Userinfo> selectUserinfoListByPoint(Userinfo item) {
		List<Userinfo> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectListByPoint(item);
			for (Userinfo userinfo : itemList) {
				// userinfo.setUpdateName(managerMapper.selectByPrimaryKey(userinfo.getUpdateUser()).getNickname());
				userinfo.setUpdateTime(DateUtil.getSecondString(userinfo.getUpdateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Override
	public List<Userinfo> selectListByMoney(Userinfo item) {
		List<Userinfo> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectListByMoney(item);
			for (Userinfo userinfo : itemList) {
				// userinfo.setUpdateName(managerMapper.selectByPrimaryKey(userinfo.getUpdateUser()).getNickname());
				// userinfo.setUpdateTime(DateUtil.getSecondString(userinfo.getUpdateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional
	public void refreshUserMoneyInfo(Userinfo item) throws Exception {

		Userinfo uq = new Userinfo();
		uq.setId(item.getId());
		Userinfo UMoney = getUserMoneyInfo(uq);

		if (UMoney == null)
			throw new Exception("获取用户费用失败!");

		Userinfo moneyuser = new Userinfo();
		moneyuser.setId(UMoney.getId());
		
		BigDecimal total= UMoney.getTotal().subtract(UMoney.getDoneMoney()).subtract(UMoney.getTodoMoney());
		if(total.doubleValue()<0)
			total=new BigDecimal(0);
		moneyuser.setAccountMoney(total);// 全部-已提现-冻结
		moneyuser.setFreezeMoney(UMoney.getTodoMoney());// 冻结

		updateByPrimaryKeySelective(moneyuser);
	}

	public Userinfo getUserMoneyInfo(Userinfo item) {
		Userinfo rst = new Userinfo();
		List<Userinfo> itemList = new ArrayList<>();
		try {

			Userinfo uquery = new Userinfo();
			uquery.setId(item.getId());
			itemList = itemMapper.selectListByMoney(uquery);

			if (itemList != null && itemList.size() > 1) {
				// 有多条数据，说明查询条件未设置 ，返回空
				return rst;
			}

			else {
				rst = itemList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return rst;
	}

	@Override
	public List<Userinfo> selectUserinfoByPhone(Userinfo userinfo) {
		return itemMapper.selectListByPhone(userinfo);
	}

	@Override
	public int untyingUserinfoById(Long id) {
		int result = 0;
		try {

			result = itemMapper.untying(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("解绑出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	/**
	 * 根据工单所属公司查询锁匠
	 *
	 * @param orderinfo
	 * @return
	 */
	@Override
	public List<Userinfo> selectLockerByOrderId(Orderinfo orderinfo) {

		List<Userinfo> userinfoList = itemMapper.selectLockerByCompanyId(orderinfo);
		return userinfoList;
	}

	@Override
	public List<Userinfo> selectUserinfoByIdCard(Userinfo userinfo) {
		return itemMapper.selectUserinfoByIdCard(userinfo);
	}

	@Override
	public int countNewLock(String startdate, String enddate) {
		Date date = new Date();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("auditFlag", 1);
		queryWrapper.eq("dataState", 1);
		queryWrapper.eq("userType", 1);
		queryWrapper.isNull("companyId");// ,null);
		queryWrapper.between("updateTime", startdate, enddate);

		// queryWrapper.between("updateTime", getStartString(getFirstDayOfWeek(date)),
		// getEndString(new Date()));
		return itemMapper.selectCount(queryWrapper);
	}

	@Override
	public List<Userinfo> selectUserinfoByPhoneAll(Userinfo tuserinfo) {
		return itemMapper.selectListByPhoneAll(tuserinfo);
	}

	/**
	 * 根据用户类型获取结账类型
	 * 
	 * @param userType
	 * @return
	 * @author zj
	 * @date 2019年6月5日
	 */
	private String getDepositTypeByuserType(String userType) {

		// 用户类型 签约锁匠=1, 合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6 ',
		// 结账类型 提现类型， 0：现结，1：周结，2：月结',

		// 1)月结： 2 自营、合伙人按月结算，下个月20日付款，15日前进行结算;
		// 2)周结：1 签约锁匠，安装完成后，质保期7天后进行结算；
		// 3)现结：0 临时锁匠，安装完成后可以现结。

		String dtype = "0";

		switch (userType) {
		case "1":
			dtype = "1";
			break;
		case "2":
			dtype = "2";
			break;
		case "3":
			dtype = "2";
			break;
		case "4":
			dtype = "2"; // 合伙人下锁匠，按合伙人月结处理，工单的结算类型从锁匠获取。
			// 当此锁匠是 从签约锁匠加入的合伙人团队，则之前的工单任然可以周结，根据之前工单的结算类型处理

			break;
		case "5":
			dtype = "2";
			break;
		case "6":
			dtype = "0";
			break;
		default:
			break;
		}

		return dtype;
	}

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveUserinfo(Userinfo item) {
		JSONObject rtn = new JSONObject();

		// // 工单所在地区
		// AreaDistrict areaDistrict =
		// areaService.getAreaDistrictByCode(item.getAreaCode());
		// item.setDistrictId(String.valueOf( areaDistrict.getId()));

		try {

			// 根据锁匠类型获取结算类型
			String dtype = getDepositTypeByuserType(item.getUserType());
			item.setDepositType(dtype);

			// 合伙人下锁匠特殊处理
			// 填入合伙人的所在地及作业区域.
			if (item.getCompanyId() != null) {
				// 合伙人
				Userinfo parter = selectUserinfoById(item.getCompanyId());
				item.setAreaCode(parter.getAreaCode());
				item.setDistrictids(parter.getDistrictids());
			}

			item.setId(snowflakeIdWorker.nextId());

			if (null != item.getDistrictids() && item.getDistrictids().length() > 0) {

				// 处理锁匠-作业区域
				// 添加
				String[] dids = item.getDistrictids().split(",");

				if (item.getUserType() != null && item.getUserType().equals("2")) {
					// 合伙人，检查区域

					boolean ischeck = true;
					for (int i = 0; i < dids.length; i++) {
						if (dids[i].trim().equals(""))
							continue;

						UserinfoJobArea jquery = new UserinfoJobArea();
						jquery.setDistrictId(Integer.parseInt(dids[i]));
						jquery.setLockId(item.getId());
						// 检查作业区域重复
						List<UserinfoJobArea> lockers = userinfoJobAreaMapper.selectParterList(jquery);
						if (lockers != null && lockers.size() > 0) {
							ischeck = false;
							rtn.put("bol", false);
							rtn.put("message",
									lockers.get(0).getDistrictName() + "区域下已存在合伙人" + lockers.get(0).getParterName());
							break;
						}
					}

					// 区域检查异常
					if (!ischeck) {
						return rtn;
					}
				}

				itemMapper.insertSelective(item);

				for (int i = 0; i < dids.length; i++) {
					if (dids[i].trim().equals(""))
						continue;
					UserinfoJobArea itemArea = new UserinfoJobArea();
					itemArea.setId(UUIDUtil.getUUID());
					itemArea.setDistrictId(Integer.parseInt(dids[i]));
					itemArea.setLockId(item.getId());

					userinfoJobAreaMapper.insertSelective(itemArea);
				}
			}

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
	public JSONObject updateUserinfo(Userinfo item) {
		JSONObject rtn = new JSONObject();

		// 根据锁匠类型获取结算类型
		String dtype = getDepositTypeByuserType(item.getUserType());
		item.setDepositType(dtype);

		// 合伙人下锁匠特殊处理
		// 填入合伙人的所在地及作业区域.
		if (item.getUserType() != null && item.getUserType().equals("4") && item.getCompanyId() != null) {
			// 合伙人
			Userinfo parter = selectUserinfoById(item.getCompanyId());
			item.setAreaCode(parter.getAreaCode());
			item.setDistrictids(parter.getDistrictids());
		}

		if (item.getId() == null) {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}
		// 工单所在地区
		if (item.getAreaCode() != null && item.getAreaCode() != "") {
			AreaDistrict areaDistrict = areaService.getAreaDistrictByCode(item.getAreaCode());
			item.setDistrictId(String.valueOf(areaDistrict.getId()));
		}
		try {
			itemMapper.updateByPrimaryKeySelective(item);

			// 合伙人处理
			if (item.getUserType() != null && !item.getUserType().equals("4")) {
				itemMapper.updateLockerParterNull(item.getId());
			}

			if (null != item.getDistrictids() && item.getDistrictids().length() > 0) {
				UserinfoJobArea q = new UserinfoJobArea();
				// 处理锁匠-作业区域
				String[] dids = item.getDistrictids().split(",");

				if (item.getUserType() != null && item.getUserType().equals("2")) { // 合伙人判断
					// 合伙人，检查区域
					boolean ischeck = true;
					for (int i = 0; i < dids.length; i++) {
						if (dids[i].trim().equals(""))
							continue;

						UserinfoJobArea jquery = new UserinfoJobArea();
						jquery.setDistrictId(Integer.parseInt(dids[i]));
						jquery.setLockId(item.getId());
						// 检查作业区域重复
						List<UserinfoJobArea> lockers = userinfoJobAreaMapper.selectParterList(jquery);
						if (lockers != null && lockers.size() > 0) {
							ischeck = false;
							rtn.put("bol", false);
							rtn.put("message",
									lockers.get(0).getDistrictName() + "区域下已存在合伙人" + lockers.get(0).getParterName());
							break;
						}
					}

					// 区域检查异常
					if (!ischeck) {
						return rtn;
					}
				}

				q.setLockId(item.getId());
				// 清空
				userinfoJobAreaMapper.deleteLockJobArea(q);

				for (int i = 0; i < dids.length; i++) {
					if (dids[i].trim().equals("")) {
						continue;
					}
					UserinfoJobArea itemArea = new UserinfoJobArea();
					itemArea.setId(UUIDUtil.getUUID());
					itemArea.setDistrictId(Integer.parseInt(dids[i]));
					itemArea.setLockId(item.getId());

					userinfoJobAreaMapper.insertSelective(itemArea);
				}
			}
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
	public List<Userinfo> selectUserinfoList(Userinfo item) {
		List<Userinfo> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
			/*
			 * for (Userinfo userinfo : itemList) {
			 * userinfo.setUpdateName(managerMapper.selectByPrimaryKey(userinfo.
			 * getUpdateUser()).getNickname()); }l
			 */
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteUserinfo(Userinfo item) {
		int result = 0;
		try {
			result = CheckOrderAndLocker(item);
			if (result == 0)
				result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	/**
	 * 检查是否有未处理工单， 如果为合伙人检查是否有下属锁匠
	 * 
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年5月23日
	 */
	public int CheckOrderAndLocker(Userinfo item) {
		// 查询锁匠未完成工单
		List<Orderinfo> orderinfoList = orderinfoService.selectOrderinfoByLockerId(item.getId());
		if (!orderinfoList.isEmpty()) {
			// 存在未完成工单，不能删除
			return -1;
		}

		Userinfo curu = selectUserinfoById(item.getId());
		if (curu.getUserType().equals("2") || curu.getUserType().equals("3")) {
			// 合伙人删除，检查名下锁匠
			Userinfo uquery = new Userinfo();
			uquery.setUserType("4");// 普通锁匠
			uquery.setDataState(1);// 正常状态的
			uquery.setCompanyId(curu.getId());// 合伙人下的
			List<Userinfo> lockers = selectUserinfoList(uquery);
			if (lockers != null && lockers.size() > 0) {
				// 存在下属锁匠，无法删除
				return -2;
			}
		}

		return 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int NoUseUserinfo(Userinfo item) {
		int result = 0;
		try {
			result = CheckOrderAndLocker(item);
			if (result == 0) {
				// item.setDataState(0);
				result = itemMapper.updateByPrimaryKeySelective(item);

				// 插入锁匠类型日志表
				UserinfoOperateLog ulog = new UserinfoOperateLog();
				ulog.setId(UUIDUtil.getUUID());
				ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
				ulog.setUserInfoId(item.getId().toString());
				ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
				if (item.getDataState() == 0)
					ulog.setContent("锁匠废弃:" + item.getAuditReason());
				else if (item.getDataState() == 3)
					ulog.setContent("锁匠拉黑:" + item.getAuditReason());

				userinfoOperateLogMapper.insert(ulog);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Message ReUseUserinfo(Userinfo item) {
		Message msg = new Message();
		msg.setBol(true);
		int result = 0;

		Userinfo curu = selectUserinfoById(item.getId());
		if (curu.getUserType().equals("4") && curu.getCompanyId() != null) {

			// 合伙人下个人锁匠恢复，检查合伙人状态
			Userinfo curparter = selectUserinfoById(curu.getCompanyId());
			if (curparter == null || (curparter != null && curparter.getDataState() != 1)) {
				msg.setBol(false);
				msg.setMessage("所属合伙人账号已停用或删除，无法恢复！");
				return msg;
				// return -1;// 合伙人状态非正常.
			}

		} else if (curu.getUserType().equals("2") || curu.getUserType().equals("3")) {
			// 合伙人恢复，检查区域冲突.
			// 处理锁匠-作业区域
			// 添加
			String[] dids = curu.getDistrictids().split(",");

			boolean ischeck = true;
			for (int i = 0; i < dids.length; i++) {
				if (dids[i].trim().equals(""))
					continue;

				UserinfoJobArea jquery = new UserinfoJobArea();
				jquery.setDistrictId(Integer.parseInt(dids[i]));
				jquery.setLockId(item.getId());
				// 检查作业区域重复
				List<UserinfoJobArea> lockers = userinfoJobAreaMapper.selectParterList(jquery);
				if (lockers != null && lockers.size() > 0) {
					ischeck = false;
					msg.setBol(false);
					msg.setMessage(lockers.get(0).getDistrictName() + "区域下已存在合伙人[" + lockers.get(0).getParterName()
							+ "],此合伙人无法恢复");
					break;
				}
			}

			// 区域检查异常
			if (!ischeck) {
				return msg;
			}

		}

		item.setDataState(1);// 启用状态
		result = updateByPrimaryKeySelective(item);

		// 插入锁匠类型日志表
		UserinfoOperateLog ulog = new UserinfoOperateLog();
		ulog.setId(UUIDUtil.getUUID());
		ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
		ulog.setUserInfoId(item.getId().toString());
		ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
		ulog.setContent("锁匠恢复使用");
		userinfoOperateLogMapper.insert(ulog);

		return msg;
	}

	@Override

	public Userinfo selectUserinfoById(Long id) {
		Userinfo data = null;

		Userinfo query = new Userinfo();
		query.setId(id);

		List<Userinfo> datas = itemMapper.selectList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		/*
		 * if (data.getDistrictId() != 0) { Map m =
		 * areaService.selectAll(data.getDistrictId()); data.setCity((String)
		 * m.get("city")); data.setProvince((String) m.get("province"));
		 * data.setDistrict((String) m.get("district")); } else if (data.getAreaCode()
		 * != null && !data.getAreaCode().equals("")) {
		 * 
		 * List<AreaDistrict> areaDistrictList =
		 * areaMapper.getAreaDistrictCode(data.getAreaCode());
		 * 
		 * if (areaDistrictList != null && areaDistrictList.size() > 0) {
		 * 
		 * Map m = areaService.selectAll(areaDistrictList.get(0).getId());
		 * data.setCity((String) m.get("city")); data.setProvince((String)
		 * m.get("province")); data.setDistrict((String) m.get("district")); } else { //
		 * 模糊匹配 省市模糊，地区默认第一个 Map m =
		 * areaMapper.selectMostLikeArea(data.getAreaCode().substring(0, 4)); if (m !=
		 * null) { data.setCity((String) m.get("city")); data.setProvince((String)
		 * m.get("province")); data.setDistrict((String) m.get("district")); } }
		 * 
		 * }
		 */

		return data;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Message updateCompanyNull(Long companyId) {
		Message message = new Message();
		try {
			int result = itemMapper.updateCompanyNull(companyId);
			message.setBol(result == 1 ? true : false);
			message.setMessage("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			message.setBol(false);
			message.setMessage("更新失败");
		}
		return message;
	}

	@Override
	public List<Userinfo> selectUserinfoByIdCardAll(Userinfo tuserinfo) {
		return itemMapper.selectListByIdCardAll(tuserinfo);

	}

	@Override
	public int deleteTrueUserinfo(Userinfo item) {
		int result = 0;
		try {
			result = itemMapper.deleteByPrimaryKey(item.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	/**
	 * 锁匠审核
	 * 
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年5月14日
	 */
	@Transactional
	public int updateAuditState(Userinfo item) {

		Userinfo locker = selectUserinfoById(item.getId());
		if (locker.getCompanyId() == null) {
			// 自由锁匠的审核
			int rst = itemMapper.updateByPrimaryKeySelective(item);

			// 插入锁匠类型日志表
			UserinfoOperateLog ulog = new UserinfoOperateLog();
			ulog.setId(UUIDUtil.getUUID());
			ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
			ulog.setUserInfoId(locker.getId().toString());
			ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
			if (item.getAuditFlag().equals("1"))
				ulog.setContent("审核通过");
			else
				ulog.setContent("审核未通过:" + item.getAuditReason());

			userinfoOperateLogMapper.insert(ulog);

			return rst;
		} else {
			// 合伙人锁匠审核
			// 如果审核通过，则修改parterauditflag =1
			if (item.getAuditFlag().equals("1")) {
				item.setParterAuditFlag("1"); // auditFalg也通过了
				int rst = itemMapper.updateByPrimaryKeySelective(item);

				// 插入锁匠类型日志表
				UserinfoOperateLog ulog = new UserinfoOperateLog();
				ulog.setId(UUIDUtil.getUUID());
				ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
				ulog.setUserInfoId(locker.getId().toString());
				ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
				ulog.setContent("加入合伙人团队审核通过");

				userinfoOperateLogMapper.insert(ulog);

				return rst;
			} else {

				// 不通过， 只修改合伙人审核状态

				Userinfo u = new Userinfo();
				u.setId(item.getId());
				u.setParterAuditFlag("2");

				int rst = itemMapper.updateByPrimaryKeySelective(u);

				// 插入锁匠类型日志表
				UserinfoOperateLog ulog = new UserinfoOperateLog();
				ulog.setId(UUIDUtil.getUUID());
				ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
				ulog.setUserInfoId(locker.getId().toString());
				ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
				ulog.setContent("加入合伙人团队审核未通过");

				userinfoOperateLogMapper.insert(ulog);

				return rst;
				// int rst = itemMapper.updateLockerParterNull(item.getId());
				// 消息发送

				// return rst;

			}
		}
	}

	public int reDoAudit(Userinfo item) {

		Userinfo locker = selectUserinfoById(item.getId());
		if (locker.getAuditFlag() != null && locker.getAuditFlag().equals("2")) {
			// 普通状态未通过，修改为待审核
			item.setAuditFlag("0");
		}

		if (locker.getParterAuditFlag() != null && locker.getParterAuditFlag().equals("2")) {
			// 合伙人审核状态未通过，修改为待审核
			item.setParterAuditFlag("0");

		}

		// 插入锁匠类型日志表
		UserinfoOperateLog ulog = new UserinfoOperateLog();
		ulog.setId(UUIDUtil.getUUID());
		ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
		ulog.setUserInfoId(item.getId().toString());
		ulog.setOperateUserId(TokenUtil.getWebLoginUser().getId());
		ulog.setContent("重新提交审核");

		userinfoOperateLogMapper.insert(ulog);

		return itemMapper.updateByPrimaryKeySelective(item);
	}

	@Transactional
	public Message userTypeChange(UserinfoAudit item, int from) {

		Message msg = new Message();
		msg.setBol(true);

		Userinfo locker = selectUserinfoById(item.getUserInfoId());

		// 检查是否有待审的
		UserinfoAudit uquery = new UserinfoAudit();
		uquery.setUserInfoId(item.getUserInfoId());
		uquery.setAuditStates(0);// 待审核
		List<UserinfoAudit> tmps = userinfoAuditMapper.selectList(uquery);
		if (tmps != null && tmps.size() > 0) {
			msg.setBol(false);
			msg.setMessage("该锁匠已有待审批的类型变更!");
			return msg;
		}
		
		
		
		int checkState= CheckOrderAndLocker(locker);
		if(checkState<0)
		{
			msg.setBol(false);
			
		
			if(checkState==-1)
			{
				msg.setMessage("锁匠当前有未完成工单,暂时不能变更锁匠类型!");
			}
			else if(checkState==-2)
			{
				msg.setMessage("该合伙人下存在锁匠,暂不能变更锁匠类型!");	
			}
			
			return msg;
		}
		
		

		// jsonArray String
		String imgstr = item.getIcons();
		String filestr = item.getFiles();

		com.alibaba.fastjson.JSONArray imgs = JSON.parseArray(imgstr);
		com.alibaba.fastjson.JSONArray files = JSON.parseArray(filestr);

		String auditId = UUIDUtil.getUUID();

		if (imgs != null && imgs.size() > 0) {
			String imgids = "";
			for (int i = 0; i < imgs.size(); i++) {
				imgids += imgs.getJSONObject(i).get("id") + ",";
			}
			item.setIcons(imgids);
		}

		String curUserId = item.getProposer();
		Manager m = TokenUtil.getWebLoginUser();
		if (m != null)
			curUserId = TokenUtil.getWebLoginUser().getId();

		item.setUserOldType(Integer.parseInt(locker.getUserType()));
		item.setId(auditId);
		item.setProposer(curUserId);
		userinfoAuditMapper.insertSelective(item);

		if (files != null && files.size() > 0) {
			String fileids = "";
			for (int i = 0; i < files.size(); i++) {
				// imgids+=files.getJSONObject(i).get("id")+",";
				UserinfoAuditAttach ua = new UserinfoAuditAttach();
				ua.setId(UUIDUtil.getUUID());
				ua.setAudit_id(auditId);
				ua.setFile_id(files.getJSONObject(i).getString("id"));
				userinfoAuditAttachMapper.insert(ua);
			}
		}

		if (from == 2) { //app端才有消息，后台不发送消息
			if (item.getUserNewType() == 4) {
				String messageStr = "锁匠[" + locker.getName() + "]申请加入您的团队,请及时在锁匠管理中审核";
				// 插入消息
				UserMessage um = new UserMessage();
				um.setId(snowflakeIdWorker.nextId());
				um.setMessageContent(messageStr);
				um.setMessageType(1L);
				um.setReceiver(item.getCompanyId());

				um.setMessageTitle("锁匠申请");
				userMessageMapper.insert(um);
			}
		}

		// 插入锁匠类型日志表
		UserinfoOperateLog ulog = new UserinfoOperateLog();
		ulog.setId(UUIDUtil.getUUID());
		ulog.setUserInfoId(locker.getId().toString());
		ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
		ulog.setOperateUserId(curUserId);
		ulog.setContent(
				"锁匠类型变更申请：" + item.getProposReason() + "," + locker.getUserTypeName(item.getUserOldType().toString())
						+ " --> " + locker.getUserTypeName(item.getUserNewType().toString()));

		userinfoOperateLogMapper.insert(ulog);

		return msg;
	}

	/**
	 * 查询指定行政区域下是否已经存在非当前编辑合伙人的其他合伙人。 一个行政区县 district只能有一个合伙人
	 * 
	 * @param tuserinfo
	 * @return
	 * @author zj
	 * @date 2019年5月14日
	 */
	@Override
	public List<Userinfo> selectJobAreaByUserAndDistrictIds(Userinfo tuserinfo) {
		return itemMapper.selectJobAreaByUserAndDistrictIds(tuserinfo);
	}

	/**
	 * 根据手机号和姓名模糊搜索合伙人代理人
	 *
	 * @param searchValue
	 * @return
	 */
	@Override
	public List<Userinfo> selectPartnerLockerLikeNameAndPhone(String searchValue) {
		return itemMapper.selectPartnerLockerLikeNameAndPhone(searchValue);
	}

}
