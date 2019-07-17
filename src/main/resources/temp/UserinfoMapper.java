/**
 * @(#)UserinfoMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ztgm.tasktransferplat.dao.plus;

import com.ztgm.tasktransferplat.pojo.Userinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface UserinfoMapper  extends BaseMapper<Userinfo> {

    /**
     * 分页查询
     * @param page
     * @param userinfo
     * @return
     */
    IPage<Userinfo> selectPage(Page page, Userinfo userinfo);

}
