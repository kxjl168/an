/**
 * @(#)OrderinfoOperateLog.java  2019-01-28 14:59:06
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_orderinfo_operate_log")
public class OrderinfoOperateLog {

	/**
	 * 操作类型
	 * OrderinfoOperateLog.java.
	 * 
	 * @author zj
	* @version 1.0.1 2019年1月31日
	* @revision zj 2019年1月31日
	* @since 1.0.1
	 */
	public enum OperateType{
		
		OperateType_LockCompany(0,"锁企操作"),
		OperateType_Kefu(1,"客服操作"),
		OperateType_Locker(2,"锁匠操作");

		
		public int type;
		public String desc;
		OperateType(int type,String desc){
			this.type=type;
			this.desc=desc;
		}
		
		
	}
	
	/**
	 * 操作类型 <br>
	 * 当Type为1时： 1：派单，2：审核，3：回访，4：评分 当Type为2时 1：接单，2：申请加钱，3：申请退单<br>
	 * OrderinfoOperateLog.java.
	 * 
	 * @author zj
	* @version 1.0.1 2019年1月31日
	* @revision zj 2019年1月31日
	* @since 1.0.1
	 */
	public enum OperateSubType{

		OperateSubType_LCompany_CreateOrder(1,"锁企新建订单"),
		OperateSubType_LCompany_CheckOrder(2,"锁企审核加钱订单"),

		OperateSubType_Paidan(1,"客服派单"),
		OperateSubType_Shenhe(2,"客服审核"),
		OperateSubType_huifang(3,"客服回访评分"),
		OperateSubType_pay(4,"结账"),
		OperateSubType_check(5,"确认工单信息"),
		OperateSubType_playMoney(6,"锁企打款"),
		OperateSubType_withdraw(7,"源匠提现"),


		OperateSubType_Lock_AcceptOrder(1,"锁匠接单"),
		OperateSubType_Lock_AddMoney(2,"锁匠申请加钱"),
		OperateSubType_Lock_Cancel(3,"锁匠申请退单"),
		OperateSubType_Lock_Completed(4,"锁匠确认完成"),
		OperateSubType_Lock_withdraw(5,"锁匠申请提现"),
		OperateSubType_Lock_Pandan(6,"合伙人派单"),
		OperateSubType_Lock_Shenhe(7,"合伙人审核");

		
		public int type;
		public String desc;
		OperateSubType(int type,String desc){
			this.type=type;
			this.desc=desc;
		}


	}

	/**
	 * 自增id
	 */
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

	/**
	 * 订单id，外键 到订单
	 */
	private String orderinfoId;

	/**
	 * 顶级type，1：客服操作，2：锁匠操作
	 */
	private Integer type;

	/**
	 * 当Type为1时： 1：派单，2：审核，3：回访，4：评分 当Type为2时 1：接单，2：申请加钱，3：申请退单
	 */
	private Integer subType;

	/**
	 * 暂时不使用
	 */
	private String title;

	/**
	 * 详细内容，可空
	 */
	private String content;
	
	/**
	 * 审核不通过原因，关联计入log zj190523
	 */
	private String auditFailReason;

	/**
	 * 操作人id,外键到User表或者manager表
	 */
	private String operateUserId;

	/**
	 * 操作时间（触发器）
	 */
	private String operateTime;

	/**
	 * 审核id，当SubType为2或者3时有值
	 */
	private Long auditId;

	/**
	 * 订单编号
	 */
	@TableField(exist=false)
	private String orderNo;

	/**
	 * 开始时间
	 */
	@TableField(exist=false)
	private String startTime;

	/**
	 * 结束时间
	 */
	@TableField(exist=false)
	private String endTime;
	/**
	 * 合伙人id
	 */
	@TableField(exist=false)
	private Long companyId;

	/**
	 * 锁企id
	 */
	@TableField(exist = false)
	private Long SellerId;

	/**
	 * 操作人名
	 */
	@TableField(exist=false)
	private String operateUserName;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getSellerId() {
		return SellerId;
	}

	public void setSellerId(Long sellerId) {
		SellerId = sellerId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderinfoId() {
		return orderinfoId;
	}

	public void setOrderinfoId(String orderinfoId) {
		this.orderinfoId = orderinfoId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
}
