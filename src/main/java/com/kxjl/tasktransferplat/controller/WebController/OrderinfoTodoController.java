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
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.timer.OrderTimer;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 待接单工单管理 OrderinfoController.java.
 *
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-31 13:20:58
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfo_todo")
public class OrderinfoTodoController {

	private static final Logger logger = LoggerFactory.getLogger(OrderinfoTodoController.class);

	@Autowired
	private OrderinfoService orderinfoService;

	@Autowired
	private OrderInfoOperateService orderInfoOperateService;

	@Autowired
	CompanyService HehuorenService;

	@Autowired
	private AreaService areaService;

	@Autowired
	OrderTimer orderTimer;

	@Autowired
	ManagerMessageService managerMessageService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	private static final String XLS = "xls";

	private static final String XLSX = "xlsx";

	@RequestMapping("/manager")
	public String manager(Model model, HttpServletRequest request) {
		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			ManagerMessage managerMessage = new ManagerMessage();
			managerMessage.setUserId(manager.getId());
			managerMessage.setType(1 + "");
			managerMessageService.updateMessageReadByUserAndType(managerMessage);

			List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
			MyWebSocket.sendMessage(JSON.toJSONString(list), managerMessage.getUserId());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("websocket发送消息报错");
		}

		return "/backend/page/torderinfo_todo/index.ftl";
	}

	/**
	 * 待接单管理员分配工单
	 *
	 * @param orderinfo
	 *            包含Id，和LockerId
	 * @param request
	 * @return
	 */
	@RequestMapping("/allocateOrder")
	@ResponseBody
	public BaseResponseDto allocateOrder(Orderinfo orderinfo, HttpServletRequest request) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("工单分配成功");

		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));
			String operateUserId = manager.getId();
			Map<String, String> paramMap = new HashMap<>(16);
			paramMap.put("orderId", String.valueOf(orderinfo.getId()));
			paramMap.put("lockerId", String.valueOf(orderinfo.getLockerId()));
			paramMap.put("addMoney", String.valueOf(orderinfo.getAddMoney()));
			paramMap.put("addMoneyDesc", String.valueOf(orderinfo.getAddMoneyDesc()));

			paramMap.put("useId", String.valueOf(manager.getId()));
			paramMap.put("from", String.valueOf("1"));// 1：后台分配 ，2：小程序合伙人分配

			BaseRequestDto baseRequestDto = new BaseRequestDto();
			baseRequestDto.setData(JSON.toJSONString(paramMap));
			int allocateResult = orderInfoOperateService.allocateOrder(operateUserId, baseRequestDto);
			if (allocateResult == 0) {
				baseResponseDto.setErrMsg("分配工单失败，该工单已经被抢走！");
			}
		} catch (Exception e) {
			logger.error("分派工单失败！", e);
			baseResponseDto.setErrCode(0);
			baseResponseDto.setErrMsg("分派工单失败");
		}
		return baseResponseDto;
	}

	/**
	 * 刷新，立刻计算工单超时状态
	 *
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@RequestMapping("/refreshOrderTimer")
	// @ManagerActionLog(operateDescribe = "恢复工单", operateFuncType =
	// FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message refreshOrderTimer(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		orderTimer.check(new Date());

		msg.setBol(true);

		return msg;
	}

	/**
	 * 重发工单核销码
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年6月24日
	 */
	@RequestMapping("/reSendCompleteNo")
	// @ManagerActionLog(operateDescribe = "恢复工单", operateFuncType =
	// FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message reSendCompleteNo(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		orderInfoOperateService.SendCompleteCodeSms(item.getOrderNo());

		msg.setBol(true);

		return msg;
	}

	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe = "查询待接单工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public JSONObject torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
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
		JSONObject result = null;
		try {
			Page page = PageUtil.getPage(pageCondition);
			torderinfos = orderinfoService.selectTodoOrderInfoList(item);
			result = PageUtil.packageTableDataObject(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 恢复
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@RequestMapping("/ReUse")
	@ManagerActionLog(operateDescribe = "恢复工单", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message ReUse(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();
		Manager manager = ((Manager) request.getSession().getAttribute("user"));

		JSONObject jobj = orderinfoService.ReUseOrderinfo(manager, item);
		if (jobj.getBooleanValue("bol")) {
			msg.setBol(true);
		}
		return msg;
	}

	/**
	 * 废弃
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@RequestMapping("/NoUsedelete")
	@ManagerActionLog(operateDescribe = "废弃工单", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message NoUsedelete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		Manager manager = ((Manager) request.getSession().getAttribute("user"));

		JSONObject jobj = orderinfoService.NoUseOrderinfo(manager, item);

		if (jobj.getBooleanValue("bol")) {

			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/realdelete")
	@ManagerActionLog(operateDescribe = "删除工单", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message realdelete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = orderinfoService.deleteOrderinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam String id, HttpServletRequest request) {
		Orderinfo torderinfos = orderinfoService.loadOrderinfoById(id);
		return JSON.toJSONString(torderinfos);
	}

	@RequestMapping("/selecttorderinfo")
	@ResponseBody
	public List<Orderinfo> selecttorderinfo(Orderinfo item) {
		return orderinfoService.selectOrderinfoList(item);
	}

}