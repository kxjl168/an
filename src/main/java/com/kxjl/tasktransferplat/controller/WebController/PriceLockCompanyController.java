/*
 * @(#)PriceLockCompanyController.java
 * @author: zhangyong
 * @Date: 2019-04-15 16:27:40
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
import com.kxjl.tasktransferplat.dao.plus.PriceLockCompanyMapper;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;
import com.kxjl.tasktransferplat.service.LockCompanyService;
import com.kxjl.tasktransferplat.service.PriceLockCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 锁企费用管理 PriceLockCompanyController.java.
 *
 * @author zhangyong
 * @version 1.0.1 2019-04-15 16:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/pricelockcompany")
public class PriceLockCompanyController {
    @Autowired
    private PriceLockCompanyService priceLockCompanyService;
    @Autowired
    private LockCompanyService lockCompanyService;


    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/priceLockCompany/index.ftl";
    }


    @RequestMapping("/selectList")
    @ManagerActionLog(operateDescribe = "查询列表", operateFuncType = FunLogType.Query, operateModelClassName = PriceLockCompanyMapper.class)
    @ResponseBody
    public JSONObject selectList(LockCompany item, HttpServletRequest request, PageCondition pageCondition) {
        Page page = PageUtil.getPage(pageCondition);
        List<LockCompany> lockCompanyList = priceLockCompanyService.selectList(item);
        JSONObject result = PageUtil.packageTableDataObject(page, lockCompanyList);
        return result;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam Long lockCompanyId,@RequestParam int serverType,@RequestParam int parentType, HttpServletRequest request) {
        LockCompany priceLockCompany = priceLockCompanyService.selectByLockCompanyIdAndType(lockCompanyId,serverType,parentType);
        return JSON.toJSONString(priceLockCompany);
    }

    /**
     * 新增或修改锁企价格
     *
     * @param priceLockCompany
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改锁企费用", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = PriceLockCompanyMapper.class)
    @ResponseBody
    public Message saveOrUpdate(PriceLockCompany priceLockCompany) {
        Message message = new Message();

        if(priceLockCompany.getLockCompanyId() == null || priceLockCompany.getLockCompanyId() == 0){
            message.setBol(false);
            message.setMessage("请选择锁企");
            return message;
        }

        if (null == priceLockCompany.getId() || priceLockCompany.getId() == 0) {
            LockCompany lockCompany = priceLockCompanyService.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(),priceLockCompany.getServerType(),priceLockCompany.getParentType());
            if(null != lockCompany && lockCompany.getLockCompanyPriceId() != 0){
                message.setBol(false);
                message.setMessage("该锁企费用类型已存在");
            }else {
                message = priceLockCompanyService.insert(priceLockCompany);
            }
        } else {
            message = priceLockCompanyService.updateByPrimaryKey(priceLockCompany);
        }
        return message;
    }

    /**
     * 删除锁企价格
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(@RequestParam Long id, HttpServletRequest request) {
        Message msg = new Message();
        int result = priceLockCompanyService.deleteByPrimaryKey(id);
        msg.setBol(result == 1 ? true : false);
        return msg;
    }

}