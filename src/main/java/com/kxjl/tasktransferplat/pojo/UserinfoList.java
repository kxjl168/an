/**
 * @(#)UserinfoList.java  2019-01-28 14:59:06
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
@TableName("t_userinfo_list")
public class UserinfoList {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户id，外键 用户表
     */
    private Long userinfoId;

    /**
     * 记录类型，普通操作日志0，涉及金额变动日志1
     */
    private Long type;

    /**
     * 简短标题
     */
    private String title;

    /**
     * 详细内容，可空
     */
    private String content;

    /**
     * 操作人 外键 manager
     */
    private String operateUserId;

    /**
     * 操作时间（触发器）
     */
    private String operateTime;

}
