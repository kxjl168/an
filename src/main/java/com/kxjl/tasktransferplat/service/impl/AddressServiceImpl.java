/**
 * @(#)AddressServiceImpl.java  2019-01-28 11:26:27
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.tasktransferplat.dao.plus.AddressMapper;
import com.kxjl.tasktransferplat.pojo.Address;
import com.kxjl.tasktransferplat.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:27
 * @since 1.0.0
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据name查询地址信息
     * @param city
     * @param county
     * @return
     */
    @Override
    public Address selectByName(String city, String county) {
        Address result;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("Name", county);
        queryWrapper.eq("level", 3);
        List<Address> countryList = addressMapper.selectList(queryWrapper);
        //如果没有查询到相应的区县，抛出异常。
        if (countryList.isEmpty()) {
            throw new RuntimeException("没有该区域，请检查");
        }
        //若查询出多个区县，则根据城市姓名查询
        if (countryList.size() > 1) {
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("Name", city);
            queryWrapper.eq("level", 2);
            List<Address> cityAddressList = addressMapper.selectList(queryWrapper);
            if (cityAddressList.isEmpty()) {
                throw new RuntimeException("没有该城市，请检查");
            } else {
                Address cityAddress = cityAddressList.get(0);
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("Name", county);
                queryWrapper.eq("level", 3);
                queryWrapper.eq("FatherId", cityAddress.getId());
                countryList = addressMapper.selectList(queryWrapper);
            }
        }
        result = countryList.get(0);
        if (result == null) {
            throw new RuntimeException("没有该市区对应关系");
        }
        return result;
    }
}
