/*
 * @(#)OrderinfoAuditController.java
 * @author: zj
 * @Date: 2019-01-31 15:53:39
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAuditMapper;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.OrderinfoAuditService;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单审核记录管理 OrderinfoAuditController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-01-31 15:53:39
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfoaudit")
public class OrderinfoAuditController {
	private static final Logger logger = LoggerFactory.getLogger(OrderinfoHuifController.class);

	@Autowired
	private OrderinfoAuditService torderinfoauditService;

	@RequestMapping("/manager")
	public String manager(Model model, HttpServletRequest request) {
		return "/backend/page/torderinfoaudit/index.ftl";
	}

	@RequestMapping("/torderinfoauditList")
	@ManagerActionLog(operateDescribe="查询订单审核记录",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public String torderinfoauditList( OrderinfoAudit item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<OrderinfoAudit> torderinfoaudits = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		torderinfoaudits = torderinfoauditService.selectOrderInfoAudit(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfoaudits);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}
	@RequestMapping("/torderinfoaudit")
	@ManagerActionLog(operateDescribe="查询订单审核记录",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public String torderinfoaudit( OrderinfoAudit item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<OrderinfoAudit> torderinfoaudits = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		torderinfoaudits = torderinfoauditService.selectOrderinfoAuditList(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfoaudits);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除订单审核记录",operateFuncType=FunLogType.Del,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public Message delete( OrderinfoAudit item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = torderinfoauditService.deleteOrderinfoAudit(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		OrderinfoAudit torderinfoaudits = torderinfoauditService.selectOrderinfoAuditById(id);
		return JSON.toJSONString(torderinfoaudits);
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderinfoaudit
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改订单审核记录",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public String saveOrUpdate(OrderinfoAudit torderinfoaudit) {

		JSONObject jsonObject = null;
		try {
			if (null == torderinfoaudit.getId()) {
				
				jsonObject = torderinfoauditService.saveOrderinfoAudit(torderinfoaudit);

			} else {
				jsonObject = torderinfoauditService.updateOrderinfoAudit(torderinfoaudit);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttorderinfoaudit")
    @ResponseBody
    public List<OrderinfoAudit> selecttorderinfoaudit( OrderinfoAudit item) {
        return torderinfoauditService.selectOrderinfoAuditList(item);
    }



	/**
	 * 锁企管理员首页显示--申请加钱
	 * @param request
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderPlus")
	public Map orderPlus(HttpServletRequest request,OrderinfoAudit item){
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		item.setEnterpriseId(manager.getCompanyId());
		Map map=new HashMap();
		List<Map<String, Object>> map1=torderinfoauditService.countPlusOrder(item);
		List<Map<String, Object>> map2=torderinfoauditService.countPlusOrderPass(item);
		map.put("orders",map1);
		map.put("ordersPass",map2);
		return map;
	}


	/**
	 * 源匠客服首页显示--我已处理的锁匠工单量统计
	 * @param request
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doOrderUserInfo")
	public Map doOrderUserInfo(HttpServletRequest request,OrderinfoAudit item){
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		item.setAuditor(manager.getId());
		item.setProposTime("2019");
		Map map=new HashMap();
		List<Map<String, Object>> map2=torderinfoauditService.countUserInfoOrderPass(item);
		List<Map<String, Object>> map1=torderinfoauditService.countUserInfoOrder(item);
		map.put("orders",map1);
		map.put("ordersPass",map2);
		return map;
	}

	/**
	 * 锁企客服首页显示--我已处理的源匠工单量统计
	 * @param request
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doOrderNormal")
	public Map doOrderNormal(HttpServletRequest request,OrderinfoAudit item){
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		item.setAuditor(manager.getId());
		item.setCompanyId(manager.getCompanyId());
		Map map=new HashMap();
		List<Map<String, Object>> map2=torderinfoauditService.countNormalOrderAllPass(item);
		List<Map<String, Object>> map1=torderinfoauditService.countNormalOrderAll(item);
		map.put("orders",map1);
		map.put("ordersPass",map2);
		return map;
	}

}