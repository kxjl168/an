/**
 * @(#)GradeMoneyService.java 2019/5/21
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.service;

import java.util.List;
import java.util.Map;

import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.GradeMoney;

/**
 *
 *
 *@author shurui
 *@date 2019/5/21
 */
public interface GradeMoneyService {

    /** 查找列表
     *
     * @return
     */
    List<GradeMoney> selectGradeMoneyList(GradeMoney gradeMoney);

    /**
     * 删除单条奖励规则
     * @param id
     * @return
     */
    Map delete(String id);

    /**
     * 通过分数查找所在分数段记录
     * @param score
     * @return
     */
    GradeMoney selectOneByScore(String score);

    /**
     * 添加记录
     * @param gradeMoney
     * @return
     */
    Map addGradeMoney(GradeMoney gradeMoney);


    Map changeGradeMoney(GradeMoney gradeMoney);
}
