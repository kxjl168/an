package com.ztgm;

import javax.websocket.*;

/**
 * @Author: zhangyong
 * @Description:
 * @Data: Created in 16:45 2019/5/9
 */
@ClientEndpoint()
public class WebsocketClient {

    @OnOpen
    public void onOpen(Session session) {}
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Client onMessage: " + message);
    }
    @OnClose
    public void onClose() {}

}
