/**
 * @(#)OrderInfoOperateProxyImpl.java 2019/4/23 10:00
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.OrderInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAttachMoneyDetailMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAuditMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.ParamUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/4/23 10:00
 * @since 1.0.0
 */
@Service
@Slf4j
public class OrderInfoOperateProxyImpl implements OrderInfoOperateProxy {

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private OrderInfoOperateService orderInfoOperateService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private OrderinfoAttachMoneyDetailMapper orderinfoAttachMoneyDetailMapper;

	@Autowired
	private OrderinfoAuditMapper orderInfoAuditMapper;

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderinfoAuditService orderinfoAuditService;

	@Autowired
	private OrderinfoMapper orderinfoMapper;
	
	@Autowired
	private OrderinfoOperateLogMapper orderinfoOperateLogMapper;


	/**
	 * 工单创建
	 * 
	 * @param orderinfo
	 * @param manager
	 * @author zj
	 * @date 2019年5月16日 modify
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createOrder(Orderinfo orderinfo, Manager manager) {
		int type = manager.getType();

		String orderid = UUIDUtil.getUUID();
		// 公共参数
		orderinfo.setId(orderid);
		orderinfo.setDataState(1);
		orderinfo.setCreateUserId(manager.getId());
		orderinfo.setOrderNo(DateUtil.timestampOrderNo());
		//orderinfo.setLockNum(1);

		// 根据服务类型计算费用
		// Map<String, BigDecimal> priceMap =
		// orderInfoOperateService.calculatePrice(orderinfo);

		// new zj 190516
		List<OrderinfoAttachMoneyDetail> moneydetails = orderInfoOperateService.calculatePriceNew(orderinfo);

		BigDecimal lockerBasePrice = new BigDecimal(0);
		BigDecimal lockerTotalPrice = new BigDecimal(0);
		BigDecimal sellerBasePrice = new BigDecimal(0);
		BigDecimal sellerTotalPrice = new BigDecimal(0);
		for (OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail : moneydetails) {
			lockerBasePrice = lockerBasePrice.add(orderinfoAttachMoneyDetail.getChangeValueLocker());
			sellerBasePrice = sellerBasePrice.add(orderinfoAttachMoneyDetail.getChangeValueSeller());
		}

		lockerTotalPrice = lockerBasePrice;
		sellerTotalPrice = sellerBasePrice;

		orderinfo.setLockerBasePrice(lockerBasePrice);
		orderinfo.setLockerTotalPrice(lockerTotalPrice);
		orderinfo.setSellerBasePrice(sellerBasePrice);
		orderinfo.setSellerTotalPrice(sellerTotalPrice);

		// 工单所在地区
		AreaDistrict areaDistrict = areaService.getAreaDistrictByCode(orderinfo.getAreaCode());
		orderinfo.setDistrictId(areaDistrict.getId());

		// 封装参数
		BaseRequestDto requestDto = new BaseRequestDto();
		Map<String, String> parameter = new HashMap<>(16);
		parameter.put("orderId", String.valueOf(orderinfo.getId()));
		parameter.put("orderInfo", JSON.toJSONString(orderinfo));
		requestDto.setData(JSON.toJSONString(parameter));

		// 分开主要是为了插入不同类型的操作日志
		// 平台创建工单
		if (type == 0) {
			orderInfoOperateService.platCreateOrder(manager.getId(), requestDto);
		}
		// 锁企创建工单
		else {
			orderInfoOperateService.lockCompanyCreateOrder(manager.getId(), requestDto);
		}

		// 工单价格记录
		for (OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail : moneydetails) {

			orderinfoAttachMoneyDetail.setId(snowflakeIdWorker.nextId());
			orderinfoAttachMoneyDetail.setOrderinfoId(orderid);
			orderinfoAttachMoneyDetail.setOperateUserId(orderinfo.getCreateUserId());

			orderinfoAttachMoneyDetailMapper.insert(orderinfoAttachMoneyDetail);

		}

	}
	
	
	/**
	 * 工单更新，修改价格相关，
	 * @param orderinfo
	 * @param manager
	 * @author zj
	 * @date 2019年5月20日
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrder(Orderinfo orderinfo, Manager manager) {
		int type = manager.getType();

		String orderid =orderinfo.getId();// UUIDUtil.getUUID();
		// 公共参数
		orderinfo.setId(orderid);
		orderinfo.setDataState(1);
		orderinfo.setCreateUserId(manager.getId());
		//orderinfo.setOrderNo(DateUtil.timestampOrderNo());
		//orderinfo.setLockNum(1);

		// 根据服务类型计算费用
		// Map<String, BigDecimal> priceMap =
		// orderInfoOperateService.calculatePrice(orderinfo);

		// new zj 190516
		List<OrderinfoAttachMoneyDetail> moneydetails = orderInfoOperateService.calculatePriceNew(orderinfo);

		BigDecimal lockerBasePrice = new BigDecimal(0);
		BigDecimal lockerTotalPrice = new BigDecimal(0);
		BigDecimal sellerBasePrice = new BigDecimal(0);
		BigDecimal sellerTotalPrice = new BigDecimal(0);
		for (OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail : moneydetails) {
			lockerBasePrice = lockerBasePrice.add(orderinfoAttachMoneyDetail.getChangeValueLocker());
			sellerBasePrice = sellerBasePrice.add(orderinfoAttachMoneyDetail.getChangeValueSeller());
		}

		lockerTotalPrice = lockerBasePrice;
		sellerTotalPrice = sellerBasePrice;

		orderinfo.setLockerBasePrice(lockerBasePrice);
		orderinfo.setLockerTotalPrice(lockerTotalPrice);
		orderinfo.setSellerBasePrice(sellerBasePrice);
		orderinfo.setSellerTotalPrice(sellerTotalPrice);

		// 工单所在地区
		AreaDistrict areaDistrict = areaService.getAreaDistrictByCode(orderinfo.getAreaCode());
		orderinfo.setDistrictId(areaDistrict.getId());

		// 封装参数
		BaseRequestDto requestDto = new BaseRequestDto();
		Map<String, String> parameter = new HashMap<>(16);
		parameter.put("orderId", String.valueOf(orderinfo.getId()));
		parameter.put("orderInfo", JSON.toJSONString(orderinfo));
		requestDto.setData(JSON.toJSONString(parameter));

		// 分开主要是为了插入不同类型的操作日志
		
		orderInfoOperateService.lockCompanyUpdate(manager.getId(), requestDto);
		
		
		//清除价格记录，从新记录.
		OrderinfoAttachMoneyDetail q=new OrderinfoAttachMoneyDetail(); 
		q.setOrderinfoId(orderid);
		orderinfoAttachMoneyDetailMapper.deleteOrderDetail(q);

		// 工单价格记录
		for (OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail : moneydetails) {

			orderinfoAttachMoneyDetail.setId(snowflakeIdWorker.nextId());
			orderinfoAttachMoneyDetail.setOrderinfoId(orderid);
			orderinfoAttachMoneyDetail.setOperateUserId(orderinfo.getCreateUserId());

			orderinfoAttachMoneyDetailMapper.insert(orderinfoAttachMoneyDetail);

		}

	}

	/**
	 * 更新工单信息统一入口 2、根据用户类型不同更新工单信息或仅修改工单信息，工单状态101
	 * 
	 * @param operateUserId
	 * @param requestDto
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCreateOrderInfo(String operateUserId, BaseRequestDto requestDto) {

		Map<String, String> paramMap = (Map<String, String>) JSON.parse(requestDto.getData());
		int type = Integer.parseInt(paramMap.get("type"));
		Orderinfo orderinfo = JSON.parseObject(paramMap.get("orderInfo"), Orderinfo.class);
		// 如果是平台更新
		if (type == 0) {
			orderInfoOperateService.platUpdate(operateUserId, requestDto);
		}
		// 锁企更新
		else {
			orderInfoOperateService.lockCompanyUpdate(operateUserId, requestDto);
		}
		//orderinfoMapper.updateById(orderinfo);
	}

	/**
	 * 工单审核统一入口
	 *
	 * @param managerId
	 * @param baseRequestDto
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void orderAudit(String managerId, BaseRequestDto baseRequestDto) {

		// 处理json参数
		String orderRequest = baseRequestDto.getData();
		Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
		// 检查入参是否为空
		ParamUtil.checkArgsNull(paramMap);
		
		String idandMoney= String.valueOf(paramMap.get("auditId"));
		// 工单id
		Long auditId = Long.valueOf(idandMoney.split(",")[0]);
		// 是否通过，1：是，2：否，3：源匠向锁企申请加钱
		Integer passState = Integer.valueOf(paramMap.get("passState"));
		// 不通过原因 ，或者主动加价原因
		String reason = paramMap.get("reason");
		// 加钱金额
		String proposMoney = paramMap.get("proposMoney");

		OrderinfoAudit orderinfoAudit = orderInfoAuditMapper.selectById(auditId);
		
		//zj 190604
		if(passState==1) //锁企通过，此处可以主动修改价格，继续记录
		{
			if(proposMoney!=null&&orderinfoAudit.getProposMoney().doubleValue()!= Double.valueOf(proposMoney).doubleValue())
			{
				double oldmoney=orderinfoAudit.getProposMoney().doubleValue();
				//价格有变动，加的价格为新价格。
				orderinfoAudit.setProposMoney(new BigDecimal(Double.valueOf(proposMoney)));
				
				orderInfoAuditMapper.updateById(orderinfoAudit);
				
				//日志
				// 插入工单日志表
				OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
				orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
				orderinfoOperateLog.setOrderinfoId(orderinfoAudit.getOrderInfoId());
				orderinfoOperateLog.setType(1);
				orderinfoOperateLog.setSubType(-4);
				orderinfoOperateLog.setContent(orderinfoAudit.getProposReason()+","+reason+" " +oldmoney+"  --> "+ proposMoney);
				orderinfoOperateLog.setOperateUserId(managerId);
				orderinfoOperateLog.setAuditId(orderinfoAudit.getId());

				orderinfoOperateLogMapper.insert(orderinfoOperateLog);
			}
			
		}

	
		// 处理切面参数
		paramMap.put("orderId", String.valueOf(orderinfoAudit.getOrderInfoId()));
		paramMap.put("auditId", String.valueOf(auditId));
		baseRequestDto.setData(JSON.toJSONString(paramMap));

		Orderinfo orderinfo = orderInfoMapper.selectById(orderinfoAudit.getOrderInfoId());
		if (orderinfo == null) {
			throw new RuntimeException("未查询到相关工单");
		}
		// 根据工单状态和审核状态进行处理
		int orderState = orderinfo.getOrderState();
		if (orderState == 4) {
			orderinfoAuditService.lockCompanyAudit(managerId, baseRequestDto, orderinfoAudit, orderinfo, passState,
					reason);
		} else {
			orderinfoAuditService.platAudit(managerId, baseRequestDto, orderinfoAudit, orderinfo, passState, reason,
					proposMoney);
		}
	}
}
