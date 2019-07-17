/*
 * @(#)UserBackQuestionController.java
 * @author: zj
 * @Date: 2019-06-04 11:36:22
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;


import com.google.gson.Gson;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.UserBackQuestion;
import com.kxjl.tasktransferplat.service.UserBackQuestionService;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 锁匠反馈消息表管理 UserBackQuestionController.java.
 *
 * @author zj
 * @version 1.0.1 2019-06-04 11:36:22
 * @since 1.0.0
 */
@RestController
@RequestMapping("/interface/tuserbackquestion")
public class MobileUserBackQuestionController {
    @Autowired
    private UserBackQuestionService tuserbackquestionService;

    /**
     * 新增普通用户请求 demo
     *
     * @param baseRequestDto
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public AppResult saveOrUpdate(BaseRequestDto baseRequestDto) {
        JSONObject jsonObject = null;
        UserBackQuestion userBackQuestion = new Gson().fromJson(baseRequestDto.getData(), UserBackQuestion.class);
        try {
            if (null == userBackQuestion.getId()) {
                jsonObject = tuserbackquestionService.saveUserBackQuestion(userBackQuestion);
            } else {
                jsonObject = tuserbackquestionService.updateUserBackQuestion(userBackQuestion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultUtil.fail("提交失败");
        }
        if (jsonObject != null && jsonObject.getBoolean("bol")) {
            return AppResultUtil.success("提交成功");
        } else {
            return AppResultUtil.fail("提交失败");
        }
    }


    @RequestMapping("/selecttuserbackquestion")
    public AppResult selecttuserbackquestion(BaseRequestDto baseRequestDto) {
        List<UserBackQuestion> userBackQuestionList = new ArrayList<>();
        try {
            UserBackQuestion userBackQuestion = new Gson().fromJson(baseRequestDto.getData(), UserBackQuestion.class);
            userBackQuestionList = tuserbackquestionService.selectUserBackQuestionList(userBackQuestion);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AppResultUtil.success(userBackQuestionList);
    }
}