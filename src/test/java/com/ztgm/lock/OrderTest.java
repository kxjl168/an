/**
 * @(#)OrderTest.java 2019/1/30 13:51
 * <p>
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ztgm.lock;


import com.alibaba.fastjson.JSON;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.OrderInfoOperateService;
import com.kxjl.tasktransferplat.service.OrderinfoService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/30 13:51
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private OrderInfoOperateService orderInfoOperateService;

    @Test
    public void orderListTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("pageSize", "10");
        paramMap.put("pageNum", "1");
        paramMap.put("type", "1");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
        System.out.println(orderinfoService.orderList(baseRequestDto));
    }

    @Test
    public void orderDetailTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("orderId", "572099584021495808");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
        Orderinfo orderinfo = orderinfoService.orderDetail(baseRequestDto);
        System.out.println(orderinfo);
    }

    @Test
    public void orderCancelTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("orderId", "570246275253927936");
        paramMap.put("mark", "654321");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
        orderInfoOperateService.orderCancel("552802544670212096", baseRequestDto);
    }

    @Test
    public void orderAddPriceTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("orderId", "570246275253927936");
        paramMap.put("description", "你看这个碗它又大又圆，就像这个面它又长又宽");
        paramMap.put("type", "1");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
        orderInfoOperateService.orderAddPrice("552802544670212096", baseRequestDto);
    }

    @Test
    public void orderAcceptTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("orderId", "123456");
        paramMap.put("lockerId", "35743215");
        paramMap.put("description", "123456789456123");
        paramMap.put("type", "1");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
//        orderinfoService.orderAccept(baseRequestDto);
    }

    @Test
    public void orderDoneTest() {
        Map<String, String> paramMap = new HashMap<>(16);
        paramMap.put("orderId", "123456");
        BaseRequestDto baseRequestDto = new BaseRequestDto();
        baseRequestDto.setData(JSON.toJSONString(paramMap));
//        orderinfoService.orderDone(baseRequestDto);
    }
}
