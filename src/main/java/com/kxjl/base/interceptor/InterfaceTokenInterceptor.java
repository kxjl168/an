package com.kxjl.base.interceptor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.kxjl.base.aopAspect.Authorization;
import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.base.SysConst;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.service.ManagerService;
import com.kxjl.video.util.TokenUtil;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InterfaceTokenInterceptor implements HandlerInterceptor {

	private static Logger logger = LogManager.getLogger(InterfaceTokenInterceptor.class);

	//@Autowired
	//private UserinfoService userService;

	@Autowired
	private ManagerService managerService;


	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getServletContext());
		// UserServiceImpl userService = (UserServiceImpl) ctx.getBean("userService");

		String token ="";
		String token5 ="";//管理员token
		String token1=request.getParameter("k");
		String token2 = request.getHeader("k");
		String token3 = request.getHeader("token");
		String token4 = request.getHeader(SysConst.AUTHORIZATION);
		token5 = request.getHeader("mToken");//管理员token

		if(token1!=null&&!token1.equals(""))
			token=token1;
		if(token2!=null&&!token2.equals(""))
			token=token2;
		if(token3!=null&&!token3.equals(""))
			token=token3;
		if(token4!=null&&!token4.equals(""))
			token=token4;
		
				

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		logger.debug("url：" + request.getRequestURI());

		//logger.debug("Token：" + token);
		//Userinfo user = userService.getUserByToken(token);

		//管理员登入
		if( token5 != null && !token5.equals("")) {
			logger.debug("url：" + request.getRequestURI());
			logger.debug("mToken：" + token5);
		}
		Manager manager = managerService.getLoginUserByToken(token5);


		/*if (user != null) {
			TokenUtil.setCurrentUser(user);
			return true;
		} else
		*/
		 if(manager != null) {
			TokenUtil.setCurrentManager(manager);
			return true;
		}else {
			if (method.getAnnotation(NoNeedAuthorization.class) != null) {
				// 如果验证token失败，但是方法注明了NoNeedAuthorization，正常请求
				// response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return true;
			} else if (method.getAnnotation(Authorization.class) != null) {

				response.getWriter().append("{\"errCode\":\"01\",\"errMsg\":\"no this user\",\"data\":{}}");
				// 如果验证token失败，并且方法注明了Authorization，返回401错误
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}

		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}
