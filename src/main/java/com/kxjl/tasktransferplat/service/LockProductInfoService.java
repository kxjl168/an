/*
 * @(#)LockProductInfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.LockProductInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

import java.util.List;

/**
 * 锁企产品.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface LockProductInfoService {


    /**
     * 新增
     */
    JSONObject saveLockProductInfo(LockProductInfo query);

    /**
     * 更新信息
     */
    JSONObject updateLockProductInfo(LockProductInfo query);


    List<LockProductInfo> selectLockProductInfoList(LockProductInfo query);

    int deleteLockProductInfo(LockProductInfo query);

    int dropLockProductInfo(LockProductInfo query);

    int resetLockProductInfo(LockProductInfo query);

   	 LockProductInfo selectLockProductInfoById(String id);


   /**
    * 通过锁企id,产品型号查询产品
    * @param orderinfo
    * @return
    * @author zj
    * @date 2019年6月18日
    */
    LockProductInfo selectLockProductByprotype(Orderinfo orderinfo);

    /**
     * 标识验证
     * * @param lockProductInfo
     * @return
     */
    List<LockProductInfo> selectLockProductByProType(LockProductInfo lockProductInfo);
}
