package com.kxjl.video.controller.AppController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.ConfigReader;
import com.kxjl.base.util.DateUtil;
import com.kxjl.base.websocket.SocketClient;
import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.ClientInfo;
import com.kxjl.video.service.ClientService;
import com.kxjl.video.service.OnlineSeatsService;
import com.kxjl.video.util.SendPostRequest;
import com.kxjl.video.pojo.DictInfo;

/**
 * 外部调用接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api")
@PropertySource("classpath:project.properties")
public class APIController extends AppBaseController {
	private static final Logger log = LoggerFactory.getLogger(APIController.class);
	
	@Value("${WXAppID}")
	private String WXAppID;

	@Value("${WXAppSecret}")
	private String WXAppSecret;

	@Autowired
	private OnlineSeatsService onlineSeatsService;
	
	@Autowired
	private ClientService clientService;
	
	private String QueryWeChatID(String weChatId) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=";
		url +=WXAppID + "&secret=" + WXAppSecret + "&js_code=";
		url +=weChatId + "&grant_type=authorization_code";
		String info = SendPostRequest.sendGetData(url);
		try {
			JSONObject json = new JSONObject(info);
			String openid = json.optString("openid");
			if(openid != null && !openid.isEmpty()) {
				return openid;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return  null;
	}
	
	/**
	 * 微信端图文报警接口
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/Client/ImageAlarm")
	public String ImageAlarm(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("ImageAlarm方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		String mintime = parseStringParam(request, "UserName");
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			AlarmInfo alarmInfo = new AlarmInfo();
			alarmInfo.setType(1);
			alarmInfo.setUserName(jsonIn.getString("UserName"));
			alarmInfo.setIdNumber(jsonIn.getString("IDNumber"));
			JSONObject jsonLocation = jsonIn.getJSONObject("Address");
			alarmInfo.setNote(jsonLocation.getString("note"));
			
			JSONObject jsonOccurrenceAddress = jsonIn.getJSONObject("OccurrenceAddress");
			alarmInfo.setArea(jsonOccurrenceAddress.getString("area"));
			alarmInfo.setLongitude(jsonOccurrenceAddress.getString("longitude"));
			alarmInfo.setLatitude(jsonOccurrenceAddress.getString("latitude"));
			
			alarmInfo.setOccurrence_address(jsonOccurrenceAddress.getString("note"));
			alarmInfo.setOccurrenceTime(jsonIn.getString("OccurrenceTime"));
			
			alarmInfo.setWeChatId(jsonIn.getString("WeChatId"));
			alarmInfo.setPhone(jsonIn.getString("Phone"));				
			alarmInfo.setDescription(jsonIn.getString("Description"));
			alarmInfo.setPictureUrl(jsonIn.optString("PhoneUrl"));			
			alarmInfo.setVideoUrl(jsonIn.optString("VideoUrl"));
			alarmInfo.setAudioUrl(jsonIn.optString("VoiceUrl"));
			alarmInfo.setOccurrenceTime(jsonIn.getString("Time"));			
			alarmInfo.setAlarmTime(DateUtil.getNowStr(null));
			alarmInfo.setStatus(1);
			alarmInfo.setOnlineSeatsId(onlineSeatsService.GetFreeOnlineSeats());
			if(onlineSeatsService.insertAlarmInfo(alarmInfo) > 0) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");	
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "图文报警失败");	

			}
		} catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);			
		}
		return jsonOut.toString();
	}
	
	/**
	 * 判断报警者是否实名认证过
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/Client/IsCertificated")
	public String IsCertificated(HttpServletRequest request){
		String params = handleNoAresRequest(request);
		log.info("IsCertificated方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String weChatId = jsonIn.getString("WeChatCode");
			//根据微信code到腾讯查询获取微信ID 在返回值中传递微信ID
			String weCharId = QueryWeChatID(weChatId);
			if(weCharId == null) {
				jsonOut.put("ResponseCode", "202");
				jsonOut.put("ResponseMsg", "获取微信ID失败");
			}else {
				ClientInfo client = new ClientInfo();
				client.setWeichatID(weCharId);
				ClientInfo clientInfo = clientService.CheckClientInfo(client);
				if(clientInfo != null) {
					jsonOut.put("ResponseCode", "200");
					jsonOut.put("ResponseMsg", "OK");
					jsonOut.put("UserName", clientInfo.getName());
					jsonOut.put("IDNumber", clientInfo.getIdentyID());
					jsonOut.put("Address", clientInfo.getNote());
					jsonOut.put("Phone", clientInfo.getMobilePhone());		
					jsonOut.put("WeChatId", weCharId);
					jsonOut.put("ECName", clientInfo.getEcname());
					jsonOut.put("ECPhone", clientInfo.getEcphone());
					jsonOut.put("ECPhone", clientInfo.getEcphone());
					jsonOut.put("Sex", clientInfo.getSex());
		
				}else {
					jsonOut.put("ResponseCode", "201");
					jsonOut.put("ResponseMsg", "Failed");		
					jsonOut.put("WeChatId", weCharId);
				}
			}
		} catch (Exception e) {
			jsonOut.put("ResponseCode", "202");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 报警者设置用户信息
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/Client/SetAlarmUserInfo")
	public String SetAlarmUserInfo(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("SetAlarmUserInfo方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();		
		try {
			JSONObject jsonIn = new JSONObject(params);
			//设置用户信息
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setId( UUID.randomUUID().toString().replaceAll("-", ""));
			clientInfo.setName(jsonIn.getString("UserName"));
			clientInfo.setIdentyID(jsonIn.getString("IDNumber"));
			clientInfo.setNote(jsonIn.optString("Address"));
			clientInfo.setWeichatID(jsonIn.getString("WeChatId"));
			clientInfo.setMobilePhone(jsonIn.getString("ECPhone"));
			clientInfo.setEcname(jsonIn.getString("ECName"));
			clientInfo.setEcphone(jsonIn.getString("ECPhone"));
			clientInfo.setSex(jsonIn.getString("Sex"));
			if(clientService.SetAlarmUserInfo(clientInfo) > 0) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");	
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "设置实名信息失败！");	
			}
		} catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 报警者查询报警记录
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/Client/QueryMyAlarm")
	public String QueryMyAlarm(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("Client QueryMyAlarm方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String userID = jsonIn.getString("UserID");
			List<AlarmInfo> lst = onlineSeatsService.getClientAlarmListByUserId(userID);
			if(lst != null && lst.size() > 0) {
				JSONArray jsonArray = new JSONArray();
				for(AlarmInfo alarm : lst) {
					JSONObject json = new JSONObject();
					json.put("ID", alarm.getId());
					json.put("Type", alarm.getType());
					json.put("Address", alarm.getNote());
					json.put("IDNumber", alarm.getIdNumber());
					json.put("UserName", alarm.getUserName());
					json.put("Phone", alarm.getPhone());
					json.put("AudioUrl", alarm.getAudioUrl());
					json.put("Description", alarm.getDescription());
					json.put("PictureUrl", alarm.getPictureUrl());
					json.put("Receiver", onlineSeatsService.getUserNameByUserId(alarm.getOnlineSeatsId()));
					json.put("VideoUrl", alarm.getVideoUrl());
					json.put("AlarmTime", alarm.getAlarmTime());
					json.put("OccurrenceTime", alarm.getOccurrenceTime());
					json.put("OccurrenceAddress", alarm.getOccurrence_address());
					JSONObject jsonLocation = new JSONObject();
					jsonLocation.put("Latitude", alarm.getLatitude());
					jsonLocation.put("Longitude", alarm.getLongitude());
					jsonLocation.put("Note", alarm.getNote());
					json.put("Location", jsonLocation);
					json.put("CaseType", alarm.getCase_type());
					json.put("CaseLevel", alarm.getCase_level());
					switch(alarm.getStatus()) {
					case 1:
						json.put("Status", "已报警");
						break;
					case 2:
						json.put("Status", "已受理");
						break;
					case 3:
						json.put("Status", "已出警");
						break;
					case 4:
						json.put("Status", "已结束");
						break;
					}
					
					jsonArray.put(json);
				}
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
				jsonOut.put("InfoList", jsonArray);
			}else {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
			}
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 在线坐席查询报警记录
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/OnlineSeats/QueryMyAlarm")
	public String QueryMyAlarm1(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("OnlineSeats QueryMyAlarm方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String userID = jsonIn.getString("UserID");
			List<AlarmInfo> lst = onlineSeatsService.getAlarmListByUserId(userID);
			String userName = onlineSeatsService.getUserNameByUserId(userID);
			if(lst != null && lst.size() > 0) {
				JSONArray jsonArray = new JSONArray();
				for(AlarmInfo alarm : lst) {
					JSONObject json = new JSONObject();
					json.put("Address", alarm.getNote());
					json.put("IDNumber", alarm.getIdNumber());
					json.put("UserName", alarm.getUserName());
					json.put("Phone", alarm.getPhone());
					json.put("AudioUrl", alarm.getAudioUrl());
					json.put("Description", alarm.getDescription());
					json.put("PictureUrl", alarm.getPictureUrl());
					json.put("Receiver", userName);
					json.put("AlarmTime", alarm.getAlarmTime());
					json.put("VideoUrl", alarm.getVideoUrl());
					JSONObject jsonLocation = new JSONObject();
					jsonLocation.put("Latitude", alarm.getLatitude());
					jsonLocation.put("Longitude", alarm.getLongitude());
					jsonLocation.put("Note", alarm.getNote());
					json.put("Location", jsonLocation);
					json.put("OccurrenceTime", alarm.getOccurrenceTime());
					json.put("OccurrenceAddress", alarm.getOccurrence_address());
					json.put("CaseType", alarm.getCase_type());
					json.put("CaseLevel", alarm.getCase_level());
					switch(alarm.getStatus()) {
					case 1:
						json.put("Status", "已报警");
						break;
					case 2:
						json.put("Status", "已受理");
						break;
					case 3:
						json.put("Status", "已出警");
						break;
					case 4:
						json.put("Status", "已结束");
						break;
					}
					jsonArray.put(json);
				}
				jsonOut.put("Code", "200");
				jsonOut.put("ResponseMsg", "OK");
				jsonOut.put("InfoList", jsonArray);
			}else {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
			}
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 在线坐席登录认证接口
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/OnlineSeats/Login")
	public String Login(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("Login方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String userid = jsonIn.getString("UserID");
			String password = jsonIn.getString("Password");
			String userId = onlineSeatsService.CheckUserInfo(userid, password);
			if(userId != null) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
				jsonOut.put("UserID", String.valueOf(userId));
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "用户名或密码错误");			
			}
			
			
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 接警端坐席设置自身状态
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/OnlineSeats/Setstatus")
	public String Setstatus(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("Setstatus方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String userid = jsonIn.getString("UserID");
			String status = jsonIn.getString("Status");
			if(onlineSeatsService.SetOnlineStatus(userid, status) > 0) {
				SocketClient.getInstance().setOnlineSeatsStatus(userid, Integer.valueOf(status));
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("RespconseMsg", "设置在线坐席状态失败");
			}
			
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 接警人员视频通话结束后，登记报警信息
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/OnlineSeats/RecordAlarm")
	public String RecordAlarm(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("RecordAlarm方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			AlarmInfo alarmInfo = new AlarmInfo();
			alarmInfo.setType(2);
			alarmInfo.setOnlineSeatsId(jsonIn.getString("OnlineSeatsId"));
			alarmInfo.setUserName(jsonIn.getString("UserName"));
			alarmInfo.setIdNumber(jsonIn.getString("IDNumber"));			
			alarmInfo.setNote(jsonIn.getString("Address"));
			alarmInfo.setLatitude(jsonIn.getString("Latitude"));
			alarmInfo.setLongitude(jsonIn.getString("Longitude"));
			alarmInfo.setWeChatId(jsonIn.getString("WeChatId"));
			alarmInfo.setPhone(jsonIn.getString("Phone"));				
			alarmInfo.setDescription(jsonIn.getString("Description"));
			alarmInfo.setPictureUrl(jsonIn.optString("PictureUrl"));			
			alarmInfo.setVideoUrl(jsonIn.optString("VideoUrl"));
			alarmInfo.setAudioUrl(jsonIn.optString("AudioUrl"));
			alarmInfo.setOccurrenceTime(jsonIn.getString("OccurrenceTime"));	
			alarmInfo.setOccurrence_address(jsonIn.getString("OccurrenceAddress"));		
			alarmInfo.setAlarmTime(jsonIn.optString("Time"));
			//根据在线坐席ID获取所属区域
			alarmInfo.setArea(onlineSeatsService.getAreaByOnlineSeatsUserID(jsonIn.getString("OnlineSeatsId")));
			SocketClient.getInstance().setOnlineSeatsStatus(jsonIn.getString("OnlineSeatsId"), 0);
			alarmInfo.setCase_type(jsonIn.optString("CaseType"));
			alarmInfo.setCase_level(jsonIn.optString("CaseLevel"));
			alarmInfo.setStatus(2);
			if(onlineSeatsService.insertAlarmInfo(alarmInfo) > 0) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");	
			}else {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "登记报警信息失败");
			}
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 获取字典信息表
	 * @param request
	 * @param response
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/QueryDictInfoList")
	public String QueryDictInfoList(HttpServletRequest request) {
		String params = handleNoAresRequest(request);
		log.info("OnlineSeats QueryDictInfoList方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String type = jsonIn.getString("Type");
			List<DictInfo> lst = onlineSeatsService.QueryDictInfoListByType(Integer.valueOf(type));
			if(lst != null && lst.size() > 0) {
				JSONArray jsonArray = new JSONArray();
				for(DictInfo dictInfo : lst) {
					JSONObject json = new JSONObject();
					json.put("Value", dictInfo.getDict_name());
					json.put("Name", dictInfo.getDict_value());
					jsonArray.put(json);
				}
				jsonOut.put("InfoList", jsonArray);
			}
			jsonOut.put("ResponseCode", "200");
			jsonOut.put("ResponseMsg", "OK");
			jsonOut.put("Type", type);
		}catch (Exception e) {
			jsonOut.put("ResponseCode", "201");
			jsonOut.put("ResponseMsg", e);
		}
		return jsonOut.toString();
	}
	
	/**
	 * 查询是否有新消息
	 * @param request
	 * @param response
	 * @param session
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/QueryHasNewInfo")
	public String QueryHasNewInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String params = handleNoAresRequest(request);
		log.info("OnlineSeats QueryHasNewInfo方法传入参数：" + params);
		JSONObject jsonOut = new JSONObject();
		
		try {
			JSONObject jsonIn = new JSONObject(params);
			String userID = jsonIn.getString("UserID");
			Integer count = onlineSeatsService.QueryHasNewInfo(userID);
			if(count != null && count > 0) {
				jsonOut.put("ResponseCode", "200");
				jsonOut.put("ResponseMsg", "OK");
			}else {
				jsonOut.put("ResponseCode", "202");
				jsonOut.put("ResponseMsg", "无新消息");
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", e);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonOut.toString();
	}
}
