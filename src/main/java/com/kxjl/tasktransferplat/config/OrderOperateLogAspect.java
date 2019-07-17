/**
 * @(#)OrderOperateLogAspect.java 2019/1/29 15:34
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.config;


import com.alibaba.fastjson.JSON;
import com.kxjl.tasktransferplat.annotation.OrderNeedLog;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/29 15:34
 * @since 1.0.0
 */
@Component
@Aspect
public class OrderOperateLogAspect {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Autowired
    private OrderinfoOperateLogMapper orderinfoOperateLogMapper;


    @AfterReturning(value = "@annotation(orderNeedLog)")
    public void afterPoint(JoinPoint point, OrderNeedLog orderNeedLog) {
        Object[] args = point.getArgs();
        //操作人id
        String operateUserId = String.valueOf(args[0]);

        Map<String, String> paramMap = (Map<String, String>) JSON.parse(((BaseRequestDto) args[1]).getData());
        String orderId = String.valueOf( paramMap.get("orderId"));

        //插入工单日志表
        OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
        orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
        orderinfoOperateLog.setOrderinfoId(orderId);
        orderinfoOperateLog.setType(orderNeedLog.type());
        orderinfoOperateLog.setSubType(orderNeedLog.subType());
        orderinfoOperateLog.setOperateUserId(operateUserId);
        
        String auditId = String.valueOf( paramMap.get("auditId"));
        if(auditId!=null&&!auditId.equals("")&&!auditId.equals("null"))
        {
            orderinfoOperateLog.setAuditId(Long.parseLong(auditId));
        }
        
        
        orderinfoOperateLogMapper.insert(orderinfoOperateLog);

        //插入工单确认日志，说明该操作是平台单条创建工单，那么直接确认
        if (orderNeedLog.type() == 1 && orderNeedLog.subType() == 0) {
        	if(true)//不自动确认
        	return;
        	try {
        		//zj 确认的记录延后1s
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(orderId);
            orderinfoOperateLog.setType(orderNeedLog.type());
            orderinfoOperateLog.setSubType(5);
            orderinfoOperateLog.setOperateUserId(operateUserId);
            orderinfoOperateLogMapper.insert(orderinfoOperateLog);
        }
    }
}
