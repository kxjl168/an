/**
 * @(#)OrderInfoOperateServiceImpl.java 2019/4/19 14:52
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.excerption.BusinessException;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.base.util.sendpost.HttpSendGet;
import com.kxjl.base.util.sendpost.HttpSendPost;
import com.kxjl.base.util.sendpost.SendPostResponse;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.annotation.OrderNeedLog;
import com.kxjl.tasktransferplat.dao.plus.*;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.ParamUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 单肙
 * @version 1.0.0 2019/4/19 14:52
 * @since 1.0.0
 */
@Service
public class OrderInfoOperateServiceImpl implements OrderInfoOperateService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisLockService redisLockService;

	@Autowired
	private OrderinfoMapper orderinfoMapper;
	
	@Autowired
	LockProductInfoService lockProductInfoService;

	@Autowired
	LockCompanyService lockCompanyService;

	@Autowired
	private OrderinfoAuditMapper orderinfoAuditMapper;

	@Autowired
	private OrderInfoAuditSimpleMapper orderInfoAuditSimpleMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private UserMessageMapper userMessageMapper;

	@Autowired
	private AreaService areaService;

	@Autowired
	private OrderinfoAuditService orderinfoAuditService;

	@Autowired
	private PriceLocksmithOtherService priceLocksmithOtherService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PriceLockCompanyService priceLockCompanyService;

	@Autowired
	private OrderinfoAttachMoneyDetailMapper orderinfoAttachMoneyDetailMapper;

	@Autowired
	private PriceLockSmithBuildService priceLockSmithBuildService;

	@Autowired
	private OrderinfoOperateLogMapper orderinfoOperateLogMapper;

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private ManagerMessageService managerMessageService;

	@Autowired
	private OrderLockInfoService orderLockInfoService;

	@Autowired
	OrderinfoAuditMapper orderInfoAuditMapper;
	/**
	 * 1、锁企创建工单，工单状态3
	 *
	 * @param operateUserId
	 * @param requestDto
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@OrderNeedLog(type = 0, subType = 1)
	public void lockCompanyCreateOrder(String operateUserId, BaseRequestDto requestDto) {
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(requestDto.getData());

		// 插入工单
		Orderinfo orderinfo = JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class);
		orderinfo.setOrderState(3);
		orderinfoMapper.insert(orderinfo);

		try {
			List<Map<String, String>> managerIdList = managerMessageService.selectManagerMessageInsertUserList(6 + "");
			for (Map<String, String> map : managerIdList) {
				String managerId = map.get("managerId");
				ManagerMessage managerMessage = new ManagerMessage();
				managerMessage.setUserId(managerId);
				managerMessage.setType(6 + "");
				managerMessage.setTitle("待确认");
				managerMessage.setContent("锁企创建工单，等待源匠确认");
				managerMessage.setIsRead(0);
				managerMessage.setId(UUIDUtil.getUUID());
				managerMessageService.insert(managerMessage);
				List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
				MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("websocket发送消息报错");
		}
	}

	/**
	 * 锁企更新-单纯更新数据，不记录日志
	 *
	 * @param operateUserId
	 * @param requestDto
	 */
	@Override
	public void lockCompanyUpdate(String operateUserId, BaseRequestDto requestDto) {
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(requestDto.getData());
		Orderinfo orderinfo = JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class);
		orderinfoMapper.updateById(orderinfo);
	}

	/**
	 * 1、平台创建工单，工单状态101
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @author zj
	 * @date 2019年5月17日
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@OrderNeedLog(type = 1)
	public void platCreateOrder(String operateUserId, BaseRequestDto requestDto) {

		// 客服建立工单，也一样处理，需要确认
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(requestDto.getData());

		// 插入工单
		Orderinfo orderinfo = JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class);
		orderinfo.setOrderState(3);
		orderinfoMapper.insert(orderinfo);

		return;

		// platCreateOrCreateOrder(operateUserId, requestDto, true);
	}

	/**
	 * 平台更新-确认工单信息
	 *
	 * @param operateUserId
	 * @param requestDto
	 */
	@Override
	@OrderNeedLog(type = 1, subType = 5)
	public void platUpdate(String operateUserId, BaseRequestDto requestDto) {

		platCreateOrCreateOrder(operateUserId, requestDto, false);

		/*
		 * Map<String, String> paramMap = (Map<String, String>)
		 * JSON.parse(requestDto.getData()); Orderinfo orderinfo =
		 * JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class); // 更新工单状态
		 * Orderinfo selectOrder = orderinfoMapper.selectById(orderinfo.getId());
		 * orderinfo.setOrderState(101); Company company =
		 * companyService.selectByAreaCode(selectOrder.getAreaCode()); if (company !=
		 * null) { orderinfo.setCompanyId(company.getId()); }
		 * orderinfoMapper.updateById(orderinfo);
		 */

	}

	/**
	 * 平台新建或者 确认工单
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @author zj
	 * @date 2019年5月20日
	 */
	private void platCreateOrCreateOrder(String operateUserId, BaseRequestDto requestDto, boolean create) {
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(requestDto.getData());

		// 插入工单
		Orderinfo orderinfo = JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class);
		orderinfo.setOrderState(101);

		// 寻找工单所属行政区域下的合伙人及锁匠
		// 使用作业区域寻找

		// 优先寻找合伙人
		Userinfo uquery = new Userinfo();
		uquery.setUserType("2");// 合伙人
		uquery.setAuditFlag("1");// 审核通过
		uquery.setDataState(1);// 正常数据状态

		AreaDistrict adistrict = areaService.getAreaDistrictByCode(orderinfo.getAreaCode());

		uquery.setDistrictId2(String.valueOf(adistrict.getId()));// 作业区域

		List<Userinfo> parters = userinfoService.selectUserinfoList(uquery);
		if (parters != null && parters.size() > 0) {
			// 自动分配也算接单时间
			orderinfo.setReceiveTime(DateUtil.nowTimestampString());
			orderinfo.setCompanyId(parters.get(0).getId());

			// 找到合伙人，则直接分给合伙人，即锁匠也是他
			orderinfo.setOrderState(201);
			orderinfo.setLockerId(parters.get(0).getId());

			// Userinfo
			// Locker=userinfoService.selectUserinfoById(orderinfoAudit.getLockerId());
			String msg = orderinfo.getOrderNo();
			// todo 3、推送
			AliSendSMS.sendMessageNoraml(parters.get(0).getPhone(), msg);

			String messageStr = "您有新的自动分配工单，工单号为" + orderinfo.getOrderNo() + "，请及时查看！";
			// 插入消息
			UserMessage um = new UserMessage();
			um.setId(snowflakeIdWorker.nextId());
			um.setMessageContent(messageStr);
			um.setMessageType(2L);
			um.setReceiver(parters.get(0).getId());
			um.setOrderInfoId(orderinfo.getId());
			um.setMessageTitle("自动分配工单");
			userMessageMapper.insert(um);

		} else {
			try {
				List<Map<String, String>> managerIdList = managerMessageService
						.selectManagerMessageInsertUserList(1 + "");
				for (Map<String, String> map : managerIdList) {
					String managerId = map.get("managerId");
					ManagerMessage managerMessage = new ManagerMessage();
					managerMessage.setUserId(managerId);
					managerMessage.setType(1 + "");
					managerMessage.setTitle("待接单");
					managerMessage.setContent("等待锁匠接单");
					managerMessage.setIsRead(0);
					managerMessage.setId(UUIDUtil.getUUID());
					managerMessageService.insert(managerMessage);
					List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
					MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("websocket发送消息报错");
			}
		}

		// 废弃 zj 091506
		/*
		 * Company company = companyService.selectByAreaCode(orderinfo.getAreaCode());
		 * if (company != null) { orderinfo.setCompanyId(company.getId()); }
		 */

		// 190624 zj 核销码生成
		// 生成验证码
		Random random = new Random();
		Integer validateCode = random.nextInt(899999);
		validateCode = validateCode + 100000;
		orderinfo.setOrderComplateCode(validateCode.toString());

		int rst = -1;
		if (create) {
			rst = orderinfoMapper.insert(orderinfo);
		} else {
			rst = orderinfoMapper.updateById(orderinfo);
		}

		if (rst > 0) {
			SendCompleteCodeSms(orderinfo.getOrderNo());
		}

	}

	public void SendCompleteCodeSms(String orderNo) {

		Orderinfo orderinfo = orderinfoMapper.selectByOrderNo(orderNo);

		// 工单确认即给客户发送核销码短信
		final String phone = orderinfo.getCustomerPhone();

		// LockCompany company=
		// lockCompanyService.selectCompanyById(orderinfo.getCompanyId());

		final String code = orderinfo.getOrderComplateCode();// validateCode.toString();
		// 短信
		new Runnable() {
			public void run() {
				boolean isOk = AliSendSMS.sendOrderComplateCodeSms(phone, "", code);
				if (isOk) {
					Orderinfo oinfo = new Orderinfo();
					oinfo.setId(orderinfo.getId());
					oinfo.setOrderComplateCodeSend("1");// 已发送
					orderinfoMapper.updateByPrimaryKeySelective(oinfo);
				}
			}
		}.run();
	}

	private String orderTypeName(String orderType) {

		// 安装 = 0,维修 = 1,开锁 = 2,特殊们改造 = 3,测量 = 4, 猫眼安装 = 5, 换锁=6,工程安装=7,工程维修=8，其他=9',
		String name = "";
		switch (orderType) {
		case "0":
			name = "安装";// 基础价格");
			break;
		case "1":
			name = "维修";// typeMoneyDetail.setReasonDes("维修基础价格");
			break;
		case "2":
			name = "开锁";// typeMoneyDetail.setReasonDes("开锁基础价格");
			break;
		case "3":
			name = "改造";// typeMoneyDetail.setReasonDes("改造基础价格");
			break;
		case "4":
			name = "测量";// typeMoneyDetail.setReasonDes("测量基础价格");
			break;
		case "5":
			name = "猫眼安装";// typeMoneyDetail.setReasonDes("猫眼安装基础价格");
			break;
		case "6":
			name = "换锁";// typeMoneyDetail.setReasonDes("猫眼安装基础价格");
			break;
		case "7":
			name = "工程安装";// typeMoneyDetail.setReasonDes("猫眼安装基础价格");
			break;

		case "8":
			name = "工程维修";// typeMoneyDetail.setReasonDes("猫眼安装基础价格");
			break;
		case "19":
			name = "其他";// typeMoneyDetail.setReasonDes("猫眼安装基础价格");
			break;
		default:
			break;
		}
		return name;
	}

	/**
	 * 计算工单的价格详细，包括锁匠及锁企的工单价格变动明细。
	 *
	 * @param orderinfo
	 * @return
	 * @author zj
	 * @date 2019年5月16日
	 */
	@Override
	@Transactional
	public List<OrderinfoAttachMoneyDetail> calculatePriceNew(Orderinfo orderinfo) {

		// 如果服务类型为安装，那么根据地市决定锁匠费用
		// serverType 为逗号分隔的多项工单类型，兼容多类型 zj 1901516
		String serverType = orderinfo.getServerType();
		long sellerId = orderinfo.getSellerId();

		List<OrderinfoAttachMoneyDetail> rst = new ArrayList<>();

		String[] stypes = serverType.split(",");
		for (String tp : stypes) {
			tp = tp.trim();
			// 针对每种类型，计算各自的价格
			if (!tp.equals("")) {

				OrderinfoAttachMoneyDetail typeMoneyDetail = new OrderinfoAttachMoneyDetail();

				// 锁企费用
				PriceLockCompany priceLockCompany = priceLockCompanyService.selectByTypeAndId(sellerId, tp, 1);

				if (priceLockCompany == null) {
					throw new BusinessException("锁企" + orderTypeName(tp) + "业务费用未配置!");
				}
				typeMoneyDetail.setChangeValueSeller(
						priceLockCompany.getPrice().multiply(new BigDecimal(orderinfo.getLockNum())));

				// 计算锁匠费用
				if (tp.equals("0")) {

					// 安装 根据地区确定

					String areaCode = orderinfo.getAreaCode();
					String provinceCode = areaCode.substring(0, 2);
					String cityCode = areaCode.substring(2, 4);
					PriceLockSmithBuild priceLockSmithBuild = priceLockSmithBuildService.selectByCityCode(cityCode,
							provinceCode);

					// 区域费用未配置，使用默认配置
					if (priceLockSmithBuild == null) {
						priceLockSmithBuild = priceLockSmithBuildService.selectByCityCode("00", "00");
					}
					// zj 190712 *锁数量
					typeMoneyDetail.setChangeValueLocker(
							priceLockSmithBuild.getMoney().multiply(new BigDecimal(orderinfo.getLockNum())));
				} else {
					// 其他费用

					// 平台锁匠服务计费规则
					PriceLocksmithOther priceLocksmithOther = priceLocksmithOtherService.selectRule(tp, 1);
					if (priceLocksmithOther == null) {
						throw new BusinessException("锁匠" + orderTypeName(tp) + "费用未配置!");
					}

					typeMoneyDetail.setChangeValueLocker(
							priceLocksmithOther.getPrice().multiply(new BigDecimal(orderinfo.getLockNum())));

				}

				typeMoneyDetail.setReasonType("1" + tp);
				typeMoneyDetail.setReasonDes(orderTypeName(tp) + "基础费用");

				rst.add(typeMoneyDetail);

			}
		}

		// 加急
		if (orderinfo.getUrgent().equals("1")) {

			OrderinfoAttachMoneyDetail typeMoneyDetail = new OrderinfoAttachMoneyDetail();

			// 锁匠加急费
			PriceLocksmithOther lockerUrgentPrice = priceLocksmithOtherService.selectRule("5", 2);
			if (lockerUrgentPrice == null) {
				throw new BusinessException("锁匠加急费用未配置");
			}

			// 锁企加急费
			PriceLockCompany companyUrgentPrice = priceLockCompanyService.selectByTypeAndId(sellerId, "5", 2);
			if (companyUrgentPrice == null) {
				throw new BusinessException("锁企加急费用未配置");
			}

			typeMoneyDetail.setChangeValueLocker(
					lockerUrgentPrice.getPrice().multiply(new BigDecimal(orderinfo.getLockNum())));
			typeMoneyDetail.setChangeValueSeller(
					companyUrgentPrice.getPrice().multiply(new BigDecimal(orderinfo.getLockNum())));

			typeMoneyDetail.setReasonType("25");
			typeMoneyDetail.setReasonDes("工单加急");

			rst.add(typeMoneyDetail);
		}

		return rst;

	}

	/**
	 * 根据服务类型获得锁匠和锁企价格
	 *
	 * @param orderinfo
	 * @return
	 */
	@Override
	@Deprecated
	public Map<String, BigDecimal> calculatePrice(Orderinfo orderinfo) {
		Map<String, BigDecimal> resultMap = new HashMap<>(16);
		BigDecimal lockerBasePrice;
		BigDecimal lockerTotalPrice;
		BigDecimal sellerBasePrice;
		BigDecimal sellerTotalPrice;
		// 如果服务类型为安装，那么根据地市决定锁匠费用
		String serverType = orderinfo.getServerType();
		long sellerId = orderinfo.getSellerId();
		PriceLockCompany priceLockCompany = priceLockCompanyService.selectByTypeAndId(sellerId, serverType, 1);
		if (priceLockCompany == null) {
			throw new BusinessException("所选锁企暂无此业务！");
		}
		sellerBasePrice = priceLockCompany.getPrice();
		if (serverType.equals("0,")) {
			// 计算锁匠安装费用
			String areaCode = orderinfo.getAreaCode();
			String provinceCode = areaCode.substring(0, 2);
			String cityCode = areaCode.substring(2, 4);
			PriceLockSmithBuild priceLockSmithBuild = priceLockSmithBuildService.selectByCityCode(cityCode,
					provinceCode);
			if (priceLockSmithBuild == null) {
				throw new BusinessException("所选地区暂不支持安装服务");
			}
			// 锁匠安装费用
			lockerBasePrice = priceLockSmithBuild.getMoney();
		}
		// 如果为其他服务类型，那么根据priceLocksmith决定
		else {
			// 平台锁匠服务计费规则
			PriceLocksmithOther priceLocksmithOther = priceLocksmithOtherService.selectRule(serverType, 1);
			if (priceLocksmithOther == null) {
				throw new BusinessException("平台暂无此业务");
			}
			lockerBasePrice = priceLocksmithOther.getPrice();
		}

		// 如果需要加急，总价增加加急费。
		String urgent = orderinfo.getUrgent();
		if (urgent != null && urgent.equals("1")) {
			// 锁匠加急费
			PriceLocksmithOther lockerUrgentPrice = priceLocksmithOtherService.selectRule("5", 2);
			if (lockerUrgentPrice == null) {
				throw new BusinessException("平台暂无加急业务，无法加急");
			}
			BigDecimal lockerAdd = lockerUrgentPrice.getPrice();
			lockerTotalPrice = lockerAdd.add(lockerBasePrice);

			// 锁企加急费
			PriceLockCompany companyUrgentPrice = priceLockCompanyService.selectByTypeAndId(sellerId, "5", 2);
			if (companyUrgentPrice == null) {
				throw new BusinessException("锁企暂无加急业务，无法加急");
			}
			BigDecimal companyAdd = companyUrgentPrice.getPrice();
			sellerTotalPrice = companyAdd.add(sellerBasePrice);
		} else {
			lockerTotalPrice = lockerBasePrice;
			sellerTotalPrice = sellerBasePrice;
		}
		resultMap.put("lockerBasePrice", lockerBasePrice);
		resultMap.put("sellerBasePrice", sellerBasePrice);
		resultMap.put("lockerTotalPrice", lockerTotalPrice);
		resultMap.put("sellerTotalPrice", sellerTotalPrice);
		return resultMap;
	}

	/**
	 * <p>
	 * 3、分配工单， /待作业，待接单可用
	 * </p>
	 * <p>
	 * 后台重新分配工单， 选择合伙人-101->101
	 * </p>
	 * <p>
	 * 后台重新分配工单， 选择自由锁匠-101->201
	 * </p>
	 *
	 * @param operateUserId
	 * @param baseRequestDto
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@Override
	// @OrderNeedLog(type = 1, subType = 1)
	@Transactional(rollbackFor = Exception.class)
	public Integer allocateOrder(String operateUserId, BaseRequestDto baseRequestDto) {
		// 处理json参数
		String orderRequest = baseRequestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
		String orderId = String.valueOf(paramMap.get("orderId"));
		String addMoney = String.valueOf(paramMap.get("addMoney"));
		String addMoneyDesc = String.valueOf(paramMap.get("addMoneyDesc"));

		String from = String.valueOf(paramMap.get("from"));// 1：后台分配 ，2：小程序合伙人分配

		Long lockerId = Long.valueOf(String.valueOf(paramMap.get("lockerId")));
		String useId = String.valueOf(String.valueOf(paramMap.get("useId")));
		String version = String.valueOf(redisLockService.getVersion());
		try {
			// 加锁
			boolean lockResult = redisLockService.blockLock("acceptOrderLock_" + orderId, version);
			if (lockResult) {
				// 查看该工单是否已经被抢走或已被分配
				Orderinfo orderinfo = orderinfoMapper.selectById(orderId);
				if (orderinfo == null || orderinfo.getDataState() != 1) {
					throw new RuntimeException("该工单已经弃用或者删除！");
				}

				if (from.equals("2")) {

					// app操作判断
					if (checkOrder(orderId, operateUserId) < 0)
						throw new RuntimeException("该工单已经被修改,请稍后重试！");// return -1; //
																		// AppResultUtil.fail("工单状态异常!工单已被修改或者取消");

				}

				/*
				 * if (orderinfo.getLockerId() != null && orderinfo.getOrderState() > 199) { //
				 * 该工单已经被抢走 return 0; }
				 */
				Integer subType = 1;
				Integer type = 1;// 1客服，2锁匠
				Userinfo u = userinfoService.selectUserinfoById(lockerId);

				// 后台分配
				if (from.equals("1")) {
					if ((orderinfo.getOrderState() > 101 && orderinfo.getOrderState() < 199)
							|| orderinfo.getOrderState() < 210) {
						// 只能重新分配待接单/ 待作业并且未确认开始的
						if (orderinfo.getOrderState() < 200) {
							subType = 1;// 带接单 分配工单
						} else {
							subType = 8;// 带作业 重新 分配工单
						}
					} else {
						// 其他状态不能分配
						return 0;
					}
					// 是否加价
					if (addMoney != null && !addMoney.equals("")) {
						OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail = new OrderinfoAttachMoneyDetail();
						orderinfoAttachMoneyDetail.setReasonDes("客服加价:" + addMoneyDesc);
						orderinfoAttachMoneyDetail.setReasonType("19");

						orderinfoAttachMoneyDetail.setChangeValueLocker(new BigDecimal(addMoney));

						orderinfoAttachMoneyDetail.setId(snowflakeIdWorker.nextId());
						orderinfoAttachMoneyDetail.setOrderinfoId(orderId);
						orderinfoAttachMoneyDetail.setOperateUserId(useId);

						orderinfoAttachMoneyDetailMapper.insert(orderinfoAttachMoneyDetail);

						// 更新工单锁匠总价
						OrderinfoAttachMoneyDetail totalData = orderinfoAttachMoneyDetailMapper
								.GetOrderTotalPrice(orderinfoAttachMoneyDetail);

						//
						orderinfo.setLockerTotalPrice(totalData.getChangeValueLocker());
					}

					String messageStr = "您有一个新的待作业工单，工单号为" + orderinfo.getOrderNo() + "，请及时查看！";
					// 插入消息
					UserMessage um = new UserMessage();
					um.setId(snowflakeIdWorker.nextId());
					um.setMessageContent(messageStr);
					um.setMessageType(2L);
					um.setReceiver(u.getId());
					um.setOrderInfoId(orderinfo.getId());
					um.setMessageTitle("待作业工单");
					userMessageMapper.insert(um);

				} else {
					type = 2;
					subType = 5;// 带作业 重新 分配工单 合伙人分配
					// 发送小程序转发订单模板消息

					String messageStr = "您好，" + TokenUtil.getCurrentUser().getName() + "给您转发了一个新的工单，工单号为"
							+ orderinfo.getOrderNo() + "，请及时查看！";
					// 插入消息
					UserMessage um = new UserMessage();
					um.setId(snowflakeIdWorker.nextId());
					um.setMessageContent(messageStr);
					um.setMessageType(2L);
					um.setReceiver(u.getId());
					um.setOrderInfoId(orderinfo.getId());
					um.setMessageTitle("转发工单");
					userMessageMapper.insert(um);

					// AliSendSMS.sendMessageSms(u.getPhone(),"您好，"+TokenUtil.getCurrentUser().getName()+"给您转发了一个新的工单，工单号为"+orderinfo.getOrderNo()+"，请及时查看！");
				}

				// zj 190517 后台分配待接单工单，
				// 如果指定的为合伙人，变为201，合伙人，锁匠都是合伙人
				// 如果指定的为锁匠，则直接分配，201

				orderinfo.setOrderType("2");
				orderinfo.setReceiveTime(DateUtil.nowTimestampString());

				// 重新分配后，超时状态重置
				orderinfo.setTimeout("0");

				if (u.getUserType().equals("1") || u.getUserType().equals("5") || u.getUserType().equals("6")) {

					// 自由锁匠

					orderinfo.setLockerId(lockerId);
					orderinfo.setOrderState(201);

					orderinfoMapper.updateById(orderinfo);

					// 后台分配，直接指定自己锁匠
					if (from.equals("1")) {
						// 清除合伙人信息
						orderinfoMapper.updateOrderParterNull(orderId);
					}
					// 小程序分配，给合伙人下的锁匠，不清空合伙人
				} else {
					// 合伙人

					if (u.getUserType().equals("4"))// app合伙人分配给名下锁匠
					{
						// orderinfo.setCompanyId(Long.parseLong(useId)); // 重新指定合伙人
						orderinfo.setLockerId(lockerId);// 锁匠
						orderinfo.setOrderState(201);
					} else // 后台分配给合伙人
					{
						orderinfo.setCompanyId(lockerId); // 重新指定合伙人
						orderinfo.setLockerId(lockerId);// 锁匠也是他
						orderinfo.setOrderState(201);
					}

					orderinfoMapper.updateById(orderinfo);

					// 清除锁匠信息
					// orderinfoMapper.updateOrderLockerNull(orderId);
				}

				// 插入工单日志表
				OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
				orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
				orderinfoOperateLog.setOrderinfoId(orderId);
				orderinfoOperateLog.setType(type);
				orderinfoOperateLog.setSubType(subType);
				orderinfoOperateLog.setOperateUserId(operateUserId);

				orderinfoOperateLogMapper.insert(orderinfoOperateLog);

				// sms通知，消息

				Userinfo Locker = u;
				String msg = orderinfo.getOrderNo();
				// todo 3、推送
				AliSendSMS.sendMessageNoraml(Locker.getPhone(), msg);

			}
		} catch (Exception e) {
			throw new RuntimeException("异常工单id" + orderId, e);
		} finally {
			// 解锁
			redisLockService.blockUnlock("acceptOrderLock_" + orderId, version);
		}
		return 1;
	}

	/**
	 * 3、锁匠接单接口，状态101——>201
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @return
	 */
	@Override
	@OrderNeedLog(type = 2, subType = 1)
	@Transactional(rollbackFor = Exception.class)
	public Integer orderAccept(String operateUserId, BaseRequestDto requestDto) {
		// 处理json参数
		String orderRequest = requestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

		String orderId = String.valueOf(paramMap.get("orderId"));
		String version = String.valueOf(redisLockService.getVersion());
		try {
			// 加锁
			boolean lockResult = redisLockService.blockLock("acceptOrderLock_" + orderId, version);
			if (lockResult) {
				// 查看该工单是否已经被抢走或已被分配
				Orderinfo orderinfo = orderinfoMapper.selectById(orderId);
				if (orderinfo == null) {
					throw new RuntimeException("该工单已经被删除！");
				}
				if (orderinfo.getLockerId() != null && orderinfo.getOrderState() > 199) {
					// 该工单已经被抢走
					return 0;
				}
				orderinfo.setOrderState(201);
				// 自动抢单
				orderinfo.setOrderType("1");
				orderinfo.setLockerId(Long.valueOf(operateUserId));
				orderinfo.setReceiveTime(new Timestamp(System.currentTimeMillis()).toString());
				orderinfoMapper.updateById(orderinfo);
			}
		} catch (Exception e) {
			throw new RuntimeException("异常工单id" + orderId, e);
		} finally {
			// 解锁
			redisLockService.blockUnlock("acceptOrderLock_" + orderId, version);
		}
		return 1;
	}

	/**
	 * app端操作时判断工单状态，当前是否正常，是否为当前锁匠所有
	 * 
	 * @param orderId
	 * @param operateUserId
	 * @return
	 * @author zj
	 * @date 2019年6月27日
	 */
	private int checkOrder(String orderId, String operateUserId) {

		int rst = 1;
		// 查询
		Orderinfo orderinfo = orderinfoMapper.selectById(orderId);
		if (orderinfo == null || orderinfo.getDataState() != 1) {
			rst = -2;
			// throw new RuntimeException("无法通过id查询出相应的工单！orderId:" + orderId);
		}

		// 取消状态判断
		if (orderinfo.getLockerId() == null || !orderinfo.getLockerId().toString().equals(operateUserId)) // 工单状态已被处理
		{
			rst = -1;// AppResultUtil.fail("工单状态异常!工单已被修改或者取消");
		}
		log.info("rst:" + rst);

		return rst;
	}

	/**
	 * 工单申请加钱接口，状态201——>1
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @return
	 */
	@Override
	// @OrderNeedLog(type = 2, subType = 2)
	@Transactional(rollbackFor = Exception.class)
	public Integer orderAddPrice(String operateUserId, BaseRequestDto requestDto) {

		// 处理json参数
		String orderRequest = requestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

		// 检查入参是否为空
		ParamUtil.checkArgsNull(paramMap);
		// 工单id
		String orderId = String.valueOf(paramMap.get("orderId"));
		String icons = paramMap.get("img");

		String version = String.valueOf(redisLockService.getVersion());
		try {
			boolean lockResult = redisLockService.blockLock("auditProposLock_" + orderId, version);
			if (lockResult) {

				// 查询
				Orderinfo orderinfo = orderinfoMapper.selectById(orderId);
				if (checkOrder(orderId, operateUserId) < 0)
					return -1;

				// 查询当前锁匠是否存在尚未处理的本次申请类型的审核。
				OrderinfoAudit orderinfoAudit = orderinfoAuditService.selectUndoneAudit(orderinfo, 1);
				if (orderinfoAudit != null) {
					// 如果存在，提醒用户
					return 0;
				}

				// 如果不存在，插入审核对象，封装公共参数
				orderinfoAudit = new OrderinfoAudit();
				orderinfoAudit.setId(snowflakeIdWorker.nextId());
				orderinfoAudit.setOrderInfoId(orderId);
				orderinfoAudit.setProposer(String.valueOf(orderinfo.getLockerId()));
				orderinfoAudit.setAuditStates(0);

				orderinfoAudit.setIcons(icons);
				String description = paramMap.get("description");
				// 加钱理由类型，1：空跑，2：远途，3：改装，4：特殊门，5：加急，6：其他
				Integer type = Integer.valueOf(String.valueOf(paramMap.get("type")));
				// 加钱金额
				PriceLocksmithOther price = priceLocksmithOtherService.selectRule(String.valueOf(type), 2);
				if (price == null) {
					throw new BusinessException("平台暂无此加钱类型");
				}
				// 锁匠用户信息，如果有合伙人则优先向合伙人申请，如果没有则直接向源匠申请
				Userinfo userinfo = userinfoService.selectUserinfoById(orderinfo.getLockerId());
				if (userinfo.getCompanyId() == null) {
					orderinfoAudit.setProposType(1);
					orderinfoAudit.setAuditStates(0);
					orderinfo.setOrderState(1);

					try {
						List<Map<String, String>> managerIdList = managerMessageService
								.selectManagerMessageInsertUserList(3 + "");
						for (Map<String, String> map : managerIdList) {
							String managerId = map.get("managerId");
							ManagerMessage managerMessage = new ManagerMessage();
							managerMessage.setUserId(managerId);
							managerMessage.setType(3 + "");
							managerMessage.setTitle("加钱审核");
							managerMessage.setContent("等待客服审核");
							managerMessage.setIsRead(0);
							managerMessage.setId(UUIDUtil.getUUID());
							managerMessageService.insert(managerMessage);
							List<Map<String, String>> list = managerMessageService
									.selectUnReadMsgCountByUser(managerMessage);
							MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
						}
					} catch (IOException e) {
						e.printStackTrace();
						log.error("websocket发送消息报错");
					}

				} else {
					orderinfoAudit.setProposType(4);
					orderinfoAudit.setAuditStates(6);
					orderinfo.setOrderState(6);

					Userinfo parter = userinfoService.selectUserinfoById(userinfo.getCompanyId());
					String msg = orderinfo.getOrderNo();
					// todo 3、推送
					AliSendSMS.sendMessageNoraml(parter.getPhone(), msg);

					String messageStr = "您好，您旗下的锁匠" + TokenUtil.getCurrentUser().getName() + "申请工单加钱,工单号为"
							+ orderinfo.getOrderNo() + "，请及时审批！";
					// 插入消息
					UserMessage um = new UserMessage();
					um.setId(snowflakeIdWorker.nextId());
					um.setMessageContent(messageStr);
					um.setMessageType(2L);
					um.setReceiver(parter.getId());
					um.setOrderInfoId(orderinfo.getId());
					um.setMessageTitle("工单加钱申请");
					userMessageMapper.insert(um);
				}

				// 封装加钱参数

				orderinfoAudit.setSubType(type);
				orderinfoAudit.setProposMoney(price.getPrice());
				orderinfoAudit.setProposReason(description);
				// 更新工单状态为审核中

				// 插入审核记录
				orderinfoAuditMapper.insert(orderinfoAudit);
				// 更新工单状态
				orderinfoMapper.updateById(orderinfo);

				// 插入工单日志表
				OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
				orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
				orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
				orderinfoOperateLog.setType(2);
				orderinfoOperateLog.setSubType(2);
				orderinfoOperateLog.setOperateUserId(operateUserId);
				orderinfoOperateLog.setContent(description);
				orderinfoOperateLog.setAuditId(orderinfoAudit.getId());

				orderinfoOperateLogMapper.insert(orderinfoOperateLog);

			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new RuntimeException("异常工单id" + orderId, e);
		} finally {
			redisLockService.blockUnlock("auditProposLock_" + orderId, version);
		}
		return 1;
	}

	/**
	 * 确认工单完成接口，状态201——>301
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @return
	 */
	@Override
	// @OrderNeedLog(type = 2, subType = 4)
	@Transactional(rollbackFor = Exception.class)
	public AppResult orderDone(String operateUserId, BaseRequestDto requestDto) {
		AppResult appResult = AppResultUtil.success("提交成功");

		// 处理json参数
		String orderRequest = requestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
		String orderId = String.valueOf(paramMap.get("orderId"));
		String finishRemark = String.valueOf(paramMap.get("des"));
		String icons = paramMap.get("img");
		String vertifyCode = String.valueOf(paramMap.get("vertifyCode"));
		// String doneTime = String.valueOf(paramMap.get("doneTime"));

		Orderinfo tmpOrderinfo = orderinfoMapper.selectByPrimaryKey(orderId);
		if (tmpOrderinfo != null && !tmpOrderinfo.getOrderComplateCode().equals(vertifyCode)) {
			appResult = AppResultUtil.fail("验证码校验失败");
			return appResult;
		}
		
		LockProductInfo lockInfo=lockProductInfoService.selectLockProductInfoById(tmpOrderinfo.getProductId());
		if(lockInfo==null)
		{
			appResult = AppResultUtil.fail("工单数据异常，缺失锁信息，无法完成!");
			return appResult;
		}

		boolean isimgOk = true;
		boolean isImeiOk = true;
		int errorindex=0;
		for (int i = 0; i < tmpOrderinfo.getLockNum(); i++) {
			
			errorindex=i;
			
			OrderLockInfo info = orderLockInfoService.loadOrderLockInfo(tmpOrderinfo.getOrderNo(),
					String.valueOf(i + 1));
			if (info == null) // String imgType//1前，2中，3后
			{
				isimgOk = false;
				break;
			}

			if (info.getStartimgs()!=null&&info.getStartimgs().split(",").length != 2) {
				isimgOk = false;
				break;
			}
			if (info.getLockimgs()!=null&&info.getLockimgs().split(",").length != 2) {
				isimgOk = false;
				break;
			}
			if (info.getDoneimgs()!=null&&info.getDoneimgs().split(",").length != 2) {
				isimgOk = false;
				break;
			}
			
			
			
			//安装/工程安装需要imei
			if(lockInfo.getImeiNeed().equals("1")&& (tmpOrderinfo.getServerType().contains("0")||tmpOrderinfo.getServerType().contains("7")))
			{
				//需要imei，
				if(info.getImeiNum()==null||info.getImeiNum().equals(""))
				{
					isImeiOk=false;
					break;
				}
			}
			
			
		}

		if (!isimgOk) {
			appResult = AppResultUtil.fail("请先上传第"+ (errorindex+1)+"把锁的完成图片");
			return appResult;
		}
		
		if (!isImeiOk) {
			appResult = AppResultUtil.fail("请先上传第"+ (errorindex+1)+"把锁的IMEI号");
			return appResult;
		}

		String version = String.valueOf(redisLockService.getVersion());

		// 更新数据库
		Orderinfo orderinfo = new Orderinfo();
		try {

			// 加锁
			boolean lockResult = redisLockService.blockLock("acceptOrderLock_" + orderId, version);
			if (lockResult) {

				// 查询
				orderinfo = orderinfoMapper.selectById(orderId);
				if (checkOrder(orderId, operateUserId) < 0)
					return AppResultUtil.fail("工单状态异常!工单已被修改或者取消"); // -1;

				// 取消状态判断
				if (!(orderinfo.getOrderState() < 301)) // 处理中的状态才能取消
				{
					return AppResultUtil.fail("工单已经处理完成,或者处理中...");
				}

				orderinfo.setId(orderId);
				orderinfo.setIcons(icons);
				orderinfo.setFinishRemark(finishRemark);
				orderinfo.setOrderState(301);
				orderinfo.setDoneTime(DateUtil.nowTimestampString());
				orderinfoMapper.updateById(orderinfo);

				// 完成回访，插入记录
				// 插入工单日志表
				OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
				orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
				orderinfoOperateLog.setOrderinfoId(orderId);
				orderinfoOperateLog.setType(OrderinfoOperateLog.OperateType.OperateType_Locker.type);// 锁匠操作
				orderinfoOperateLog.setSubType(OrderinfoOperateLog.OperateSubType.OperateSubType_Lock_Completed.type);// 确认完成
				orderinfoOperateLog.setOperateUserId(operateUserId);

				orderinfoOperateLog.setContent(finishRemark);
				orderinfoOperateLogMapper.insert(orderinfoOperateLog);

				try {
					List<Map<String, String>> managerIdList = managerMessageService
							.selectManagerMessageInsertUserList(2 + "");
					for (Map<String, String> map : managerIdList) {
						String managerId = map.get("managerId");
						ManagerMessage managerMessage = new ManagerMessage();
						managerMessage.setUserId(managerId);
						managerMessage.setType(2 + "");
						managerMessage.setTitle("待回访");
						managerMessage.setContent("等待客服回访");
						managerMessage.setIsRead(0);
						managerMessage.setId(UUIDUtil.getUUID());
						managerMessageService.insert(managerMessage);
						List<Map<String, String>> list = managerMessageService
								.selectUnReadMsgCountByUser(managerMessage);
						MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
					}
				} catch (IOException e) {
					e.printStackTrace();
					log.error("websocket发送消息报错");
				}
			} else {
				return AppResultUtil.fail("提交失败,请稍后重试");
			}

		} catch (Exception e) {
			throw new RuntimeException("异常工单id" + orderId, e);
		} finally {
			redisLockService.unlock("acceptOrderLock_" + orderId);
		}
		return appResult;
	}

	/**
	 * 取消工单接口，状态201——>101
	 *
	 * @param operateUserId
	 * @param requestDto
	 * @return
	 */
	@Override
	// @OrderNeedLog(type = 2, subType = 3)
	@Transactional(rollbackFor = Exception.class)
	public Integer orderCancel(String operateUserId, BaseRequestDto requestDto) {

		// 处理json参数
		String orderRequest = requestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

		// 检查入参是否为空
		ParamUtil.checkArgsNull(paramMap);
		// 工单id
		String orderId = String.valueOf(paramMap.get("orderId"));
		// 取消说明
		String mark = String.valueOf(paramMap.get("mark"));

		// 取消方 为back 标识是后台客服主动取消，其他为锁匠小程序取消
		String type = String.valueOf(paramMap.get("type"));

		Orderinfo orderinfo = orderinfoMapper.selectById(orderId);

		// 将该锁匠还未处理的申请加钱的记录删除
		QueryWrapper wrapper = new QueryWrapper();
		wrapper.eq("OrderInfoId", orderId);
		wrapper.in("AuditStates", 0, 5, 6);
		orderInfoAuditSimpleMapper.delete(wrapper);
		orderInfoAuditMapper.updateAllAuditDone( orderinfo.getId());
		
		

		String version = String.valueOf(redisLockService.getVersion());
		try {

			// 加锁
			boolean lockResult = redisLockService.blockLock("acceptOrderLock_" + orderId, version);
			if (lockResult) {

				// 查询
				orderinfo = orderinfoMapper.selectById(orderId);

				if (type == null || !type.equals("back")) { // app操作判断
					if (checkOrder(orderId, operateUserId) < 0)
						return -1; // AppResultUtil.fail("工单状态异常!工单已被修改或者取消");
				}

				// 取消状态判断
				if (!(orderinfo.getOrderState() < 301)) // 处理中的状态才能取消
				{
					return -1;
				}

				// 更新工单状态为待分配，更新工单锁匠为空，更新工单的锁匠费用为基础费用
				BigDecimal changeValueLocker = orderinfo.getLockerBasePrice().subtract(orderinfo.getLockerTotalPrice());
				orderinfo.setLockerTotalPrice(orderinfo.getLockerBasePrice());

				// zj 180527 插入工单日志表
				OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();

				if (type == null || !type.equals("back")) {
					// 小程序取消工单

					Userinfo userinfo = TokenUtil.getCurrentUser();
					if (userinfo.getUserType().equals("4") && userinfo.getCompanyId() != null) {
						// 锁匠取消
						orderinfo.setOrderState(201);
						orderinfo.setLockerId(userinfo.getCompanyId());
						Userinfo partnerInfo = userinfoService.selectUserinfoById(userinfo.getCompanyId());
						// AliSendSMS.sendMessageSms(partnerInfo.getPhone(),"您好，您旗下的锁匠"+TokenUtil.getCurrentUser().getName()+"取消了工单，工单号为"+orderinfo.getOrderNo()+"，该工单号将流转到您的名下，请及时查看！");

						// Userinfo
						// Locker=userinfoService.selectUserinfoById(orderinfoAudit.getLockerId());
						String msg = orderinfo.getOrderNo();
						// todo 3、推送
						AliSendSMS.sendMessageNoraml(partnerInfo.getPhone(), msg);

						String messageStr = "您好，您旗下的锁匠" + TokenUtil.getCurrentUser().getName() + "取消了工单，工单号为"
								+ orderinfo.getOrderNo() + "，原因说明:" + mark + ",该工单号将流转到您的名下，请及时查看！";
						// 插入消息
						UserMessage um = new UserMessage();
						um.setId(snowflakeIdWorker.nextId());
						um.setMessageContent(messageStr);
						um.setMessageType(2L);
						um.setReceiver(partnerInfo.getId());
						um.setOrderInfoId(orderinfo.getId());
						um.setMessageTitle("取消工单");
						userMessageMapper.insert(um);

					} else {
						// 合伙人小程序取消
						orderinfo.setOrderState(101);
						orderinfo.setLockerId(null);
						orderinfo.setCompanyId(null);

						try {
							List<Map<String, String>> managerIdList = managerMessageService
									.selectManagerMessageInsertUserList(4 + "");
							for (Map<String, String> map : managerIdList) {
								String managerId = map.get("managerId");
								ManagerMessage managerMessage = new ManagerMessage();
								managerMessage.setUserId(managerId);
								managerMessage.setType(1 + "");
								managerMessage.setTitle("取消工单");
								managerMessage.setContent("等待客服重新分派工单");
								managerMessage.setIsRead(0);
								managerMessage.setId(UUIDUtil.getUUID());
								managerMessageService.insert(managerMessage);
								List<Map<String, String>> list = managerMessageService
										.selectUnReadMsgCountByUser(managerMessage);
								MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
							}
						} catch (IOException e) {
							e.printStackTrace();
							log.error("websocket发送消息报错");
						}

					}
					orderinfoMapper.updateById(orderinfo);

					if (userinfo.getUserType().equals("4") && userinfo.getCompanyId() != null) {

					} else {
						// zj 合伙人取消工单那， 更新工单那的锁匠/合伙人为空
						orderinfoMapper.updateOrderLockerNull(orderId);
						orderinfoMapper.updateOrderParterNull(orderId);
					}

					orderinfoOperateLog.setContent(" 退单原因:" + mark);

				} else {
					// 后台客服取消工单

					Userinfo ulock = userinfoService.selectUserinfoById(orderinfo.getLockerId());

					orderinfo.setOrderState(101);

					orderinfoMapper.updateById(orderinfo);
					orderinfoMapper.updateOrderLockerNull(orderId);
					orderinfoMapper.updateOrderParterNull(orderId);

					orderinfoOperateLog.setContent(" 后台取消分配");

					// 短信通知，工单取消
					String smsmessage = "您的工单：" + orderinfo.getOrderNo() + "已被客服在后台取消重新分配,请知悉!";

					// 消息表消息
					UserMessage umessage = new UserMessage();
					umessage.setId(snowflakeIdWorker.nextId());
					umessage.setMessageType(2L);// 工单消息
					umessage.setReceiver(ulock.getId());
					umessage.setMessageTitle("工单取消");
					umessage.setOrderInfoId(orderId);
					umessage.setMessageContent(smsmessage);
					userMessageMapper.insert(umessage);

					// 短信通知
					AliSendSMS.sendMessageNoraml(ulock.getPhone(), smsmessage);

				}

				// zj 180527 插入工单日志表
				orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
				orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
				orderinfoOperateLog.setType(2);
				orderinfoOperateLog.setSubType(3);
				orderinfoOperateLog.setOperateUserId(operateUserId);
				orderinfoOperateLogMapper.insert(orderinfoOperateLog);
			}

			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		} finally {
			redisLockService.unlock("acceptOrderLock_" + orderId);
		}

	}
}
