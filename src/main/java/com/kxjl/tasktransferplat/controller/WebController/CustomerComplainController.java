/*
 * @(#)CustomerComplainController.java
 * @author: zj
 * @Date: 2019-06-03 13:24:36
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
import com.kxjl.tasktransferplat.dao.plus.CustomerComplainMapper;
import com.kxjl.tasktransferplat.pojo.CustomerComplain;
import com.kxjl.tasktransferplat.service.CustomerComplainService;
import com.kxjl.tasktransferplat.service.OrderinfoService;

import net.sf.json.JSONObject;

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
 * 客户投诉表管理 CustomerComplainController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-06-03 13:24:36
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tcustomercomplain")
public class CustomerComplainController {
	@Autowired
	private CustomerComplainService tcustomercomplainService;
	@Autowired
	private OrderinfoService orderinfoService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tcustomercomplain/index.ftl";
	}

	@RequestMapping("/tcustomercomplainList")
	@ManagerActionLog(operateDescribe="查询客户投诉表",operateFuncType=FunLogType.Query,operateModelClassName=CustomerComplainMapper.class)
	@ResponseBody
	public String tcustomercomplainList( CustomerComplain item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<CustomerComplain> tcustomercomplains = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tcustomercomplains = tcustomercomplainService.selectCustomerComplainList(item);

		try {
			rst = PageUtil.packageTableData(page, tcustomercomplains);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除客户投诉表",operateFuncType=FunLogType.Del,operateModelClassName=CustomerComplainMapper.class)
	@ResponseBody
	public Message delete( CustomerComplain item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tcustomercomplainService.deleteCustomerComplain(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		CustomerComplain tcustomercomplains = tcustomercomplainService.selectCustomerComplainById(id);
		return JSONObject.fromObject(tcustomercomplains).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tcustomercomplain
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改客户投诉表",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=CustomerComplainMapper.class)
	@ResponseBody
	public String saveOrUpdate(CustomerComplain tcustomercomplain) {

		JSONObject jsonObject = null;
		if(tcustomercomplain.getOrderNo()!=null){
			tcustomercomplain.setOrderInfoId(orderinfoService.selectOrderByOrerNo(tcustomercomplain.getOrderNo()).getId());
		}
		try {
			if (null == tcustomercomplain.getId()) {
				
				jsonObject = tcustomercomplainService.saveCustomerComplain(tcustomercomplain);

			} else {
				jsonObject = tcustomercomplainService.updateCustomerComplain(tcustomercomplain);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttcustomercomplain")
    @ResponseBody
    public List<CustomerComplain> selecttcustomercomplain( CustomerComplain item) {
        return tcustomercomplainService.selectCustomerComplainList(item);
    }


}