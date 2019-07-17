/**
 * @(#)AssessQuestion.java 2019/5/17
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;

/**
 *
 *
 *@author shurui
 *@date 2019/5/17
 */
@Data
@TableName("t_assess_question")
public class AssessQuestion {

    /**
     * 主键
     */
    private String id;

    /**
     * '问卷调查标题'
     */
    @TableField("title")
    private String title;

    /**
     * ''是否当前使用0：未启用，1：启用，全表只能有一条记录为1''
     */
    @TableField("curUse")
    private String curUse;

    /**
     * '创建时间（insert 触发器 确定）'
     */
    @TableField("CreateTime")
    private String createTime;

    /**
     * '上次更新时间（update 触发器 确定）'
     */
    @TableField("UpdateTime")
    private String updateTime;

    /**
     * '数据状态，1：可用，0：禁用，2：删除'
     */
    @TableField("DataState")
    private Integer dataState;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    /**
     * 所有问题
     */
    @TableField(exist = false)
    private ArrayList<AssessQuestionDQ> questions;

}
