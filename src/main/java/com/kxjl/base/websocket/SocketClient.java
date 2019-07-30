package com.kxjl.base.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.kxjl.base.util.ByteIntChange;
import com.kxjl.base.util.aes.AESEncryptUtil;
import com.kxjl.video.pojo.ClientInfo;
import com.kxjl.video.service.OnlineSeatsService;

public class SocketClient extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SocketClient instance = null;
	public static String aesPassword = "kqdas@hlj1234567";
	
	private static Logger logger = Logger.getLogger(SocketClient.class);
	
	//客户端连接信息HashMap
	private static HashMap<SocketChannel, ClientInfo> clientList = new HashMap<SocketChannel, ClientInfo>();
	private static HashMap<SocketChannel, ClientInfo> wecharClientList = new HashMap<SocketChannel, ClientInfo>();
	private static HashMap<Session, ClientInfo> sessionClientList = new HashMap<Session, ClientInfo>();
	private static HashMap<Session, ClientInfo> wecharSessionClientList = new HashMap<Session, ClientInfo>();
	private static List<String> wecharClientTime = new ArrayList<String>();//微信ID
	//private static List<String> onlineSteatsFree = new ArrayList<String>();//在线坐席ID
	//微信ID
	private static HashMap<String, ClientInfo> videoAlarmClientList = new HashMap<String, ClientInfo>();
	private static HashMap<SocketChannel, ClientInfo> onlineSteatsList = new HashMap<SocketChannel, ClientInfo>();
	
	OnlineSeatsService onlineSeatsService;
	String MEDIA_SERVER_URL;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//autowire起作用
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
						config.getServletContext());
		instance = this;
	}
	
	public static SocketClient getInstance() {
		// 判断是否实例化
		if (instance == null) {
			instance = new SocketClient();
		}
		return instance;
	}	
	
	public synchronized void putSocketInfo(SocketChannel channel,
			ClientInfo pcInfo, int clienttype) {
		clientList.put(channel, pcInfo);
		if(clienttype == 1) {
			wecharClientList.put(channel, pcInfo);
		}else if(clienttype == 3) {
			onlineSteatsList.put(channel, pcInfo);
			if(onlineSteatsList.size() == 1) {
				onlineSeatsService.updateOnlineSteatsToNull(pcInfo.getUserid());
			}
		}
	}
	
	public synchronized void putSocketInfo(Session channel,
			ClientInfo pcInfo, int clienttype) {
		sessionClientList.put(channel, pcInfo);
		if(clienttype == 1){
			wecharSessionClientList.put(channel, pcInfo);
		}
		
	}

	public synchronized ClientInfo getSocketInfo(SocketChannel channel) {
		return clientList.get(channel);
	}
	
	public synchronized ClientInfo getSocketInfo(Session channel) {
		return sessionClientList.get(channel);
	}

	/**
	 * 检测超时
	 */
	public synchronized void checkOverTime() {
		Iterator<SocketChannel> keys=onlineSteatsList.keySet().iterator();
		long currentTime = System.currentTimeMillis();
		while(keys.hasNext()){ 
			SocketChannel key = keys.next();
			ClientInfo pc=onlineSteatsList.get(key);
			if(pc != null){
				if (currentTime - pc.getMsgTime()> (90 * 1000)) {//设置会话超时时间为90s
					//onlineSteatsFree.remove(pc.getUserid());
					removeClientSocket(key);							
					continue;
				}
			}
		}
		keys=onlineSteatsList.keySet().iterator();
		while(keys.hasNext()){ 
			SocketChannel key = keys.next();
			ClientInfo pc=onlineSteatsList.get(key);
			if(pc != null){
				if (pc.getStatus() != 0 && pc.getCloseTime()!= 0 &&
						currentTime - pc.getCloseTime() > (60 * 1000)) {//设置会话超时时间为60s
					pc.setStatus(0);
					pc.setCloseTime(0);
				}
			}
			
		}
	}
	
	/**
	 * 分配在线坐席
	 */
	public synchronized void DistributionOnlineSeats() {
		int iFlag =-1;
		if(wecharClientTime.size() > 0) {
			for(int i=0; i<wecharClientTime.size(); i++ ) {
				//if(onlineSteatsFree.size() > 0) {
					//获取在线坐席
					//String userid = onlineSteatsFree.get(0);
					Iterator<SocketChannel> keys=onlineSteatsList.keySet().iterator();
					while(keys.hasNext()){ 
						SocketChannel key = keys.next();
						ClientInfo pc=onlineSteatsList.get(key);
						if(pc.getStatus() == 0) {
							//获取在线坐席
							//发送连接信息
							long timeNow = System.currentTimeMillis();
							ClientInfo client = videoAlarmClientList.get(wecharClientTime.get(i));
							SendOnlineWechatAlarmInfo(pc.getChannel(), client,timeNow);							
							client.setAlarmStatus(0);
							client.setAcceptTime(timeNow);
							client.setCloseTime(0);
							//onlineSteatsFree.remove(0);
							pc.setStatus(1);
							iFlag = i;
							break;
						}
					}
					if(iFlag == -1) {
						//onlineSteatsFree.clear();
						ClientInfo client = videoAlarmClientList.get(wecharClientTime.get(i));
						client.setAlarmStatus(7);
						
					}
				//}else {
					//ClientInfo client = videoAlarmClientList.get(wecharClientTime.get(i));
					//client.setAlarmStatus(7);
				//}
			}
		}
		if(iFlag >= 0) {
			for(int i=0; i<=iFlag; i++ ) {
				wecharClientTime.remove(i);
			}
		}
		 
	}

	public synchronized void putSocketClinetInfo(SocketChannel channel, int clienttype, ClientInfo clientInfo) {
		if(clienttype == 1) {
			if(wecharClientList.get(channel) != null) {
				//相同socket发过来的信息不处理
				logger.info("微信报警端再次发送连接UserID请求");
			}else {
				//查找该用户有没有连接
				for(ClientInfo info : wecharClientList.values()) {
					if(channel == info.getChannel()) {
						break;
					}
					if(info.getUserid().equals(clientInfo.getUserid())){
						//清除old连接
						removeClientSocket(info.getChannel());
						break;
					}
				}
				//根据位置信息获取空闲的在线坐席
				//获取空闲在线坐席
				for(ClientInfo info : onlineSteatsList.values()) {
					if(info.getStatus() == 0) {
						//找到空闲在线坐席
						long timeNow = System.currentTimeMillis() % 1000000000;
						SendOnlineWechatAlarmInfo(info.getChannel(), clientInfo, timeNow);
						clientInfo.setAcceptTime(timeNow);
						info.setAcceptTime(timeNow);
						info.setCloseTime(0);
						//设置在线坐席状态
						info.setStatus(1);
						SendWeChatAlarmStatus(clientInfo.getChannel(), "101", timeNow);
						clientInfo.setOtherchannel(info.getChannel());
						info.setOtherchannel(clientInfo.getChannel());
						break;
					}				
				}
				putSocketInfo(channel, clientInfo, clienttype);
			}
			
		}
		if(clienttype == 3) {
			if(onlineSteatsList.get(channel) != null) {
				//相同socket发过来的信息不处理
				logger.info("在线坐席再次发送连接UserID请求");
			}else {
				//查找该用户有没有连接
				for(ClientInfo info : onlineSteatsList.values()) {
					if(info.getUserid().equals(clientInfo.getUserid())){
						//清除old连接
						removeClientSocket(info.getChannel());
						break;
					}
				}
				//更新数据库  设置在线坐席状态为0	
				clientInfo.setStatus(0);
				onlineSeatsService.SetOnlineStatus(clientInfo.getUserid(), "0");
							
				putSocketInfo(channel, clientInfo, clienttype);
			}
		}
		
	}
	
	public synchronized void putSocketClinetInfo(Session session, int clienttype, ClientInfo clientInfo) {
		if(clienttype == 1) {
			if(wecharSessionClientList.get(session) != null) {
				//相同socket发过来的信息不处理
				logger.info("微信报警端再次发送连接UserID请求");
			}else {
				//查找该用户有没有连接
				for(ClientInfo info : sessionClientList.values()) {
					if(session == info.getSession()) {
						break;
					}
					if(info.getIdentyID().equals(clientInfo.getIdentyID())){
						//清除old连接
						removeClientSocket(info.getSession());
						break;
					}
				}
				//根据位置信息获取空闲的在线坐席
				boolean flag = false;
				//获取空闲在线坐席
				for(ClientInfo info : onlineSteatsList.values()) {
					if(info.getStatus() == 0) {
						//找到空闲在线坐席
						long timeNow = System.currentTimeMillis();
						SendOnlineWechatAlarmInfo(info.getChannel(), clientInfo, timeNow);
						//设置在线坐席状态
						info.setStatus(1);
						SendWeChatAlarmStatus(clientInfo.getSession(), "101", timeNow);
						clientInfo.setAcceptTime(timeNow);
						info.setAcceptTime(timeNow);
						info.setCloseTime(0);
						clientInfo.setOtherchannel(info.getChannel());
						info.setSession(clientInfo.getSession());
						flag = true;
						break;
					}				
				}
				if(flag == false) {
					//没有空闲坐席
					putVideoAlarm(clientInfo);
					SendWeChatAlarmStatus(session, "400", 0);
				}
				putSocketInfo(session, clientInfo, clienttype);
			}
			
		}
		
	}

	public void SendWeChatAlarmStatus(SocketChannel channel, String info, long timeNow ) {
		if(channel == null)
			return;
		JSONObject json = new JSONObject();
		try {
			json.put("type",1);
			json.put("ackCode",info);
			json.put("rtmpPushUri",MEDIA_SERVER_URL + String.valueOf(timeNow));
			json.put("rtmpPlayUri",MEDIA_SERVER_URL + String.valueOf(timeNow + 1));
			ByteBuffer writeBuf = ByteBuffer.allocate((json.toString()).getBytes().length);
            writeBuf.put((json.toString()).getBytes());
            writeBuf.flip();
            channel.write(writeBuf);
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void SendWeChatAlarmStatus(Session session, String info, long timeNow) {
		if(session == null)
			return;
		JSONObject json = new JSONObject();
		try {
			if(info.equals("200")) {
				json.put("type",1);
				json.put("ackCode",info);
				json.put("rtmpPushUri",MEDIA_SERVER_URL + String.valueOf(timeNow));
				json.put("rtmpPlayUri",MEDIA_SERVER_URL + String.valueOf(timeNow + 1));
			}else {
				json.put("type",1);
				json.put("ackCode",info);
			}			
			session.getBasicRemote().sendText(json.toString());
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	private void SendOnlineWechatAlarmInfo(SocketChannel channel, ClientInfo clientInfo, long timeNow) {
		if(channel == null)
			return;
		JSONObject json = new JSONObject();
		try {
			json.put("type",1);
			JSONObject jsonUserInfo = new JSONObject();
			jsonUserInfo.put("IdentyID", clientInfo.getIdentyID());
			jsonUserInfo.put("name", clientInfo.getName());
			jsonUserInfo.put("mobilePhone", clientInfo.getMobilePhone());
			json.put("userinfo",jsonUserInfo);
			JSONObject jsonLocation = new JSONObject();
			jsonLocation.put("area", clientInfo.getArea());
			jsonLocation.put("latitude", clientInfo.getLatitude());
			jsonLocation.put("longitude", clientInfo.getLongitude());
			jsonLocation.put("note", clientInfo.getNote());
			json.put("location",jsonLocation);			
			json.put("weichatID",clientInfo.getWeichatID());
			json.put("rtmpPushUri",MEDIA_SERVER_URL + String.valueOf(timeNow + 1));
			json.put("rtmpPlayUri",MEDIA_SERVER_URL + String.valueOf(timeNow));
			clientInfo.setRtmpPlayUri(MEDIA_SERVER_URL + String.valueOf(timeNow + 1));
			clientInfo.setRtmpPushUri(MEDIA_SERVER_URL + String.valueOf(timeNow));
			byte[] v_datapath = AESEncryptUtil.encryptB(json.toString(), SocketClient.aesPassword);
            byte[] buffResponse1 = new byte[4 + v_datapath.length];
            byte[] bLength = ByteIntChange.IntToByteFour(v_datapath.length);
    		for(int i=0; i<bLength.length; i++){
    			buffResponse1[i] = bLength[i];
    		}
    		
    		for (int i = 0; i < v_datapath.length; i++) {
    			buffResponse1[4+i] = v_datapath[i];
    		}
    		ByteBuffer writeBuf = ByteBuffer.allocate(buffResponse1.length);
            writeBuf.put(buffResponse1);
            writeBuf.flip();
            channel.write(writeBuf);
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 移除连接
	 * @param channel
	 * @param clienttype
	 */
	public void removeClientSocket(SocketChannel channel) {
		ClientInfo clientInfo = clientList.get(channel);
		if(clientInfo != null) {
			if(clientInfo.getChannel() != null) {									
				RemoveSocket(clientInfo.getOtherchannel());
				RemoveSocket(clientInfo.getSession());
			}
			if(clientInfo.getType() == 1) {
				wecharClientList.remove(channel);
			}else if(clientInfo.getType() == 3){
				onlineSeatsService.SetOnlineStatus(clientInfo.getUserid(), "4");
				String infoResponse = "{\"type\":6}";
				byte[] v_datapath = AESEncryptUtil.encryptB(infoResponse, SocketClient.aesPassword);	            
	            byte[] buffResponse1 = new byte[4 + v_datapath.length];
	            byte[] bLength = ByteIntChange.IntToByteFour(v_datapath.length);
	    		for(int i=0; i<bLength.length; i++){
	    			buffResponse1[i] = bLength[i];
	    		}
	    		
	    		for (int i = 0; i < v_datapath.length; i++) {
	    			buffResponse1[4+i] = v_datapath[i];
	    		}
	    		ByteBuffer writeBuf = ByteBuffer.allocate(buffResponse1.length);
	            writeBuf.put(buffResponse1);
	            writeBuf.flip();
	            try {
					channel.write(writeBuf);
				} catch (IOException e) {
					e.printStackTrace();
				}
				onlineSteatsList.remove(channel);
				
			}
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}			
		clientList.remove(channel);	
	}
	
	public void removeClientSocket(Session session) {
		ClientInfo clientInfo = sessionClientList.get(session);
		if(clientInfo != null) {
			if(clientInfo.getOtherchannel() != null) {									
				RemoveSocket(clientInfo.getOtherchannel());
			}
			wecharSessionClientList.remove(session);
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		sessionClientList.remove(session);		
	}

	/**
	 * 移除另外一端连接
	 * @param channel
	 */
	private void RemoveSocket(SocketChannel channel) {
		if(channel == null)
			return;
		ClientInfo clientInfo = clientList.get(channel);
		if(clientInfo != null) {
			if(clientInfo.getType() == 1) {
				//发送微信信息  连接异常断开
				wecharClientList.remove(channel);
				clientList.remove(channel);
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(clientInfo.getType() == 3){
				if(clientInfo.getStatus() == 1) {
					clientInfo.setStatus(0);
					clientInfo.setCloseTime(0);
				}else {
					clientInfo.setCloseTime(System.currentTimeMillis());
				}
				clientInfo.setOtherchannel(null);
				
			}
		}		
	}
	
	/**
	 * 移除另外一端连接
	 * @param channel
	 */
	private void RemoveSocket(Session session) {
		if(session == null)
			return;
		ClientInfo clientInfo = sessionClientList.get(session);
		if(clientInfo != null) {
			//发送微信信息  连接异常断开
			wecharSessionClientList.remove(session);
			sessionClientList.remove(session);
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	public synchronized void putVideoAlarm(ClientInfo clientInfo) {
		for(int i=0; i<wecharClientTime.size(); i++) {
			if(wecharClientTime.get(i).equals(clientInfo.getUserid())) {
				wecharClientTime.remove(i);
				break;
			}
		}
		if(videoAlarmClientList.get(clientInfo.getWeichatID()) == null) {
			wecharClientTime.add(clientInfo.getWeichatID());
		}
		videoAlarmClientList.put(clientInfo.getWeichatID(), clientInfo);		
	}

	public synchronized JSONObject GetVideoAlarmStatus(String weChatID) {
		JSONObject jsonOut = new JSONObject();	
		ClientInfo info = videoAlarmClientList.get(weChatID);
		try {
			if(info != null) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
				jsonOut.put("Status", info.getAlarmStatus());
				if(info.getAlarmStatus() == 1) { 
					jsonOut.put("rtmpPushUri",info.getRtmpPushUri());
					jsonOut.put("rtmpPlayUri",info.getRtmpPlayUri());
				}
				
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "weChatID对应的视频报警记录不存在");
			}			
		} catch (JSONException e) {
			
			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "FAILED");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return jsonOut;
	}

	/**
	 * 设置在线坐席状态
	 * @param userid
	 * @param status 
	 */
	public synchronized void setOnlineSeatsStatus(String userid, int status) {
		for(ClientInfo info : onlineSteatsList.values()) {			
			if(info.getUserid().equals(userid)) {
				if(status == 3) {
					removeClientSocket(info.getChannel());
				}else {
					info.setStatus(Integer.valueOf(status));
					if(status == 0) {
						//onlineSteatsFree.add(userid);
						info.setCloseTime(0);
						info.setSession(null);
					}
					onlineSteatsList.put(info.getChannel(), info);
				}
				break;
			}
		}
		
	}
	
	public void CallResponseInfo(String ackCode, ClientInfo clientInfo) {		
		/*if(ackCode.equals("200")) {			
			//接听
			JSONObject json = new JSONObject();
			try {
				json.put("type",2);
				json.put("ackCode",ackCode);
				json.put("rtmpPushUri",videoUrl + String.valueOf(clientInfo.getAcceptTime() + 1));
				json.put("rtmpPlayUri",videoUrl + String.valueOf(clientInfo.getAcceptTime()));
				SendInfoThroughChannle(json.toString() , clientInfo.getOtherchannel());
				logger.info("CallResponseInfo" + json.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else */if(ackCode.equals("201") || ackCode.equals("202") || ackCode.equals("203")|| ackCode.equals("204")) {
			//201拒接  202超时  203呼叫者取消呼叫
			String info = "{\"type\":2,\"ackCode\":\"" +ackCode + "\"}";			
			clientInfo.setStatus(0);		
			SocketChannel clientChannel = clientInfo.getOtherchannel();
			if(clientChannel != null) {
				logger.info("向接警端发送消息:" + info);
				ClientInfo calledinfo = clientList.get(clientChannel);			
				calledinfo.setStatus(0);
				clientInfo.setOtherchannel(null);
				calledinfo.setOtherchannel(null);
				SendInfoThroughChannle(info , clientChannel);
			}
		}
		
	}
	
	public void SendInfoThroughChannle(String infoResponse, SocketChannel channel) {
		byte[] v_datapath = AESEncryptUtil.encryptB(infoResponse, SocketClient.aesPassword);	            
        byte[] buffResponse1 = new byte[4 + v_datapath.length];
        byte[] bLength = ByteIntChange.IntToByteFour(v_datapath.length);
		for(int i=0; i<bLength.length; i++){
			buffResponse1[i] = bLength[i];
		}
		
		for (int i = 0; i < v_datapath.length; i++) {
			buffResponse1[4+i] = v_datapath[i];
		}
		ByteBuffer writeBuf = ByteBuffer.allocate(buffResponse1.length);
		writeBuf.put(buffResponse1);
		//ByteBuffer writeBuf = ByteBuffer.allocate(infoResponse.length());
		//writeBuf.put(infoResponse.getBytes());
        writeBuf.flip();
        try {
        	channel.write(writeBuf);
        	logger.info("向接警端发送消息成功:" + infoResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setOnlineSeatsSeervice(String MEDIA_SERVER_URL, OnlineSeatsService onlineSeatsService) {
		this.MEDIA_SERVER_URL = MEDIA_SERVER_URL;
		this.onlineSeatsService = onlineSeatsService;
		
	}

}