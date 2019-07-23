package com.kxjl.video.service;

import java.util.List;

import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.DictInfo;

/**
 * 短信验证码测试相关通用接口
 * 
 * @author zj
 *
 */
public interface OnlineSeatsService {

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
	 * 检测在线坐席ID的有效性
	 * @param userid
	 * @return
	 */
	boolean CheckOnlineSeatsUserId(String userid);

	int insertAlarmInfo(AlarmInfo alarmInfo);

	int SetOnlineStatus(String userid, String status);

	/**
	 * 获取在线坐席所属区域
	 * @param string
	 * @return
	 */
	String getAreaByOnlineSeatsUserID(String userid);

	List<AlarmInfo> getAlarmListByUserId(String userID);

	/**
	 * 获取在线坐席姓名
	 * @param userID
	 * @return
	 */
	String getUserNameByUserId(String userID);

	List<AlarmInfo> getClientAlarmListByUserId(String userID);

	List<DictInfo> QueryDictInfoListByType(int valueOf);

	String GetFreeOnlineSeats();

	void updateOnlineSteatsToNull(String userid);

	Integer QueryHasNewInfo(String userid);

	
	
	
}
