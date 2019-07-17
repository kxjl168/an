package com.kxjl.tasktransferplat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.dao.plus.UserPhoneMapper;
import com.kxjl.tasktransferplat.pojo.UserPhoneLog;
import com.kxjl.tasktransferplat.service.UserPhoneService;

@Service
public class UserPhoneServiceImpl implements UserPhoneService{
    @Autowired
    private UserPhoneMapper userPhoneMapper;

    @Override
    public int recordPhoneLog(UserPhoneLog userPhoneLog) {
        return userPhoneMapper.recordPhoneLog(userPhoneLog);
    }
}
