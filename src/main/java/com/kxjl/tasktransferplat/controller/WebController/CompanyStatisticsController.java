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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/companyStatistics")
public class CompanyStatisticsController {

        @Autowired
        private OrderStasticService stasticService;


        @RequestMapping("/manager")
        public String manager(Model model) { return "/backend/page/companyStatistics/index.ftl"; }



        @RequestMapping("/companyList")
        @ManagerActionLog(operateDescribe = "工单类型统计", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
        @ResponseBody
        public String torderAreaStatistics(Orderinfo item, PageCondition pageCondition,HttpServletRequest request) {

            HashMap m=  (HashMap) request.getAttribute("principal");

            Manager manager = (Manager)m.get("user");

            if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
                item.setCompanyId(manager.getCompanyId());
            }

            String rst = "";
            List<Map> order = new ArrayList<>();

            Page page = PageUtil.getPage(pageCondition);
            order = stasticService.selectCompanyStatistics(item);

            try {
                rst = PageUtil.packageTableData(page, order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return rst;
        }




    @RequestMapping("/orderList")
    @ManagerActionLog(operateDescribe = "工单状态图表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public Map orderList(Orderinfo item, PageCondition pageCondition,HttpServletRequest request) {

        HashMap m=  (HashMap) request.getAttribute("principal");

        Manager manager = (Manager)m.get("user");

        if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
            item.setCompanyId(manager.getCompanyId());
        }

        String rst = "";
        Map order = new HashMap();

        order = stasticService.selectStatistics(item);


        return order;
    }

    @RequestMapping("/companyExcel")
    @ManagerActionLog(operateDescribe = "合伙人/代理人财务报表", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
    @ResponseBody
    public void partnerFinancialStatisticsExcel(Orderinfo item, HttpServletResponse response, HttpServletRequest request) {
        HashMap m=  (HashMap) request.getAttribute("principal");

        Manager manager = (Manager)m.get("user");

        if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
            item.setCompanyId(manager.getCompanyId());
        }
        List<Map> dataList = stasticService.selectCompanyStatistics(item);
        BasicDBObject timeQuery = new BasicDBObject();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("财务年度报表");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("月份");
        cell = row.createCell(1);
        cell.setCellValue("待结账工单数");
        cell = row.createCell(2);
        cell.setCellValue("已结算工单数");
        cell = row.createCell(3);
        cell.setCellValue("总单数");
        cell = row.createCell(4);
        cell.setCellValue("总锁匠金额");
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
                    cell.setCellValue(dataMap.get("month")+"");
                    cell = row.createCell(1);
                    cell.setCellValue((dataMap.get("num1")!=null?dataMap.get("num1"):0)+"");
                    cell = row.createCell(2);
                    cell.setCellValue((dataMap.get("num2")!=null?dataMap.get("num2"):0)+"");
                    cell = row.createCell(3);
                    cell.setCellValue((dataMap.get("num3")!=null?dataMap.get("num3"):0)+"");
                    cell = row.createCell(4);
                    cell.setCellValue((dataMap.get("num4")!=null?dataMap.get("num4"):0)+"");
                    rowCount++;
                }
            }
        }
        String fileName = "合伙人/代理人年度财务报表.xlsx";
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
