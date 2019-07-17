/**
 * @(#)MobileLockerServerServiceImpl.java 2019/5/27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.MobileLockerServerService;
import com.kxjl.tasktransferplat.util.ParamUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/27
 */
@Service
public class MobileLockerServerServiceImpl implements MobileLockerServerService {

    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerAppointmentTime(BaseRequestDto requestDto){
        // 处理json参数
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);
        //解析封装Dao层操作入参
        Orderinfo orderinfo = new Orderinfo();
        orderinfo.setId(String.valueOf(paramMap.get("orderId")));
        orderinfo.setLockerAppointmentTime(String.valueOf(paramMap.get("lockerAppointmentTime")));
        try {
            int i = orderinfoMapper.updateById(orderinfo);
        } catch (Exception e) {
            throw new RuntimeException("{}订单修改 - 锁匠预约时间出错" + paramMap.get("orderId"), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void beforeSererSign(BaseRequestDto requestDto){
        // 处理json参数
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);
        //解析封装Dao层操作入参
        LocalDateTime rightNow= LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String arravieTime = formatter.format(rightNow);//打卡签到时间
        Orderinfo orderinfo = new Orderinfo();
        orderinfo.setId(String.valueOf(paramMap.get("orderId")));//订单id
        orderinfo.setStartimgs(paramMap.get("img"));//服务前打卡图片
        orderinfo.setArravieTime(arravieTime);//上门安装前打卡时间

        try {
            int i = orderinfoMapper.updateById(orderinfo);
        } catch (Exception e) {
            throw new RuntimeException("{}订单修改 - 锁匠上门前安装打卡出错" + paramMap.get("orderId"), e);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void beforeInstallSign(BaseRequestDto requestDto){
        // 处理json参数
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);
        //解析封装Dao层操作入参
        Orderinfo orderinfo = new Orderinfo();
        orderinfo.setId(String.valueOf(paramMap.get("orderId")));//订单id
        orderinfo.setLockimgs(paramMap.get("img"));//安装前产品图片

        try {
            int i = orderinfoMapper.updateById(orderinfo);
        } catch (Exception e) {
            throw new RuntimeException("{}订单修改 - 锁匠安装前打卡出错" + paramMap.get("orderId"), e);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterInstallSign(BaseRequestDto requestDto){
        // 处理json参数
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
        //解析封装Dao层操作入参
        Orderinfo orderinfo = new Orderinfo();
        orderinfo.setId(String.valueOf(paramMap.get("orderId")));//订单id
        orderinfo.setDoneimgs(paramMap.get("img"));//安装后完工产品图片

        try {
            int i = orderinfoMapper.updateById(orderinfo);
        } catch (Exception e) {
            throw new RuntimeException("{}订单修改 - 锁匠安装后完工打卡出错" + paramMap.get("orderId"), e);
        }
    }

}
