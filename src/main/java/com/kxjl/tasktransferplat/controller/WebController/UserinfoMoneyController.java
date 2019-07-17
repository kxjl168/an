/*
 * @(#)UserinfoController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-29 10:29:14
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
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoMoneyDetailService;
import com.kxjl.tasktransferplat.service.UserinfoService;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 费用管理 UserinfoPointController.java.
 * 
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-29 10:29:14
 * @since 1.0.0
 */
@Controller
	@RequestMapping("/manager/tuserinfoMoney")
public class UserinfoMoneyController {
	@Autowired
	private UserinfoService tuserinfoService;
	@Autowired
	private OrderinfoService orderinfoService;
	@Autowired
	private UserinfoMoneyDetailService userinfoMoneyDetailService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tuserinfoMoney/index.ftl";
	}
	@RequestMapping("/detail")
	public String detail(Model model) {

		return "/backend/page/tuserinfoMoneyDetail/index.ftl";
	}


	/**
	 * 锁匠账户查询
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年6月11日
	 */
	@RequestMapping("/list")
	@ManagerActionLog(operateDescribe="查询锁匠费用",operateFuncType=FunLogType.Query,operateModelClassName=UserinfoMapper.class)
	@ResponseBody
	public String tuserinfoList( Userinfo item, HttpServletRequest request,PageCondition pageCondition) {
		HashMap m=  (HashMap) request.getAttribute("principal");

		Manager manager = (Manager)m.get("user");

		if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
			item.setCompanyId(manager.getCompanyId());
		}
		String rst = "";
		List<Userinfo> tuserinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tuserinfos = tuserinfoService.selectListByMoney(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/moneyList")
	@ManagerActionLog(operateDescribe="查询费用历史",operateFuncType=FunLogType.Query,operateModelClassName=UserinfoMapper.class)
	@ResponseBody
	public String pointList( Userinfo item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<UserinfoMoneyDetail> tuserinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		
		tuserinfos = userinfoMoneyDetailService.selectMoneyList(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}



}