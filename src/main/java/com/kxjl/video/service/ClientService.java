package com.kxjl.video.service;

import com.kxjl.video.pojo.ClientInfo;

/**
 * 短信验证码测试相关通用接口
 * 
 * @author zj
 *
 */
public interface ClientService {

	/**
	 * 检测用户名  密码的有效性
	 * @param username
	 * @param password
	 * @return
	 */
	int CheckUserInfo(String username, String password);

	/**
	 * 更新该用户已使用短信数
	 * @param username
	 * @param password
	 */
	void UpdateUseNumber(String username, String password);
	
	/**
	 * 判断连接用户有没有实名认证
	 * @param clientInfo
	 */
	ClientInfo CheckClientInfo(ClientInfo clientInfo);

	/**
	 * 设置报警者实名信息
	 * @param clientInfo
	 * @return
	 */
	int SetAlarmUserInfo(ClientInfo clientInfo);

	
	
	
}
