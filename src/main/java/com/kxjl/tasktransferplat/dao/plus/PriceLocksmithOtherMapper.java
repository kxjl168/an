/**
 * @(#)PriceLocksmithOtherMapper.java 2019-04-16 16:16:34
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.PriceLocksmithOther;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019-04-16 16:16:34
 * @since 1.0.0
 */
public interface PriceLocksmithOtherMapper extends BaseMapper<PriceLocksmithOther> {

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int deleteByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    @Override
    int insert(PriceLocksmithOther priceLocksmithOther);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    PriceLocksmithOther selectByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int updateByPrimaryKey(PriceLocksmithOther priceLocksmithOther);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    List<PriceLocksmithOther> selectList(PriceLocksmithOther priceLocksmithOther);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    PriceLocksmithOther selectByType(@Param("serverType") String serverType,@Param("parentType") Integer parentType);

}
