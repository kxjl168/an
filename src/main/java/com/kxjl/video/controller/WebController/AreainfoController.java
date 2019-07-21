/*
 * @(#)AreainfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:21:31
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
import com.kxjl.video.dao.AreainfoMapper;

import com.kxjl.video.pojo.Areainfo;
import com.kxjl.video.service.AreainfoService;

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
 * 片区信息管理 AreainfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:21:31
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tareainfo")
public class AreainfoController {
	@Autowired
	private AreainfoService tareainfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tareainfo/index.ftl";
	}

	@RequestMapping("/tareainfoList")
	@ManagerActionLog(operateDescribe="查询片区信息",operateFuncType=FunLogType.Query,operateModelClassName=AreainfoMapper.class)
	@ResponseBody
	public String tareainfoList( Areainfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Areainfo> tareainfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tareainfos = tareainfoService.selectAreainfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tareainfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除片区信息",operateFuncType=FunLogType.Del,operateModelClassName=AreainfoMapper.class)
	@ResponseBody
	public Message delete( Areainfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tareainfoService.deleteAreainfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Areainfo tareainfos = tareainfoService.selectAreainfoById(id);
		
		   return JSON.toJSONString(tareainfos);
		//return JSONObject.fromObject(tareainfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tareainfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改片区信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=AreainfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Areainfo tareainfo) {

		JSONObject jsonObject = null;
		try {
			if (null == tareainfo.getId() || "".equals(tareainfo.getId())) {
				
				jsonObject = tareainfoService.saveAreainfo(tareainfo);

			} else {
				jsonObject = tareainfoService.updateAreainfo(tareainfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttareainfo")
    @ResponseBody
    public List<Areainfo> selecttareainfo( Areainfo item) {
        return tareainfoService.selectAreainfoList(item);
    }


}