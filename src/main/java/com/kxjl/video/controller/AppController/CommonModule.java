package com.kxjl.video.controller.AppController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.controller.Common.UploadFileController;
import com.kxjl.base.pojo.SvrFileInfo;
import com.kxjl.base.service.SvrFileInfoService;
import com.kxjl.base.service.impl.SvrFileInfoServiceImpl;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.aes.AesHelper;
import com.kxjl.base.util.sendpost.HttpSendPost;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.pojo.VideoalarmTalkinfo;
import com.kxjl.video.service.VideoalarmInfoService;
import com.kxjl.video.service.VideoalarmTalkinfoService;
import com.kxjl.base.util.BufferHttpServletRequestWrapper;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/interface/app")
@PropertySource("classpath:project.properties")
public class CommonModule extends AppBaseController {

	@Value("${weblock.url}")
	private String url;

	private static final Logger logger = LoggerFactory.getLogger(CommonModule.class);

	@Autowired
	VideoalarmTalkinfoService videoalarmTalkinfoService;

	@Autowired
	VideoalarmInfoService videoalarmInfoService;

	@Autowired
	UploadFileController uploadFileController;

	@Autowired
	private SvrFileInfoService fileService;

	@Value("${FILE_SVR_PATH}")
	private String FILE_SVR_PATH;

	

	@Value("${HTTP_PATH}")
	private String HTTP_PATH;
	/**
	 * App聊天接口，提交聊天数据
	 * 
	 * @param imgFiles
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年7月20日
	 */
	@NoNeedAuthorization
	@RequestMapping("/sendTalkMsg")
	public AppResult sendTalkMsg(MultipartFile[] imgFiles, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();

		String responsedata = "";
		try {
			String alarmId = parseStringParam(request, "alarmId"); // 报警事件id

			String msgType = parseStringParam(request, "msgType");// 消息类型 ，1文本
			String msg = parseStringParam(request, "msgContent");// 文本消息

			String fileurl = parseStringParam(request, "fileUrl");// 文本消息

			if (msgType == null || msgType.equals("")) {
				return AppResultUtil.fail("消息类型参数错误");
			}

			String md5 = "";
			if (msgType.equals("1")) {
				if (msg.equals(""))
					return AppResultUtil.fail("不能发送空消息");
			} else {
				if (fileurl == null || fileurl.equals("")) {
					return AppResultUtil.fail("附件id不能为空");
				}

				SvrFileInfo fquery = new SvrFileInfo();
				fquery.setFile_md5(fileurl);
				SvrFileInfo uploadFile = fileService.getFileInfo(fquery);
				if (uploadFile == null) {
					return AppResultUtil.fail("附件id错误");
				}

				if (imgFiles != null && imgFiles.length > 0) {
					responsedata = uploadFileController.UploadFileXhr(imgFiles[0], null, null, null);
					org.json.JSONObject jsonRes = new org.json.JSONObject(responsedata);
					md5 = jsonRes.optString("md5");

				} else {
					md5 = fileurl;// 直接上传md5
				}
			}

			// 文件上传
			// responsedata = mongoImgSvrCtroller.upload(imgFiles, null, null);

			// 数据存储
			VideoalarmTalkinfo talkinfo = new VideoalarmTalkinfo();
			talkinfo.setAlarmId(Integer.parseInt(alarmId));
			talkinfo.setMsgType(msgType);
			talkinfo.setTalkType("1");// app->web
			talkinfo.setMsgContent(msg);
			talkinfo.setFileUrl(md5);
			String tid = UUIDUtil.getUUID();
			talkinfo.setId(tid);
			videoalarmTalkinfoService.saveVideoalarmTalkinfo(talkinfo);

			try {
				
				
				SvrFileInfo fquery = new SvrFileInfo();
				fquery.setFile_md5(fileurl);
				SvrFileInfo uploadFile = fileService.getFileInfo(fquery);
				
				
				
				VideoalarmInfo valarm = videoalarmInfoService.selectVideoalarmInfoById(Long.parseLong(alarmId));
				if (valarm != null) {

					VideoalarmInfo vq = new VideoalarmInfo();
					vq.setId(valarm.getId());
					vq.setHasNewInfo("1");// 有
					videoalarmInfoService.updateVideoalarmInfo(vq);

					String ttid = valarm.getSeat_id();

					org.json.JSONObject jmsg = new org.json.JSONObject();
					jmsg.put("uid", alarmId);// 报警事件id
					jmsg.put("tid", ttid); // 坐席id
					jmsg.put("msg", msg);
					//jmsg.put("fileurl", FILE_SVR_PATH + "upload/file/" + md5);
					if(uploadFile!=null)
					jmsg.put("fileurl", HTTP_PATH + uploadFile.getHttp_relative_path());
					jmsg.put("msgtype", msgType);

					MyWebSocket.sendByteMessage(jmsg.toString(), ttid);
				}

			} catch (IOException e) {
				e.printStackTrace();

			}

			return AppResultUtil.success(tid);

		} catch (Exception e) {

			e.printStackTrace();
			return AppResultUtil.fail();
		}
	}

	/**
	 * app端聊天消息获取接口，查询聊天消息
	 * 
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年7月20日
	 */
	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/talklist")
	public AppResult talklist(HttpServletRequest request) {
		try {
			String curl = url + "/interface/provide/communityList";

			BufferHttpServletRequestWrapper request2 = new BufferHttpServletRequestWrapper(
					(HttpServletRequest) request);

			// String pageSize = parseStringParam(request, "pageSize");
			String mintime = parseStringParam(request2, "mintime");
			String maxtime = parseStringParam(request2, "maxtime");
			String alarmId = parseStringParam(request2, "alarmId");

			PageCondition p = new PageCondition();
			p.setPageNum("1");
			p.setPageSize("10");

			if (maxtime == null || maxtime.equals("") || mintime == null || mintime.equals("")) {
				// 有一个时间没填，默认分页10条
				Page page = PageUtil.getPage(p);
			}

			VideoalarmTalkinfo query = new VideoalarmTalkinfo();
			try {
				query.setAlarmId(Integer.parseInt(alarmId));
			} catch (Exception e) {
				return AppResultUtil.fail("alarmId:错误!");
			}

			query.setMintime(mintime);
			query.setMaxtime(maxtime);
			// query.setOrder("asc");
			List<VideoalarmTalkinfo> talks = videoalarmTalkinfoService.selectVideoalarmTalkinfoList(query);

			
			
			
			
			for (int i = 0; i < talks.size(); i++) {
				
				if (talks.get(i).getFileUrl() != null && !talks.get(i).getFileUrl().equals("")) {
					
					
					SvrFileInfo fquery = new SvrFileInfo();
					fquery.setFile_md5(talks.get(i).getFileUrl());
					SvrFileInfo uploadFile = fileService.getFileInfo(fquery);
					talks.get(i).setFileUrl(HTTP_PATH + uploadFile.getHttp_relative_path());
					
					
					//替換
					//talks.get(i).setFileUrl(FILE_SVR_PATH + "upload/file/" + talks.get(i).getFileUrl());
				}
			}

			return AppResultUtil.success(talks);
		} catch (Exception e) {
			return AppResultUtil.fail("错误原因" + e);
		}
	}

}
