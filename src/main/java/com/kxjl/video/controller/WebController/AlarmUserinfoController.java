/*
 * @(#)AlarmUserinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:20:45
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
import com.kxjl.video.dao.AlarmUserinfoMapper;

import com.kxjl.video.pojo.AlarmUserinfo;
import com.kxjl.video.service.AlarmUserinfoService;

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
 * 报警用户信息管理 AlarmUserinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:20:45
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/talarmuserinfo")
public class AlarmUserinfoController {
	@Autowired
	private AlarmUserinfoService talarmuserinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/talarmuserinfo/index.ftl";
	}

	@RequestMapping("/talarmuserinfoList")
	@ManagerActionLog(operateDescribe="查询报警用户信息",operateFuncType=FunLogType.Query,operateModelClassName=AlarmUserinfoMapper.class)
	@ResponseBody
	public String talarmuserinfoList( AlarmUserinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<AlarmUserinfo> talarmuserinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		talarmuserinfos = talarmuserinfoService.selectAlarmUserinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, talarmuserinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除报警用户信息",operateFuncType=FunLogType.Del,operateModelClassName=AlarmUserinfoMapper.class)
	@ResponseBody
	public Message delete( AlarmUserinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = talarmuserinfoService.deleteAlarmUserinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		AlarmUserinfo talarmuserinfos = talarmuserinfoService.selectAlarmUserinfoById(id);
		
		   return JSON.toJSONString(talarmuserinfos);
		//return JSONObject.fromObject(talarmuserinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param talarmuserinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改报警用户信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=AlarmUserinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(AlarmUserinfo talarmuserinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == talarmuserinfo.getId() || "".equals(talarmuserinfo.getId())) {
				
				jsonObject = talarmuserinfoService.saveAlarmUserinfo(talarmuserinfo);

			} else {
				jsonObject = talarmuserinfoService.updateAlarmUserinfo(talarmuserinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttalarmuserinfo")
    @ResponseBody
    public List<AlarmUserinfo> selecttalarmuserinfo( AlarmUserinfo item) {
        return talarmuserinfoService.selectAlarmUserinfoList(item);
    }


}