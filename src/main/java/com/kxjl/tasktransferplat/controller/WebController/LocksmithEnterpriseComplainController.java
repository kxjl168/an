/*
 * @(#)LocksmithEnterpriseComplainController.java
 * @author: zj
 * @Date: 2019-06-03 14:24:21
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
import com.kxjl.tasktransferplat.dao.plus.LocksmithEnterpriseComplainMapper;
import com.kxjl.tasktransferplat.pojo.LocksmithEnterpriseComplain;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.LocksmithEnterpriseComplainService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 琐企投诉表管理 LocksmithEnterpriseComplainController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-06-03 14:24:21
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tlocksmithenterprisecomplain")
public class LocksmithEnterpriseComplainController {
	@Autowired
	private LocksmithEnterpriseComplainService tlocksmithenterprisecomplainService;
	@Autowired
	private OrderinfoService orderinfoService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tlocksmithenterprisecomplain/index.ftl";
	}

	@RequestMapping("/tlocksmithenterprisecomplainList")
	@ManagerActionLog(operateDescribe="查询琐企投诉表",operateFuncType=FunLogType.Query,operateModelClassName=LocksmithEnterpriseComplainMapper.class)
	@ResponseBody
	public String tlocksmithenterprisecomplainList( LocksmithEnterpriseComplain item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<LocksmithEnterpriseComplain> tlocksmithenterprisecomplains = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tlocksmithenterprisecomplains = tlocksmithenterprisecomplainService.selectLocksmithEnterpriseComplainList(item);

		try {
			rst = PageUtil.packageTableData(page, tlocksmithenterprisecomplains);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除琐企投诉表",operateFuncType=FunLogType.Del,operateModelClassName=LocksmithEnterpriseComplainMapper.class)
	@ResponseBody
	public Message delete( LocksmithEnterpriseComplain item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = tlocksmithenterprisecomplainService.deleteLocksmithEnterpriseComplain(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load( @RequestParam Long id,HttpServletRequest request) {
		 
		
		LocksmithEnterpriseComplain tlocksmithenterprisecomplains = tlocksmithenterprisecomplainService.selectLocksmithEnterpriseComplainById(id);
		return JSONObject.fromObject(tlocksmithenterprisecomplains).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tlocksmithenterprisecomplain
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改琐企投诉表",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=LocksmithEnterpriseComplainMapper.class)
	@ResponseBody
	public String saveOrUpdate(LocksmithEnterpriseComplain tlocksmithenterprisecomplain) {

		JSONObject jsonObject = null;
		if(tlocksmithenterprisecomplain.getOrderNo()!=null){
			tlocksmithenterprisecomplain.setOrderInfoId(orderinfoService.selectOrderByOrerNo(tlocksmithenterprisecomplain.getOrderNo()).getId());
		}
		try {
			if (null == tlocksmithenterprisecomplain.getId()) {
				
				jsonObject = tlocksmithenterprisecomplainService.saveLocksmithEnterpriseComplain(tlocksmithenterprisecomplain);

			} else {
				jsonObject = tlocksmithenterprisecomplainService.updateLocksmithEnterpriseComplain(tlocksmithenterprisecomplain);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttlocksmithenterprisecomplain")
    @ResponseBody
    public List<LocksmithEnterpriseComplain> selecttlocksmithenterprisecomplain( LocksmithEnterpriseComplain item) {
        return tlocksmithenterprisecomplainService.selectLocksmithEnterpriseComplainList(item);
    }

	@RequestMapping("/company")
	@ResponseBody
	public Map company( LocksmithEnterpriseComplain item) {
		Map map=new HashMap();
		Orderinfo orderinfo=orderinfoService.selectOrderByOrerNo(item.getOrderNo());
		map.put("enterpriseName",orderinfo.getEnterpriseName());
		map.put("enterpriseId",orderinfo.getSellerId());
		map.put("CustomerPhone",orderinfo.getCustomerPhone());
		map.put("CustomerName",orderinfo.getCustomerName());
		return map;
	}

}