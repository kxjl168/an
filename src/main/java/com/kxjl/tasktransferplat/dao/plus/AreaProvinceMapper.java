/**
 * @(#)AreaProvinceMapper.java  2019-02-18 14:14:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.AreaProvince;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019-02-18 14:14:03
 * @since 1.0.0
 */
public interface AreaProvinceMapper  extends BaseMapper<AreaProvince> {

    /**
     * 查询所有有安装业务的省份
     * @return
     */
    List<AreaProvince> lockInstallProvince();
}
