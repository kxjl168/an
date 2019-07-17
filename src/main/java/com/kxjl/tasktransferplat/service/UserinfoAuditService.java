/*
 * @(#)UserinfoAuditService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;

import java.util.List;

/**
 * 锁匠类型变更审核.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface UserinfoAuditService {


    /**
     * 新增
     */
    JSONObject saveUserinfoAudit(UserinfoAudit query);

    /**
     * 更新信息
     */
    JSONObject updateUserinfoAudit(UserinfoAudit query);


    List<UserinfoAudit> selectUserinfoAuditList(UserinfoAudit query);

    int deleteUserinfoAudit(UserinfoAudit query);
    

   	 UserinfoAudit selectUserinfoAuditById(String id);


   	 /**
   	  * 用户类型审核,包括后台变更及app合伙人操作
   	  * @param item
   	  * @return
   	  * @author zj
   	  * @date 2019年6月6日
   	  */
   	 Message userTypeChangeDone(UserinfoAudit item) ;
}
