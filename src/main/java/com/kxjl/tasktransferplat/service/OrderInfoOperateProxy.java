/**
 * @(#)OrderInfoOperateProxy.java 2019/4/23 9:29
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import com.kxjl.base.pojo.Manager;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

/**
 * @author 单肙
 * @version 1.0.0 2019/4/23 9:29
 * @since 1.0.0
 *
 * 工单操作代理类，由于在方法内部调用方法不会走代理，
 * 所以建立该代理类，用于使注解生效。
 */
public interface OrderInfoOperateProxy {

    /**
     * 创建工单统一入口
     * @param orderinfo
     * @param manager
     */
    void createOrder(Orderinfo orderinfo, Manager manager);

    
    /**
     * 工单更新
     * @param orderinfo
     * @param manager
     * @author zj
     * @date 2019年5月20日
     */
	public void updateOrder(Orderinfo orderinfo, Manager manager); 
    /**
     * 更新工单信息统一入口
     * @param operateUserId
     * @param requestDto
     */
    void updateCreateOrderInfo(String operateUserId, BaseRequestDto requestDto);


    /**
     * 工单审核统一入口
     * @param managerId
     * @param baseRequestDto
     */
    void orderAudit(String managerId, BaseRequestDto baseRequestDto);
}
