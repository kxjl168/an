/*
 * @(#)UserinfoAuditController.java
 * @author: zj
 * @Date: 2019-06-05 13:27:26
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
import com.kxjl.tasktransferplat.dao.plus.UserinfoAuditMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoAuditService;
import com.kxjl.tasktransferplat.service.UserinfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁匠类型变更审核管理 UserinfoAuditController.java.
 * 
 * @author zj
 * @version 1.0.1 2019-06-05 13:27:26
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/userTypeaudit")
public class UserinfoAuditController {
	@Autowired
	private UserinfoAuditService tuserinfoauditService;
	
	@Autowired
	private UserinfoService userinfoService;
	
	@Autowired
	private OrderinfoService orderinfoService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tuserinfoAudit/index.ftl";
	}

	@RequestMapping("/tuserinfoauditList")
	@ManagerActionLog(operateDescribe = "查询锁匠类型变更审核", operateFuncType = FunLogType.Query, operateModelClassName = UserinfoAuditMapper.class)
	@ResponseBody
	public String tuserinfoauditList(UserinfoAudit item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
		List<UserinfoAudit> tuserinfoaudits = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tuserinfoaudits = tuserinfoauditService.selectUserinfoAuditList(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfoaudits);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除锁匠类型变更审核", operateFuncType = FunLogType.Del, operateModelClassName = UserinfoAuditMapper.class)
	@ResponseBody
	public Message delete(UserinfoAudit item, HttpServletRequest request) {

		Message msg = new Message();

		int result = tuserinfoauditService.deleteUserinfoAudit(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	/**
	 * 锁匠类型变更 详情
	 * @param id
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年6月10日
	 */
	@RequestMapping("/load")
	@ResponseBody
	public Map load(@RequestParam String id, HttpServletRequest request) {

		Map mp=new HashMap<>();
		
		
		UserinfoAudit tuserinfoaudits = tuserinfoauditService.selectUserinfoAuditById(id);

		mp.put("info", tuserinfoaudits);
		
		//未完成工单
		List<Orderinfo> orders= orderinfoService. selectOrderinfoByLockerId(tuserinfoaudits.getUserInfoId());

		mp.put("order", orders);
		
		//列出合伙人下的锁匠
			Userinfo curu=  userinfoService. selectUserinfoById(tuserinfoaudits.getUserInfoId());
			if(curu==null)
			{
				mp.put("error", "锁匠已被删除");
				return mp;
			}
			
		if (curu.getUserType().equals("2")||curu.getUserType().equals("3")) {
			// 合伙人删除，检查名下锁匠
			Userinfo uquery = new Userinfo();
			uquery.setUserType("4");// 普通锁匠
			uquery.setDataState(1);// 正常状态的
			uquery.setCompanyId(curu.getId());// 合伙人下的
			
			List<Userinfo> lockers = userinfoService.selectUserinfoList(uquery);
			
			mp.put("locker", lockers);
		}
		
		return mp;

	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tuserinfoaudit
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "保存修改锁匠类型变更审核", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoAuditMapper.class)
	@ResponseBody
	public String saveOrUpdate(UserinfoAudit tuserinfoaudit) {

		JSONObject jsonObject = null;
		try {
			if (null == tuserinfoaudit.getId() || "".equals(tuserinfoaudit.getId())) {

				jsonObject = tuserinfoauditService.saveUserinfoAudit(tuserinfoaudit);

			} else {
				jsonObject = tuserinfoauditService.updateUserinfoAudit(tuserinfoaudit);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	/**
	 * 审核锁匠类型变更
	 * 
	 * @param tuserinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年6月6日
	 */
	@RequestMapping("/userTypeChangeDone")
	@ResponseBody
	public Message userTypeChangeDone(UserinfoAudit tuserinfo, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Message rst = new Message();
		JSONObject jsonObject = new JSONObject();
		try {

			rst = tuserinfoauditService.userTypeChangeDone(tuserinfo);

		} catch (Exception e) {
			e.printStackTrace();
			rst.setBol(false);
			rst.setMessage("审核失败!");
		}
		// assert jsonObject != null;
		return rst;
	}

	@RequestMapping("/selecttuserinfoaudit")
	@ResponseBody
	public List<UserinfoAudit> selecttuserinfoaudit(UserinfoAudit item) {
		return tuserinfoauditService.selectUserinfoAuditList(item);
	}

}