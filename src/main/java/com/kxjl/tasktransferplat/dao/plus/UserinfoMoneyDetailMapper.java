/**
 * @(#)UserinfoMoneyDetailMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface UserinfoMoneyDetailMapper  extends BaseMapper<UserinfoMoneyDetail> {

    /**
     * 分页查询
     * @param page
     * @param userinfoMoneyDetail
     * @return
     */
    IPage<UserinfoMoneyDetail> selectPage(Page page, UserinfoMoneyDetail userinfoMoneyDetail);


    /**
     * 分页查询
     * @param page
     * @param userinfoId
     * @return
     */
    IPage<Map<String,Object>> selectDriverList(@Param("page") Page page, @Param("userinfoId") Long userinfoId);
    
    
    /**
     * 锁匠账户流水查询
     * @param u
     * @return
     * @author zj
     * @date 2019年6月21日
     */
    List<UserinfoMoneyDetail> selectDetailList(Userinfo u);
}
