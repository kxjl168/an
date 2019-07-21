/*
 * @(#)UnitinfoManagerController.java
 * @author: zj
 * @Date: 2019-07-19 22:22:56
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
import com.kxjl.video.dao.UnitinfoManagerMapper;

import com.kxjl.video.pojo.UnitinfoManager;
import com.kxjl.video.service.UnitinfoManagerService;

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
 * 单位超级管理员 1对多管理 UnitinfoManagerController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:22:56
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tunitinfomanager")
public class UnitinfoManagerController {
	@Autowired
	private UnitinfoManagerService tunitinfomanagerService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tunitinfomanager/index.ftl";
	}

	@RequestMapping("/tunitinfomanagerList")
	@ManagerActionLog(operateDescribe="查询单位超级管理员 1对多",operateFuncType=FunLogType.Query,operateModelClassName=UnitinfoManagerMapper.class)
	@ResponseBody
	public String tunitinfomanagerList( UnitinfoManager item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<UnitinfoManager> tunitinfomanagers = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tunitinfomanagers = tunitinfomanagerService.selectUnitinfoManagerList(item);

		try {
			rst = PageUtil.packageTableData(page, tunitinfomanagers);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除单位超级管理员 1对多",operateFuncType=FunLogType.Del,operateModelClassName=UnitinfoManagerMapper.class)
	@ResponseBody
	public Message delete( UnitinfoManager item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tunitinfomanagerService.deleteUnitinfoManager(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		UnitinfoManager tunitinfomanagers = tunitinfomanagerService.selectUnitinfoManagerById(id);
		
		   return JSON.toJSONString(tunitinfomanagers);
		//return JSONObject.fromObject(tunitinfomanagers).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tunitinfomanager
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改单位超级管理员 1对多",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=UnitinfoManagerMapper.class)
	@ResponseBody
	public String saveOrUpdate(UnitinfoManager tunitinfomanager) {

		JSONObject jsonObject = null;
		try {
			if (null == tunitinfomanager.getId() || "".equals(tunitinfomanager.getId())) {
				
				jsonObject = tunitinfomanagerService.saveUnitinfoManager(tunitinfomanager);

			} else {
				jsonObject = tunitinfomanagerService.updateUnitinfoManager(tunitinfomanager);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttunitinfomanager")
    @ResponseBody
    public List<UnitinfoManager> selecttunitinfomanager( UnitinfoManager item) {
        return tunitinfomanagerService.selectUnitinfoManagerList(item);
    }


}