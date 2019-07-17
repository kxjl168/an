/*
 * @(#)UserBackQuestionController.java
 * @author: zj
 * @Date: 2019-06-04 11:36:22
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.UserBackQuestionMapper;
import com.kxjl.tasktransferplat.pojo.UserBackQuestion;
import com.kxjl.tasktransferplat.service.UserBackQuestionService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 锁匠反馈消息表管理 UserBackQuestionController.java.
 *
 * @author zj
 * @version 1.0.1 2019-06-04 11:36:22
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tuserbackquestion")
public class UserBackQuestionController {
    @Autowired
    private UserBackQuestionService tuserbackquestionService;


    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/tuserbackquestion/index.ftl";
    }

    @RequestMapping("/tuserbackquestionList")
    @ManagerActionLog(operateDescribe = "查询锁匠反馈消息表", operateFuncType = FunLogType.Query, operateModelClassName = UserBackQuestionMapper.class)
    @ResponseBody
    public String tuserbackquestionList(UserBackQuestion item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<UserBackQuestion> tuserbackquestions = new ArrayList<>();

        Page page = PageUtil.getPage(pageCondition);
        tuserbackquestions = tuserbackquestionService.selectUserBackQuestionList(item);

        try {
            rst = PageUtil.packageTableData(page, tuserbackquestions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除锁匠反馈消息表", operateFuncType = FunLogType.Del, operateModelClassName = UserBackQuestionMapper.class)
    @ResponseBody
    public Message delete(UserBackQuestion item, HttpServletRequest request) {

        Message msg = new Message();


        int result = tuserbackquestionService.deleteUserBackQuestion(item);
        if (result == 1) {
            msg.setBol(true);
        }
        return msg;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam Long id, HttpServletRequest request) {


        UserBackQuestion tuserbackquestions = tuserbackquestionService.selectUserBackQuestionById(id);
        return JSONObject.fromObject(tuserbackquestions).toString();
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param tuserbackquestion
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改锁匠反馈消息表", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserBackQuestionMapper.class)
    @ResponseBody
    public String saveOrUpdate(UserBackQuestion tuserbackquestion) {

        JSONObject jsonObject = null;
        try {
            if (null == tuserbackquestion.getId()) {
                jsonObject = tuserbackquestionService.saveUserBackQuestion(tuserbackquestion);
            } else {
                jsonObject = tuserbackquestionService.updateUserBackQuestion(tuserbackquestion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }


    @RequestMapping("/selecttuserbackquestion")
    @ResponseBody
    public List<UserBackQuestion> selecttuserbackquestion(UserBackQuestion item) {
        return tuserbackquestionService.selectUserBackQuestionList(item);
    }


}