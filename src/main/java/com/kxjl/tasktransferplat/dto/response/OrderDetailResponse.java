/**
 * @(#)OrderDetailResponse.java 2019/1/29 10:32
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dto.response;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Address;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/29 10:32
 * @since 1.0.0
 */
@Data
public class OrderDetailResponse extends BaseResponseDto {

    /**
     * 订单id(本表数据，是从orderinfo里获取，已经orderinfo的外键里取数据)
     */
    private Long id;

    /**
     * 工单业务编号
     */
    private String orderNo;

    /**
     * （跟随订单）
     */
    private Long orderState;

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
    private Address address;

    /**
     * 客户详细地址
     */
    private String custAddressDetail;

    /**
     * 工单备注
     */
    private String createRemark;

    /**
     * 锁匠工单金额
     */
    private BigDecimal lockerTotalPrice;

}
