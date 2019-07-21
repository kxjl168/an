/*
 * @(#)PhoneinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:21:49
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
import com.kxjl.video.dao.PhoneinfoMapper;

import com.kxjl.video.pojo.Phoneinfo;
import com.kxjl.video.service.PhoneinfoService;

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
 * 接警手机信息管理 PhoneinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:21:49
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tphoneinfo")
public class PhoneinfoController {
	@Autowired
	private PhoneinfoService tphoneinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tphoneinfo/index.ftl";
	}

	@RequestMapping("/tphoneinfoList")
	@ManagerActionLog(operateDescribe="查询接警手机信息",operateFuncType=FunLogType.Query,operateModelClassName=PhoneinfoMapper.class)
	@ResponseBody
	public String tphoneinfoList( Phoneinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Phoneinfo> tphoneinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tphoneinfos = tphoneinfoService.selectPhoneinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tphoneinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除接警手机信息",operateFuncType=FunLogType.Del,operateModelClassName=PhoneinfoMapper.class)
	@ResponseBody
	public Message delete( Phoneinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tphoneinfoService.deletePhoneinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Phoneinfo tphoneinfos = tphoneinfoService.selectPhoneinfoById(id);
		
		   return JSON.toJSONString(tphoneinfos);
		//return JSONObject.fromObject(tphoneinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tphoneinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改接警手机信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=PhoneinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Phoneinfo tphoneinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == tphoneinfo.getId() || "".equals(tphoneinfo.getId())) {
				
				jsonObject = tphoneinfoService.savePhoneinfo(tphoneinfo);

			} else {
				jsonObject = tphoneinfoService.updatePhoneinfo(tphoneinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttphoneinfo")
    @ResponseBody
    public List<Phoneinfo> selecttphoneinfo( Phoneinfo item) {
        return tphoneinfoService.selectPhoneinfoList(item);
    }


}