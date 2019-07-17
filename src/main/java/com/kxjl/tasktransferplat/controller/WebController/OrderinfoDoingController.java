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
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderInfoOperateProxy;
import com.kxjl.tasktransferplat.service.OrderInfoOperateService;
import com.kxjl.tasktransferplat.service.OrderinfoAuditService;
import com.kxjl.tasktransferplat.service.OrderinfoService;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待作业工单管理 OrderinfoController.java.
 * 
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-31 13:20:58
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfo_doing")
public class OrderinfoDoingController {

	@Autowired
	private OrderinfoService torderinfoService;

	@Autowired
	CompanyService HehuorenService;

	@Autowired
	private OrderinfoAuditService orderinfoAuditService;

	@Autowired
	private OrderInfoOperateProxy orderInfoOperateProxy;

	private static final Logger logger = LoggerFactory.getLogger(OrderinfoDoingController.class);

	@RequestMapping("/manager")
	public String manager(Model model) {
		
		return "/backend/page/torderinfo_doing/index.ftl";
	}

	@Autowired
	private OrderInfoOperateService orderInfoOperateService;



	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe = "查询待作业工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public JSONObject torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		JSONObject rst = null;
		List<Orderinfo> torderinfos = new ArrayList<>();

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

		Page page = PageUtil.getPage(pageCondition);

		try {
			torderinfos = torderinfoService.selectDoingOrderInfoList(item);
			rst = PageUtil.packageTableDataObject(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	/**
	 * 查询公司价钱金额
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectCompanyProposMoney")
	@ResponseBody
	public BigDecimal selectCompanyProposMoney(OrderinfoAudit item, HttpServletRequest request) {
		BigDecimal proposMoney;
		try {
			proposMoney = orderinfoAuditService.selectCompanyProposMoney(item);
		} catch (Exception e) {
			proposMoney = new BigDecimal(0).setScale(2);
			logger.error("查询公司价钱金额失败", e);
		}
		return proposMoney;
	}

	/**
	 * 查询工单对应的加钱审核
	 * 
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 */
	@RequestMapping("/selectAuditByOrderId")
	@ResponseBody
	public String selectAuditByOrderId(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
		Page page = PageUtil.getPage(pageCondition);
		Manager manager = (Manager) request.getSession().getAttribute("user");
		try {
			List<OrderinfoAudit> auditList = orderinfoAuditService.selectAuditByOrderId(item, manager);
			rst = PageUtil.packageTableData(page, auditList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	@RequestMapping("/orderAudit")
	@ResponseBody
	public BaseResponseDto orderAudit(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {

		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("操作成功");

		Manager manager = (Manager) request.getSession().getAttribute("user");
		String managerId = manager.getId();

		BaseRequestDto baseRequestDto = new BaseRequestDto();
		baseRequestDto.setData(JSON.toJSONString(paramMap));
		try {
			orderInfoOperateProxy.orderAudit(managerId, baseRequestDto);
		} catch (Exception e) {
			logger.error("操作失败", e);
			baseResponseDto.setErrMsg("操作失败");
			baseResponseDto.setErrCode(0);
		}
		return baseResponseDto;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除待作业工单", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMapper.class)
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
		return JSON.toJSONString(torderinfos);
	}

	@RequestMapping("/loadOrderInfo")
	@ResponseBody
	public Orderinfo loadOrderInfo(@RequestParam String id, HttpServletRequest request) {
		Orderinfo torderinfos = torderinfoService.loadOrderinfoById(id);
		return torderinfos;
	}

	@RequestMapping("/updateOrder")
	@ResponseBody
	public BaseResponseDto updateOrder(Orderinfo orderinfo, HttpServletRequest request) {

		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("操作成功");
		try {
			torderinfoService.updateOrder(orderinfo);
		} catch (Exception e) {
			logger.error("操作成功", e);
			baseResponseDto.setErrMsg("操作失败");
			baseResponseDto.setErrCode(0);
		}
		return baseResponseDto;
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "修改待作业工单", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Orderinfo torderinfo) {

		JSONObject jsonObject = null;
		try {
			if (null == torderinfo.getId()) {

				jsonObject = torderinfoService.saveOrderinfo(torderinfo);

			} else {
				jsonObject = torderinfoService.updateOrderinfo(torderinfo);

			}
		} catch (Exception e) {
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

}