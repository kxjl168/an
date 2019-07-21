/*
 * @(#)UnitinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:22:29
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
import com.kxjl.video.dao.UnitinfoMapper;

import com.kxjl.video.pojo.Unitinfo;
import com.kxjl.video.service.UnitinfoService;

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
 * 单位信息管理 UnitinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:22:29
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tunitinfo")
public class UnitinfoController {
	@Autowired
	private UnitinfoService tunitinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tunitinfo/index.ftl";
	}

	@RequestMapping("/tunitinfoList")
	@ManagerActionLog(operateDescribe="查询单位信息",operateFuncType=FunLogType.Query,operateModelClassName=UnitinfoMapper.class)
	@ResponseBody
	public String tunitinfoList( Unitinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Unitinfo> tunitinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tunitinfos = tunitinfoService.selectUnitinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tunitinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除单位信息",operateFuncType=FunLogType.Del,operateModelClassName=UnitinfoMapper.class)
	@ResponseBody
	public Message delete( Unitinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tunitinfoService.deleteUnitinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Unitinfo tunitinfos = tunitinfoService.selectUnitinfoById(id);
		
		   return JSON.toJSONString(tunitinfos);
		//return JSONObject.fromObject(tunitinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tunitinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改单位信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=UnitinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Unitinfo tunitinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == tunitinfo.getId() || "".equals(tunitinfo.getId())) {
				
				jsonObject = tunitinfoService.saveUnitinfo(tunitinfo);

			} else {
				jsonObject = tunitinfoService.updateUnitinfo(tunitinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttunitinfo")
    @ResponseBody
    public List<Unitinfo> selecttunitinfo( Unitinfo item) {
        return tunitinfoService.selectUnitinfoList(item);
    }


}