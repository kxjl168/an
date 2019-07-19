
package com.kxjl.video.util;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.kxjl.base.pojo.Manager;


/**
 * 接口token验证 TokenUtil.java.
 * 
 * @author zj
 * @version 1.0.1 2019年1月24日
 * @revision zj 2019年1月24日
 * @since 1.0.1
 */
public class TokenUtil {


	//管理员 - 锁企
	private static ThreadLocal<Manager> currentManager = new ThreadLocal<>();

	private String token;

	//管理员token
	private String mtoken;

	

	public static Manager getCurrentManager() {
		return currentManager.get();
	}

	public static void setCurrentManager(Manager manager) {
		currentManager.set(manager);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMtoken() {
		return mtoken;
	}

	public void setMtoken(String mtoken) {
		this.mtoken = mtoken;
	}

	/**
	 * 获取web端当前登陆用户
	 * @return
	 * @author zj
	 * @date 2019年1月31日
	 */
	public static Manager getWebLoginUser() {
		Subject currentUser = SecurityUtils.getSubject();
		Manager user = null;
		if (currentUser != null && currentUser.isAuthenticated()) {
			Map principal = (Map) currentUser.getPrincipal();
			user = (Manager) principal.get("user");
		}
		return user;

	}

}
