/*
 * @(#)CommunityService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.pojo.SysPlat;

import java.util.List;



/**
 * 小区
 * CommunityService.java.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface SysPlatService {


    /**
     * 新增
     */
    JSONObject saveSysPlat(SysPlat query);

    /**
     * 更新信息
     */
    JSONObject updateSysPlat(SysPlat query);


    List<SysPlat> selectSysPlatList(SysPlat query);

    int deleteSysPlat(SysPlat query);
    
    SysPlat selectSysPlatById(String id);




}
