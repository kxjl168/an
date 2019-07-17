/**
 * @(#)GradeMoneyController.java 2019/5/21
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.controller.WebController;

import com.github.pagehelper.Page;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.GradeMoney;
import com.kxjl.tasktransferplat.service.GradeMoneyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/21
 */
@Controller
@RequestMapping("/manager/gradeMoney")
public class GradeMoneyController {

    @Autowired
    private GradeMoneyService gradeMoneyService;

    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/gradeMoney/index.ftl";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(GradeMoney gradeMoney, PageCondition pageCondition) {
        String rst = "";
        Page page = PageUtil.getPage(pageCondition);
        List<GradeMoney> gradeMonies = gradeMoneyService.selectGradeMoneyList(gradeMoney);
        try {
            rst = PageUtil.packageTableData(page, gradeMonies);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(String id) {
        Map rst = gradeMoneyService.delete(id);
        return rst;
    }

    /**
     * 通过分数查找所在分数段记录
     * @param score
     * @return
     */
    @RequestMapping("/selectOneByScore")
    @ResponseBody
    public GradeMoney selectOneByScore(String score) {
        GradeMoney gradeMoney = gradeMoneyService.selectOneByScore(score);
        return gradeMoney;
    }

    /**
     * 添加奖励规则记录
     * @param gradeMoney
     * @return
     */
    @RequestMapping("/addGradeMoney")
    @ResponseBody
    public Map addGradeMoney(GradeMoney gradeMoney) {
        Map rst = gradeMoneyService.addGradeMoney(gradeMoney);
        return rst;
    }

    /**
     * 修改奖励规则记录
     * @param gradeMoney
     * @return
     */
    @RequestMapping("/changeGradeMoney")
    @ResponseBody
    public Map changeGradeMoney(GradeMoney gradeMoney) {
        Map rst = gradeMoneyService.changeGradeMoney(gradeMoney);
        return rst;
    }
}
