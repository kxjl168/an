/**
 * @(#)MobileUserController.java 2019-01-24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.controller.Common.UploadFileController;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.PasswordUtil;
import com.kxjl.base.util.sendpost.HttpSendPost;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAdd;
import com.kxjl.tasktransferplat.service.UserinfoAddService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.kxjl.base.shiro.MyShiroRealm.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 锁企登录
 * 
 * @author shurui
 * @version 1.0.1 2019/4/23
 * @since 1.0.1
 */
@Controller
@RequestMapping("/interface/LCompany")
@PropertySource("classpath:project.properties")
public class MobileLockCompanyLoginController extends AppBaseController {

/*	@Value("${HTTP_PATH}")
	private String FILE_SVR_HTTP_OUTER_PATH;

	@Value("${FILE_SVR_PATH}")
	private String FILE_SVR_PATH;

	@Value("${login.wxappid}")
	private String appid;
	
	@Value("${login.wxsecret}")
	private String secret;*/

	@Autowired
	private ManagerService managerService;

/*	@Autowired
	UploadFileController uploadController;*/

/*	@Autowired
	private UserinfoAddService userinfoAddService;*/

	private Logger logger = Logger.getLogger(MobileLockCompanyLoginController.class);


	/**
	 * 锁企用户登录接口
	 *
	 *
	 * @return
	 * @author shurui
	 */
	@RequestMapping("/login")
	@ResponseBody
	@NoNeedAuthorization
	public AppResult mobileLogin(HttpServletRequest request) {

		/*
		 * phone true string 收取短信验证码的手机号 pwd false string 密码登录时用的，登录密码，md5加密过的 wxId
		 * false string 微信登录时用的，微信id type true string 登录类型：1:密码登录，2:微信登录 location true
		 * string 经纬度例如：“32.234232，118.237434”
		 */
		String phone = parseStringParam(request, "phone");
		String pwd = parseStringParam(request, "pwd");
//			String wxId = parseStringParam(request, "wxId");	//暂无微信登录
//			String type = parseStringParam(request, "type");	//无登录类别

		//判断入参
		if(phone == null || phone.equals("")) {
			return AppResultUtil.fail("手机号为空");
		}else if(pwd == null || pwd.equals("")){
			return AppResultUtil.fail("密码为空");
		}

		try {
			//封装查询用户信息
			Manager query = new Manager();
			query.setType(2);	//指定锁企用户
			query.setKey(phone);	//电话
			//查询用户
			Manager loginUser = managerService.getLoginUser(query);

			//判断用户是否存在，密码是否正确
			if(loginUser == null) {
				return AppResultUtil.fail("用户不存在或输入手机号有误");
			}else {
				if(! loginUser.getPassword().equals(PasswordUtil.encrypt(pwd,phone))){
					return AppResultUtil.fail("用户账号密码错误");
				}else {
					//登入成功，更新TOKEN并返回出去
					String usertoken = UUID.randomUUID().toString();
					loginUser.setToken(usertoken);
					// 更新用户toke
					managerService.updateByPrimaryKey(loginUser);

					//封装结果集
					HashMap mp = new HashMap<>();
					mp.put("mToken", usertoken);//mToken表示管理员token（manage表），区别于锁匠token
					// 返回数据
					return AppResultUtil.success(mp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(phone + "用户登录失败");
			return AppResultUtil.fail("用户登录失败");
		}

	}


}
