package com.kxjl.tasktransferplat.controller.WebController;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.StatisticsManagerMapper;
import com.kxjl.tasktransferplat.pojo.CustomerService;
import com.kxjl.tasktransferplat.service.StatisticsManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/statisticsManager")
public class StatisticsManagerController {


    @Autowired
    private StatisticsManagerService statisticsManagerService;


    @RequestMapping("/customerService")
    public String customerService(Model model) {

        return "/backend/page/customerService/index.ftl";
    }

    @RequestMapping("/customerServiceList")
    @ManagerActionLog(operateDescribe = "客服服务", operateFuncType = FunLogType.Query, operateModelClassName = StatisticsManagerMapper.class)
    @ResponseBody
    public String customerServiceList(CustomerService item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<CustomerService> list = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        list = statisticsManagerService.selectCustomerServiceList(item);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }


}
