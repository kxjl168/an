/**
 * @(#)PriceLockSmithBuildMapper.java 2019-04-17 11:50
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.PriceLockSmithBuild;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangyong
 * @version 1.0.0 2019-04-17 11:50
 * @since 1.0.0
 */
public interface PriceLockSmithBuildMapper extends BaseMapper<PriceLockSmithBuild> {
    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    @Override
    int insert(PriceLockSmithBuild priceLockSmithBuild);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    int deleteById(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    @Override
    int updateById(PriceLockSmithBuild entity);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    PriceLockSmithBuild selectById(Long id);
    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    List<PriceLockSmithBuild> selectByProvinceCode(String provinceCode);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    PriceLockSmithBuild selectByCityCode(@Param("cityCode") String cityCode,
                                         @Param("provinceCode") String provinceCode);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    List<PriceLockSmithBuild> selectList(PriceLockSmithBuild priceLockSmithBuild);

}
