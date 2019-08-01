/*
 * @(#)VideoalarmErrorinfoController.java
 * @author: zj
 * @Date: 2019-08-01 16:31:45
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.controller.WebController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.video.dao.plus.VideoalarmErrorinfoMapper;

import com.kxjl.video.pojo.AlarmErrorinfo;
import com.kxjl.video.service.VideoalarmErrorinfoService;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析管理 VideoalarmErrorinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-08-01 16:31:45
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/videoalarmerrorinfo")
public class VideoalarmErrorinfoController {
	@Autowired
	private VideoalarmErrorinfoService videoalarmerrorinfoService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/videoalarmerrorinfo/index.ftl";
	}

	@RequestMapping("/videoalarmerrorinfoList")
	@ManagerActionLog(operateDescribe = "查询统计分析", operateFuncType = FunLogType.Query, operateModelClassName = VideoalarmErrorinfoMapper.class)
	@ResponseBody
	public String videoalarmerrorinfoList(AlarmErrorinfo item, HttpServletRequest request,
			PageCondition pageCondition) {

		String rst = "";
		List<AlarmErrorinfo> videoalarmerrorinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		videoalarmerrorinfos = videoalarmerrorinfoService.selectVideoalarmErrorinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, videoalarmerrorinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	/**
	 * 总计
	 * 
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年8月1日
	 */
	@RequestMapping("/allTotal")
	// @ManagerActionLog(operateDescribe="查询统计分析",operateFuncType=FunLogType.Query,operateModelClassName=VideoalarmErrorinfoMapper.class)
	@ResponseBody
	public AppResult allTotal(AlarmErrorinfo item, HttpServletRequest request, PageCondition pageCondition) {

		// String rst = "";
		List<HashMap> videoalarmerrorinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		videoalarmerrorinfos = videoalarmerrorinfoService.selectTotal(item);

		return AppResultUtil.success(videoalarmerrorinfos);

	}

	/**
	 * 天统计
	 * 
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年8月1日
	 */
	@RequestMapping("/dayTotal")
	// @ManagerActionLog(operateDescribe="查询统计分析",operateFuncType=FunLogType.Query,operateModelClassName=VideoalarmErrorinfoMapper.class)
	@ResponseBody
	public AppResult dayTotal(AlarmErrorinfo item, HttpServletRequest request, PageCondition pageCondition) {

		// String rst = "";
		List<HashMap> videoalarmerrorinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		videoalarmerrorinfos = videoalarmerrorinfoService.selectDayTotal(item);

		return AppResultUtil.success(videoalarmerrorinfos);
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除统计分析", operateFuncType = FunLogType.Del, operateModelClassName = VideoalarmErrorinfoMapper.class)
	@ResponseBody
	public Message delete(AlarmErrorinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = videoalarmerrorinfoService.deleteVideoalarmErrorinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam Long id, HttpServletRequest request) {

		AlarmErrorinfo videoalarmerrorinfos = videoalarmerrorinfoService.selectVideoalarmErrorinfoById(id);

		return JSON.toJSONString(videoalarmerrorinfos);
		// return JSONObject.fromObject(videoalarmerrorinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param videoalarmerrorinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "保存修改统计分析", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = VideoalarmErrorinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(AlarmErrorinfo videoalarmerrorinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == videoalarmerrorinfo.getId()) {

				jsonObject = videoalarmerrorinfoService.saveVideoalarmErrorinfo(videoalarmerrorinfo);

			} else {
				jsonObject = videoalarmerrorinfoService.updateVideoalarmErrorinfo(videoalarmerrorinfo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selectvideoalarmerrorinfo")
	@ResponseBody
	public List<AlarmErrorinfo> selectvideoalarmerrorinfo(AlarmErrorinfo item) {
		return videoalarmerrorinfoService.selectVideoalarmErrorinfoList(item);
	}

}