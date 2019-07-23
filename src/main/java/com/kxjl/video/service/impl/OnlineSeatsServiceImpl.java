package com.kxjl.video.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.video.dao.OnlineSeatsDao;
import com.kxjl.video.pojo.AlarmInfo;
import com.kxjl.video.pojo.DictInfo;
import com.kxjl.video.service.OnlineSeatsService;

@Service
public class OnlineSeatsServiceImpl implements OnlineSeatsService {
	
	@Autowired
	OnlineSeatsDao onlineSeatsDao;

	@Override
	public int CheckUserInfo(String username, String password) {
		return onlineSeatsDao.CheckUserInfo(username, password);
	}

	@Override
	public void UpdateUseNumber(String username, String password) {
		
	}

	@Override
	public boolean CheckOnlineSeatsUserId(String userid) {
		String username = getUserNameByUserId(userid);
		if(username == null || username.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public int insertAlarmInfo(AlarmInfo alarmInfo) {
		return onlineSeatsDao.insertAlarmInfo(alarmInfo);
	}

	@Override
	public int SetOnlineStatus(String userid, String status) {
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
	

}
