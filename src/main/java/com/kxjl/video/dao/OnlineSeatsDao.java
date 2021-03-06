package com.kxjl.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.DictInfo;
import com.kxjl.video.pojo.VideoAlarmErrorInfo;

public interface OnlineSeatsDao {

	int insertAlarmInfo(AlarmInfo alarmInfo);

	String CheckUserInfo(@Param(value="username")String username, @Param(value="password")String password);

	String GetUserSeatsId(@Param(value="username")String username, @Param(value="password")String password);

	int SetOnlineStatus(@Param(value="userid")String userid, @Param(value="status")String status);

	void SetOnlineStatusOnline(@Param(value="userid")String userId);

	String getAreaByOnlineSeatsUserID(@Param(value="userid")String userid);

	String getUserNameByUserId(@Param(value="userid")String userID);

	String getUserNameByUserIdThroughOnSeats(@Param(value="userid")String userid);

	List<AlarmInfo> getAlarmListByUserId(@Param(value="userid")String userID);

	List<AlarmInfo> getClientAlarmListByUserId(@Param(value="userid")String userID);

	boolean CheckOnlineSeatsUserId(@Param(value="userid")String userid);

	List<DictInfo> QueryDictInfoListByType(@Param(value="type")int type);

	String GetFreeOnlineSeats();

	void updateOnlineSteatsToNull(@Param(value="onlineseats_id")String onlineseats_id);

	Integer QueryHasNewInfo(@Param(value="userid")String userid);

	String getReceiveIdByOnSeatsID(@Param(value="seatId")String onlineSeatsId);

	void insertLoginInfo(@Param(value="userid")String userId, @Param(value="onlineseats_id")String seatsId);

	String getReceiveIdByOnSeatsIDFromLogInfo(@Param(value="seatId")String onlineSeatsId);

	void updateLogoutTime(@Param(value="seatId")String seatId);

	void insertAlarmInfoError(VideoAlarmErrorInfo video);
	
}
