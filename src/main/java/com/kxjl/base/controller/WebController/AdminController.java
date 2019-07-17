package com.kxjl.base.controller.WebController;


import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxjl.base.pojo.Manager;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.service.OrderinfoAuditService;
import com.kxjl.tasktransferplat.service.OrderinfoOperateLogService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.kxjl.base.util.DateUtil.formatterDate;
import static com.kxjl.base.util.DateUtil.getEndString;
import static com.kxjl.base.util.DateUtil.getStartString;
import static com.kxjl.tasktransferplat.util.DateUtil.getFirstDayOfWeek;

import java.util.*;

@Controller
@RequestMapping("/manager")
public class AdminController {

    @Autowired
    private OrderinfoAuditService orderinfoAuditService;
    @Autowired
    private OrderinfoService orderinfoService;
    
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private OrderinfoOperateLogService orderinfoOperateLogService;
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
            if (role.get("sys_role_id").equals("yuanjiang_admin")) {
                // 源匠管理员
                return "/backend/page/homePage/homePage1.ftl";
            }else if (role.get("sys_role_id").equals("company_admin")) {
                // 合伙人管理员
                return "/backend/page/homePage/homePage2.ftl";
            }else if (role.get("sys_role_id").equals("enterprise_admin")) {
                //锁企管理员
                return "/backend/page/homePage/homePage.ftl";
            }else if (role.get("sys_role_id").equals("yuanjiang_customer")) {
                return "/backend/page/homePage/homePage3.ftl";
            }else if (role.get("sys_role_id").equals("enterprise_customer")) {
                return "/backend/page/homePage/homePage4.ftl";

            }
        }

        return "/backend/admin/welcome(new).ftl";
    }

    /**
     * 锁企管理员
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/enterpriseCount")
    public Map enterpriseCount(HttpServletRequest request){
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");

        Orderinfo item=new Orderinfo();
        item.setSellerId(manager.getCompanyId());
        item.setType(14);
        item.setDataState(1);
        List<Orderinfo>orderinfos1 = orderinfoService.selectOrderinfoList(item);
        int num3=orderinfos1.size();


        Map map=new HashMap();

        Orderinfo oqueryNew=new Orderinfo();
        oqueryNew.setSellerId(manager.getCompanyId());
        Map mdata=orderinfoService. selectOrderNumInfo(oqueryNew);

        map.put("numToConfirm",mdata.get("numToConfirm"));
        map.put("numToDo",mdata.get("numToDo"));
        map.put("numDoing",mdata.get("numDoing"));
        map.put("numAudit",num3);
        map.put("numHuiche",mdata.get("numHuiche"));
        map.put("numCompleted",mdata.get("numCompleted"));
        map.put("numBack",mdata.get("numBack"));
        map.put("numAfter",mdata.get("numAfter"));


        return map;
    }

    /**
     * 源匠管理员
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/normalCount")
    public Map normalCount(HttpServletRequest request,String startTime,String endTime){

        Map map=new HashMap();
        Orderinfo oqueryNew=new Orderinfo();
        Map mdata=orderinfoService. selectOrderNumInfo(oqueryNew);

        map.put("numToConfirm",mdata.get("numToConfirm"));
        map.put("numToDo",mdata.get("numToDo"));
        map.put("numDoing",mdata.get("numDoing"));
        map.put("numAudit",mdata.get("numAudit"));
        map.put("numHuifang",mdata.get("numHuifang"));
        map.put("numBack",mdata.get("numBack"));
        map.put("numAfter",mdata.get("numAfter"));


        return map;
    }


    /**
     * 源匠合伙人
     * @param request
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/admin/companyCount")
    public Map companyCount(HttpServletRequest request){
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");
        Orderinfo orderinfo=new Orderinfo();
        orderinfo.setCompanyId(manager.getCompanyId());
        int num1=orderinfoService.countNumOrderCompany(orderinfo);
        int num2=orderinfoService.countHuifOrder(orderinfo);
        int num3=orderinfoService.countPayOrder(orderinfo);
        int num4=orderinfoService.countCompletedOrder(orderinfo);
        Map map=new HashMap();
        map.put("num1",num1);
        map.put("num2",num2);
        map.put("num3",num3);
        map.put("num4",num4);
        return map;
    }

    /**
     * 源匠客服
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/normalOneCount")
    public Map normalOneCount(HttpServletRequest request,String startTime,String endTime){
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");

        Map map=new HashMap();

        
        
        Orderinfo oqueryNew=new Orderinfo();
        Map mdata=orderinfoService. selectOrderNumInfo(oqueryNew);
        
        map.put("numToConfirm",mdata.get("numToConfirm"));
        map.put("numToDo",mdata.get("numToDo"));
        map.put("numDoing",mdata.get("numDoing"));
        map.put("numAudit",mdata.get("numAudit"));
        map.put("numHuifang",mdata.get("numHuifang"));
        map.put("numBack",mdata.get("numBack"));
        map.put("numAfter",mdata.get("numAfter"));
        
        
        
        return map;
    }


    /**
     * 锁企客服
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/enterpriseOneCount")
    public Map enterpriseOneCount(HttpServletRequest request,String startTime,String endTime){
        HashMap m = (HashMap) request.getAttribute("principal");
        Manager manager = (Manager) m.get("user");

        Orderinfo item=new Orderinfo();
        item.setSellerId(manager.getCompanyId());
        item.setType(14);
        item.setDataState(1);
        List<Orderinfo>orderinfos1 = orderinfoService.selectOrderinfoList(item);
        int num3=orderinfos1.size();

        Map map=new HashMap();

        Orderinfo oqueryNew=new Orderinfo();
        oqueryNew.setSellerId(manager.getCompanyId());
        Map mdata=orderinfoService. selectOrderNumInfo(oqueryNew);

        map.put("numToConfirm",mdata.get("numToConfirm"));
        map.put("numToDo",mdata.get("numToDo"));
        map.put("numDoing",mdata.get("numDoing"));
        map.put("numAudit",num3);
        map.put("numHuiche",mdata.get("numHuiche"));
        map.put("numCompleted",mdata.get("numCompleted"));
        map.put("numBack",mdata.get("numBack"));
        map.put("numAfter",mdata.get("numAfter"));



        return map;
    }
}
