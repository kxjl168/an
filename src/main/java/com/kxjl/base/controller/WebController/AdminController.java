package com.kxjl.base.controller.WebController;


import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxjl.base.pojo.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.kxjl.base.util.DateUtil.formatterDate;
import static com.kxjl.base.util.DateUtil.getEndString;
import static com.kxjl.base.util.DateUtil.getStartString;

import java.util.*;

@Controller
@RequestMapping("/manager")
public class AdminController {

   /* @Autowired
    private OrderinfoAuditService orderinfoAuditService;
    @Autowired
    private OrderinfoService orderinfoService;
    
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private OrderinfoOperateLogService orderinfoOperateLogService;*/
    //登录后  首页
    @RequestMapping("/admin/index")
    public String admin(Map<String, Object> map, HttpServletRequest request) {
        Map principal = (Map) request.getAttribute("principal");
        
        if(principal==null)
        	return "redirect:/login/signIn";

        Manager manager = (Manager) principal.get("user");
  

        request.setAttribute("operateUserId",manager.getId());


        List<Map> roleList = (List<Map>) principal.get("roles");
        for (Map role : roleList) {
            if (role.get("sys_role_id").equals("unitadmin")||role.get("sys_role_id").equals("superadmin")) {
                // 源匠管理员
                return "/backend/page/tseatinfoAdmin/index.ftl";
            }else if (role.get("sys_role_id").equals("1")) {
                // 合伙人管理员
            	 return "/backend/page/tseatinfoAdmin/index.ftl";
            }else if (role.get("sys_role_id").equals("enterprise_admin")) {
               
            }
        }

        return "/backend/admin/welcome(new).ftl";
    }

   
}
