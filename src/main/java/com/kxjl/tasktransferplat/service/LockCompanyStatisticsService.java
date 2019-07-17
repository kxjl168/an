package com.kxjl.tasktransferplat.service;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockCPStatistics;
import com.kxjl.tasktransferplat.pojo.MouthOrderStatistics;

public interface LockCompanyStatisticsService {

    List<LockCMStistics> selectLockProductInfoList(LockCMStistics query);

    List<LockCMStistics> selectLockOrderList(LockCMStistics query);

    List<MouthOrderStatistics> selectMouthOrderList(LockCMStistics query);

    List<LockCPStatistics> selectProvinceOrderList(LockCMStistics query);

    List<LockCPStatistics> selectTurnoverOrderList(LockCMStistics query);
}
