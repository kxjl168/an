/*
 * @(#)CompanyController.java
 * @author: zj
 * @Date: 2019-01-29 16:27:40
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderStasticService;
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
 * 统计
 * 
 * @author zj
 * @version 1.0.1 2019-02-12 16:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/stastic")
public class OrderStasticController {
	@Autowired
	private OrderStasticService stasticService;

	@RequestMapping("/statuslist")
	public String manager(Model model) {

		return "/backend/page/stastic/index1.ftl";
	}

	/**
	 * 工单状态统计
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年2月12日
	 */
	@RequestMapping("/statusStastic")
	// @ManagerActionLog(operateDescribe="查询企业待审核",operateFuncType=FunLogType.Query,operateModelClassName=CompanyMapper.class)
	@ResponseBody
	public String statusStastic(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		HashMap m=  (HashMap) request.getAttribute("principal");

		Manager manager = (Manager)m.get("user");

		if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
			item.setCompanyId(manager.getCompanyId());
		}

		String rst = "";
		List<Orderinfo> list = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		list = stasticService.selectOrderStatusStasticList(item);

		try {
			rst = PageUtil.packageTableData(page, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

}