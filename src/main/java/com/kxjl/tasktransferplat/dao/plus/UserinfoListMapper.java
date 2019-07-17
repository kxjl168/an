/**
 * @(#)UserinfoListMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserinfoList;
/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface UserinfoListMapper  extends BaseMapper<UserinfoList> {

    /**
     * 分页查询
     * @param page
     * @param userinfoList
     * @return
     */
    IPage<UserinfoList> selectPage(Page page, UserinfoList userinfoList);

}
