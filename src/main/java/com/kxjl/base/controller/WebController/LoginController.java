package com.kxjl.base.controller.WebController;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.kxjl.base.base.ZtgmErrorCode;
import com.kxjl.base.pojo.Manager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 后台登陆 LoginController.java.
 * 
 * @author zj
 * @version 1.0.1 2018年11月9日
 * @revision zj 2018年11月9日
 * @since 1.0.1
 */
@Controller
@RequestMapping("/")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login.action")
	public ModelAndView signIn(ModelAndView model, Manager userinput, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();

		String str = "";

		String username = userinput.getTelephone();
		String password = userinput.getPassword();

		session.setAttribute("truepassword", password);
		try {
			if (username == null || username == "") {
				request.setAttribute("code", ZtgmErrorCode.NameOrPassNull.code);
				request.setAttribute("msg", ZtgmErrorCode.NameOrPassNull.msg);
				str = "/frontend/login/login.ftl";
			} else {
				// User userInquire = userService.queryPasswordByUsername(username);
				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				// 访问安全管理器
				subject.login(token);
				// 一定是认证通过了
				// 获取到认证通过后的user对象

				Map map = (Map) subject.getPrincipal();
				Manager user = (Manager) map.get("user");
				// 存入到session，key别瞎写，JSP页面已经写好了
				request.getSession().setAttribute("user", user);

				str = "redirect:/manager/admin/index";

				request.setAttribute("list", map.get("userMenus"));

				return new ModelAndView(str);

			}
		} catch (Exception e) {
			request.setAttribute("code", ZtgmErrorCode.NameOrPassError.code);
			request.setAttribute("msg", ZtgmErrorCode.NameOrPassError.msg);

			String exceptionClassName = e.getClass().getName();
			// 根据shiro返回的异常类路径判断，抛出指定异常信息
			if (exceptionClassName != null) {
				if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
					// 最终会抛给异常处理器
					request.setAttribute("error", ZtgmErrorCode.NoNameExist.msg);
				} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
					request.setAttribute("error", ZtgmErrorCode.NameOrPassError.msg);
				} else if ("randomCodeError".equals(exceptionClassName)) {
					request.setAttribute("error", ZtgmErrorCode.ValideCodeError.msg);
				} else {
					request.setAttribute("error", ZtgmErrorCode.UnKnowError.msg);
				}
			}

			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return new ModelAndView("/frontend/login/login.ftl");

	}

	@RequestMapping(value = "/logout")
	// @ActionLog(operateModelNm="登录访问",operateFuncNm="query",operateDescribe="访问登录页")
	public String logout(ModelAndView model, HttpServletRequest request, String username, String password) {
		// 先从HttpSession中删除掉user对象
		request.getSession().removeAttribute("user");
		// 帮助shiro框架进行会话管理操作
		Subject subject = SecurityUtils.getSubject();
		// 退出
		subject.logout();
		return "/frontend/login/login.ftl";
	}


}
