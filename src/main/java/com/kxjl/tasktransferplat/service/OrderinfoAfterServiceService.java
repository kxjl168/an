/*
 * @(#)OrderinfoAfterServiceService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;




import java.util.List;

import com.kxjl.tasktransferplat.pojo.OrderinfoAfterService;

import net.sf.json.JSONObject;

/**
 * 订单售后表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface OrderinfoAfterServiceService {


    /**
     * 新增
     */
    JSONObject saveOrderinfoAfterService(OrderinfoAfterService query);

    /**
     * 更新信息
     */
    JSONObject updateOrderinfoAfterService(OrderinfoAfterService query);


    List<OrderinfoAfterService> selectOrderinfoAfterServiceList(OrderinfoAfterService query);

    int deleteOrderinfoAfterService(OrderinfoAfterService query);
    

   	 OrderinfoAfterService selectOrderinfoAfterServiceById(String id);



}
