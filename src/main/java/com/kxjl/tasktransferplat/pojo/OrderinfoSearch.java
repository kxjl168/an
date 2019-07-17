/**
 * @(#)OrderinfoSearch.java  2019-01-28 14:59:06
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

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_orderinfo_search")
public class OrderinfoSearch {

    /**
     * 订单id(本表数据，是从orderinfo里获取，已经orderinfo的外键里取数据)
     */
    private String id;

    /**
     * （跟随订单）默认 1(待审核1, 待接单11, 待完成21, 待回访31, 待结账41, 冻结101,废弃111)',
     */
    private Long orderState;

    /**
     * （跟随订单）订单创建时间（这个创建时间要跟随主表，要跟主表一样）
     */
    private String createTime;

    /**
     * （跟随订单）(锁企0, 客户1, 默认是锁企0)
     */
    private Long paymentType;

    /**
     * （跟随订单）(安装0，维修1, 其他101)
     */
    private Long serverType;

    /**
     * （跟随订单）锁具数量
     */
    private Long lockNum;

    /**
     * （跟随订单）预约下次联系时间
     */
    private String appointmentTime;

    /**
     * （跟随订单）客户姓名
     */
    private String custName;

    /**
     * （跟随订单）客户电话
     */
    private String custPhone;

    /**
     * （跟随订单）客户地址 外键 address
     */
    private Long custAddress;

    /**
     * （跟随订单）锁匠id 外键
     */
    private Long lockerId;

    /**
     * （跟随锁匠，锁匠表如果更新，也要更新这个）锁匠姓名
     */
    private String lockerName;

    /**
     * （跟随锁匠，锁匠表如果更新，也要更新这个）锁匠电话
     */
    private String lockerPhone;

    /**
     * （跟随订单）接单时间
     */
    private String receiveTime;

    /**
     * （跟随订单）完工时间（锁匠点击完成）
     */
    private String doneTime;

    /**
     * （跟随订单）对锁匠结账时间
     */
    private String accountTime;

    /**
     * （跟随订单）商家ID
     */
    private Long sellerId;

    /**
     * （跟随商家，商家表如果更新，也要更新这个）商家名称
     */
    private String sellerName;

    /**
     * （跟随订单）商家内部id
     */
    private String sellerInnerId;


    @TableLogic
    private Long dataState;

}
