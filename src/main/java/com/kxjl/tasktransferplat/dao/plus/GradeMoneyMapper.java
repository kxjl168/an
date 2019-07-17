/**
 * @(#)AddressMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.GradeMoney;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author shurui
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface GradeMoneyMapper extends BaseMapper<GradeMoney> {

    List<GradeMoney> selectList(GradeMoney gradeMoney);


    @Select("select * from t_grade_money t where t.startScore <= #{score} and t.endScore >= #{score}")
    GradeMoney selectOneByScore(String score);
}
