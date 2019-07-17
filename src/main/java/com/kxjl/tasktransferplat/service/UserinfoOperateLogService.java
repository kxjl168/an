/*
 * @(#)UserinfoOperateLogService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;

import java.util.List;

/**
 * 锁匠变更日志.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface UserinfoOperateLogService {


    /**
     * 新增
     */
    JSONObject saveUserinfoOperateLog(UserinfoOperateLog query);

    /**
     * 更新信息
     */
    JSONObject updateUserinfoOperateLog(UserinfoOperateLog query);


    List<UserinfoOperateLog> selectUserinfoOperateLogList(UserinfoOperateLog query);

    int deleteUserinfoOperateLog(UserinfoOperateLog query);
    

   	 UserinfoOperateLog selectUserinfoOperateLogById(String id);



}
