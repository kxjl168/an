/*
 * @(#)CustomerComplainService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import net.sf.json.JSONObject;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.CustomerComplain;

/**
 * 客户投诉表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface CustomerComplainService {


    /**
     * 新增
     */
    JSONObject saveCustomerComplain(CustomerComplain query);

    /**
     * 更新信息
     */
    JSONObject updateCustomerComplain(CustomerComplain query);


    List<CustomerComplain> selectCustomerComplainList(CustomerComplain query);

    int deleteCustomerComplain(CustomerComplain query);
    


   	 CustomerComplain selectCustomerComplainById(Long id) ;


}
