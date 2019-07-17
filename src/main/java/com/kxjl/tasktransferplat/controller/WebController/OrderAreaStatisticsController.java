package com.kxjl.tasktransferplat.controller.WebController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.OrderStasticService;

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
@Controller
@RequestMapping("/manager/torderAreaStatistics")
public class OrderAreaStatisticsController {

        @Autowired
        private OrderStasticService stasticService;

        @RequestMapping("/manager")
        public String manager(Model model) { return "/backend/page/torderAreaStatistics/index.ftl"; }


        @RequestMapping("/areaStatisticsList")
        @ManagerActionLog(operateDescribe = "工单区域统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
        @ResponseBody
        public String torderAreaStatistics(Orderinfo item, PageCondition pageCondition,HttpServletRequest request) {

            HashMap m=  (HashMap) request.getAttribute("principal");

            Manager manager = (Manager)m.get("user");

            if (manager.getCompanyId() != null &&manager.getCompanyId() != 0) {
                item.setCompanyId(manager.getCompanyId());
            }

            String rst = "";
            List<Orderinfo> order = new ArrayList<>();

            Page page = PageUtil.getPage(pageCondition);
            order = stasticService.selectAreaStatistics(item);

            try {
                rst = PageUtil.packageTableData(page, order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return rst;
        }

    }

