/**
 * @(#)UserinfoMoneyDetail.java  2019-01-28 14:59:06
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

import java.math.BigDecimal;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_userinfo_money_detail")
public class UserinfoMoneyDetail {

	/**
	 *费用变更原因类型
	 * MoneyChangeType.java.
	 * 
	 * @author zj
	* @version 1.0.1 2019年1月31日
	* @revision zj 2019年1月31日
	* @since 1.0.1
	 */
	public enum MoneyChangeType{
		
		OperateType_Tixian(1,"提现"),
		OperateType_Income(2,"工单入账费用"),
		OperateType_Modify(3,"工单改价");
		
		
		public long type;
		public String desc;
		MoneyChangeType(long type,String desc){
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
     * 账单流水编号，用于查询和前端展示(根据时间到秒+3位随机数)
     */
    private String moneyNo;
	
    /**
     * 用户id，外键
     */
    private Long userinfoId;

    /**
     * 费用变更原因类型，1：提现，2：工单费用
     */
    private Long reasonType;

    /**
     * 费用变更原因说明
     */
    private String reasonDes;

    /**
     * 变动额度，-为减少，+ 为增加，单位 元
     */
    private java.math.BigDecimal changeValue;

    /**
     * 操作人，外键 manager
     */
    private String operateUserId;

    /**
     * 操作时间（触发器）
     */
    private String operateTime;

    /**
     * 变动后总费用
     */
    private BigDecimal totalPrice;

    /**
     * 造成这条记录的id,若ReasonType为1则为提现记录id，若ReasonType为2则为工单id
     */
    private String effectiveId;

	@TableField(exist = false)
	private String createTime;

	@TableField(exist = false)
	private String des;

	@TableField(exist = false)
	private String type;
	
	
	@TableField(exist = false)
	private String name;
	
}
