/**
 * @(#)AddressService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.Address;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface AddressService {

    /**
     * 根据name查询地址信息
     * @param city
     * @param county
     * @return
     */
    Address selectByName(String city, String county);
}
