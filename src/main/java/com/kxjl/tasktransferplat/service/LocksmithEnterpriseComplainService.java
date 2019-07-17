/*
 * @(#)LocksmithEnterpriseComplainService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import net.sf.json.JSONObject;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.LocksmithEnterpriseComplain;

/**
 * 琐企投诉表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface LocksmithEnterpriseComplainService {


    /**
     * 新增
     */
    JSONObject saveLocksmithEnterpriseComplain(LocksmithEnterpriseComplain query);

    /**
     * 更新信息
     */
    JSONObject updateLocksmithEnterpriseComplain(LocksmithEnterpriseComplain query);


    List<LocksmithEnterpriseComplain> selectLocksmithEnterpriseComplainList(LocksmithEnterpriseComplain query);

    int deleteLocksmithEnterpriseComplain(LocksmithEnterpriseComplain query);
    


   	 LocksmithEnterpriseComplain selectLocksmithEnterpriseComplainById(Long id) ;


}
