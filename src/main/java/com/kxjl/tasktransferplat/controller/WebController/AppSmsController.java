/*
 * @(#)AppSmsController.java
 * @author: zj
 * @Date: 2019-05-20 10:56:32
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.AppSmsMapper;
import com.kxjl.tasktransferplat.pojo.AppSms;
import com.kxjl.tasktransferplat.service.AppSmsService;
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
 * 短信验证表管理 AppSmsController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-05-20 10:56:32
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/appsms")
public class AppSmsController {
	@Autowired
	private AppSmsService appsmsService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/appsms/index.ftl";
	}

	@RequestMapping("/appsmsList")
	@ManagerActionLog(operateDescribe="查询短信验证表",operateFuncType=FunLogType.Query,operateModelClassName=AppSmsMapper.class)
	@ResponseBody
	public String appsmsList( AppSms item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<AppSms> appsmss = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		appsmss = appsmsService.selectAppSmsList(item);

		try {
			rst = PageUtil.packageTableData(page, appsmss);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除短信验证表",operateFuncType=FunLogType.Del,operateModelClassName=AppSmsMapper.class)
	@ResponseBody
	public Message delete( AppSms item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = appsmsService.deleteAppSms(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		AppSms appsmss = appsmsService.selectAppSmsById(id);
		
		return JSONObject.toJSONString(appsmss);
		
//		return JSONObject.fromObject(appsmss).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param appsms
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改短信验证表",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=AppSmsMapper.class)
	@ResponseBody
	public String saveOrUpdate(AppSms appsms) {

		JSONObject jsonObject = null;
		try {
			if (null == appsms.getId() || "".equals(appsms.getId())) {
				
				jsonObject = appsmsService.saveAppSms(appsms);

			} else {
				jsonObject = appsmsService.updateAppSms(appsms);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selectappsms")
    @ResponseBody
    public List<AppSms> selectappsms( AppSms item) {
        return appsmsService.selectAppSmsList(item);
    }


}