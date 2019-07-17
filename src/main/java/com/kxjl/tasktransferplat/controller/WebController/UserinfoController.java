/*
 * @(#)UserinfoController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-29 10:29:14
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
import com.kxjl.base.util.PasswordUtil;
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;
import com.kxjl.tasktransferplat.service.AreaService;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.management.LockInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 锁匠管理 UserinfoController.java.
 * 
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-29 10:29:14
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/tuserinfo")
public class UserinfoController {
	@Autowired
	private UserinfoService tuserinfoService;

	@Autowired
	private OrderinfoService orderinfoService;
	@Autowired
	CompanyService HehuorenService;

	@Autowired
	private AreaService areaService;

	/**
	 * 锁匠管理
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/tuserinfo/index.ftl";
	}

	/**
	 * 锁匠审核
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/managerPass")
	public String managerPass(Model model) {

		return "/backend/page/tuserinfoAuditNormal/index.ftl";
		// return "/backend/page/tuserinfoPass/index.ftl";
	}

	/**
	 * 所有未通过审核锁匠
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/managerNoPass")
	public String managerNoPass(Model model) {

		return "/backend/page/tuserinfoNoPass/index.ftl";
	}

	/**
	 * mobile test
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/test")
	public String test(Model model) {

		return "/frontend/mobile/test.jsp";
	}

	/**
	 * 合伙人下的待审核锁匠
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/companyManager")
	public String companyManager(Model model) {

		return "/backend/page/companyManager/index.ftl";
	}

	/**
	 * 合伙人下的已审核锁匠
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/companyManagerPass")
	public String companyManagerPass(Model model) {

		return "/backend/page/companyManagerPass/index.ftl";
	}

	/**
	 * 合伙人锁匠管理
	 * 
	 * @param model
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/lockparter")
	public String lockparter(Model model) {

		return "/backend/page/tuserinfoparter/index.ftl";
	}

	@RequestMapping("/blacklist")
	public String blacklist(Model model) {

		return "/backend/page/tuserinfoblacklist/index.ftl";
	}

	@RequestMapping("/selectLockerByOrderId")
	@ResponseBody
	public String selectLockerByOrderId(Orderinfo orderinfo) {
		return JSON.toJSONString(tuserinfoService.selectLockerByOrderId(orderinfo));
	}

	/**
	 * 检查所选择的行政区域是否已经有其他合伙人
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月14日
	 */
	@RequestMapping("/checkLockAndDistrict")
	@ResponseBody
	public Message checkLockAndDistrict(HttpServletRequest request) {

		Message msg = new Message();

		String districtids = request.getParameter("districtids");//
		String lockid = request.getParameter("lockid");// 锁匠id

		if (districtids.endsWith(","))
			districtids = districtids.substring(0, districtids.length() - 1);

		Userinfo user = tuserinfoService.selectUserinfoById(Long.parseLong(lockid));
		if (user.getUserType().equals("1")) {
			// 自由锁匠不检查区域冲突
			msg.setBol(true);
			return msg;
		} else {

			Userinfo uquery = new Userinfo();
			uquery.setName(lockid);// 当前合伙人id
			uquery.setDistrictids(districtids);// 已选作业区域

			// 检查作业区域是否已有其他合伙人
			List<Userinfo> ousers = tuserinfoService.selectJobAreaByUserAndDistrictIds(uquery);

			StringBuilder sb = new StringBuilder();
			if (ousers != null && ousers.size() > 0) {
				for (int i = 0; i < ousers.size(); i++) {
					Userinfo uother = ousers.get(i);
					sb.append(uother.getDistrict() + " 已存在合伙人:" + uother.getName() + "(" + uother.getPhone() + ")");
				}

				msg.setBol(false);
				msg.setMessage(sb.toString());
				return msg;
			} else {
				msg.setBol(true);
				return msg;
			}
		}

	}

	/**
	 * <p>
	 * 后台界面分配工单时选择使用
	 * </p>
	 * <p>
	 * select2分页查询锁匠
	 * </p>
	 * <p>
	 * 根据工单客户所在区域选择作业区域相同的非合伙人下锁匠
	 * </p>
	 * 
	 * 
	 * @param orderinfo
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年5月7日
	 */
	@RequestMapping("/selectLockerByOrderIdNew")
	@ResponseBody
	public String selectLockerByOrderIdNew(Orderinfo orderinfo, HttpServletRequest request,
			PageCondition pageCondition) {

		String rst = "";
		List<Userinfo> tcompanys = new ArrayList<>();

		Orderinfo torderinfo = orderinfoService.loadOrderinfoById(orderinfo.getId());

	
		// 优先寻找合伙人os
		Userinfo uquery = new Userinfo();
		// uquery.setUserType("2");//合伙人
		uquery.setDataState(1);
		uquery.setAuditFlag("1");// 审核通过
		uquery.setParterquery("0");// 自由锁匠或者合伙人 companyid is null;
		AreaDistrict adistrict = areaService.getAreaDistrictByCode(torderinfo.getAreaCode());

		uquery.setDistrictId2(String.valueOf(adistrict.getId()));// 作业区域

		Page page = PageUtil.getPage(pageCondition);

		tcompanys = tuserinfoService.selectUserinfoList(uquery);

		// tcompanys = tuserinfoService.selectLockerByOrderId(orderinfo);

		try {
			rst = PageUtil.packageTableData(page, tcompanys);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/tuserAuditinfoList")
	@ManagerActionLog(operateDescribe = "查询锁匠待审核", operateFuncType = FunLogType.Query, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public String tuserAuditinfoList(Userinfo item, HttpServletRequest request, PageCondition pageCondition) {

		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");

		// if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
		// item.setCompanyId(manager.getCompanyId());
		// }

		String rst = "";
		List<Userinfo> tuserinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		tuserinfos = tuserinfoService.selectUnAuditUserinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/tuserinfoList")
	@ManagerActionLog(operateDescribe = "查询锁匠", operateFuncType = FunLogType.Query, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public String tuserinfoList(Userinfo item, HttpServletRequest request, PageCondition pageCondition) {

		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");

		/*
		 * if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
		 * item.setCompanyId(manager.getCompanyId());
		 * 
		 * Company hehuoren = HehuorenService.selectCompanyById(manager.getCompanyId());
		 * 
		 * if (hehuoren.getAreaCode() != null && !hehuoren.getAreaCode().equals("")) {
		 * // zj 190507 // 设置查询区域 item.setAreaCode(hehuoren.getAreaCode().substring(0,
		 * 4));
		 * 
		 * } else item.setAreaCode("888888"); // 合伙人没有区域，直接查询不到锁匠
		 * 
		 * }
		 */

		String rst = "";
		List<Userinfo> tuserinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);

		tuserinfos = tuserinfoService.selectUserinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, tuserinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	/**
	 * 逻辑删除，界面不可见
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "逻辑删除锁匠", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public Message delete(Userinfo item, HttpServletRequest request) {

		Message msg = new Message();

		msg.setBol(false);
		msg.setMessage("操作失败!");

		int result = tuserinfoService.deleteUserinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		if (result == -1) {
			msg.setBol(false);
			msg.setMessage("锁匠存在未完成工单，不能删除！");
		} else if (result == -2) {
			msg.setBol(false);
			msg.setMessage("合伙人下存在锁匠，不能删除,请先处理所属锁匠!");
		}
		return msg;
	}

	/**
	 * 停用，界面任然可见
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/NoUsedelete")
	@ManagerActionLog(operateDescribe = "停用锁匠", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public Message NoUsedelete(Userinfo item, HttpServletRequest request) {

		Message msg = new Message();

		msg.setBol(false);
		msg.setMessage("操作失败!");

		int result = tuserinfoService.NoUseUserinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		if (result == -1) {
			msg.setBol(false);
			if(item.getDataState()==0)
			msg.setMessage("锁匠存在未完成工单，不能废弃！");
			if(item.getDataState()==3)
				msg.setMessage("锁匠存在未完成工单，不能拉黑！");
		} else if (result == -2) {
			msg.setBol(false);
			msg.setMessage("合伙人下存在锁匠，不能拉黑或废弃,请先处理所属锁匠!");
		}
		return msg;
	}

	/**
	 * 启用，
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/ReUse")
	@ManagerActionLog(operateDescribe = "启用锁匠", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public Message ReUse(Userinfo item, HttpServletRequest request) {

		Message msg = new Message();

		try {
			item.setDataState(1);// 启用状态
			msg = tuserinfoService.ReUseUserinfo(item);

			/*if (result == 1) {
				msg.setBol(true);
			}
			if (result == -1) {
				msg.setBol(false);
				msg.setMessage("所属合伙人账号已停用或删除，无法恢复！");
			}*/
		} catch (Exception e) {
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}

		return msg;
	}

	/**
	 * 变更为自由锁匠
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/changeTofree")
	@ManagerActionLog(operateDescribe = "变为自由锁匠", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public Message changeTofree(Userinfo item, HttpServletRequest request) {

		Message msg = new Message();

		msg = tuserinfoService.updateLockerParterNull(item.getId());

		return msg;
	}

	/**
	 * 物理删除
	 * 
	 * @param item
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	@RequestMapping("/truedelete")
	@ManagerActionLog(operateDescribe = "真实删除锁匠", operateFuncType = FunLogType.Del, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public Message truedelete(Userinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = tuserinfoService.deleteTrueUserinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		if (result == -1) {
			msg.setBol(false);
			msg.setMessage("锁匠存在未完成工单，不能删除！");
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam Long id, HttpServletRequest request) {

		Userinfo tuserinfos = tuserinfoService.selectUserinfoById(id);
		return JSON.toJSONString(tuserinfos);
	}

	/**
	 * 解绑vx
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/untying")
	@ResponseBody
	public Message untying(Long id, HttpServletRequest request) {

		Message msg = new Message();

		int result = tuserinfoService.untyingUserinfoById(id);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	/**
	 * 审核
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAudit")
	@ResponseBody
	public String updateAudit(Userinfo tuserinfo, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = (Manager) m.get("user");

		JSONObject jsonObject = new JSONObject();
		try {

			// 普通自由锁匠的审核
			int rst = tuserinfoService.updateAuditState(tuserinfo);

			if (rst > 0)
				jsonObject.put("bol", true);
			else
				jsonObject.put("bol", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// assert jsonObject != null;
		return jsonObject.toString();
	}

	/**
	 * 提交锁匠类型变更申请
	 * 
	 * @param tuserinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年6月6日
	 */
	@RequestMapping("/userTypeChange")
	@ResponseBody
	public Message userTypeChange(UserinfoAudit tuserinfo, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Message rst = new Message();
		JSONObject jsonObject = new JSONObject();
		try {
			/*
			 * String modifyrst= saveOrUpdate(tuserinfo,request);
			 * 
			 * jsonObject=JSONObject.parseObject(modifyrst);
			 * if(jsonObject!=null&&!jsonObject.getBooleanValue("bol")) { //更新失败 return
			 * jsonObject.toString(); } //TODO
			 * 
			 * 
			 * // 普通自由锁匠的审核 int rst = tuserinfoService.reDoAudit(tuserinfo);
			 */

		
			
			rst = tuserinfoService.userTypeChange(tuserinfo,1);

		} catch (Exception e) {
			e.printStackTrace();
			rst.setBol(false);

		}
		// assert jsonObject != null;
		return rst;
	}

	/**
	 * 重新提交审核
	 * 
	 * @param tuserinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年6月6日
	 */
	@RequestMapping("/reDoAudit")
	@ResponseBody
	public String reDoAudit(Userinfo tuserinfo, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = (Manager) m.get("user");

		JSONObject jsonObject = new JSONObject();
		try {
			String modifyrst = saveOrUpdate(tuserinfo, request);

			jsonObject = JSONObject.parseObject(modifyrst);
			if (jsonObject != null && !jsonObject.getBooleanValue("bol")) {
				// 更新失败
				return jsonObject.toString();
			}

			// 普通自由锁匠的审核
			int rst = tuserinfoService.reDoAudit(tuserinfo);

			if (rst > 0)
				jsonObject.put("bol", true);
			else
				jsonObject.put("bol", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// assert jsonObject != null;
		return jsonObject.toString();
	}

	/**
	 * 锁匠手机号重复检查
	 * 
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月14日
	 */
	@RequestMapping("/checkPhone")
	@ResponseBody
	public Message checkPhone(HttpServletRequest request) {

		Message msg = new Message();

		String phone = request.getParameter("phone");
		String id = String.valueOf(request.getParameter("id"));

		Userinfo uquery = new Userinfo();
		uquery.setPhone(phone);
		List<Userinfo> userinfos = tuserinfoService.selectUserinfoByPhoneAll(uquery);

		boolean isRepeatPhone = false;
		if (userinfos != null) {
			for (Userinfo userinfo : userinfos) {
				if (userinfo.getPhone().equals(phone)) {
					if (id != null && !id.equals("")) {
						if (userinfo.getId() != Long.parseLong(id))
							isRepeatPhone = true;
					} else
						isRepeatPhone = true;
					break;
				}
			}
		}

		if (isRepeatPhone) {
			msg.setBol(true);
			msg.setMessage("此手机号码已存在");
		}
		return msg;
	}

	/**
	 * 身份证检查
	 * 
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月14日
	 */
	@RequestMapping("/checkCard")
	@ResponseBody
	public Message checkCard(HttpServletRequest request) {
		Message msg = new Message();

		String idcard = request.getParameter("idcard");
		String id = String.valueOf(request.getParameter("id"));

		Userinfo uquery = new Userinfo();
		uquery.setIdCard(idcard);
		List<Userinfo> userinfos = tuserinfoService.selectUserinfoByIdCardAll(uquery);

		boolean isRepeat = false;
		if (userinfos != null) {
			for (Userinfo userinfo : userinfos) {
				if (id != null && !id.equals("")) {
					if (userinfo.getId() != Long.parseLong(id))
						isRepeat = true;
				} else
					isRepeat = true;

				break;

			}
		}

		if (isRepeat) {
			msg.setBol(true);
			msg.setMessage("此身份证已存在");
		}
		return msg;
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param tuserinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "保存修改锁匠", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = UserinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Userinfo tuserinfo, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = (Manager) m.get("user");

		JSONObject jsonObject = null;
		try {
			if (tuserinfo.getCompanyId() == null && manager.getCompanyId() != null && manager.getCompanyId() != 0) {
				tuserinfo.setCompanyId(manager.getCompanyId());
			}
			List<Userinfo> userinfos1 = tuserinfoService.selectUserinfoByPhone(tuserinfo);// tuserinfoService.selectUserinfoByIdCardAll(tuserinfo);

			if (null == tuserinfo.getId()) {
				if (!isBlank(tuserinfo.getPassword())) {
					tuserinfo.setPassword(PasswordUtil.encrypt(tuserinfo.getPassword(), tuserinfo.getPhone()));
				} else {
					// 设置默认密码
					tuserinfo.setPassword(PasswordUtil.encrypt("123456", tuserinfo.getPhone()));
				}

				tuserinfo.setParterAuditFlag("0"); // 其他用户都是待审核。
				// 当前操作的为临时锁匠，直接不用审批
				if (tuserinfo.getUserType() != null && (tuserinfo.getUserType().equals("6")))
					tuserinfo.setAuditFlag("1");
				else
					tuserinfo.setAuditFlag("0"); // 其他用户都是待审核。

				// 手机号存在,更新信息 List<Userinfo> userinfos =

				// 查询已经废弃或者逻辑删除过的
				if (!userinfos1.isEmpty()&&userinfos1.get(0).getDataState()==2) {

					tuserinfo.setId(userinfos1.size() != 0 ? userinfos1.get(0).getId() : userinfos1.get(0).getId());
					tuserinfo.setUpdateUser(manager.getId());
					tuserinfo.setDataState(1);// 状态改为可用。

					jsonObject = tuserinfoService.updateUserinfo(tuserinfo);
					return jsonObject.toString();
				}

				jsonObject = tuserinfoService.saveUserinfo(tuserinfo);

			} else {

//				List<Userinfo> userinfos1 = tuserinfoService.selectUserinfoByPhone(tuserinfo);// tuserinfoService.selectUserinfoByIdCardAll(tuserinfo);
				if (!userinfos1.isEmpty()&&!tuserinfo.getId().equals(userinfos1.get(0).getId())&&userinfos1.get(0).getDataState()==2) {

					 tuserinfoService.deleteTrueUserinfo(userinfos1.get(0));
				}
				tuserinfo.setParterAuditFlag("0"); // 其他用户都是待审核。
				Userinfo userinfo = tuserinfoService.selectUserinfoById(tuserinfo.getId());
				// 不为空，为空则不修改， 如果前端密码和已有密码不一致，证明密码有修改，重新加盐。
				if (!tuserinfo.getPassword().equals("") && !tuserinfo.getPassword().equals(userinfo.getPassword())) {
					tuserinfo.setPassword(PasswordUtil.encrypt(tuserinfo.getPassword(), tuserinfo.getPhone()));
				} else {
					// tuserinfo.setPassword(null);
				}
				tuserinfo.setUpdateUser(manager.getId());

				jsonObject = tuserinfoService.updateUserinfo(tuserinfo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selecttuserinfo")
	@ResponseBody
	public String selecttuserinfo(Userinfo item, HttpServletRequest request) {
		HashMap m = (HashMap) request.getAttribute("principal");
		Manager manager = (Manager) m.get("user");
		if (manager.getType() == 1) {
			item.setCompanyId(manager.getCompanyId());
		}
		item.setAuditFlag("1");
		return JSON.toJSONString(tuserinfoService.selectUserinfoList(item));
	}

	@RequestMapping(value = "check")
	@ResponseBody
	public boolean check(Userinfo userinfo) throws Exception {
		// 通过电话查询
		List<Userinfo> list = tuserinfoService.selectUserinfoByPhone(userinfo);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "checkId")
	@ResponseBody
	public boolean checkId(Userinfo userinfo) throws Exception {
		// 通过电话查询
		List<Userinfo> list = tuserinfoService.selectUserinfoByIdCard(userinfo);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}