/*
 * @(#)CompanyController.java
 * @author: zj
 * @Date: 2019-01-29 16:27:40
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
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理人/合伙人管理 CompanyController.java.
 *
 * @author zj
 * @version 1.0.1 2019-01-29 16:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tcompany")
public class CompanyController {
    @Autowired
    private CompanyService tcompanyService;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private OrderinfoService orderinfoService;


    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/tcompany/index.ftl";
    }

    @RequestMapping("/managerPass")
    public String managerPass(Model model) {

        return "/backend/page/tcompanyPass/index.ftl";
    }

    @RequestMapping("/managerNoPass")
    public String managerNoPass(Model model) {

        return "/backend/page/tcompanyNoPass/index.ftl";
    }


    @RequestMapping("/tcompanyUnAuditList")
    @ManagerActionLog(operateDescribe = "查询代理人/合伙人待审核", operateFuncType = FunLogType.Query, operateModelClassName = CompanyMapper.class)
    @ResponseBody
    public String tcompanyUnAuditList(Company item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<Company> tcompanys = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        tcompanys = tcompanyService.selectUnAuditCompanyList(item);

        try {
            rst = PageUtil.packageTableData(page, tcompanys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    /**
     * 查询所有企业，下拉框使用
     *
     * @param item
     * @param request
     * @param pageCondition
     * @return
     */
    @RequestMapping("/allCompany")
    @ResponseBody
    public String allCompanyList(Company item, HttpServletRequest request, PageCondition pageCondition) {
        List<Company> companyList = tcompanyService.allCompanyList(item);
        return JSON.toJSONString(companyList);
    }

    @RequestMapping("/tcompanyList")
    @ManagerActionLog(operateDescribe = "查询代理人/合伙人", operateFuncType = FunLogType.Query, operateModelClassName = CompanyMapper.class)
    @ResponseBody
    public String tcompanyList(Company item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<Company> tcompanys = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        tcompanys = tcompanyService.selectCompanyList(item);

        try {
            rst = PageUtil.packageTableData(page, tcompanys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }


    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除代理人/合伙人", operateFuncType = FunLogType.Del, operateModelClassName = CompanyMapper.class)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public JSONObject delete(Company item, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {

            int count = orderinfoService.selectPartnerUnFinishOrderCount(item.getId());
            if (count > 0) {
                jsonObject.put("bol", false);
                jsonObject.put("message", "该合伙人/代理人有未完成工单，请完成后再进行删除");
                return jsonObject;
            }
            int result = tcompanyService.deleteCompany(item);
            jsonObject.put("bol", result == 1 ? true : false);
            jsonObject.put("message", result == 1 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("bol", false);
            jsonObject.put("message", "删除失败");
        }
        return jsonObject;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam Long id, HttpServletRequest request) {


        Company tcompanys = tcompanyService.selectCompanyById(id);
        return JSON.toJSONString(tcompanys);
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param tcompany
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改代理人/合伙人", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = CompanyMapper.class)
    @ResponseBody
    public String saveOrUpdate(Company tcompany) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (null == tcompany.getId()) {
                Company company = tcompanyService.selectByCompanyName(tcompany.getCompanyName());
                if (null != company && !company.getAuditFlag().equals("2")) {
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "名称已存在");
                    return jsonObject.toString();
                }
                company = tcompanyService.selectByCompanyPhone(tcompany.getCompanyPhone());
                if (null != company && !company.getAuditFlag().equals("2")) {
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "电话已存在");
                    return jsonObject.toString();
                }
                if (null != tcompanyService.selectByProvinceAndCityCode(tcompany.getAreaCode().substring(0, 4))) {
                    jsonObject = new JSONObject();
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "该地区已存在合伙人或代理人");
                    return jsonObject.toString();
                }
                jsonObject = tcompanyService.saveCompany(tcompany);
            } else {
                Company company = tcompanyService.selectByCompanyName(tcompany.getCompanyName());
                if (null != company && !company.getId().equals(tcompany.getId())  && !company.getAuditFlag().equals("2")) {
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "名称已存在");
                    return jsonObject.toString();
                }
                company = tcompanyService.selectByCompanyPhone(tcompany.getCompanyPhone());
                if(null != company && !company.getId().equals(tcompany.getId()) && !company.getAuditFlag().equals("2")){
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "电话已存在");
                    return jsonObject.toString();
                }
                company = tcompanyService.selectByProvinceAndCityCode(tcompany.getAreaCode().substring(0, 4));
                if (null != company && !company.getId().equals(tcompany.getId())) {
                    jsonObject = new JSONObject();
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "该地区已存在合伙人或代理人");
                    return jsonObject.toString();
                }
                jsonObject = tcompanyService.updateCompany(tcompany);
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }


    @RequestMapping("/selecttcompany")
    @ResponseBody
    public List<Company> selecttcompany(Company item) {
        return tcompanyService.selectCompanyList(item);
    }


}