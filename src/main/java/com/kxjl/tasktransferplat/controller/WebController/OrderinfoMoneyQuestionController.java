/*
 * @(#)OrderinfoMoneyQuestionController.java
 * @author: zy
 * @Date: 2019-06-10 15:42:22
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
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMoneyQuestionMapper;
import com.kxjl.tasktransferplat.pojo.OrderinfoMoneyQuestion;
import com.kxjl.tasktransferplat.service.OrderinfoMoneyQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单金额申述表管理 OrderinfoMoneyQuestionController.java.
 *
 * @author zy
 * @version 1.0.1 2019-06-10 15:42:22
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfomoneyquestion")
public class OrderinfoMoneyQuestionController {
    @Autowired
    private OrderinfoMoneyQuestionService torderinfomoneyquestionService;

    @RequestMapping("/manager")
    public String manager(Model model) {
        return "/backend/page/torderinfomoneyquestion/index.ftl";
    }

    @RequestMapping("/torderinfomoneyquestionList")
    @ManagerActionLog(operateDescribe = "查询订单金额申述表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMoneyQuestionMapper.class)
    @ResponseBody
    public String torderinfomoneyquestionList(HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<Map<String, Object>> list = new ArrayList<>();

        String orderNo = request.getParameter("orderNo");
        String phone = request.getParameter("phone");
        Object state = request.getParameter("serviceState");
        String serviceState = (state == null||state=="") ? null : String.valueOf(state.toString());
        Page page = PageUtil.getPage(pageCondition);
        list = torderinfomoneyquestionService.selectOrderinfoMoneyQuestionList(orderNo, phone, serviceState);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除订单金额申述表", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMoneyQuestionMapper.class)
    @ResponseBody
    public Message delete(OrderinfoMoneyQuestion item, HttpServletRequest request) {

        Message msg = new Message();


        int result = torderinfomoneyquestionService.deleteOrderinfoMoneyQuestion(item);
        if (result == 1) {
            msg.setBol(true);
        }
        return msg;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam String id, HttpServletRequest request) {


        OrderinfoMoneyQuestion torderinfomoneyquestions = torderinfomoneyquestionService.selectOrderinfoMoneyQuestionById(id);

        return JSON.toJSONString(torderinfomoneyquestions);
        //return JSONObject.fromObject(torderinfomoneyquestions).toString();
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param torderinfomoneyquestion
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改订单金额申述表", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMoneyQuestionMapper.class)
    @ResponseBody
    public String saveOrUpdate(OrderinfoMoneyQuestion torderinfomoneyquestion) {

        JSONObject jsonObject = null;
        try {
            if (null == torderinfomoneyquestion.getId() || "".equals(torderinfomoneyquestion.getId())) {

                jsonObject = torderinfomoneyquestionService.saveOrderinfoMoneyQuestion(torderinfomoneyquestion);

            } else {
                jsonObject = torderinfomoneyquestionService.updateOrderinfoMoneyQuestion(torderinfomoneyquestion);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }
}