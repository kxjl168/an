/**
 * @(#)OrderinfoAttachMoneyDetail.java  2019-01-28 14:59:06
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.math.BigDecimal;

/**
 * 工单费用明细，包含锁匠及锁企费用每一步变化
 * OrderinfoAttachMoneyDetail.java.
 * 
 * @author zj
* @version 1.0.1 2019年5月16日
* @revision zj 2019年5月16日
* @since 1.0.1
 */
@Data
@TableName("t_orderinfo_attach_money_detail")
public class OrderinfoAttachMoneyDetail {

	
	
	
	
    /**
     * 自增id
     */
    private Long id;

    /**
     * 订单id，外键到订单
     */
    private String orderinfoId;

    /**
     * 同审核表的SubType
     * '变动原因，1：空跑，2：远途，3：改装，4：特殊门，5：加急，6：其他,安装 = 00,维修 = 01,开锁 = 02,特殊们改造 = 03,测量 = 04, 猫眼安装 = 05 ，8：加急 ,9:客服加价:';
     */
    private String reasonType;

    /**
     * 费用变更原因说明
     */
    private String reasonDes;

    /**
     * 商家变动额度，-为减少，+ 为增加，单位是 元，目前不使用
     */
    private BigDecimal changeValueSeller;

    /**
     * 锁匠变动额度，-为减少，+ 为增加，单位是 元
     */
    private java.math.BigDecimal changeValueLocker;

    /**
     * 操作人ID，外键到manager表
     */
    private String operateUserId;

    /**
     * 操作时间（触发器）
     */
    private String operateTime;

    /**
     * 锁匠总费用，单位 元
     */
    private java.math.BigDecimal lockerTotalPrice;

    /**
     * 商家总费用，单位 元，目前不使用
     */
    private BigDecimal sellerTotalPrice;


    private Long auditId;

}
