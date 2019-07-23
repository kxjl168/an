package com.kxjl.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.DictInfo;

public interface OnlineSeatsDao {

	int insertAlarmInfo(AlarmInfo alarmInfo);

	int CheckUserInfo(@Param(value="username")String username, @Param(value="password")String password);

	int SetOnlineStatus(@Param(value="userid")String userid, @Param(value="status")String status);

	String getAreaByOnlineSeatsUserID(@Param(value="userid")String userid);

	String getUserNameByUserId(@Param(value="userid")String userID);

	List<AlarmInfo> getAlarmListByUserId(@Param(value="userid")String userID);

	List<AlarmInfo> getClientAlarmListByUserId(@Param(value="userid")String userID);

	boolean CheckOnlineSeatsUserId(@Param(value="userid")String userid);

	List<DictInfo> QueryDictInfoListByType(@Param(value="type")int type);

	String GetFreeOnlineSeats();

	void updateOnlineSteatsToNull(@Param(value="onlineseats_id")String onlineseats_id);

	Integer QueryHasNewInfo(@Param(value="userid")String userid);
	
}
