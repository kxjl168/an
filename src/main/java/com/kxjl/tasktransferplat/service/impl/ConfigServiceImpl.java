/**
 * @(#)ConfigServiceImpl.java  2019-01-28 11:26:26
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.dao.plus.ConfigMapper;
import com.kxjl.tasktransferplat.service.ConfigService;
/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:26
 * @since 1.0.0
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

}
