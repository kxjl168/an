/*
 * @(#)TOrderinfoController.java
 * @author: zhangJ
 * @Date: 2019-01-29 10:48:35
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderLockInfoService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已结账工单管理
 *
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-29 10:48:35
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/orderCompleted")
public class OrderCompletedController {
	@Autowired
	private OrderinfoService orderinfoService;
	
	@Autowired
	OrderLockInfoService orderLockInfoService;

	@Autowired
	CompanyService HehuorenService;
	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/orderCompleted/index.ftl";
	}

	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe="查询已结账工单",operateFuncType= FunLogType.Query,operateModelClassName=OrderinfoMapper.class)
	@ResponseBody
	public String torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		HashMap m=  (HashMap) request.getAttribute("principal");

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		if (type == 1) {
			if (manager.getType()==1){
				item.setCompanyId(manager.getCompanyId());
				
				Company hehuoren = HehuorenService.selectCompanyById(manager.getCompanyId());

				if (hehuoren.getAreaCode() != null && !hehuoren.getAreaCode().equals("")) {
					// zj 190507
					// 设置查询区域
					item.setAreaCode(hehuoren.getAreaCode().substring(0, 4));

				} else
					item.setAreaCode("888888"); // 合伙人没有区域，直接查询不到工单
				
			}else if (manager.getType()==2){
				item.setSellerId(manager.getCompanyId());
			}
		} else if (type == 2) {
			item.setSellerId(manager.getCompanyId());
		}



		String rst = "";
		List<Orderinfo> orderCompleteds = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);

		orderCompleteds = orderinfoService.selectOrderinfoListByComplete(item);

		try {
			rst = PageUtil.packageTableData(page, orderCompleteds);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除已结账工单",operateFuncType= FunLogType.Del,operateModelClassName=OrderinfoMapper.class)
	@ResponseBody
	public Message delete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();



		int result = orderinfoService.deleteOrderinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam String id,HttpServletRequest request) {

		Orderinfo orderinfo = orderinfoService.selectOrderinfoById(id);
		

		// 获取对应的锁完成图片信息
		OrderLockInfo olquery = new OrderLockInfo();
		olquery.setOrderNo(orderinfo.getOrderNo());
		List<OrderLockInfo> picinfos = orderLockInfoService.selectOrderLockInfoList(olquery);

		Map m = new HashMap<>();
		m.put("order", orderinfo);
		m.put("imgs", picinfos);

		Gson gs = new Gson();
		String rst = gs.toJson(m);

		return rst;
		
		
		//return JSON.toJSONString(orderinfo);
	}


    @RequestMapping("/selectorderCompleted")
    @ResponseBody
    public List<Orderinfo> selectorderCompleted(Orderinfo item) {
        return orderinfoService.selectOrderinfoList(item);
    }


}