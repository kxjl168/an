package com.kxjl.base.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kxjl.base.pojo.Manager;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.util.PasswordUtil;
import com.kxjl.base.util.SpringUtils;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.UserinfoService;

/**
 * 密码比较器
 * 
 * @author Administrator
 */

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Autowired
	ManagerService userinfoService;

	/**
	 * 自己来编写密码比较方法
	 * 
	 * token 从JSP页面传过来的用户名和密码 info 从数据库中查询到密码
	 */
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		// 获取到JSP页面的用户名和密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 提供了方法
		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());
		// password没有加密
		// 对象password加密
		// String formPwd =password;//页面已经计算md5
		String formPwd = PasswordUtil.encrypt(password, username);

		// 获取到数据库中的密码
		String dbPwd = (String) info.getCredentials();

		// 密码加密 ,兼容原密码
		if (formPwd.equals(dbPwd) || password.equals(dbPwd)) {

			userinfoService=(ManagerService)SpringUtils.getBean("managerService",ManagerService.class);
			
			// 更新pass，加密存储
			if (dbPwd.length() < 8) {

				Manager query = new Manager();
				query.setKey(username);

				Manager user = userinfoService.getLoginUser(query);

				Manager m=new Manager();
				m.setId(user.getId());
				m.setPassword(PasswordUtil.encrypt(password, username));
				
				userinfoService.updateByPrimaryKeySelective(m);
			}

			return true;

		}
		return false;

	}

}
