/*
 * @(#)PhoneinfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.service;



import com.alibaba.fastjson.JSONObject;

import java.util.List;

import com.kxjl.video.pojo.Phoneinfo;

/**
 * 接警手机信息.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface PhoneinfoService {


    /**
     * 新增
     */
    JSONObject savePhoneinfo(Phoneinfo query);

    /**
     * 更新信息
     */
    JSONObject updatePhoneinfo(Phoneinfo query);


    List<Phoneinfo> selectPhoneinfoList(Phoneinfo query);

    int deletePhoneinfo(Phoneinfo query);
    

   	 Phoneinfo selectPhoneinfoById(String id);



}
