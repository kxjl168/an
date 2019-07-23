package com.kxjl.base.websocket;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.kxjl.video.pojo.ClientInfo;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
@Component
public class WebSocketSvr {
	private static Logger logger = Logger.getLogger(WebSocketSvr.class);
	
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setSession(session);
        clientInfo.setType(0);
        clientInfo.setMsgTime(System.currentTimeMillis());
        SocketClient.getInstance().putSocketInfo(session, clientInfo, 0);
        
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        SocketClient.getInstance().removeClientSocket(this.session);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	//解析message
    	logger.info("来自客户端的消息:" + message);
    	JSONObject jsonIn;
		try {
			ClientInfo clientInfo = SocketClient.getInstance().getSocketInfo(session);
			if(clientInfo != null) {
				jsonIn = new JSONObject(new String(message));
				int type = jsonIn.getInt("type");
				switch(type) {
        		case 1:{
					JSONObject jsonUserInfo = jsonIn.getJSONObject("userinfo");
					clientInfo.setIdentyID(jsonUserInfo.getString("IdentyID"));
					clientInfo.setName(jsonUserInfo.getString("name"));
					clientInfo.setMobilePhone(jsonUserInfo.getString("mobilePhone"));
					clientInfo.setIdentyID(jsonUserInfo.getString("IdentyID"));
					clientInfo.setWeichatID(jsonIn.getString("weichatID"));
					JSONObject jsonLocation = jsonIn.getJSONObject("location");
					clientInfo.setArea(jsonLocation.getString("area"));
					clientInfo.setLongitude(jsonLocation.getString("longitude"));
					clientInfo.setLatitude(jsonLocation.getString("latitude"));
					clientInfo.setNote(jsonLocation.getString("note"));	
					clientInfo.setType(1);
					clientInfo.setMsgTime(System.currentTimeMillis());
					SocketClient.getInstance().putSocketClinetInfo(session, 1, clientInfo);
					break;
        			}        		
        		case 2:{
        			String ackCode = jsonIn.getString("ackCode");
        			if(ackCode.equals("204")) {
        				//挂断
        				SocketClient.getInstance().setOnlineSeatsStatus(clientInfo.getUserid(), 4);
        				SocketClient.getInstance().CallResponseInfo("204", clientInfo);
        			}else if(ackCode.equals("203")) {
        				//取消
        				SocketClient.getInstance().CallResponseInfo("203", clientInfo);
        			}
        			break;
        		}
        		default:
        			break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
        /*try {
			sendMessage("1231231231");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
    	logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
    	WebSocketSvr.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketSvr.onlineCount--;
    }
}
