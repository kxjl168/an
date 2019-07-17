/*
 * @(#)LockProductAttachService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.LockProductAttach;

import java.util.List;

/**
 * 产品附件表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface LockProductAttachService {


    /**
     * 新增
     */
    JSONObject saveLockProductAttach(LockProductAttach query);

    /**
     * 更新信息
     */
    JSONObject updateLockProductAttach(LockProductAttach query);


    List<LockProductAttach> selectLockProductAttachList(LockProductAttach query);

    int deleteLockProductAttach(LockProductAttach query);
    

   	 LockProductAttach selectLockProductAttachById(String id);



}
