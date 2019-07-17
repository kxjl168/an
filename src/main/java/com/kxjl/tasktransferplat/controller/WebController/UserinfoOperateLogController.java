/*
 * @(#)UserinfoOperateLogController.java
 * @author: zj
 * @Date: 2019-06-05 13:27:01
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
import com.kxjl.tasktransferplat.dao.plus.UserinfoOperateLogMapper;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;
import com.kxjl.tasktransferplat.service.UserinfoOperateLogService;
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
 * 锁匠变更日志管理 UserinfoOperateLogController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-06-05 13:27:01
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tuserinfooperatelog")
public class UserinfoOperateLogController {
	@Autowired
	private UserinfoOperateLogService tuserinfooperatelogService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tuserinfooperatelog/index.ftl";
	}

	@RequestMapping("/tuserinfooperatelogList")
	@ManagerActionLog(operateDescribe="查询锁匠变更日志",operateFuncType=FunLogType.Query,operateModelClassName=UserinfoOperateLogMapper.class)
	@ResponseBody
	public String tuserinfooperatelogList( UserinfoOperateLog item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<UserinfoOperateLog> tuserinfooperatelogs = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tuserinfooperatelogs = tuserinfooperatelogService.selectUserinfoOperateLogList(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfooperatelogs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除锁匠变更日志",operateFuncType=FunLogType.Del,operateModelClassName=UserinfoOperateLogMapper.class)
	@ResponseBody
	public Message delete( UserinfoOperateLog item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tuserinfooperatelogService.deleteUserinfoOperateLog(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		UserinfoOperateLog tuserinfooperatelogs = tuserinfooperatelogService.selectUserinfoOperateLogById(id);
		
		   return JSON.toJSONString(tuserinfooperatelogs);

	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tuserinfooperatelog
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改锁匠变更日志",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=UserinfoOperateLogMapper.class)
	@ResponseBody
	public String saveOrUpdate(UserinfoOperateLog tuserinfooperatelog) {

		JSONObject jsonObject = null;
		try {
			if (null == tuserinfooperatelog.getId() || "".equals(tuserinfooperatelog.getId())) {
				
				jsonObject = tuserinfooperatelogService.saveUserinfoOperateLog(tuserinfooperatelog);

			} else {
				jsonObject = tuserinfooperatelogService.updateUserinfoOperateLog(tuserinfooperatelog);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttuserinfooperatelog")
    @ResponseBody
    public List<UserinfoOperateLog> selecttuserinfooperatelog( UserinfoOperateLog item) {
        return tuserinfooperatelogService.selectUserinfoOperateLogList(item);
    }


}