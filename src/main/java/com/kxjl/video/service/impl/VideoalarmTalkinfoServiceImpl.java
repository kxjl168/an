package com.kxjl.video.service.impl;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.base.SysConst;
import com.kxjl.base.pojo.SvrFileInfo;
import com.kxjl.base.service.SvrFileInfoService;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.DateUtil;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.video.dao.VideoalarmTalkinfoMapper;
import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.pojo.VideoalarmTalkinfo;
import com.kxjl.video.service.VideoalarmInfoService;
import com.kxjl.video.service.VideoalarmTalkinfoService;
import com.kxjl.video.util.FtpClientUtil;
import com.kxjl.video.util.SFTPUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
@PropertySource("classpath:project.properties")
public class VideoalarmTalkinfoServiceImpl implements VideoalarmTalkinfoService {

	private static Logger log = LoggerFactory.getLogger(VideoalarmTalkinfoServiceImpl.class);

	@Autowired
	private VideoalarmTalkinfoMapper itemMapper;
	@Autowired
	private SvrFileInfoService fileService;

	@Autowired
	VideoalarmInfoService videoalarmInfoService;
	
	@Autowired
	FtpClientUtil ftpClientUtil ; 
	
	@Autowired
	SFTPUtil sFTPUtil;

	//警情交互fromWX
	@Value("${talk.wxTalkSendUrl}")
	private String wxTalkSendUrl;
	
	//警情交流FromSLT
	@Value("${talk.platTalkSendUrl}")
	private String platTalkSendUrl;

	
	//遠程目錄
	@Value("${ftp.remotePath}")
	private String remotePath;
	/**
	 * 保存聊天数据
	 * 
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveVideoalarmTalkinfo(VideoalarmTalkinfo item) {
		JSONObject rtn = new JSONObject();

		try {

			// item.setId(UUIDUtil.getUUID());

			if (item.getId() == null || item.getId().equals(""))
				item.setId(UUIDUtil.getUUID());

			itemMapper.insertSelective(item);

			rtn.put("bol", true);

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// 接口推送数据至其他平台
					TransTalkDatatoOtherPlat(item);
				}
			}).start();
			

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}

	/**
	 * 转发聊天数据
	 * 
	 * @param item
	 * @author zj
	 * @date 2019年8月14日
	 */
	@Async
	private void TransTalkDatatoOtherPlat(VideoalarmTalkinfo item) {

		// http://192.168.0.110:8780 /AlarmExchFromWXMeth POST
		// DS（微信端发过来的交流消息，需要通过本接口更新到110指挥系统）

		org.json.JSONObject jobject = new org.json.JSONObject();
		jobject.put("DIALOGID", item.getId().toString()); /// 流水号
		jobject.put("RELATION_ID", item.getAlarmId().toString()); /// 报警关联流水号

		jobject.put("CREATE_TIME", DateUtil.getNowStr("")); /// /// 消息创建时间
		jobject.put("MSGTYPE", item.getMsgType()); /// 消息类型 1:文本 ,2：图片，3：音频，4：视频

		jobject.put("CONTENT", item.getMsgContent()); /// 文本消息内容
		jobject.put("MEDIAID", item.getFileUrl()); /// 多媒体id

		SvrFileInfo fquery = new SvrFileInfo();
		fquery.setFile_md5(item.getFileUrl());
		SvrFileInfo uploadFile = fileService.getFileInfo(fquery);
		if (uploadFile != null) {

			jobject.put("MEDIANAME", uploadFile.getOld_name()); /// 多媒体id

			jobject.put("FORMAT", uploadFile.getOld_name()); /// 多媒体格式
			File f=new File(uploadFile.getFull_path());
			try {
			
			// ftp 文件上传，传入ftp目标服务器路径. 或者相对路径
		
			boolean isok=ftpClientUtil.uploadOneStep(f, remotePath);
			
			
			if(!isok)
			{
				sFTPUtil. uploadSimple(f,uploadFile.getSave_name(),remotePath);
			}
			
			} catch (Exception e) {
				log.debug("ftp失敗"+e.getMessage());
				
			}
			
			

			jobject.put("MEDIAURL", remotePath+uploadFile.getSave_name()); /// 多媒体文件路径

		}

		VideoalarmInfo alarm = videoalarmInfoService.selectVideoalarmInfoById((long) item.getAlarmId());

		jobject.put("LOCATION_X", alarm.getLongitude()); /// 经度
		jobject.put("LOCATION_Y", alarm.getLatitude()); /// 纬度

		jobject.put("LABEL", alarm.getAddress()); /// 地理位置文本信息
		jobject.put("SEND_TIME", DateUtil.getNowStr("")); /// 发送时间

		if (item.getTalkType().equals("2"))
			jobject.put("METHOD", "send"); ///

		/*
		 * 
		 * /// 备用字段1 public string BYZD1;
		 * 
		 * /// 备用字段2 public string BYZD2;
		 * 
		 * /// 信息说明 public string XXSM;
		 */

		try {
			boolean istest=true;
			//暫時屏蔽，調試打開
			if(istest)
				return;
			
			if (item.getTalkType().equals("1"))
				sendJsonHttpData("", wxTalkSendUrl, jobject.toString());
			else
				sendJsonHttpData("", platTalkSendUrl, jobject.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static String sendJsonHttpData(String auth, String url, String str) throws Exception {

		log.debug("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);

		httpPost.setRequestHeader("Content-type", "application/json");
		// httpPost.setRequestHeader("Content-type",
		// "application/x-www-form-urlencoded;charset=UTF-8");

		httpPost.setRequestHeader("Accept", "application/json");
		httpPost.setRequestHeader("Connection", "close");
		httpPost.setRequestHeader(SysConst.AUTHORIZATION, auth);

		// httpPost.setRequestHeader("Authorization", "Basic YWRtaW46MTIz");
		httpPost.setRequestBody(is);

		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			if (resStatusCode == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpPost.getResponseBodyAsStream(), "utf-8"));
				log.debug("HTTP Request CHARSET:" + httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();
				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
			} else {
				log.error("http请求失败 " + resStatusCode + ":" + httpPost.getStatusText());
				exception = new Exception("[SerialHttpSender] HttpErrorCode:" + resStatusCode);
			}
			if (exception != null) {
				throw exception;
			}
		} catch (java.net.ConnectException ex) {
			ex.printStackTrace();
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
			// org.apache.commons.httpclient.HttpRecoverableException:
			// java.net.SocketTimeoutException: Read timed out

			String message = ex.getMessage();
			if (message != null && message.toLowerCase().indexOf("read timed") > -1) {
				throw new Exception(ex.getMessage());
			} else {
				ex.printStackTrace();
				throw ex;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			httpPost.releaseConnection();

		}

		log.debug("HTTP Request Result:" + responseData);
		return responseData;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateVideoalarmTalkinfo(VideoalarmTalkinfo item) {
		JSONObject rtn = new JSONObject();

		if (null == item || null == item.getId()) {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			itemMapper.updateByPrimaryKeySelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新出错", e);
			rtn.put("bol", false);
			rtn.put("message", "更新出错");
			return rtn;
		}
	}

	@Override
	public List<VideoalarmTalkinfo> selectVideoalarmTalkinfoList(VideoalarmTalkinfo item) {
		List<VideoalarmTalkinfo> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteVideoalarmTalkinfo(VideoalarmTalkinfo item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override

	public VideoalarmTalkinfo selectVideoalarmTalkinfoById(String id) {

		VideoalarmTalkinfo data = null;

		VideoalarmTalkinfo query = new VideoalarmTalkinfo();
		query.setId(id);

		List<VideoalarmTalkinfo> datas = selectVideoalarmTalkinfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
