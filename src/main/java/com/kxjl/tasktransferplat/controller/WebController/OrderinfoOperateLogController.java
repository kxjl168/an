/*
 * @(#)OrderinfoOperateLogController.java
 * @author: zj
 * @Date: 2019-02-01 14:43:03
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.service.OrderinfoOperateLogService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.mongodb.BasicDBObject;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单历史管理 OrderinfoOperateLogController.java.
 *
 * @author zj
 * @version 1.0.1 2019-02-01 14:43:03
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfooperatelog")
public class OrderinfoOperateLogController {
    @Autowired
    private OrderinfoOperateLogService torderinfooperatelogService;
    @Autowired
    private OrderinfoService orderinfoService;

    @RequestMapping("/manager")
    public String manager(Model model) {
        return "/backend/page/torderinfooperatelog/index.ftl";
    }


    /**
     * 工单综合查询
     *
     * @param model
     * @return
     * @author zj
     * @date 2019年5月6日
     */
    @RequestMapping("/all")
    public String all(Model model) {
        return "/backend/page/torderinfooperatelog/indexAll.ftl";
    }

    /**
     * 工单综合查询，增加日志关联查询，可以关联首页跳转，查询操作状态及最新操作时间 orderAllList
     *
     * @param item
     * @param request
     * @param pageCondition
     * @return
     * @author zj
     * @date 2019年5月6日
     */
    @RequestMapping("/orderAllList")
    // @ManagerActionLog(operateDescribe="工单综合查询",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoOperateLogMapper.class)
    @ResponseBody
    public String orderAllList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {
        HashMap m = (HashMap) request.getAttribute("principal");

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type != 2) {
            item.setCompanyId(manager.getCompanyId());
        } else {
            item.setSellerId(manager.getCompanyId());
        }

        String rst = "";
        List<Orderinfo> orderinfos = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        orderinfos = orderinfoService.selectAllOrderInfoList(item);

        try {
            rst = PageUtil.packageTableData(page, orderinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/torderinfooperatelogList")
    @ManagerActionLog(operateDescribe = "查询工单历史", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoOperateLogMapper.class)
    @ResponseBody
    public String torderinfooperatelogList(OrderinfoOperateLog item, HttpServletRequest request,
                                           PageCondition pageCondition) {
        HashMap m = (HashMap) request.getAttribute("principal");

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type != 2) {
            item.setCompanyId(manager.getCompanyId());
        } else {
            item.setSellerId(manager.getCompanyId());
        }

        String rst = "";
        List<OrderinfoOperateLog> torderinfooperatelogs = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        torderinfooperatelogs = torderinfooperatelogService.selectOrderinfoOperateLogList(item);

        try {
            rst = PageUtil.packageTableData(page, torderinfooperatelogs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除工单历史", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoOperateLogMapper.class)
    @ResponseBody
    public Message delete(OrderinfoOperateLog item, HttpServletRequest request) {

        Message msg = new Message();

        int result = torderinfooperatelogService.deleteOrderinfoOperateLog(item);
        if (result == 1) {
            msg.setBol(true);
        }
        return msg;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam Long id, HttpServletRequest request) {

        OrderinfoOperateLog torderinfooperatelogs = torderinfooperatelogService.selectOrderinfoOperateLogById(id);
        return JSON.toJSONString(torderinfooperatelogs);
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param torderinfooperatelog
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    //@ManagerActionLog(operateDescribe = "保存修改工单历史", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoOperateLogMapper.class)
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request, OrderinfoOperateLog torderinfooperatelog) {

        JSONObject jsonObject = null;
        try {
            if (null == torderinfooperatelog.getId()) {
                HashMap m = (HashMap) request.getAttribute("principal");
                Manager manager = (Manager) m.get("user");
                torderinfooperatelog.setType(1);
                torderinfooperatelog.setSubType(9);
                torderinfooperatelog.setOperateUserId(manager.getId());

                jsonObject = torderinfooperatelogService.saveOrderinfoOperateLog(torderinfooperatelog);

            } else {
                jsonObject = torderinfooperatelogService.updateOrderinfoOperateLog(torderinfooperatelog);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }

    @RequestMapping("/selecttorderinfooperatelog")
    @ResponseBody
    public List<OrderinfoOperateLog> selecttorderinfooperatelog(OrderinfoOperateLog item) {
        return torderinfooperatelogService.selectOrderinfoOperateLogList(item);
    }

    /**
     * 锁企管理员首页显示--下发订单
     *
     * @param request
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderTotals")
    public Map orderTotals(HttpServletRequest request, OrderinfoOperateLog item) {
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");
        if (manager.getCompanyId() != null) {
            if (manager.getType() == 1) {
                item.setCompanyId(manager.getCompanyId());
            } else if (manager.getType() == 2) {
                item.setSellerId(manager.getCompanyId());
            }
        }
        Map map = new HashMap();
        List<Map<String, Object>> map1 = torderinfooperatelogService.countIssueOrder(item);
        List<Map<String, Object>> map2 = torderinfooperatelogService.countIssueOrderPass(item);
        map.put("orders", map1);
        map.put("ordersPass", map2);
        return map;
    }

    /**
     * 源匠管理员首页显示--发单年度统计
     *
     * @param request
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderEnterpriseYear")
    public Map orderCompanyYear(HttpServletRequest request, OrderinfoOperateLog item) {
        item.setOperateTime("2019");
        Map map = new HashMap();
        List<Map<String, Object>> map1 = torderinfooperatelogService.countIssueOrder(item);
        List<Map<String, Object>> map2 = torderinfooperatelogService.countIssueOrderPass(item);
        map.put("orders", map1);
        map.put("ordersPass", map2);
        return map;
    }

    /**
     * 源匠管理员首页显示--锁企发单总数
     *
     * @param request
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderEnterprise")
    public Map orderEnterprise(HttpServletRequest request, OrderinfoOperateLog item) {
        Map map = new HashMap();
        List<Map<String, Object>> map1 = torderinfooperatelogService.countEnterpriseOrder(item);
        List<Map<String, Object>> map2 = torderinfooperatelogService.countEnterpriseOrderPass(item);
        map.put("orders", map1);
        map.put("ordersPass", map2);
        return map;
    }

    /**
     * 源匠客服首页显示--我已处理的锁企工单量统计
     *
     * @param request
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("/doOrderEnterprise")
    public Map doOrderEnterprise(HttpServletRequest request, OrderinfoOperateLog item) {
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");
        item.setOperateUserId(manager.getId());
        Map map = new HashMap();
        List<Map<String, Object>> map1 = torderinfooperatelogService.countIssueOrder(item);
        List<Map<String, Object>> map2 = torderinfooperatelogService.countIssueOrderPass(item);
        map.put("orders", map1);
        map.put("ordersPass", map2);
        return map;
    }

    @RequestMapping("/orderExcel")
    @ManagerActionLog(operateDescribe = "工单统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoOperateLogMapper.class)
    @ResponseBody
    public void partnerFinancialStatisticsExcel(Orderinfo item, HttpServletResponse response, HttpServletRequest request) {

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {
            item.setSellerId(manager.getCompanyId());
        }

        List<Orderinfo> orderinfos = new ArrayList<>();

        orderinfos = orderinfoService.selectAllOrderInfoList(item);

        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("工单统计");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("订单编号");
        cell = row.createCell(1);
        cell.setCellValue("订单状态");
        cell = row.createCell(2);
        cell.setCellValue("锁匠名称");
        cell = row.createCell(3);
        cell.setCellValue("工单创建时间");
        cell = row.createCell(4);
        cell.setCellValue("客户姓名");
        cell = row.createCell(5);
        cell.setCellValue("客户电话");
        cell = row.createCell(6);
        cell.setCellValue("服务类型");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }

        int rowCount = 1;
        if (orderinfos != null && orderinfos.size() > 0) {
            for (int i = 0; i < orderinfos.size(); i++) {
                Orderinfo data = orderinfos.get(i);

                String time = "";
                if (data.getCreateTime() != null){

                    time = data.getCreateTime().split("\\.")[0];
                }

                if (data != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(data.getOrderNo() + "");
                    cell = row.createCell(1);
                    cell.setCellValue((
                            this.choiceOrderStatus(data.getOrderState())
                    ) + "");
                    cell = row.createCell(2);
                    cell.setCellValue((data.getLockName() != null ? data.getLockName() : "-") + "");
                    cell = row.createCell(3);
                    cell.setCellValue((data.getCreateTime() != null ? time : "-") + "");
                    cell = row.createCell(4);
                    cell.setCellValue((data.getCustomerName() != null ? data.getCustomerName() : "-") + "");
                    cell = row.createCell(5);
                    cell.setCellValue((data.getCustomerPhone() != null ? data.getCustomerPhone() : "-") + "");
                    cell = row.createCell(6);
                    cell.setCellValue((
                            this.choiceServerType(data.getServerType())
                    ) + "");
                    rowCount++;
                }
            }
        }
        String fileName = "工单统计.xlsx";
        try {
            if (workbook != null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));// mod
                response.flushBuffer(); // by
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String choiceOrderStatus(int orderState) {

        if (orderState == 1) {

            return "加钱待平台审核";
        } else if (orderState == 2) {

            return "退单待平台审核";
        } else if (orderState == 3) {

            return "待平台确认";
        } else if (orderState == 4) {

            return "平台申请待锁企审核";
        } else if (orderState == 5) {

            return "锁企不通过待平台处理";
        } else if (orderState == 6) {

            return "加钱待合伙人审核";
        } else if (orderState > 100 && orderState < 200) {

            return "待接单";
        } else if (orderState > 200 && orderState < 300) {

            if (orderState == 210) {

                return "待作业(已确认)";
            }else  if (orderState == 213) {

                return "已预约";
            }else if (orderState == 214) {

                return "预约失败";
            }else  if (orderState ==255) {

                return "待作业(回访问题单)";
            }else {

                return "待作业";
            }
        } else if (orderState > 300 && orderState < 400) {

            return "待回访";
        } else if (orderState > 400 && orderState < 500) {

            return "待结账";
        } else if (orderState > 500 && orderState < 600) {

            return "已完结";
        } else {
            return "-";
        }
    }

    private String choiceServerType(String serverType) {

        String[] strArray = serverType.split(",");

        List<String> resultArr=new ArrayList<String>();
        for (String num : strArray) {

            String name = this.getserverTypeName(num);
            resultArr.add(name);
        }

        String  result= StringUtils.join(resultArr,",");

        return result;
    }

    private String getserverTypeName(String serverType) {

        if (Integer.valueOf(serverType) == 0) {

            return "安装";
        } else if (Integer.valueOf(serverType) == 9) {

            return "其他";
        } else if (Integer.valueOf(serverType) == 1) {

            return "维修";
        } else if (Integer.valueOf(serverType) == 2) {

            return "开锁";
        } else if (Integer.valueOf(serverType) == 3) {

            return "特殊们改造";
        } else if (Integer.valueOf(serverType) == 4) {

            return "测量";
        } else if (Integer.valueOf(serverType) == 5) {

            return "猫眼安装";
        } else if (Integer.valueOf(serverType) == 6) {

            return "换锁";
        } else if (Integer.valueOf(serverType) == 7) {

            return "工程安装";
        } else if (Integer.valueOf(serverType) == 8) {

            return "工程维修";
        }  else {
            return "-";
        }
    }
}