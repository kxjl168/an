/**
 * @(#)GradeMoney.java 2019/5/21
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 客户评分奖励规则
 *
 *@author shurui
 *@date 2019/5/21
 */
@Data
@TableName("t_grade_money")
public class GradeMoney {

    private String id;

    /**
     * '起始分数'
     */
    @TableField("startScore")
    private String startScore;

    /**
     * '结束分数'
     */
    @TableField("endScore")
    private String endScore;

    /**
     * '奖励金额'
     */
    @TableField("addMoney")
    private java.math.BigDecimal addMoney;

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
    private String searchScore;

}
