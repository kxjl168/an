/**
 * @(#)OrderInfoOperateService.java 2019/4/19 14:42
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import java.math.BigDecimal;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.AppResult;
import com.kxjl.tasktransferplat.annotation.OrderNeedLog;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
import com.kxjl.tasktransferplat.pojo.Userinfo;

/**
 * @author 单肙
 * @version 1.0.0 2019/4/19 14:42
 * @since 1.0.0
 */
public interface OrderInfoOperateService {

    /**
     * 1、锁企创建工单，工单状态3
     * @param operateUserId
     * @param requestDto
     */
    void lockCompanyCreateOrder(String operateUserId, BaseRequestDto requestDto);

    /**
     * 重发工单核销验证码
     * @param orderNo
     * @author zj
     * @date 2019年6月24日
     */
    public void SendCompleteCodeSms(String orderNo);
 
    
    /**
     * 计算工单的价格详细，包括锁匠及锁企的工单价格变动明细。
     * @param orderinfo
     * @return
     * @author zj
     * @date 2019年5月16日
     */

    public java.util.List<OrderinfoAttachMoneyDetail> calculatePriceNew(Orderinfo orderinfo);
    
    /**
     * 1、平台创建工单，工单状态101
     * @param operateUserId
     * @param requestDto
     */
    @OrderNeedLog(type = 1)
    void platCreateOrder(String operateUserId, BaseRequestDto requestDto);

    /**
     * 根据服务类型获得锁匠和锁企价格
     * @param orderinfo
     * @return
     */
    Map<String, BigDecimal> calculatePrice(Orderinfo orderinfo);

    /**
     * 平台更新
     * @param operateUserId
     * @param requestDto
     */
    void platUpdate (String operateUserId, BaseRequestDto requestDto);

    /**
     * 锁企更新
     * @param operateUserId
     * @param requestDto
     */
    void lockCompanyUpdate (String operateUserId, BaseRequestDto requestDto);

    /**
     * 3、分配工单
     * @param operateUserId
     * @param baseRequestDto
     */
    Integer allocateOrder(String operateUserId, BaseRequestDto baseRequestDto);


    /**
     * 3、锁匠接单接口
     * @param requestDto
     * @return
     */
    Integer orderAccept(String operateUserId, BaseRequestDto requestDto);

    /**
     * 工单申请加钱接口
     * @param requestDto
     * @return
     */
    Integer orderAddPrice(String operateUserId, BaseRequestDto requestDto);

    /**
     * 确认工单完成接口
     * @param requestDto
     * @return
     */
    AppResult orderDone(String operateUserId, BaseRequestDto requestDto);

    /**
     * 取消工单接口
     * @param requestDto
     * @return
     */
    Integer orderCancel(String operateUserId, BaseRequestDto requestDto);
}
