/*
 * @(#)UnitinfoManagerService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.service;



import com.alibaba.fastjson.JSONObject;

import java.util.List;

import com.kxjl.video.pojo.UnitinfoManager;

/**
 * 单位超级管理员 1对多.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface UnitinfoManagerService {


    /**
     * 新增
     */
    JSONObject saveUnitinfoManager(UnitinfoManager query);

    /**
     * 更新信息
     */
    JSONObject updateUnitinfoManager(UnitinfoManager query);


    List<UnitinfoManager> selectUnitinfoManagerList(UnitinfoManager query);

    int deleteUnitinfoManager(UnitinfoManager query);
    

   	 UnitinfoManager selectUnitinfoManagerById(String id);



}
