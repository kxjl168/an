/**
 * @(#)PriceLockCompanyMapper.java 2019-04-15 11:50
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangyong
 * @version 1.0.0 2019-04-15 11:50
 * @since 1.0.0
 */
public interface PriceLockCompanyMapper extends BaseMapper<PriceLockCompany> {
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
    int deleteByLockCompanyId(Long lockCompanyId);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    @Override
    int insert(PriceLockCompany priceLockCompany);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    PriceLockCompany selectByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int updateByPrimaryKey(PriceLockCompany priceLockCompany);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int updateByLockCompanyIdAndType(PriceLockCompany priceLockCompany);


    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    List<PriceLockCompany> selectByLockCompanyId(Long lockCompanyId);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    LockCompany selectByLockCompanyIdAndType(@Param("lockCompanyId") Long lockCompanyId, @Param("serverType") Integer serverType, @Param("parentType") Integer parentType);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    List<LockCompany> selectList(LockCompany lockCompany);

}
