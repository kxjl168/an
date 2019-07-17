/**
 * @(#)AssessManagerController.java 2019/5/17
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
import com.kxjl.tasktransferplat.pojo.AssessQuestionDetail;
import com.kxjl.tasktransferplat.service.AssessQService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/17
 */
@Controller
@RequestMapping("/manager/custormAssessQ")
public class AssessQManagerController {

    @Autowired
    AssessQService assessQService;

    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/tcustormAssessQ/index.ftl";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(AssessQuestion assessQuestion,PageCondition pageCondition) {
        String rst = "";
        Page page = PageUtil.getPage(pageCondition);
        List<AssessQuestion> assessQuestions = assessQService.selectAssessQuestion(assessQuestion);
        try {
            rst = PageUtil.packageTableData(page, assessQuestions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    @RequestMapping("/addAssessQuestion")
    @ResponseBody
    public Map addAssessQuestion(AssessQuestion assessQuestion) {
        Map map = assessQService.addAssessQuestion(assessQuestion);
        return map;
    }

    @RequestMapping("/openOrClose")
    @ResponseBody
    public Map openOrClose(AssessQuestion assessQuestion) {
        Map map = assessQService.openOrClose(assessQuestion);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(AssessQuestion assessQuestion) {
        Map map = assessQService.delete(assessQuestion);
        return map;
    }

    @RequestMapping("/addQuestionDetails")
    @ResponseBody
    public Map addQuestionDetails(@RequestParam String questionDetails,String assessTitle,String assessId) {
        Map map = assessQService.addQuestionDetails(questionDetails,assessTitle,assessId);
        return map;
    }

    @RequestMapping("/selectAssessWithDetails")
    @ResponseBody
    public Map selectAssessWithDetails(@RequestParam String id) {
        Map map1 = assessQService.selectAssessWithDetails(id);
        return map1;
    }

    /**
     * 查询选项
     * @param id
     * @return
     */
    @RequestMapping("/selectOption")
    @ResponseBody
    public Map selectOption(@RequestParam String id) {
        Map map1 = assessQService.selectOption(id);
        return map1;
    }


    /**
     * 删除选项/问题
     * @param id
     * @return
     */
    @RequestMapping("/deleteOptionOrQuestion")
    @ResponseBody
    public Map deleteDetail(@RequestParam String id,String assessId) {
        Map map1 = assessQService.deleteDetail(id,assessId);
        return map1;
    }

    /**
     * 修改选项
     * @return
     */
    @RequestMapping("/updateOption")
    @ResponseBody
    public Map updateOption(AssessQuestionDetail assessQuestionDetail) {
        if(null == assessQuestionDetail.getId() || "".equals(assessQuestionDetail.getId())){
            Map map1 = assessQService.saveOption(assessQuestionDetail);
            return map1;
        }else{
            Map map2 = assessQService.updateOption(assessQuestionDetail);
            return map2;
        }
    }

    /**
     * 查询问题或选项（type： 1 问题  2  选项）
     * @return
     */
    @RequestMapping("/selectQusOrOpt")
    @ResponseBody
    public List selectQusOrOpt(String assessId,String type,String parentid) {
        List<AssessQuestionDetail> assessQuestionDetails = assessQService.selectQusOrOpt(assessId, type,parentid);
        return assessQuestionDetails;
    }


}
