/*
 * @(#)AreainfoAreaController.java
 * @author: zj
 * @Date: 2019-07-19 22:23:26
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
import com.kxjl.video.dao.AreainfoAreaMapper;

import com.kxjl.video.pojo.AreainfoArea;
import com.kxjl.video.service.AreainfoAreaService;

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
 * 片区对应的物理行政区域 1对多管理 AreainfoAreaController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:23:26
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tareainfoarea")
public class AreainfoAreaController {
	@Autowired
	private AreainfoAreaService tareainfoareaService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tareainfoarea/index.ftl";
	}

	@RequestMapping("/tareainfoareaList")
	@ManagerActionLog(operateDescribe="查询片区对应的物理行政区域 1对多",operateFuncType=FunLogType.Query,operateModelClassName=AreainfoAreaMapper.class)
	@ResponseBody
	public String tareainfoareaList( AreainfoArea item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<AreainfoArea> tareainfoareas = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tareainfoareas = tareainfoareaService.selectAreainfoAreaList(item);

		try {
			rst = PageUtil.packageTableData(page, tareainfoareas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除片区对应的物理行政区域 1对多",operateFuncType=FunLogType.Del,operateModelClassName=AreainfoAreaMapper.class)
	@ResponseBody
	public Message delete( AreainfoArea item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tareainfoareaService.deleteAreainfoArea(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		AreainfoArea tareainfoareas = tareainfoareaService.selectAreainfoAreaById(id);
		
		   return JSON.toJSONString(tareainfoareas);
		//return JSONObject.fromObject(tareainfoareas).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tareainfoarea
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改片区对应的物理行政区域 1对多",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=AreainfoAreaMapper.class)
	@ResponseBody
	public String saveOrUpdate(AreainfoArea tareainfoarea) {

		JSONObject jsonObject = null;
		try {
			if (null == tareainfoarea.getId() || "".equals(tareainfoarea.getId())) {
				
				jsonObject = tareainfoareaService.saveAreainfoArea(tareainfoarea);

			} else {
				jsonObject = tareainfoareaService.updateAreainfoArea(tareainfoarea);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttareainfoarea")
    @ResponseBody
    public List<AreainfoArea> selecttareainfoarea( AreainfoArea item) {
        return tareainfoareaService.selectAreainfoAreaList(item);
    }


}