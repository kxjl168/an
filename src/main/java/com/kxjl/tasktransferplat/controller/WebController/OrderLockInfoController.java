/*
 * @(#)OrderLockInfoController.java
 * @author: zj
 * @Date: 2019-07-12 10:35:54
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
import com.kxjl.tasktransferplat.dao.plus.OrderLockInfoMapper;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;
import com.kxjl.tasktransferplat.service.OrderLockInfoService;
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
 * 工单上多把锁的安装图片信息管理 OrderLockInfoController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-07-12 10:35:54
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderlockinfo")
public class OrderLockInfoController {
	@Autowired
	private OrderLockInfoService torderlockinfoService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/torderlockinfo/index.ftl";
	}

	@RequestMapping("/torderlockinfoList")
	@ManagerActionLog(operateDescribe="查询工单上多把锁的安装图片信息",operateFuncType=FunLogType.Query,operateModelClassName=OrderLockInfoMapper.class)
	@ResponseBody
	public String torderlockinfoList( OrderLockInfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<OrderLockInfo> torderlockinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		torderlockinfos = torderlockinfoService.selectOrderLockInfoList(item);

		try {
			rst = PageUtil.packageTableData(page, torderlockinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除工单上多把锁的安装图片信息",operateFuncType=FunLogType.Del,operateModelClassName=OrderLockInfoMapper.class)
	@ResponseBody
	public Message delete( OrderLockInfo item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = torderlockinfoService.deleteOrderLockInfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		OrderLockInfo torderlockinfos = torderlockinfoService.selectOrderLockInfoById(id);
		
		   return JSON.toJSONString(torderlockinfos);
		//return JSONObject.fromObject(torderlockinfos).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderlockinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改工单上多把锁的安装图片信息",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=OrderLockInfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(OrderLockInfo torderlockinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == torderlockinfo.getId() || "".equals(torderlockinfo.getId())) {
				
				jsonObject = torderlockinfoService.saveOrderLockInfo(torderlockinfo);

			} else {
				jsonObject = torderlockinfoService.updateOrderLockInfo(torderlockinfo);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttorderlockinfo")
    @ResponseBody
    public List<OrderLockInfo> selecttorderlockinfo( OrderLockInfo item) {
        return torderlockinfoService.selectOrderLockInfoList(item);
    }


}