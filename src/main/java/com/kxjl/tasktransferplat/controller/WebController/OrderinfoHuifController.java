/*
 * @(#)OrderinfoController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-31 13:20:58
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.OrderLockInfoService;
import com.kxjl.tasktransferplat.service.OrderinfoService;

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
 * 待回访工单管理 OrderinfoController.java.
 * 
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-31 13:20:58
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfo_huif")
public class OrderinfoHuifController {

	private static final Logger logger = LoggerFactory.getLogger(OrderinfoHuifController.class);

	@Autowired
	private OrderinfoService torderinfoService;

	@Autowired
	OrderLockInfoService orderLockInfoService;

	@Autowired
	CompanyService HehuorenService;
	@Autowired
	private ManagerMessageService managerMessageService;

	@RequestMapping("/manager")
	public String manager(Model model, HttpServletRequest request) {
		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			ManagerMessage managerMessage = new ManagerMessage();
			managerMessage.setUserId(manager.getId());
			managerMessage.setType(2 + "");
			managerMessageService.updateMessageReadByUserAndType(managerMessage);

			List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
			MyWebSocket.sendMessage(JSON.toJSONString(list), managerMessage.getUserId());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("websocket发送消息报错");
		}
		return "/backend/page/torderinfo_huif/index.ftl";
	}

	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe = "查询待回访工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		if (type == 1) {
			item.setCompanyId(manager.getCompanyId());

			Company hehuoren = HehuorenService.selectCompanyById(manager.getCompanyId());

			if (hehuoren.getAreaCode() != null && !hehuoren.getAreaCode().equals("")) {
				// zj 190507
				// 设置查询区域
				item.setAreaCode(hehuoren.getAreaCode().substring(0, 4));

			} else
				item.setAreaCode("888888"); // 合伙人没有区域，直接查询不到工单

		} else if (type == 2) {
			item.setSellerId(manager.getCompanyId());
		}
		String rst = "";
		List<Orderinfo> torderinfos = new ArrayList<>();

		item.setType(3);

		Page page = PageUtil.getPage(pageCondition);
		torderinfos = torderinfoService.selectOrderinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除待回访工单", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message delete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = torderinfoService.deleteOrderinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam String id, HttpServletRequest request) {

		Orderinfo torderinfos = torderinfoService.selectOrderinfoById(id);

		// 获取对应的锁完成图片信息
		OrderLockInfo olquery = new OrderLockInfo();
		olquery.setOrderNo(torderinfos.getOrderNo());
		List<OrderLockInfo> picinfos = orderLockInfoService.selectOrderLockInfoList(olquery);

		Map m = new HashMap<>();
		m.put("order", torderinfos);
		m.put("imgs", picinfos);

		Gson gs = new Gson();
		String rst = gs.toJson(m);

		return rst;
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "修改待回访工单", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Orderinfo torderinfo) {

		JSONObject jsonObject = null;
		try {
			// 回访完成
			jsonObject = torderinfoService.ComplateHuifOrderinfo(torderinfo);

		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("bol", false);
			jsonObject.put("message", "操作失败!");
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selecttorderinfo")
	@ResponseBody
	public List<Orderinfo> selecttorderinfo(Orderinfo item) {
		return torderinfoService.selectOrderinfoList(item);
	}

	/**
	 * 源匠合伙人首页显示--锁匠已作业工单统计
	 * 
	 * @param request
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/huiFlock")
	public Map HuiFlock(HttpServletRequest request, Orderinfo item) {
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		item.setCompanyId(manager.getCompanyId());
		item.setReceiveTime("2019");
		item.setDoneTime("2019");
		Map map = new HashMap();
		List<Map<String, Object>> map1 = torderinfoService.countHuiFlockOrder(item);
		List<Map<String, Object>> map2 = torderinfoService.countHuiFlockOrderPass(item);
		map.put("orders", map1);
		map.put("ordersPass", map2);
		return map;
	}

	/**
	 * 源匠合伙人首页显示--已作业工单统计
	 * 
	 * @param request
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finishLock")
	public Map finishLock(HttpServletRequest request, Orderinfo item) {
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		item.setCompanyId(manager.getCompanyId());
		item.setDoneTime(item.getCreateTime());
		Map map = new HashMap();
		List<Map<String, Object>> map1 = torderinfoService.countFinishLockOrder(item);
		List<Map<String, Object>> map2 = torderinfoService.countHuiFlockOrderPass(item);
		map.put("orders", map1);
		map.put("ordersPass", map2);
		return map;
	}
}