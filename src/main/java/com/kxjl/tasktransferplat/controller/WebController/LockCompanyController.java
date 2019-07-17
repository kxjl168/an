/*
 * @(#)LockCompanyController.java
 * @author: zj
 * @Date: 2019-04-11 16:27:40
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;
import com.kxjl.tasktransferplat.service.LockCompanyService;
import com.kxjl.tasktransferplat.service.PriceLockCompanyService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 锁企管理 LockCompanyController.java.
 *
 * @author zhangyong
 * @version 1.0.1 2019-04-11 16:27:40
 * @since 1.0.0
 */
@Controller
// " /ttfpzj/manager/tlockcompany/"
@RequestMapping("/manager/tlockcompany")
public class LockCompanyController {
	@Autowired
	private LockCompanyService tlockCompanyService;

	@RequestMapping("/manager")
	public String manager(Model model) {
		return "/backend/page/tlockCompany/index.ftl";
	}

	@RequestMapping("/managerPass")
	public String managerPass(Model model) {
		return "/backend/page/tlockCompanyPass/index.ftl";
	}

	@RequestMapping("/managerNoPass")
	public String managerNoPass(Model model) {
		return "/backend/page/tlockCompanyNoPass/index.ftl";
	}

	@RequestMapping("/tlockCompanyUnAuditList")
	@ManagerActionLog(operateDescribe = "查询企业待审核", operateFuncType = FunLogType.Query, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public String tcompanyUnAuditList(LockCompany item, HttpServletRequest request, PageCondition pageCondition) {
		
		
		
		
		String rst = "";
		List<LockCompany> tcompanys = new ArrayList<>();
		Page page = PageUtil.getPage(pageCondition);
		tcompanys = tlockCompanyService.selectUnAuditCompanyList(item);
		try {
			rst = PageUtil.packageTableData(page, tcompanys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	/**
	 * 查询所有企业，下拉框使用
	 *
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 */
	@RequestMapping("/allCompany")
	@ResponseBody
	public String allCompanyList(LockCompany item, HttpServletRequest request, PageCondition pageCondition) {
		List<LockCompany> companyList = tlockCompanyService.allCompanyList(item);
		return JSON.toJSONString(companyList);
	}

	// tlockCompanyList
	@RequestMapping("/tlockCompanyList")
	@ManagerActionLog(operateDescribe = "查询企业", operateFuncType = FunLogType.Query, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public String tcompanyList(LockCompany item, HttpServletRequest request, PageCondition pageCondition) {
		String rst = "";
		List<LockCompany> tcompanys = new ArrayList<>();
		Page page = PageUtil.getPage(pageCondition);

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		if (type == 2) {
			item.setId(manager.getCompanyId());
		}

		tcompanys = tlockCompanyService.selectCompanyList(item);

		try {
			rst = PageUtil.packageTableData(page, tcompanys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	@RequestMapping("/drop")
	@ManagerActionLog(operateDescribe = "废弃企业", operateFuncType = FunLogType.Del, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public Message drop(LockCompany item, HttpServletRequest request) {
		Message msg = tlockCompanyService.dropCompany(item);
		return msg;
	}

	@RequestMapping("/reset")
	@ManagerActionLog(operateDescribe = "恢复企业", operateFuncType = FunLogType.Del, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public Message reset(LockCompany item, HttpServletRequest request) {
		Message msg = tlockCompanyService.resetCompany(item);
		return msg;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除企业", operateFuncType = FunLogType.Del, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public Message delete(LockCompany item, HttpServletRequest request) {
		Message msg = tlockCompanyService.deleteCompany(item);
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam Long id, HttpServletRequest request) {
		LockCompany tcompanys = tlockCompanyService.selectCompanyById(id);
		return JSON.toJSONString(tcompanys);
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tcompany
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "保存修改企业", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = CompanyMapper.class)
	@ResponseBody
	public String saveOrUpdate(LockCompany tcompany) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (null == tcompany.getId()) {
				LockCompany lockCompany = tlockCompanyService.selectLockCompanyByName(tcompany.getEnterpriseName());
				if (null != lockCompany && !lockCompany.getAuditFlag().equals("2")) {
					jsonObject.put("bol", false);
					jsonObject.put("message", "该名称已存在");
					return jsonObject.toString();
				}
				lockCompany = tlockCompanyService.selectLockCompanyByPhone(tcompany.getEnterprisePhone());
				if (null != lockCompany) {
					if (lockCompany.getAuditFlag().equals("1")&&lockCompany.getDataState()!=2) {
						jsonObject.put("bol", false);
						jsonObject.put("message", "该电话已存在");
						return jsonObject.toString();
					} else {

						// 已存在废弃或者删除的 使用原数据
						tcompany.setId(lockCompany.getId());
						tcompany.setDataState(1);
						tcompany.setAuditFlag("1");
						;

						jsonObject = tlockCompanyService.updateCompany(tcompany);
						return jsonObject.toString();

					}

				}

				tcompany.setAuditFlag("1");// 审核通过
				jsonObject = tlockCompanyService.saveCompany(tcompany);

			} else {
				LockCompany lockCompany = tlockCompanyService.selectLockCompanyByName(tcompany.getEnterpriseName());
				if (null != lockCompany && !lockCompany.getId().equals(tcompany.getId())
						&& !lockCompany.getAuditFlag().equals("2")) {
					jsonObject.put("bol", false);
					jsonObject.put("message", "该名称已存在");
					return jsonObject.toString();
				}
				lockCompany = tlockCompanyService.selectLockCompanyByPhone(tcompany.getEnterprisePhone());
				if (null != lockCompany && !lockCompany.getId().equals(tcompany.getId())
						&& !lockCompany.getAuditFlag().equals("2")) {
					jsonObject.put("bol", false);
					jsonObject.put("message", "该电话已存在");
					return jsonObject.toString();
				}
				tcompany.setAuditFlag("1");// 审核通过
				jsonObject = tlockCompanyService.updateCompany(tcompany);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selecttlockcompany")
	@ResponseBody
	public List<LockCompany> selecttcompany(LockCompany item) {
		return tlockCompanyService.selectCompanyList(item);
	}

}