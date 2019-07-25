package com.kxjl.base.websocket;

import com.alibaba.fastjson.JSON;
import com.kxjl.video.pojo.VideoalarmTalkinfo;
import com.kxjl.video.service.VideoalarmTalkinfoService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 页面聊天websocket
 * @author zj
 *
 */
@ServerEndpoint("/talkwebsocket/{userId}")
@Component
public class MyWebSocket {

    private static Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    public static VideoalarmTalkinfoService videoalarmTalkinfoService;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 线程安全的Map 存储用户信息 ，一个用户id，只能一处登录
     */
    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        //加入set中;
        webSocketMap.put(userId, session);
        //在线数加1
        incrOnlineCount();
        logger.info("有新连接加入!当前在线人数为：" + getOnlineCount());
        sendInitMessage(userId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        Map<String, String> map = session.getPathParameters();
        //从set中删除
        webSocketMap.remove(map.get("userId"));
        decOnlineCount();
        logger.info("一个连接已关闭,当前在线计数：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("来自客户端的消息：", message);
        Map<String, String> map = session.getPathParameters();
    }
    
    

	@OnMessage
	public void onMessage(byte[] messages, Session session) {
		try {
			 String strmsg=new String(messages, "utf-8");
			System.out.println("接收到消息:" +strmsg);

			transferMessage(strmsg,true);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 发自定义消息
	 */
	public static void sendByteMessage(String text, String userId) throws IOException {
		try {
			Session session = webSocketMap.get(userId);
			if (session != null && session.isOpen()) {
				// session.getBasicRemote().sendText(text);

				ByteBuffer bf = ByteBuffer.wrap(text.getBytes("utf-8"));
				session.getBasicRemote().sendBinary(bf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析数据
	 * 
	 * @param message
	 * @author zj
	 * @date 2019年7月16日
	 */
	public static void transferMessage(String message, boolean isbyte) {
		JSONObject jmsg = null;
		try {
			jmsg = new JSONObject(message);
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}

		if (jmsg != null) {

			String uid = jmsg.optString("uid");
			String tid = jmsg.optString("tid");
			String msg = jmsg.optString("msg");
			String type = jmsg.optString("msgtype");
			String fileurl = jmsg.optString("fileurl");
			
			
			//web 发送的，计入数据库
			if(type.equals("websend"))
			{
				VideoalarmTalkinfo talkinfo=new VideoalarmTalkinfo();
				talkinfo.setAlarmId(Integer.parseInt( tid));
				talkinfo.setMsgType(type);
				talkinfo.setTalkType("2");
				talkinfo.setMsgContent(msg);
				talkinfo.setFileUrl(fileurl);
				videoalarmTalkinfoService.saveVideoalarmTalkinfo(talkinfo);
			}
			
			
			logger.info("talk info" + jmsg.toString());
			// System.out.println(message);
			try {
				if (isbyte)
					sendByteMessage(message, tid);
				else
					sendMessage(message, tid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        Map<String, String> map = session.getPathParameters();
        //从set中删除
        webSocketMap.remove(map.get("userId"));
        logger.error("WebSocket发生错误");
        error.printStackTrace();
    }

    /**
     * 发自定义消息
     */
    public static void sendMessage(String text, String userId) throws IOException {
        try {
        	 logger.info("sendMessage：", text);
            Session session = webSocketMap.get(userId);
            if (session != null && session.isOpen()) {
            	
           	 logger.info("sendMessage2：", text);
                session.getBasicRemote().sendText(text);
            }
        } catch (Exception e) {
       	 logger.info("sendMessage3：", text);
            e.printStackTrace();
        }
    }

    private void sendInitMessage(String userId) {
        try {
           // ManagerMessage managerMessage = new ManagerMessage();
           // managerMessage.setUserId(userId);
          //  List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
          //  MyWebSocket.sendMessage(JSON.toJSONString(list), userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return MyWebSocket.onlineCount;
    }

    private static synchronized void incrOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    private static synchronized void decOnlineCount() {
        MyWebSocket.onlineCount--;
    }


}
