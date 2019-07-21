/*
 * @(#)SysDictInfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:14:51
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
import com.kxjl.video.dao.SysDictInfoMapper;

import com.kxjl.video.pojo.SysDictInfo;
import com.kxjl.video.service.SysDictInfoService;

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
 * 系统字典表管理 SysDictInfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:14:51
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/sysdictinfo")
public class SysDictInfoController {
	@Autowired
	private SysDictInfoService sysdictinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/sysdictinfo/index.ftl";
	}

	@RequestMapping("/sysdictinfoList")
	@ManagerActionLog(operateDescribe="查询系统字典表",operateFuncType=FunLogType.Query,operateModelClassName=SysDictInfoMapper.class)
	@ResponseBody
	public String sysdictinfoList( SysDictInfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<SysDictInfo> sysdictinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		sysdictinfos = sysdictinfoService.selectSysDictInfoList(item);

		try {
			rst = PageUtil.packageTableData(page, sysdictinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除系统字典表",operateFuncType=FunLogType.Del,operateModelClassName=SysDictInfoMapper.class)
	@ResponseBody
	public Message delete( SysDictInfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = sysdictinfoService.deleteSysDictInfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		SysDictInfo sysdictinfos = sysdictinfoService.selectSysDictInfoById(id);
		
		   return JSON.toJSONString(sysdictinfos);
		//return JSONObject.fromObject(sysdictinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param sysdictinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改系统字典表",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=SysDictInfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(SysDictInfo sysdictinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == sysdictinfo.getId()) {
				
				jsonObject = sysdictinfoService.saveSysDictInfo(sysdictinfo);

			} else {
				jsonObject = sysdictinfoService.updateSysDictInfo(sysdictinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selectsysdictinfo")
    @ResponseBody
    public List<SysDictInfo> selectsysdictinfo( SysDictInfo item) {
        return sysdictinfoService.selectSysDictInfoList(item);
    }


}