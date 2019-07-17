/**
 * @(#)UserMessage.java  2019-01-28 14:59:06
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("user_message")
public class UserMessage {

    /**
     * 消息id
     */
    private Long id;

    /**
     * 发送这条消息的人的id，关联manager表
     */
    private String sender;

    /**
     * 消息接收人id，关联userinfo表
     */
    private Long receiver;

    /**
     * 消息题目
     */
    private String messageTitle;

    /**
     * 消息体
     */
    private String messageContent;

    /**
     * 消息类型，1：系统消息，2：工单消息
     */
    private Long messageType;

    /**
     * 消息关联的工单id
     */
    private String orderInfoId;

    /**
     * 消息发送时间
     */
    private String createTime;

    /**
     * 是否已读，0：否，1：是
     */
    private Long hasRead;

}
