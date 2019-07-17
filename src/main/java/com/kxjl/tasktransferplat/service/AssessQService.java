/**
 * @(#)AssessService.java 2019/5/17
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.kxjl.tasktransferplat.service;

import java.util.List;
import java.util.Map;

import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.AssessQuestionDetail;

/**
 *
 *
 *@author shurui
 *@date 2019/5/17
 */
public interface AssessQService {


    /** 查找客户评价表列表（全部信息）
     *
     * @return
     */
    List<AssessQuestion> selectAssessQuestion(AssessQuestion assessQuestion);

    /** tianjia
     *
     * @return
     */
    Map addAssessQuestion(AssessQuestion assessQuestion);

    /**
     * 开启或关闭调查表
     * @param assessQuestion
     * @return
     */
    Map openOrClose(AssessQuestion assessQuestion);


    Map delete(AssessQuestion assessQuestion);


    Map addQuestionDetails(String questionDetails,String assessTitle,String assessId);

    /**
     * 通过id查询评价问卷所有信息
     * @param id
     * @return
     */
    Map selectAssessWithDetails(String id);

    /**
     * 查询选项
     * @param id
     * @return
     */
    Map selectOption(String id);


    /**
     * 删除选项
     * @param id
     * @return
     */
    Map deleteDetail(String id,String assessId);


    Map updateOption(AssessQuestionDetail assessQuestionDetail);


    Map saveOption(AssessQuestionDetail assessQuestionDetail);

    /**
     * 查询所有题目或选项
     * @param assessId
     * @return
     */
    List<AssessQuestionDetail> selectQusOrOpt(String assessId,String type,String parentid);
}
