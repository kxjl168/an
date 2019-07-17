/**
 * @(#)OrderinfoAttachMoneyDetailServiceImpl.java  2019-01-28 11:26:27
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.OrderInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAttachMoneyDetailMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.dao.plus.UserDepositMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMoneyDetailMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
import com.kxjl.tasktransferplat.pojo.OrderinfoMoneyQuestion;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.pojo.UserDeposit;
import com.kxjl.tasktransferplat.pojo.UserMessage;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail.MoneyChangeType;
import com.kxjl.tasktransferplat.service.OrderinfoAttachMoneyDetailService;
import com.kxjl.tasktransferplat.service.OrderinfoMoneyQuestionService;
import com.kxjl.tasktransferplat.service.UserDepositService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:27
 * @since 1.0.0
 */
@Service
public class OrderinfoAttachMoneyDetailServiceImpl implements OrderinfoAttachMoneyDetailService {

	@Autowired
	private OrderinfoAttachMoneyDetailMapper orderinfoAttachMoneyDetailMapper;
	@Autowired
	private OrderinfoOperateLogMapper orderinfoOperateLogMapper;

	@Autowired
	UserinfoService userinfoService;

	@Autowired
	UserinfoMoneyDetailMapper userinfoMoneyDetailMapper;
	@Autowired
	UserDepositMapper userDepositMapper;

	@Autowired
	UserDepositService userDepositService;
	
	@Autowired
	OrderinfoMoneyQuestionService orderinfoMoneyQuestionService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private UserMessageMapper userMessageMapper;

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	/**
	 * 工单金额明细
	 * 
	 * @param query
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	public List<OrderinfoAttachMoneyDetail> selectOrderMoneyDetailList(OrderinfoAttachMoneyDetail query) {
		return orderinfoAttachMoneyDetailMapper.selectOrderMoneyDetailList(query);
	}

	@Transactional
	@Override
	public Message ChangeOrderMoneydetail(Map<String, String> datas, String reason, String orderId, Manager user)
			throws Exception {
		Message rst = new Message();

		String desc = "";
		for (String key : datas.keySet()) {

			OrderinfoAttachMoneyDetail odetail = new OrderinfoAttachMoneyDetail();
			odetail.setId(Long.parseLong(key));
			odetail.setChangeValueLocker(BigDecimal.valueOf(Long.parseLong(datas.get(key))));

			OrderinfoAttachMoneyDetail oldMoney = orderinfoAttachMoneyDetailMapper.selectById(Long.parseLong(key));

			// 价格与原价格不同，则改价
			if (oldMoney.getChangeValueLocker().doubleValue() != odetail.getChangeValueLocker().doubleValue()) {
				orderinfoAttachMoneyDetailMapper.updateById(odetail);

				desc += oldMoney.getReasonDes() + " 价格变动: " + oldMoney.getChangeValueLocker().toString() + " --> "
						+ odetail.getChangeValueLocker().toString() + " \n";
			}

		}

		Orderinfo oinfo = orderInfoMapper.selectById(orderId);
		Userinfo moneyuser = null;
		// 跟新锁匠余额,有合伙人则更新合伙人的账户，否则跟新个人的账户
		if (oinfo.getCompanyId() != null)
			moneyuser = userinfoService.selectUserinfoById(oinfo.getCompanyId());
		else if (oinfo.getLockerId() != null)
			moneyuser = userinfoService.selectUserinfoById(oinfo.getLockerId());

		if (moneyuser != null)
			userinfoService.refreshUserMoneyInfo(moneyuser);

		// 更新工单锁匠总价，及琐企总价 zj 190527
		OrderinfoAttachMoneyDetail dquery = new OrderinfoAttachMoneyDetail();
		dquery.setOrderinfoId(orderId);
		OrderinfoAttachMoneyDetail totalData = orderinfoAttachMoneyDetailMapper.GetOrderTotalPrice(dquery);
		Orderinfo orderinfo = new Orderinfo();
		orderinfo.setId(orderId);
		orderinfo.setLockerTotalPrice(totalData.getChangeValueLocker());
		orderinfo.setSellerTotalPrice(totalData.getChangeValueSeller());
		orderInfoMapper.updateById(orderinfo);

		// 更新工单审核表中对应工单的审核价格 zj 190611,工单审核金额根据工单记录的LockerTotalPrice计算
		UserDeposit ud = new UserDeposit();
		ud.setOrderInfoId(orderId);

		UserDeposit uddata = userDepositService.selectUserDepositByOrderId(orderId);
		
		  List<Map<String,Object>> oQs= orderinfoMoneyQuestionService .selectOrderinfoMoneyQuestionList(oinfo.getOrderNo(), "","");
		 
		 
		if ((uddata != null && uddata.getId() != null ) ||(oQs!=null&&oQs.size()>0)) {

			Long duid=0L;
			//或者申述审核改价
			// 记录提现审核中的价格变更，记录到锁匠费用详情表中
			if(uddata != null && uddata.getId() != null )
			{
			uddata.setDepositMoney(orderinfo.getLockerTotalPrice());
			userDepositMapper.updateDepositMoneyByOrder(uddata);
			
			duid=uddata.getUserId();
			}
			else if(oQs!=null&&oQs.size()>0)
			{
				duid=Long.parseLong(String.valueOf( oQs.get(0).get("userId")));
			}

			// 提现过程修改了工单价格， 记录入用户账户变动
			UserinfoMoneyDetail detail = new UserinfoMoneyDetail();
			detail.setId(snowflakeIdWorker.nextId());
			detail.setMoneyNo(DateUtil.timestampOrderNo());
			detail.setUserinfoId(duid);
			detail.setReasonType(MoneyChangeType.OperateType_Modify.type);
			detail.setReasonDes("财务改价:" + reason + " ," + desc);

			Userinfo UMoney = userinfoService.getUserMoneyInfo(moneyuser);
			// 当前重新计算的总价；
			BigDecimal total = UMoney.getTotal().subtract(UMoney.getDoneMoney());
			// 改价前总价
			BigDecimal oldtotal = moneyuser.getAccountMoney().add(moneyuser.getFreezeMoney());

			BigDecimal change = total.subtract(oldtotal);

			moneyuser.setAccountMoney(UMoney.getTotal().subtract(UMoney.getDoneMoney()));// 全部-已提现

			detail.setChangeValue(change);
			detail.setOperateUserId(user.getId());
			detail.setTotalPrice(total);

			
			detail.setEffectiveId(oinfo.getOrderNo()); // 统一使用工单OrderNo

			// detail.setEffectiveId(uddata.getId());
			userinfoMoneyDetailMapper.insert(detail);

		}

		if (moneyuser != null)

			userinfoService.refreshUserMoneyInfo(moneyuser);

		// AliSendSMS.sendMessageNoraml(userinfo.getPhone(), msg);

		if (oinfo.getLockerId() != null) {
			String messageStr = "工单号为" + oinfo.getOrderNo() + "的工单服务费用已变更," + desc + "当前服务费:"
					+ orderinfo.getLockerTotalPrice().toString();
			// 插入消息
			UserMessage um = new UserMessage();
			um.setId(snowflakeIdWorker.nextId());
			um.setMessageContent(messageStr);
			um.setMessageType(1L);
			um.setReceiver(oinfo.getLockerId());
			um.setOrderInfoId(oinfo.getId());
			um.setMessageTitle("工单改价");
			userMessageMapper.insert(um);

		}

		// 记录改价说明
		// 插入工单日志表
		OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
		orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
		orderinfoOperateLog.setOrderinfoId(orderId);
		orderinfoOperateLog.setType(1);// 1源匠，0锁企
		orderinfoOperateLog.setSubType(-3); // 改价
		orderinfoOperateLog.setOperateUserId(user.getId());
		orderinfoOperateLog.setContent(reason + " ," + desc);

		orderinfoOperateLogMapper.insert(orderinfoOperateLog);

		rst.setBol(true);

		return rst;
	}
}
