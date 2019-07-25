package com.kxjl.video.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.video.dao.ClientDao;
import com.kxjl.video.pojo.ClientInfo;
import com.kxjl.video.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private Logger logger = Logger.getLogger(ClientServiceImpl.class);
	
	@Autowired
	ClientDao clientDao;

	@Override
	public int CheckUserInfo(String username, String password) {
		return 0;
	}

	@Override
	public void UpdateUseNumber(String username, String password) {
		
	}

	@Override
	public ClientInfo CheckClientInfo(ClientInfo clientInfo) {
		return clientDao.CheckClientInfo(clientInfo);
	}

	@Override
	public int SetAlarmUserInfo(ClientInfo clientInfo) {
		ClientInfo client = CheckClientInfo(clientInfo);
		if(client != null) {
			clientInfo.setId(client.getId());
			return clientDao.updateAlarmUserInfo(clientInfo);
		}else {
			return clientDao.insertAlarmUserInfo(clientInfo);
		}
		
	}
	
	

}
