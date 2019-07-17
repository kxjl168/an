/*
 * @(#)UserOpinionService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.UserOpinion;

import java.util.List;

/**
 * 用户意见反馈表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface UserOpinionService {


    List<UserOpinion> selectUserOpinionList(UserOpinion query);

    /**
     * 查询客户意见列表
     * @param query
     * @return
     */
    List<UserOpinion> selectCustormerOpinionList(UserOpinion query);

    int deleteUserOpinion(UserOpinion query);
    


   	 UserOpinion selectUserOpinionById(Long id) ;


    /**
     * 用户意见反馈-提交
     * @param data
     * @return
     */
    int submitOpinion(UserOpinion data);
}
