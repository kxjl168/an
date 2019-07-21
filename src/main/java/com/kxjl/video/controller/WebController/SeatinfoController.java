/*
 * @(#)SeatinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:22:17
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
import com.kxjl.video.dao.SeatinfoMapper;

import com.kxjl.video.pojo.Seatinfo;
import com.kxjl.video.service.SeatinfoService;

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
 * 坐席信息管理 SeatinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:22:17
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tseatinfo")
public class SeatinfoController {
	@Autowired
	private SeatinfoService tseatinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tseatinfo/index.ftl";
	}

	@RequestMapping("/tseatinfoList")
	@ManagerActionLog(operateDescribe="查询坐席信息",operateFuncType=FunLogType.Query,operateModelClassName=SeatinfoMapper.class)
	@ResponseBody
	public String tseatinfoList( Seatinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Seatinfo> tseatinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tseatinfos = tseatinfoService.selectSeatinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tseatinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除坐席信息",operateFuncType=FunLogType.Del,operateModelClassName=SeatinfoMapper.class)
	@ResponseBody
	public Message delete( Seatinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tseatinfoService.deleteSeatinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Seatinfo tseatinfos = tseatinfoService.selectSeatinfoById(id);
		
		   return JSON.toJSONString(tseatinfos);
		//return JSONObject.fromObject(tseatinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tseatinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改坐席信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=SeatinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Seatinfo tseatinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == tseatinfo.getId() || "".equals(tseatinfo.getId())) {
				
				jsonObject = tseatinfoService.saveSeatinfo(tseatinfo);

			} else {
				jsonObject = tseatinfoService.updateSeatinfo(tseatinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttseatinfo")
    @ResponseBody
    public List<Seatinfo> selecttseatinfo( Seatinfo item) {
        return tseatinfoService.selectSeatinfoList(item);
    }


}