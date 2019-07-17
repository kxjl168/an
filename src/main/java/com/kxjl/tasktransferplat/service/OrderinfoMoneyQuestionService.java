/*
 * @(#)OrderinfoMoneyQuestionService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.OrderinfoMoneyQuestion;

import java.util.List;
import java.util.Map;

/**
 * 订单金额申述表.
 *
 * @author zj
 * @version 1.0.1 2018年12月11日
 * @revision zj 2018年12月11日
 * @since 1.0.1
 */
public interface OrderinfoMoneyQuestionService {


    /**
     * 新增
     */
    JSONObject saveOrderinfoMoneyQuestion(OrderinfoMoneyQuestion query);

    /**
     * 更新信息
     */
    JSONObject updateOrderinfoMoneyQuestion(OrderinfoMoneyQuestion query);


    List<Map<String,Object>> selectOrderinfoMoneyQuestionList(String orderNo,String phone,String serviceState);

    int deleteOrderinfoMoneyQuestion(OrderinfoMoneyQuestion query);


    OrderinfoMoneyQuestion selectOrderinfoMoneyQuestionById(String id);


}
