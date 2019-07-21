/*
 * @(#)ReceivepoliceinfoController.java
 * @author: zj
 * @Date: 2019-07-19 22:22:03
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
import com.kxjl.video.dao.ReceivepoliceinfoMapper;

import com.kxjl.video.pojo.Receivepoliceinfo;
import com.kxjl.video.service.ReceivepoliceinfoService;

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
 * 接警人员信息管理 ReceivepoliceinfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-19 22:22:03
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/treceivepoliceinfo")
public class ReceivepoliceinfoController {
	@Autowired
	private ReceivepoliceinfoService treceivepoliceinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/treceivepoliceinfo/index.ftl";
	}

	@RequestMapping("/treceivepoliceinfoList")
	@ManagerActionLog(operateDescribe="查询接警人员信息",operateFuncType=FunLogType.Query,operateModelClassName=ReceivepoliceinfoMapper.class)
	@ResponseBody
	public String treceivepoliceinfoList( Receivepoliceinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<Receivepoliceinfo> treceivepoliceinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		treceivepoliceinfos = treceivepoliceinfoService.selectReceivepoliceinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, treceivepoliceinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除接警人员信息",operateFuncType=FunLogType.Del,operateModelClassName=ReceivepoliceinfoMapper.class)
	@ResponseBody
	public Message delete( Receivepoliceinfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = treceivepoliceinfoService.deleteReceivepoliceinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		Receivepoliceinfo treceivepoliceinfos = treceivepoliceinfoService.selectReceivepoliceinfoById(id);
		
		   return JSON.toJSONString(treceivepoliceinfos);
		//return JSONObject.fromObject(treceivepoliceinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param treceivepoliceinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改接警人员信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=ReceivepoliceinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Receivepoliceinfo treceivepoliceinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == treceivepoliceinfo.getId() || "".equals(treceivepoliceinfo.getId())) {
				
				jsonObject = treceivepoliceinfoService.saveReceivepoliceinfo(treceivepoliceinfo);

			} else {
				jsonObject = treceivepoliceinfoService.updateReceivepoliceinfo(treceivepoliceinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttreceivepoliceinfo")
    @ResponseBody
    public List<Receivepoliceinfo> selecttreceivepoliceinfo( Receivepoliceinfo item) {
        return treceivepoliceinfoService.selectReceivepoliceinfoList(item);
    }


}