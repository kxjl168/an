/**
 * @(#)AssessQuestionDQ.java 2019/5/21
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.ArrayList;

/** 问卷问题（封装视图类）
 *
 *
 *@author shurui
 *@date 2019/5/21
 */
@Data
public class AssessQuestionDQ {


    /**
     * 主键
     */
    private String id;

    /**
     * 试卷id
     */
    private String assessId;

    /**
     * '问题标题'
     */
    private String title;

    /**
     * '排序号'
     */
    private String sortstring;

    /**
     * '题目类别'
     */
    private String resultType;

    /**
     * '创建时间（insert 触发器 确定）'
     */
    private String createTime;

    /**
     * 选项
     */
    private ArrayList<AssessQuestionDetail>  options;

}
