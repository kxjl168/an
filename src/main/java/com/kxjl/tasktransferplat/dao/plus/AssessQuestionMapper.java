/**
 * @(#)AddressMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Address;
import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.AssessQuestionDetail;

import java.util.List;

/**
 * @author shurui
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface AssessQuestionMapper extends BaseMapper<AssessQuestion> {

    List<AssessQuestion> selectList(AssessQuestion assessQuestion);

    void batchInsertAQDetails(List<AssessQuestionDetail> assessQuestionDetails);

    void batchDeletetAQDetails(List<AssessQuestionDetail> assessQuestionDetails);

}
