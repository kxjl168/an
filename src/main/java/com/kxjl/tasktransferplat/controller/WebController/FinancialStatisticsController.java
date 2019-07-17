/*
 * @(#)FinancialStatisticsController.java
 * @author: zhangyong
 * @Date: 2019-04-22 11:27:40
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.FinancialStasticService;
import com.mongodb.BasicDBObject;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 锁企管理 FinancialStatisticsController.java.
 *
 * @author zhangyong
 * @version 1.0.1 2019-04-22 11:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/financialStatistics")
public class FinancialStatisticsController {

    @Autowired
    private FinancialStasticService financialStasticService;

    @RequestMapping("/partnerManager")
    public String partnerManager(Model model) {
        return "/backend/page/partnerFinancialStatistics/index.ftl";
    }

    @RequestMapping("/lockCompanyManager")
    public String lockcompanyManager(Model model) {
        return "/backend/page/lockCompanyFinancialStatistics/index.ftl";
    }


    @RequestMapping("/partnerFinancialStatisticsList")
    @ManagerActionLog(operateDescribe = "有合伙人代理人区域财务统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public String partnerFinancialStatisticsList(Orderinfo item, PageCondition pageCondition, HttpServletRequest request) {

        Page page = PageUtil.getPage(pageCondition);
        List<Map> dataList = financialStasticService.selectPartnerFinancialStatistics(item);
        String rst = null;
        try {
            rst = PageUtil.packageTableData(page, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/noPartnerFinancialStatisticsList")
    @ManagerActionLog(operateDescribe = "无合伙人代理人区域财务统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public String noPartnerFinancialStatisticsList(Orderinfo item, PageCondition pageCondition, HttpServletRequest request) {

        Page page = PageUtil.getPage(pageCondition);
        List<Map> dataList = financialStasticService.selectNoPartnerFinancialStatistics(item);
        String rst = null;
        try {
            rst = PageUtil.packageTableData(page, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    /**
     * 锁企按月份，统计各个锁企已经完成的工单数量
     * @param item
     * @param pageCondition
     * @param request
     * @return
     * @author zj
     * @date 2019年5月22日
     */
    @RequestMapping("/lockCompanyFinancialStatisticsList")
    @ManagerActionLog(operateDescribe = "与锁企财务统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public String lockCompanyFinancialStatisticsList(Orderinfo item, PageCondition pageCondition, HttpServletRequest request) {
        Page page = PageUtil.getPage(pageCondition);
        List<Map> dataList = financialStasticService.selectLockCompanyFinancialStatistics(item);
        String rst = null;
        try {
            rst = PageUtil.packageTableData(page, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    @RequestMapping("/partnerFinancialStatisticsExcel")
    @ManagerActionLog(operateDescribe = "合伙人财务报表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public void partnerFinancialStatisticsExcel(Orderinfo item, HttpServletResponse response, HttpServletRequest request) {
        List<Map> dataList = financialStasticService.selectPartnerFinancialStatistics(item);
        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("合伙人财务报表-" + item.getMonth() + "月");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("合伙人/代理人");
        cell = row.createCell(2);
        cell.setCellValue("区域");
        cell = row.createCell(3);
        cell.setCellValue("订单数量");
        cell = row.createCell(4);
        cell.setCellValue("总金额");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }
        int rowCount = 1;
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                if (dataMap != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(dataMap.get("month") + "");
                    cell = row.createCell(1);
                    cell.setCellValue(dataMap.get("CompanyName") + "");
                    cell = row.createCell(2);
                    cell.setCellValue(dataMap.get("Province") + "" + dataMap.get("City"));
                    cell = row.createCell(3);
                    cell.setCellValue(dataMap.get("countNum") + "");
                    cell = row.createCell(4);
                    cell.setCellValue(dataMap.get("totalPrice") + "");
                    rowCount++;
                }
            }
        }
        String fileName = "合伙人财务报表-" + item.getMonth() + "月.xlsx";
        try {
            if (workbook != null) {
                fileName = URLEncoder.encode(fileName,"UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/noPartnerFinancialStatisticsExcel")
    @ManagerActionLog(operateDescribe = "无合伙人城市财务报表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public void noPartnerFinancialStatisticsExcel(Orderinfo item, HttpServletResponse response, HttpServletRequest request) {
        List<Map> dataList = financialStasticService.selectNoPartnerFinancialStatistics(item);
        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("无合伙人城市财务报表-" + item.getMonth() + "月");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("区域");
        cell = row.createCell(2);
        cell.setCellValue("订单数量");
        cell = row.createCell(3);
        cell.setCellValue("总金额");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }
        int rowCount = 1;
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                if (dataMap != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(dataMap.get("month") + "");
                    cell = row.createCell(1);
                    cell.setCellValue(dataMap.get("custAddress")+"");
                    cell = row.createCell(2);
                    cell.setCellValue(dataMap.get("num") + "");
                    cell = row.createCell(3);
                    cell.setCellValue(dataMap.get("totalPrice") + "");
                    rowCount++;
                }
            }
        }
        String fileName = "无合伙人城市财务报表-" + item.getMonth() + "月.xlsx";
        try {
            if (workbook != null) {
                fileName = URLEncoder.encode(fileName,"UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/lockCompanyFinancialStatisticsExcel")
    @ManagerActionLog(operateDescribe = "锁企财务报表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public void lockCompanyFinancialStatisticsExcel(Orderinfo item, HttpServletResponse response, HttpServletRequest request) {
        List<Map> dataList = financialStasticService.selectLockCompanyFinancialStatistics(item);
        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("锁企财务报表-" + item.getMonth() + "月");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("锁企名称");
        cell = row.createCell(2);
        cell.setCellValue("锁企电话");
        cell = row.createCell(3);
        cell.setCellValue("订单数量");
        cell = row.createCell(4);
        cell.setCellValue("总金额");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }
        int rowCount = 1;
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                if (dataMap != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(dataMap.get("month") + "");
                    cell = row.createCell(1);
                    cell.setCellValue(dataMap.get("enterpriseName") + "");
                    cell = row.createCell(2);
                    cell.setCellValue(dataMap.get("enterprisePhone") + "");
                    cell = row.createCell(3);
                    cell.setCellValue(dataMap.get("countNum") + "");
                    cell = row.createCell(4);
                    cell.setCellValue(dataMap.get("totalPrice") + "");
                    rowCount++;
                }
            }
        }
        String fileName = "锁企财务报表-" + item.getMonth() + "月.xlsx";
        try {
            if (workbook != null) {
                fileName = URLEncoder.encode(fileName,"UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

