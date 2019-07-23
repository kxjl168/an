/*
 * @(#)VideoalarmTalkinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:23:11
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.controller.WebController;


import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.video.dao.VideoalarmTalkinfoMapper;
import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.pojo.VideoalarmTalkinfo;
import com.kxjl.video.service.VideoalarmTalkinfoService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报警事件聊天记录管理 VideoalarmTalkinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:23:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tvideoalarmtalkinfo")
public class VideoalarmTalkinfoController {
	@Autowired
	private VideoalarmTalkinfoService tvideoalarmtalkinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tvideoalarmtalkinfo/index.ftl";
	}

	@RequestMapping("/tvideoalarmtalkinfoList")
	@ManagerActionLog(operateDescribe="查询报警事件聊天记录",operateFuncType=FunLogType.Query,operateModelClassName=VideoalarmTalkinfoMapper.class)
	@ResponseBody
	public String tvideoalarmtalkinfoList( VideoalarmTalkinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<VideoalarmTalkinfo> tvideoalarmtalkinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tvideoalarmtalkinfos = tvideoalarmtalkinfoService.selectVideoalarmTalkinfoList(item);

		
		
		
		
		try {
			rst = PageUtil.packageTableData(page, tvideoalarmtalkinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除报警事件聊天记录",operateFuncType=FunLogType.Del,operateModelClassName=VideoalarmTalkinfoMapper.class)
	@ResponseBody
	public Message delete( VideoalarmTalkinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tvideoalarmtalkinfoService.deleteVideoalarmTalkinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		VideoalarmTalkinfo tvideoalarmtalkinfos = tvideoalarmtalkinfoService.selectVideoalarmTalkinfoById(id);
		
		   return JSON.toJSONString(tvideoalarmtalkinfos);
		//return JSONObject.fromObject(tvideoalarmtalkinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tvideoalarmtalkinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改报警事件聊天记录",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=VideoalarmTalkinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(VideoalarmTalkinfo tvideoalarmtalkinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == tvideoalarmtalkinfo.getId() || "".equals(tvideoalarmtalkinfo.getId())) {
				
				jsonObject = tvideoalarmtalkinfoService.saveVideoalarmTalkinfo(tvideoalarmtalkinfo);

			} else {
				jsonObject = tvideoalarmtalkinfoService.updateVideoalarmTalkinfo(tvideoalarmtalkinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttvideoalarmtalkinfo")
    @ResponseBody
    public List<VideoalarmTalkinfo> selecttvideoalarmtalkinfo( VideoalarmTalkinfo item) {
        return tvideoalarmtalkinfoService.selectVideoalarmTalkinfoList(item);
    }


}