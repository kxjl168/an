/**
 * @(#)UserDeposit.java  2019-01-28 14:59:06
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_user_deposit")
public class UserDeposit {

    /**
     * 提现请求id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String id;

    /**
     * 锁匠id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    
    @TableField(exist = false)
    private String userName;//
    
    @TableField(exist = false)
    private String lockerPhone;

    /**
     * 提现方式：0：支付宝到账，1：微信到账，2：银行卡到账
     */
    private Integer depositType;

    /**
     * 根据用户选择的提现方式含有不同的意义：支付宝到账：支付宝登录号，微信零钱到账：商户appid下，某用户的openid，银行卡到账：银行卡号
     */
    private String depositNumber;

    /**
     * 提现金额
     */
    private java.math.BigDecimal depositMoney;

    /**
     * 提现到银行卡时不为空，为微信的银行卡idn工商银行：1002n农业银行：1005n中国银行：1026n建设银行：1003n招商银行：1001n邮储银行：1066n交通银行：1020n浦发银行：1004n民生银行：1006n兴业银行：1009n平安银行：1010n中信银行：1021n华夏银行：1025n广发银行：1027n光大银行：1022n北京银行：1032n宁波银行：1056
     */
    private String bankId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 收款人真实姓名，用于校验
     */
    private String depositReceiver;

    /**
     * 收款人电话
     */
    private String depositReceiverPhone;

    /**
     * 微信要求的IP地址，当提现类型为微信零钱到账时不为空
     */
    private String depositIp;

    /**
     * 提现状态
     * 0：待审核，
     * 1：审核通过，
     * 2：审核未通过，
     * 3：审核通过且提现成功，
     * 4：审核通过但提现不成功。
     */
    private Integer depositStatus;
    
    
    @TableField(exist = false)
    private String depositStatusQuery;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String updateTime;

    /**
     * 审核人id，即manager的id
     */
    private String auditor;

    /**
     * 审核意见：不通过时必填
     */
    private String auditOpinion;

    /**
     * 转账失败原因：当审核通过但转账失败时不为空
     */
    private String failCause;

    @TableField(exist = false)
    private Long companyId;

    /**
     * 訂單號
     */
    private String orderInfoId;
    
    @TableField(exist = false)
    private String orderNo;

    @TableField(exist = false)
    private String startTime;//提现时间
    
    @TableField(exist = false)
    private String endTime;
    
    @TableField(exist = false)
    private String startdoneTime; //完成时间
    
    @TableField(exist = false)
    private String enddoneTime;
}
