/*
 * @(#)VideoalarmInfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:24:08
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
import com.kxjl.video.dao.VideoalarmInfoMapper;

import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.service.VideoalarmInfoService;
import com.kxjl.video.util.TokenUtil;
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
 * 报警事件管理 VideoalarmInfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:24:08
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/videoalarminfo")
public class VideoalarmInfoController {
	@Autowired
	private VideoalarmInfoService videoalarminfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/videoalarminfo/index.ftl";
	}

	@RequestMapping("/videoalarminfoList")
	@ManagerActionLog(operateDescribe="查询报警事件",operateFuncType=FunLogType.Query,operateModelClassName=VideoalarmInfoMapper.class)
	@ResponseBody
	public String videoalarminfoList( VideoalarmInfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<VideoalarmInfo> videoalarminfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		
		item.setCurUid(TokenUtil.getWebLoginUser().getId());
		videoalarminfos = videoalarminfoService.selectVideoalarmInfoList(item);

		try {
			rst = PageUtil.packageTableData(page, videoalarminfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除报警事件",operateFuncType=FunLogType.Del,operateModelClassName=VideoalarmInfoMapper.class)
	@ResponseBody
	public Message delete( VideoalarmInfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = videoalarminfoService.deleteVideoalarmInfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		VideoalarmInfo videoalarminfos = videoalarminfoService.selectVideoalarmInfoById(id);
		
		   return JSON.toJSONString(videoalarminfos);
		//return JSONObject.fromObject(videoalarminfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param videoalarminfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改报警事件",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=VideoalarmInfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(VideoalarmInfo videoalarminfo) {

		JSONObject jsonObject = null;
		try {
			if (null == videoalarminfo.getId()) {
				
				jsonObject = videoalarminfoService.saveVideoalarmInfo(videoalarminfo);

			} else {
				jsonObject = videoalarminfoService.updateVideoalarmInfo(videoalarminfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selectvideoalarminfo")
    @ResponseBody
    public List<VideoalarmInfo> selectvideoalarminfo( VideoalarmInfo item) {
        return videoalarminfoService.selectVideoalarmInfoList(item);
    }


}