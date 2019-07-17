/**
 * @(#)UserDepositTest.java 2019/1/31 14:18
 * <p>
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ztgm.lock;


import com.alibaba.fastjson.JSON;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.service.UserDepositService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 14:18
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDepositTest {

    @Autowired
    private UserDepositService userDepositService;

    @Test
    public void applyTest () {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("userId", "35743215");
        paramMap.put("depositMoney", "0");
        paramMap.put("bankName", "中国银行");
        paramMap.put("bankNum", "6222222222222222222222");
        paramMap.put("cardName", "张三");
        paramMap.put("cardUserPhone", "12345678998");

        BaseRequestDto depositDto = new BaseRequestDto();
        depositDto.setData(JSON.toJSON(paramMap).toString());

//        userDepositService.applyForDeposit(depositDto);
    }

    @Test
    public void listDriverDepositTest () {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("userId", "35743215");

        BaseRequestDto depositDto = new BaseRequestDto();
        depositDto.setData(JSON.toJSON(paramMap).toString());

        System.out.println(userDepositService.listDriverDeposit(depositDto));
    }

    @Test
    public void queryAvailableMoneyTest () {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("userId", "35743215");

        BaseRequestDto depositDto = new BaseRequestDto();
        depositDto.setData(JSON.toJSON(paramMap).toString());

        System.out.println(userDepositService.queryAvailableMoney(depositDto));
    }
}
