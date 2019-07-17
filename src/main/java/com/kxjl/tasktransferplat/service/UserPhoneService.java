package com.kxjl.tasktransferplat.service;

import com.kxjl.tasktransferplat.pojo.UserPhoneLog;

public interface UserPhoneService {

    /**
     * 电话记录
     * @param userPhoneLog
     */
    int recordPhoneLog(UserPhoneLog userPhoneLog);
}
