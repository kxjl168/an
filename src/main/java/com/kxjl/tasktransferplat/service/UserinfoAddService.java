/**
 * @(#)UserinfoAddService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import java.util.HashMap;

import com.kxjl.tasktransferplat.pojo.UserinfoAdd;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface UserinfoAddService {

    /**
     * 绑定银行卡
     * @param userinfoAdd
     */
    int insertbindBankCard(UserinfoAdd userinfoAdd);

    UserinfoAdd selectById(Long id);

    int updatebindBankCard(UserinfoAdd userinfoAdd);
}
