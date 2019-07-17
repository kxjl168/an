/*
 * @(#)OrderinfoAfterServiceController.java
 * @author: zj
 * @Date: 2019-05-23 13:18:34
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
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAfterServiceMapper;
import com.kxjl.tasktransferplat.pojo.OrderinfoAfterService;
import com.kxjl.tasktransferplat.service.OrderinfoAfterServiceService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单售后表管理 OrderinfoAfterServiceController.java.
 * 
 * @author sr
 * @version 1.0.1 2019-05-23 13:18:34
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfoafterservice")
public class OrderinfoAfterServiceController {
	@Autowired
	private OrderinfoAfterServiceService torderinfoafterserviceService;


	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/torderinfoafterservice/index.ftl";
	}

	@RequestMapping("/torderinfoafterserviceList")
	@ManagerActionLog(operateDescribe="查询订单售后表",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoAfterServiceMapper.class)
	@ResponseBody
	public String torderinfoafterserviceList( OrderinfoAfterService item, HttpServletRequest request,PageCondition pageCondition) {

		String rst = "";
		List<OrderinfoAfterService> torderinfoafterservices = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		torderinfoafterservices = torderinfoafterserviceService.selectOrderinfoAfterServiceList(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfoafterservices);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe="删除订单售后表",operateFuncType=FunLogType.Del,operateModelClassName=OrderinfoAfterServiceMapper.class)
	@ResponseBody
	public Message delete( OrderinfoAfterService item,HttpServletRequest request) {

		Message msg = new Message();
		
	
		
		int result = torderinfoafterserviceService.deleteOrderinfoAfterService(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
		public String load( @RequestParam String id,HttpServletRequest request) {
		 
		
		OrderinfoAfterService torderinfoafterservices = torderinfoafterserviceService.selectOrderinfoAfterServiceById(id);
		return JSONObject.fromObject(torderinfoafterservices).toString();
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderinfoafterservice
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe="保存修改订单售后表",operateFuncType=FunLogType.SaveOrUpdate,operateModelClassName=OrderinfoAfterServiceMapper.class)
	@ResponseBody
	public String saveOrUpdate(OrderinfoAfterService torderinfoafterservice) {

		JSONObject jsonObject = null;
		try {
			if (null == torderinfoafterservice.getId() || "".equals(torderinfoafterservice.getId())) {
				
				jsonObject = torderinfoafterserviceService.saveOrderinfoAfterService(torderinfoafterservice);

			} else {
				jsonObject = torderinfoafterserviceService.updateOrderinfoAfterService(torderinfoafterservice);
			

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}


    @RequestMapping("/selecttorderinfoafterservice")
    @ResponseBody
    public List<OrderinfoAfterService> selecttorderinfoafterservice( OrderinfoAfterService item) {
        return torderinfoafterserviceService.selectOrderinfoAfterServiceList(item);
    }


}