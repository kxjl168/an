package com.kxjl.tasktransferplat.controller.WebController;


import com.mongodb.BasicDBObject;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.BaseController;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.LockCompanyStatisticsMapper;
import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockCPStatistics;
import com.kxjl.tasktransferplat.pojo.MouthOrderStatistics;
import com.kxjl.tasktransferplat.service.LockCompanyStatisticsService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/lockCompanyStatistics")
public class LockCompanyStatisticsController extends BaseController {
    @Autowired
    private LockCompanyStatisticsService lockCompanyStatisticsService;

    @RequestMapping("/mouthStatistics")//月统计
    public String mouthStatistics(@RequestParam Map<String, String> params, Model model) {

        return "/backend/page/tlockCompanyMouthstatistics/index.ftl";
    }

    @RequestMapping("/billingStatistics")//发单量统计
    public String manager(@RequestParam Map<String, String> params, Model model) {

        return "/backend/page/tlockCompanyBillingStatistics/index.ftl";
    }

    @RequestMapping("/mouthOrderCount")//月单量统计
    public String mouthOrderCount(@RequestParam Map<String, String> params, Model model) {

        return "/backend/page/tlockCompanyMouthOrderCount/index.ftl";
    }

    @RequestMapping("/provinceOrderCount")//省单量统计
    public String provinceOrderCount(@RequestParam Map<String, String> params, Model model) {

        return "/backend/page/tlockCompanyProvinceOrderCount/index.ftl";
    }

    @RequestMapping("/turnover")//营业额
    public String turnover(@RequestParam Map<String, String> params, Model model) {

        return "/backend/page/tlockCompanyTurnover/index.ftl";
    }

    @RequestMapping("/mouthList")
    @ManagerActionLog(operateDescribe = "查询锁企月报", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public String tlockCompanyMouthstatisticsList(LockCMStistics item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<LockCMStistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        list = lockCompanyStatisticsService.selectLockProductInfoList(item);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/orderCountList")
    @ManagerActionLog(operateDescribe = "发单量统计", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public List tlockCompanyBillingStatisticsList(LockCMStistics item, HttpServletRequest request) {

        List<LockCMStistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        try {
            list = lockCompanyStatisticsService.selectLockOrderList(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @RequestMapping("/mouthOrderList")
    @ManagerActionLog(operateDescribe = "月单量统计", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public String tlockCompanyMouthOrderList(LockCMStistics item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<MouthOrderStatistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        list = lockCompanyStatisticsService.selectMouthOrderList(item);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/provinceOrderList")
    @ManagerActionLog(operateDescribe = "省单量统计", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public String tlockCompanyProvinceOrderList(LockCMStistics item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<LockCPStatistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        list = lockCompanyStatisticsService.selectProvinceOrderList(item);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/turnoverList")
    @ManagerActionLog(operateDescribe = "营业额统计", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public String tlockCompanyTurnoverList(LockCMStistics item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<LockCPStatistics> list = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setId(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        list = lockCompanyStatisticsService.selectTurnoverOrderList(item);

        try {
            rst = PageUtil.packageTableData(page, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }


    @RequestMapping("/enterpriseExcel")
    @ManagerActionLog(operateDescribe = "锁企月报", operateFuncType = FunLogType.Query, operateModelClassName = LockCompanyStatisticsMapper.class)
    @ResponseBody
    public void partnerFinancialStatisticsExcel(LockCMStistics item, HttpServletResponse response, HttpServletRequest request) {

        List<LockCMStistics> list  = lockCompanyStatisticsService.selectLockProductInfoList(item);

        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("锁企月报");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("锁企名称");
        cell = row.createCell(1);
        cell.setCellValue("年份");
        cell = row.createCell(2);
        cell.setCellValue("月份");
        cell = row.createCell(3);
        cell.setCellValue("订单数");
        cell = row.createCell(4);
        cell.setCellValue("应收锁企金额");
        cell = row.createCell(5);
        cell.setCellValue("应付锁匠金额");
        cell = row.createCell(6);
        cell.setCellValue("总额差价");
        BasicDBObject query = new BasicDBObject();
        if (!timeQuery.isEmpty()) {
            query.append("downdate", timeQuery);
        }

        int rowCount = 1;
        if(list != null && list.size()>0) {
            for (int i = 0; i < list.size(); i++) {

                LockCMStistics data = list.get(i);

                String[] strArray=data.getCreatTime().split("-");

                if (data != null) {
                    row = sheet.createRow(rowCount);
                    cell = row.createCell(0);
                    cell.setCellValue(data.getEnterpriseName()+"");
                    cell = row.createCell(1);
                    cell.setCellValue((strArray[0]!=null?strArray[0]:"-")+"");
                    cell = row.createCell(2);
                    cell.setCellValue((strArray[1]!=null?strArray[1]:"-")+"");
                    cell = row.createCell(3);
                    cell.setCellValue((data.getOrderCount()!=null?data.getOrderCount():"-")+"");
                    cell = row.createCell(4);
                    cell.setCellValue((data.getTotalReceivable()!=null?data.getTotalReceivable():"-")+"");
                    cell = row.createCell(5);
                    cell.setCellValue((data.getTotalPayable()!=null?data.getTotalPayable():"-")+"");
                    cell = row.createCell(6);
                    cell.setCellValue((data.getTotalPriceDifference()!=null?data.getTotalPriceDifference():"-")+"");
                    rowCount++;
                }
            }
        }
        String fileName = "锁企月报.xlsx";
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
