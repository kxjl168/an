/*
 * @(#)MessageService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.Message;

import java.util.List;

/**
 * 消息表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface MessageService {


    /**
     * 新增
     */
    JSONObject saveMessage(Message query);

    /**
     * 更新信息
     */
    JSONObject updateMessage(Message query);


    List<Message> selectMessageList(Message query);

    int deleteMessage(Message query);
    


   	 Message selectMessageById(Long id) ;


    /**未读消息数
     * @param Id
     * @return
     */
    int selectUnreadMessageByCount(Long Id);
}
