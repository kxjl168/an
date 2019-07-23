package com.kxjl.video.dao;

import com.kxjl.video.pojo.ClientInfo;

public interface ClientDao {

	ClientInfo CheckClientInfo(ClientInfo clientInfo);

	int updateAlarmUserInfo(ClientInfo clientInfo);

	int insertAlarmUserInfo(ClientInfo clientInfo);
	
}
