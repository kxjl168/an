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

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private static String validateCodeKey = "validateCode";

    public static final String shiroLoginFailureKey = "shiroLoginFailure";

	
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
				
				// 用户填写的验证码
				String userValidateCode = request.getParameter(validateCodeKey);

				// 用户为填写验证码不进行身份验证
				if (null == userValidateCode || "".equals(userValidateCode.trim())) {
					request.setAttribute("code", ZtgmErrorCode.ValideCodeError.code);
					request.setAttribute("error", ZtgmErrorCode.ValideCodeError.msg);
					str = "/frontend/login/login.ftl";
					return new ModelAndView(str);
				}
				String crctValidateCode = (String) session.getAttribute(validateCodeKey);

				// 验证用户填写验证码是否正确
				if (userValidateCode != null && crctValidateCode != null && !userValidateCode.equals(crctValidateCode)) {
					request.setAttribute("code", ZtgmErrorCode.ValideCodeError.code);
					request.setAttribute("error", ZtgmErrorCode.ValideCodeError.msg);
					str = "/frontend/login/login.ftl";
					return new ModelAndView(str);
				}
				
				
				
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
	
	/**
	 * 图片验证码
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int width = 60;
		int height = 32;
		// create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		String hash1 = Integer.toHexString(rdm.nextInt());
		// System.out.print(hash1);
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String capstr = hash1.substring(0, 4);
		// 将生成的验证码存入session
		request.getSession().setAttribute("validateCode", capstr);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(capstr, 8, 24);
		g.dispose();
		// 输出图片
		response.setContentType("image/jpeg");

		OutputStream strm = response.getOutputStream();
		ImageIO.write(image, "jpeg", strm);
		strm.close();
	}



}
