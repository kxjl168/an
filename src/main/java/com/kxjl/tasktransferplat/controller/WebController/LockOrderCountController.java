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
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockMStatistics;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.FinancialStasticService;
import com.kxjl.tasktransferplat.service.LockOrderCountService;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁匠工单统计 FinancialStatisticsController.java.
 *
 * @author sr
 * @version 1.0.1 2019-04-22 11:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/LockOrderCount")
public class LockOrderCountController {

    @Autowired
    private LockOrderCountService lockOrderCountService;

    @RequestMapping("/countOrderBadRatio")
    public String countOrderBadRatio(Model model) {
        return "/backend/page/lock_bad_ratio/index.ftl";
    }

    @RequestMapping("/mouthOrderCount")
    public String mouthOrderCount(Model model) {
        return "/backend/page/LockMouthOrderCount/index.ftl";
    }



    @RequestMapping("/selectLockOrderBadRatio")
    @ManagerActionLog(operateDescribe = "锁匠月工单不良率统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public String selectLockOrderBadRatio(String createTime, PageCondition pageCondition, HttpServletRequest request) {

        Page page = PageUtil.getPage(pageCondition);
        List<Map> dataList = lockOrderCountService.selectLockOrderBadRatio(createTime);
        String rst = null;
        try {
            rst = PageUtil.packageTableData(page, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/mouthOrderList")
    @ManagerActionLog(operateDescribe = "锁匠月单量统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public String tlockCompanyMouthOrderList(LockCMStistics item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<LockMStatistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        list = lockOrderCountService.selectLockMouthStatistics(item);

        try {
            rst = PageUtil.packageTableDataOne(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }


    /**
     * 导出锁匠月工单不良率报表
     * @param createTime
     * @param response
     * @param request
     */
    @RequestMapping("/LockOrderCountExcel")
    @ManagerActionLog(operateDescribe = "导出锁匠月工单不良率报表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public void LockOrderCountExcel(String createTime, HttpServletResponse response, HttpServletRequest request) {
        /*HashMap m=  (HashMap) request.getAttribute("principal");

        Manager manager = (Manager)m.get("user");

        if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
            item.setCompanyId(manager.getCompanyId());
        }*/
        List<Map> dataList = lockOrderCountService.selectLockOrderBadRatio(createTime);
        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("锁匠月工单不良率报表");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("锁匠名");
        cell = row.createCell(2);
        cell.setCellValue("不良工单数");
        cell = row.createCell(3);
        cell.setCellValue("工单总数");
        cell = row.createCell(4);
        cell.setCellValue("月工单不良率（%）");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }
        int rowCount = 1;
        if(dataList != null && dataList.size()>0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                if (dataMap != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(dataMap.get("date")+"");
                    cell = row.createCell(1);
                    cell.setCellValue(dataMap.get("Name")+"");
                    cell = row.createCell(2);
                    cell.setCellValue((dataMap.get("part")!=null?dataMap.get("part"):0)+"");
                    cell = row.createCell(3);
                    cell.setCellValue((dataMap.get("count")!=null?dataMap.get("count"):0)+"");
                    cell = row.createCell(4);
                    cell.setCellValue((dataMap.get("persent")!=null?dataMap.get("persent")+"%":0+"%")+"");
                    rowCount++;
                }
            }
        }
        String fileName = "锁匠月工单不良率报表.xlsx";
        try {
            if (workbook != null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));// mod
                response.flushBuffer(); // by
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

