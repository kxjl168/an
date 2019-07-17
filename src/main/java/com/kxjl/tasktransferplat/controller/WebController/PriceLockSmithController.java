/*
 * @(#)PriceLockSmithController.java
 * @author: zhangyong
 * @Date: 2019-04-17 16:27:40
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
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.PriceLockSmithBuildMapper;
import com.kxjl.tasktransferplat.dao.plus.PriceLocksmithOtherMapper;
import com.kxjl.tasktransferplat.pojo.PriceLockSmithBuild;
import com.kxjl.tasktransferplat.pojo.PriceLocksmithOther;
import com.kxjl.tasktransferplat.service.PriceLockSmithBuildService;
import com.kxjl.tasktransferplat.service.PriceLocksmithOtherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 锁匠费用管理 PriceLockSmithController.java.
 *
 * @author zhangyong
 * @version 1.0.1 2019-04-17 16:27:40
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager")
public class PriceLockSmithController {
    @Autowired
    private PriceLockSmithBuildService priceLockSmithBuildService;
    @Autowired
    private PriceLocksmithOtherService priceLocksmithOtherService;

    @RequestMapping("/pricelocksmithbuild/manager")
    public String buildManager(Model model) {
        return "/backend/page/priceLockSmithBuild/index.ftl";
    }

    @RequestMapping("/pricelocksmithother/manager")
    public String otherManager(Model model) {
        return "/backend/page/priceLockSmithOther/index.ftl";
    }

    /**
     * 新增或修改锁匠安装价格
     *
     * @param priceLockSmithBuild
     * @return
     */
    @RequestMapping("/pricelocksmithbuild/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "新增或修改锁匠安装价格", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = PriceLockSmithBuildMapper.class)
    @ResponseBody
    public Message saveOrUpdate(PriceLockSmithBuild priceLockSmithBuild) {
        if (null == priceLockSmithBuild.getId() || priceLockSmithBuild.getId() == 0) {
            if (priceLockSmithBuild.getProvinceCode() == null || priceLockSmithBuild.getProvinceCode() == "") {
                Message message = new Message();
                message.setBol(false);
                message.setMessage("请选择省");
                return message;
            }
            if (priceLockSmithBuild.getCityCode() == null || priceLockSmithBuild.getCityCode() == "") {
                Message message = new Message();
                message.setBol(false);
                message.setMessage("请选择市");
                return message;
            }
            if (null == priceLockSmithBuild.getMoney() || priceLockSmithBuild.getMoney().compareTo(new BigDecimal(0)) != 1) {
                Message message = new Message();
                message.setBol(false);
                message.setMessage("请输入合适的安装价格");
                return message;
            }
            PriceLockSmithBuild tmpPriceLockSmithBuild = priceLockSmithBuildService.selectByCityCode(priceLockSmithBuild.getCityCode(), priceLockSmithBuild.getProvinceCode());
            if (null != tmpPriceLockSmithBuild) {
            	
            	priceLockSmithBuild.setId(tmpPriceLockSmithBuild.getId());
            	 return priceLockSmithBuildService.updateById(priceLockSmithBuild);
            	/*
                Message message = new Message();
                message.setBol(false);
                message.setMessage("该城市已存在");
                return message;*/
            }
            return priceLockSmithBuildService.insert(priceLockSmithBuild);
        } else {
            return priceLockSmithBuildService.updateById(priceLockSmithBuild);
        }
    }

    /**
     * 新增或修改锁匠其他服务价格
     *
     * @param priceLocksmithOther
     * @return
     */
    @RequestMapping("/pricelocksmithother/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "新增或修改锁匠其他服务价格", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = PriceLocksmithOtherMapper.class)
    @ResponseBody
    public Message saveOrUpdate(PriceLocksmithOther priceLocksmithOther) {
        if (null == priceLocksmithOther.getPrice() || priceLocksmithOther.getPrice().compareTo(new BigDecimal(0)) != 1) {
            Message message = new Message();
            message.setBol(false);
            message.setMessage("请输入合适的服务价格");
            return message;
        }
        if (null == priceLocksmithOther.getId() || priceLocksmithOther.getId() == 0) {
            if (null != priceLocksmithOtherService.selectByType(priceLocksmithOther.getServerType(), priceLocksmithOther.getParentType())) {
                Message message = new Message();
                message.setBol(false);
                message.setMessage("该类型已存在");
                return message;
            }
            return priceLocksmithOtherService.insert(priceLocksmithOther);
        } else {
            return priceLocksmithOtherService.updateByPrimaryKey(priceLocksmithOther);
        }
    }

    /**
     * 根据id删除锁企安装价格
     *
     * @param id
     * @return
     */
    @RequestMapping("/pricelocksmithbuild/delete")
    @ManagerActionLog(operateDescribe = "根据id删除锁企安装价格", operateFuncType = FunLogType.Del, operateModelClassName = PriceLockSmithBuildMapper.class)
    @ResponseBody
    public Message delete(@RequestParam Long id, HttpServletRequest request) {
        Message msg = priceLockSmithBuildService.deleteById(id);
        return msg;
    }

    /**
     * 根据id删除锁企其他服务价格
     *
     * @param id
     * @return
     */
    @RequestMapping("/pricelocksmithother/delete")
    @ManagerActionLog(operateDescribe = "根据id删除锁企其他服务价格", operateFuncType = FunLogType.Del, operateModelClassName = PriceLocksmithOtherMapper.class)
    @ResponseBody
    public Message deleteOtherPrice(@RequestParam Long id, HttpServletRequest request) {
        Message msg = priceLocksmithOtherService.deleteByPrimaryKey(id);
        return msg;
    }

    /**
     * 查询锁匠安装费用列表
     *
     * @param priceLockSmithBuild
     * @return
     */
    @RequestMapping("/pricelocksmithbuild/selectList")
    @ManagerActionLog(operateDescribe = "查询锁匠安装费用列表", operateFuncType = FunLogType.Query, operateModelClassName = PriceLockSmithBuildMapper.class)
    @ResponseBody
    public String selectList(PriceLockSmithBuild priceLockSmithBuild, HttpServletRequest request, PageCondition pageCondition) {
        Page page = PageUtil.getPage(pageCondition);
        List<PriceLockSmithBuild> priceLockSmithBuildList = priceLockSmithBuildService.selectList(priceLockSmithBuild);
        String result = PageUtil.packageTableData(page, priceLockSmithBuildList);
        return result;
    }

    /**
     * 查询锁匠其他服务费用列表
     *
     * @param priceLocksmithOther
     * @return
     */
    @RequestMapping("/pricelocksmithother/selectList")
    @ManagerActionLog(operateDescribe = "查询锁匠其他服务费用列表", operateFuncType = FunLogType.Query, operateModelClassName = PriceLocksmithOtherMapper.class)
    @ResponseBody
    public String selectList(PriceLocksmithOther priceLocksmithOther, HttpServletRequest request, PageCondition pageCondition) {
        Page page = PageUtil.getPage(pageCondition);
        List<PriceLocksmithOther> priceLocksmithOtherList = priceLocksmithOtherService.selectList(priceLocksmithOther);
        String result = PageUtil.packageTableData(page, priceLocksmithOtherList);
        return result;
    }

    /**
     * 根据id查询锁匠安装费用
     *
     * @param id
     * @return
     */
    @RequestMapping("/pricelocksmithbuild/load")
    @ManagerActionLog(operateDescribe = "根据id查询锁匠安装费用", operateFuncType = FunLogType.Query, operateModelClassName = PriceLockSmithBuildMapper.class)
    @ResponseBody
    public String load(@RequestParam Long id, HttpServletRequest request) {
        PriceLockSmithBuild priceLockSmithBuild = priceLockSmithBuildService.selectById(id);
        return JSON.toJSONString(priceLockSmithBuild);
    }

    /**
     * 根据id查询锁匠其他服务费用
     *
     * @param id
     * @return
     */
    @RequestMapping("/pricelocksmithother/load")
    @ManagerActionLog(operateDescribe = "根据id查询锁匠其他服务费用", operateFuncType = FunLogType.Query, operateModelClassName = PriceLocksmithOtherMapper.class)
    @ResponseBody
    public String loadOtherPrice(@RequestParam Long id, HttpServletRequest request) {
        PriceLocksmithOther priceLocksmithOther = priceLocksmithOtherService.selectByPrimaryKey(id);
        return JSON.toJSONString(priceLocksmithOther);
    }

    @RequestMapping("/pricelocksmithbuild/selectByCityCode")
    @ManagerActionLog(operateDescribe = "根据id查询地区费用", operateFuncType = FunLogType.Query, operateModelClassName = PriceLockSmithBuildMapper.class)
    @ResponseBody
    public String selectByCityCode(@RequestParam String provinceCode, @RequestParam String cityCode, HttpServletRequest request) {
        PriceLockSmithBuild priceLockSmithBuildDate = priceLockSmithBuildService.selectByCityCode(cityCode, provinceCode);
        return JSON.toJSONString(priceLockSmithBuildDate);
    }


}