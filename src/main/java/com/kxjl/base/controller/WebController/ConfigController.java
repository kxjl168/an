/*
 * @(#)ConfigController.java
 * @author: zj
 * @Date: 2019-07-20 09:55:35
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.controller.WebController;


import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.dao.ConfigMapper;

import com.kxjl.base.pojo.Config;
import com.kxjl.base.service.ConfigService;

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
 * 配置项管理 ConfigController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-20 09:55:35
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tconfig")
public class ConfigController {
	@Autowired
	private ConfigService tconfigService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tconfig/index.ftl";
	}

	@RequestMapping("/tconfigList")
	@ManagerActionLog(operateDescribe="查询配置项",operateFuncType=FunLogType.Query,operateModelClassName=ConfigMapper.class)
	@ResponseBody
	public String tconfigList( Config item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Config> tconfigs = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tconfigs = tconfigService.selectConfigList(item);

		try {
			rst = PageUtil.packageTableData(page, tconfigs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除配置项",operateFuncType=FunLogType.Del,operateModelClassName=ConfigMapper.class)
	@ResponseBody
	public Message delete( Config item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tconfigService.deleteConfig(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Config tconfigs = tconfigService.selectConfigById(id);
		
		   return JSON.toJSONString(tconfigs);
		//return JSONObject.fromObject(tconfigs).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tconfig
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改配置项",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=ConfigMapper.class)
	@ResponseBody
	public String saveOrUpdate(Config tconfig) {

		JSONObject jsonObject = null;
		try {
			if (null == tconfig.getCkey() || "".equals(tconfig.getCkey())) {
				
				jsonObject = tconfigService.saveConfig(tconfig);

			} else {
				jsonObject = tconfigService.updateConfig(tconfig);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttconfig")
    @ResponseBody
    public List<Config> selecttconfig( Config item) {
        return tconfigService.selectConfigList(item);
    }


}