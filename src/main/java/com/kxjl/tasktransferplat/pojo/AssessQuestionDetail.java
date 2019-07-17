/**
 * @(#)AssessQuestionDetail.java 2019/5/20
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 问题/选项
 *
 *@author shurui
 *@date 2019/5/20
 */
@Data
@TableName("t_assess_question_detail")
public class AssessQuestionDetail implements Serializable {

    private String id;

    /**
     * '所属问卷ID'
     */
    private String assessId;

    /**
     * '问题或者选项描述'
     */
    private String title;


    /**
     * '类型：1，问题；  2，选项；'
     */
    private String type;

    /**
     * '问题结果：0，单选； 1，可多选；2：手动输入'
     */
    private String resultType;

    /**
     * '1:选择此选项是否立刻生成售后单据'
     */
    private String addService;

    /**
     * '当前为选项时，对应本表的问题id'
     */
    private String parentid;

    /**
     * '排序号'
     */
    private String sortstring;

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

    /**
     * '分数'（选择为选项时存在）
     */
    @TableField("score")
    private Long score;

    public AssessQuestionDetail(String id, String assessId, String title, String type, String resultType, String addService, String parentid, String sortstring, String createTime, String updateTime, Integer dataState, Long score) {
        this.id = id;
        this.assessId = assessId;
        this.title = title;
        this.type = type;
        this.resultType = resultType;
        this.addService = addService;
        this.parentid = parentid;
        this.sortstring = sortstring;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.dataState = dataState;
        this.score = score;
    }

    public AssessQuestionDetail() {
    }
}
