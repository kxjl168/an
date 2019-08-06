package com.kxjl.video.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.video.dao.OnlineSeatsDao;
import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.ClientInfo;
import com.kxjl.video.pojo.DictInfo;
import com.kxjl.video.pojo.VideoAlarmErrorInfo;
import com.kxjl.video.service.OnlineSeatsService;

@Service
public class OnlineSeatsServiceImpl implements OnlineSeatsService {
	
	@Autowired
	OnlineSeatsDao onlineSeatsDao;

	@Override
	public String CheckUserInfo(String username, String password) {
		//获取坐席ID
		String userId = onlineSeatsDao.CheckUserInfo(username, password);
		if(userId != null) {
			String seatsId = onlineSeatsDao.GetUserSeatsId(username, password);
			onlineSeatsDao.SetOnlineStatusOnline(userId);

			onlineSeatsDao.insertLoginInfo(userId,seatsId);
			return seatsId;
		}
		//更新状态
		return userId;
	}

	@Override
	public void UpdateUseNumber(String username, String password) {
		
	}

	@Override
	public boolean CheckOnlineSeatsUserId(String userid) {
		String username = getUserNameByUserIdThroughOnSeats(userid);
		if(username == null || username.isEmpty()) {
			return false;
		}
		return true;
	}

	private String getUserNameByUserIdThroughOnSeats(String userid) {
		return onlineSeatsDao.getUserNameByUserIdThroughOnSeats(userid);
	}

	@Override
	public int insertAlarmInfo(AlarmInfo alarmInfo) {
		return onlineSeatsDao.insertAlarmInfo(alarmInfo);
	}

	@Override
	public int SetOnlineStatus(String userid, String status) {
		if(status.equals("3")) {
			onlineSeatsDao.updateLogoutTime(userid);
		}
		return onlineSeatsDao.SetOnlineStatus(userid, status);
	}

	@Override
	public String getAreaByOnlineSeatsUserID(String userid) {
		return onlineSeatsDao.getAreaByOnlineSeatsUserID(userid);
	}

	@Override
	public List<AlarmInfo> getAlarmListByUserId(String userID) {
		return onlineSeatsDao.getAlarmListByUserId(userID);
	}

	@Override
	public String getUserNameByUserId(String userID) {
		return onlineSeatsDao.getUserNameByUserId(userID);
	}

	@Override
	public List<AlarmInfo> getClientAlarmListByUserId(String userID) {
		return onlineSeatsDao.getClientAlarmListByUserId(userID);
	}

	@Override
	public List<DictInfo> QueryDictInfoListByType(int type) {
		return onlineSeatsDao.QueryDictInfoListByType(type);
	}

	@Override
	public String GetFreeOnlineSeats() {
		return onlineSeatsDao.GetFreeOnlineSeats();
	}

	@Override
	public void updateOnlineSteatsToNull(String userid) {
		onlineSeatsDao.updateOnlineSteatsToNull(userid);		
	}

	@Override
	public Integer QueryHasNewInfo(String userid) {
		return onlineSeatsDao.QueryHasNewInfo(userid);
	}

	@Override
	public String getReceiveIdByOnSeatsID(String onlineSeatsId) {
		String userid = onlineSeatsDao.getReceiveIdByOnSeatsID(onlineSeatsId);
		if(userid == null) {
			userid = onlineSeatsDao.getReceiveIdByOnSeatsIDFromLogInfo(onlineSeatsId);
		}
		return userid;
	}

	@Override
	public void insertAlarmInfoError(int type, ClientInfo clientInfo, String onseatsId) {
		VideoAlarmErrorInfo video = new VideoAlarmErrorInfo();
		video.setType(type);
		if(type == 2) {
			video.setOnlineseats_id(getReceiveIdByOnSeatsID(onseatsId));
		}
		video.setUserName(clientInfo.getName());
		video.setIdNumber(clientInfo.getIdentyID());
		video.setArea(clientInfo.getArea());
		video.setLatitude(clientInfo.getLatitude());
		video.setLongitude(clientInfo.getLongitude());
		video.setAddress(clientInfo.getNote());
		video.setPhone(clientInfo.getMobilePhone());
		video.setWechat_id(clientInfo.getWeichatID());
		onlineSeatsDao.insertAlarmInfoError(video);
	}
	

}
