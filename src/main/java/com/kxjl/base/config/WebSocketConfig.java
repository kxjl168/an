/**
 * @(#)WebSocketConfig.java 15:14 2019/5/9
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.service.ManagerMessageService;

/**
 * @Author: zhangyong
 * @Description:websocket 配置
 * @Data: Created in 15:14 2019/5/9
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter() ;
    }

    @Autowired
    public void setMessageService(ManagerMessageService managerMessageService){
        MyWebSocket.managerMessageService = managerMessageService;
    }

}
