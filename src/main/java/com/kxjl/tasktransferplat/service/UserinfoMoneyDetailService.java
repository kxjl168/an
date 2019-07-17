/**
 * @(#)UserinfoMoneyDetailService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface UserinfoMoneyDetailService {

    /**
     * 账单明细
     * @return
     */
    IPage<Map<String,Object>> listDriver(Page page);


    /**
     * 费用历史
     * @param id
     * @return
     */
    List<UserinfoMoneyDetail> selectMoneyList(Userinfo user);
}
