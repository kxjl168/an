/*
 * @(#)MessageController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-29 14:54:44
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.MessageMapper;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.pojo.Message;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.MessageService;
import com.kxjl.tasktransferplat.util.TokenUtil;
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
 * 消息表管理 MessageController.java.
 * 
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-29 14:54:44
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/usermessage")
public class MessageController {
	@Autowired
	private MessageService usermessageService;

	@Autowired
	private ManagerMessageService managerMessageService;

	/**
	 * 系统顶部消息接口
	 * 
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年6月18日
	 */
	@RequestMapping("/managermessageList")
	@ResponseBody
	public String managermessageList(Message item, HttpServletRequest request,
			PageCondition pageCondition) {

		String rst = "";
		List<Message> usermessages = new ArrayList<>();

		Manager m = TokenUtil.getWebLoginUser();
		ManagerMessage managerMessage = new ManagerMessage();
		managerMessage.setUserId(m.getId());
		List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
		Gson gs=new Gson();
		String data= gs.toJson(list);

		return data;
	}

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/usermessage/index.ftl";
	}

	@RequestMapping("/usermessageList")
	@ManagerActionLog(operateDescribe = "查询消息表", operateFuncType = FunLogType.Query, operateModelClassName = MessageMapper.class)
	@ResponseBody
	public String usermessageList(Message item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
		List<Message> usermessages = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		usermessages = usermessageService.selectMessageList(item);

		try {
			rst = PageUtil.packageTableData(page, usermessages);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除消息表", operateFuncType = FunLogType.Del, operateModelClassName = MessageMapper.class)
	@ResponseBody
	public com.kxjl.base.util.Message delete(Message item, HttpServletRequest request) {

		com.kxjl.base.util.Message msg = new com.kxjl.base.util.Message();

		int result = usermessageService.deleteMessage(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam Long id, HttpServletRequest request) {

		Message usermessages = usermessageService.selectMessageById(id);
		return JSONObject.toJSONString(usermessages);
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param usermessage
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "保存修改消息表", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = MessageMapper.class)
	@ResponseBody
	public String saveOrUpdate(Message usermessage) {

		JSONObject jsonObject = null;
		try {
			if (null == usermessage.getMessageId()) {

				jsonObject = usermessageService.saveMessage(usermessage);

			} else {
				jsonObject = usermessageService.updateMessage(usermessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selectusermessage")
	@ResponseBody
	public List<Message> selectusermessage(Message item) {
		return usermessageService.selectMessageList(item);
	}

}