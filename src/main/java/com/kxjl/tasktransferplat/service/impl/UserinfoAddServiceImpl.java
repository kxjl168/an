/**
 * @(#)UserinfoAddServiceImpl.java  2019-01-28 11:26:27
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.dao.plus.UserinfoAddMapper;
import com.kxjl.tasktransferplat.pojo.UserinfoAdd;
import com.kxjl.tasktransferplat.service.UserinfoAddService;

import java.util.HashMap;
import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:27
 * @since 1.0.0
 */
@Service
public class UserinfoAddServiceImpl implements UserinfoAddService {

    @Autowired
    private UserinfoAddMapper userinfoAddMapper;

    @Override
    public int insertbindBankCard(UserinfoAdd userinfoAdd) {
        return userinfoAddMapper.insert(userinfoAdd);
    }

    @Override
    public UserinfoAdd selectById(Long id) {
        return userinfoAddMapper.selectById(id);
    }

    @Override
    public int updatebindBankCard(UserinfoAdd userinfoAdd) {
        return userinfoAddMapper.updateById(userinfoAdd);
    }
}
