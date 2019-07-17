/*
 * @(#)UserOpinionController.java
 * @author: zj
 * @Date: 2019-01-31 09:26:34
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.UserOpinionMapper;
import com.kxjl.tasktransferplat.pojo.UserOpinion;
import com.kxjl.tasktransferplat.service.UserOpinionService;
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
 * 用户意见反馈表管理 UserOpinionController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-01-31 09:26:34
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tuseropinion")
public class UserOpinionController {
	@Autowired
	private UserOpinionService tuseropinionService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tuseropinion/index.ftl";
	}

	@RequestMapping("/tuseropinionList")
	@ManagerActionLog(operateDescribe="查询用户意见反馈表",operateFuncType=FunLogType.Query,operateModelClassName=UserOpinionMapper.class)
	@ResponseBody
	public String tuseropinionList( UserOpinion item, HttpServletRequest request,PageCondition pageCondition) {

		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");

		if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
			item.setCompanyId(manager.getCompanyId());
		}
		String rst = "";
		List<UserOpinion> tuseropinions = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tuseropinions = tuseropinionService.selectUserOpinionList(item);

		try {
			rst = PageUtil.packageTableData(page, tuseropinions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除用户意见反馈表",operateFuncType=FunLogType.Del,operateModelClassName=UserOpinionMapper.class)
	@ResponseBody
	public Message delete( UserOpinion item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tuseropinionService.deleteUserOpinion(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		UserOpinion tuseropinions = tuseropinionService.selectUserOpinionById(id);
//		return JSONObject.fromObject(tuseropinions).toString();
		return JSON.toJSONString(tuseropinions);
	}



    @RequestMapping("/selecttuseropinion")
    @ResponseBody
    public List<UserOpinion> selecttuseropinion( UserOpinion item) {
        return tuseropinionService.selectUserOpinionList(item);
    }


}