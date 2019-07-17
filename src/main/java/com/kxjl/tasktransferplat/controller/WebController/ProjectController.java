/*
 * @(#)ProjectController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-29 10:25:18
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
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.ProjectMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Project;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.ProjectService;
import com.alibaba.fastjson.JSONObject;

import org.aspectj.weaver.ast.Or;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程表管理 ProjectController.java.
 *
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-29 10:25:18
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tproject")
public class ProjectController {
    @Autowired
    private ProjectService tprojectService;
    @Autowired
    private OrderinfoService orderinfoService;

    @RequestMapping("/manager")
    public String manager(Model model) {

        return "/backend/page/tproject/index.ftl";
    }

    @RequestMapping("/allProject")
    @ResponseBody
    public String allProject(Project project, HttpServletRequest request) {

        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");

        if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
            project.setCompanyId(manager.getCompanyId());
        }
        List<Project> projectList = tprojectService.selectProjectList(project);

        return JSON.toJSONString(projectList);
    }

    @RequestMapping("/tprojectList")
    @ManagerActionLog(operateDescribe = "查询工程表", operateFuncType = FunLogType.Query, operateModelClassName = ProjectMapper.class)
    @ResponseBody
    public String tprojectList(Project item, HttpServletRequest request, PageCondition pageCondition) {

        String rst = "";
        List<Project> tprojects = new ArrayList<>();
        HashMap m = (HashMap) request.getAttribute("principal");

        Manager manager = (Manager) m.get("user");

        if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
            item.setCompanyId(manager.getCompanyId());
        }

        Page page = PageUtil.getPage(pageCondition);
        tprojects = tprojectService.selectProjectList(item);

        try {
            rst = PageUtil.packageTableData(page, tprojects);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    @RequestMapping("/delete")
    @ManagerActionLog(operateDescribe = "删除工程表", operateFuncType = FunLogType.Del, operateModelClassName = ProjectMapper.class)
    @ResponseBody
    public Message delete(Project item, HttpServletRequest request) {

        Message msg = new Message();


        int result = orderinfoService.selectCount(item.getId());

        if (result==0) {
            tprojectService.deleteProject(item);
            msg.setBol(true);
        }else{
            msg.setBol(false);
            msg.setMessage("工程下存在工单，不可删除工程");
        }
        return msg;
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam String id, HttpServletRequest request) {


        Project tprojects = tprojectService.selectProjectById(id);
        return JSON.toJSONString(tprojects);
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param tproject
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ManagerActionLog(operateDescribe = "保存修改工程表", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = ProjectMapper.class)
    @ResponseBody
    public String saveOrUpdate(Project tproject, HttpServletRequest request) {

        HashMap m = (HashMap) request.getAttribute("principal");

        Manager manager = (Manager) m.get("user");

        if (manager.getCompanyId()!= null && manager.getCompanyId() != 0) {
            tproject.setCompanyId(manager.getCompanyId());
        }

        JSONObject jsonObject = null;
        try {
            if (null == tproject.getId()) {

				tproject.setCreateUser(manager.getId());
				tproject.setPrincipal(manager.getId());
				jsonObject = tprojectService.saveProject(tproject);

			} else {
				jsonObject = tprojectService.updateProject(tproject);
			

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        return jsonObject.toString();
    }


    @RequestMapping("/selecttproject")
    @ResponseBody
    public List<Project> selecttproject(Project item) {
        return tprojectService.selectProjectList(item);
    }


}