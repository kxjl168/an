package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.kxjl.base.base.BaseController;
import com.kxjl.base.base.PageManager;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.service.RoleService;
import com.kxjl.base.util.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/lockCompany")
public class LockCompanyManagerController extends BaseController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String manager(@RequestParam Map<String, String> params, Model model) {
        // pageNo
        String pn = params.get("pn");
        // pageSize;
        String ps = params.get("ps");

        int pageNo = 0;
        int pageSize = 10;
        try {
            if (null != pn) {
                pageNo = Integer.parseInt(pn);
            }
            if (null != ps) {
                pageSize = Integer.parseInt(ps);
            }
        } catch (NumberFormatException e) {

        }

        //PageHelper.startPage(pageNo, pageSize);
        PageManager p = new PageManager(pageNo, 2);


        List<Map> users = managerService.selectManagerByManager(params);
        // 分页信息
        PageInfo<Map> pageInfo = new PageInfo<>(users);
        List<Map> roles = roleService.selectRoles();

        model.addAttribute("users", users);

        // map.put("pager",pageInfo);
        model.addAttribute("pager", pageInfo);
        model.addAttribute("roles", roles);

        return "/backend/page/tlockCompanyManager/index.ftl";
    }

    /**
     * bootstrap tables jquery查询数据
     *
     * @param request
     * @param response
     * @return
     * @author zj
     * @date 2018年5月9日
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam Map<String, String> params, HttpServletRequest request,
                       HttpServletResponse response) {

        String name = request.getParameter("name");
        // pageNo
        String offset = request.getParameter("offset");

        // pageSize;
        String ps = request.getParameter("pageSize");

        int pageNo = 0;
        int pageSize = 10;
        int ofst = 0;
        try {
            if (offset != null) {
                ofst = Integer.parseInt(offset);

            }

            if (null != ps) {
                pageSize = Integer.parseInt(ps);
            }

            pageNo = (int) Math.ceil(((double) (ofst + 1)) / ((double) pageSize));

        } catch (NumberFormatException e) {

        }
        //Page page = PageHelper.startPage(pageNo, pageSize);
        PageManager page = new PageManager(pageNo, pageSize);
        /*
         * Subject subject = SecurityUtils.getSubject(); String userId =
         * ManagerIDUtil.getManagerID(subject, response);
         */

		
		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		
        
        
        Manager q = new Manager();
        q.setUsername(name);
        
if (type == 0) { //系统都可以看
			
		}
		else { //2 锁企，只能看自己的
			q.setCompanyId(manager.getCompanyId());
		}
        
        
        
        PageInfo<Manager> rst = managerService.selectLockCompanyByManager(page, q);

        Gson gs = new Gson();
        JSONObject data = new JSONObject();
        String datastr = "";
        try {
            // JSONArray rows = new JSONArray(gs.toJson(rst));
            data.put("total", rst.getTotal());
            data.put("rows", rst.getList());
            datastr = data.toString();
        } catch (Exception e) {
            logger.error("error", e);
        }

        return datastr;// responseData(request, response, datastr);

    }

    @RequestMapping("/ManagerList")
    @ResponseBody
    public List<Map> ManagerList() {
        return managerService.selectManagerList(null);
    }

    @RequestMapping("/deleteManager")
    @ResponseBody
    public Message ManagerList(HttpServletRequest request) {
        String uid = request.getParameter("id");
        Message msg = new Message();

        int result = managerService.deleteManager(uid);
        if (result == 1) {
            msg.setBol(true);
        }


        return msg;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(HttpServletRequest request) {
        String uid = request.getParameter("id");
        Message msg = new Message();

        int result = managerService.delete(uid);
        if (result == 1) {
            msg.setBol(true);
        }


        return msg;
    }

    @RequestMapping("/drop")
    @ResponseBody
    public Message drop(HttpServletRequest request) {
        String uid = request.getParameter("id");
        Message msg = new Message();

        int result = managerService.drop(uid);
        if (result == 1) {
            msg.setBol(true);
        }


        return msg;
    }

    @RequestMapping("/reset")
    @ResponseBody
    public Message reset(HttpServletRequest request) {
        String uid = request.getParameter("id");
        Message msg = new Message();

        int result = managerService.reset(uid);
        if (result == 1) {
            msg.setBol(true);
        }


        return msg;
    }

    @RequestMapping("/getManager")
    @ResponseBody
    public String getManager(@RequestParam Map<String, String> params) {
        JSONObject jsonObject = new JSONObject();
        List<Map> users = managerService.selectManagerByManager(params);
        if (null != users && 1 == users.size()) {
            jsonObject.putAll(users.get(0));
        }
        return jsonObject.toString();
    }

    /**
     * 新增普通用户请求 demo
     *
     * @param user
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(Manager user, Model model, HttpServletRequest request) {
        String jsonObject = null;

        Manager manager = ((Manager) request.getSession().getAttribute("user"));
        int type = manager.getType();
        if (type == 2) {

            user.setCompanyId(manager.getCompanyId());
        }

        try {

            String menuids = request.getParameter("roleids");

            //	String cid= request.getParameter("companyId");

            //if(cid!=null&&!cid.equals(""))
            //user.setCompanyId(Long.parseLong(cid));

            if (null == user.getId() || "".equals(user.getId())) {

                if (user.getUsername() == null || user.getUsername().equals(""))
                    user.setUsername(user.getTelephone());

                jsonObject = managerService.saveManager(user, menuids);
            } else {
                jsonObject = managerService.updateManager(user, menuids);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @RequestMapping("/etrpermissionTransfer")
    public String etrPermissionTransfer() {
        return "/frontend/permission/permissiontransfer";
    }
}
