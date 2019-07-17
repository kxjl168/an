/*
 * @(#)LockProductInfoController.java
 * @author: zj
 * @Date: 2019-05-13 17:06:44
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.LockProductInfoMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.LockProductInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.LockProductInfoService;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 锁企产品管理 LockProductInfoController.java.
 *
 * @author zj
 * @version 1.0.1 2019-05-13 17:06:44
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tlockproductinfo")
public class LockProductInfoController {
    @Autowired
    private LockProductInfoService tlockproductinfoService;


    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/tlockproductinfo/index.ftl";
    }

    @RequestMapping("/tlockproductinfoList")
    @ManagerActionLog(operateDescribe = "查询锁企产品", operateFuncType = FunLogType.Query, operateModelClassName = LockProductInfoMapper.class)
    @ResponseBody
    public String tlockproductinfoList(LockProductInfo item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<LockProductInfo> tlockproductinfos = new ArrayList<>();

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            item.setLockEnterpriseID(manager.getCompanyId().toString());
        }

        Page page = PageUtil.getPage(pageCondition);
        tlockproductinfos = tlockproductinfoService.selectLockProductInfoList(item);

        try {
            rst = PageUtil.packageTableData(page, tlockproductinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }
    
   

    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除锁企产品", operateFuncType = FunLogType.Del, operateModelClassName = LockProductInfoMapper.class)
    @ResponseBody
    public Message delete(LockProductInfo item, HttpServletRequest request) {

        Message msg = new Message();


        int result = tlockproductinfoService.deleteLockProductInfo(item);
        if (result == 1) {
            msg.setBol(true);
        }
        if (result == -1) {
            msg.setBol(false);
            msg.setMessage("存在关联该产品的未完成工单,暂时不能删除！");
        }
        return msg;
    }

    @RequestMapping("/drop")
    @ManagerActionLog(operateDescribe = "删除锁企产品", operateFuncType = FunLogType.Del, operateModelClassName = LockProductInfoMapper.class)
    @ResponseBody
    public Message drop(LockProductInfo item, HttpServletRequest request) {

        Message msg = new Message();


        int result = tlockproductinfoService.dropLockProductInfo(item);
        if (result == 1) {
            msg.setBol(true);
        }
        if (result == -1) {
            msg.setBol(false);
            msg.setMessage("存在关联该产品的未完成工单,暂时不能废弃！");
        }
        return msg;
    }

    @RequestMapping("/reset")
    @ManagerActionLog(operateDescribe = "删除锁企产品", operateFuncType = FunLogType.Del, operateModelClassName = LockProductInfoMapper.class)
    @ResponseBody
    public Message reset(LockProductInfo item, HttpServletRequest request) {

        Message msg = new Message();


        int result = tlockproductinfoService.resetLockProductInfo(item);
        if (result == 1) {
            msg.setBol(true);
        }
        if (result == -1) {
            msg.setBol(false);
            msg.setMessage("操作失败！");
        }
        return msg;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam String id, HttpServletRequest request) {


        LockProductInfo tlockproductinfos = tlockproductinfoService.selectLockProductInfoById(id);
        Gson gs = new Gson();
        return gs.toJson(tlockproductinfos);
        //return JSONObject.fromObject(tlockproductinfos).toString();
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param tlockproductinfo
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改锁企产品", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = LockProductInfoMapper.class)
    @ResponseBody
    public String saveOrUpdate(LockProductInfo tlockproductinfo, HttpServletRequest request) {

        JSONObject jsonObject = null;

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        Orderinfo orderinfo=new Orderinfo();
        int type = manager.getType();
        if (type == 2) {

            tlockproductinfo.setLockEnterpriseID(manager.getCompanyId().toString());
        }
        try {
            List<LockProductInfo> lockProductInfo=tlockproductinfoService.selectLockProductByProType(tlockproductinfo);

            if (null == tlockproductinfo.getId() || "".equals(tlockproductinfo.getId())) {

                tlockproductinfo.setUserId(manager.getId());

                if (lockProductInfo != null && lockProductInfo.size()>0) {
                    jsonObject = new JSONObject();
                    jsonObject.put("bol", false);
                    jsonObject.put("message", "该锁企已有此产品型号");
                    return jsonObject.toJSONString();
                }
                jsonObject = tlockproductinfoService.saveLockProductInfo(tlockproductinfo);

            } else {
                jsonObject = tlockproductinfoService.updateLockProductInfo(tlockproductinfo);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }


    @RequestMapping("/selecttlockproductinfo")
    @ResponseBody
    public List<LockProductInfo> selecttlockproductinfo(LockProductInfo item) {
        return tlockproductinfoService.selectLockProductInfoList(item);
    }



    @RequestMapping("/checkProductInfo")
    @ResponseBody
    public boolean checkProductInfo(LockProductInfo item) {
        List<LockProductInfo> lockProductInfo=tlockproductinfoService.selectLockProductByProType(item);
        if (lockProductInfo != null  && lockProductInfo.size()>0) {
            return true;
        }
        return false;
    }
}