/**
 * @(#)LockOrderCountServiceImpl.java 2019/5/24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.dao.plus.LockOrderCountMapper;
import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockMStatistics;
import com.kxjl.tasktransferplat.service.LockOrderCountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/24
 */
@Service
@Slf4j
public class LockOrderCountServiceImpl implements LockOrderCountService {

    @Autowired
    private LockOrderCountMapper lockOrderCountMapper;

    @Override
    public List<Map> selectLockOrderBadRatio(String createTime){
        List<Map> maps1 = new ArrayList<>();
        try {
            maps1 = lockOrderCountMapper.selectLockOrderBadRatio(createTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询锁匠月工单不良率统计出错");
        }
        return maps1;
    }

    @Override
    public List<LockMStatistics> selectLockMouthStatistics(LockCMStistics item){
        List<LockMStatistics> data = new ArrayList<>();
        try {
            data = lockOrderCountMapper.selectLockMouthStatisticsList(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询锁匠月工单统计出错");
        }
        return data;
    }
}
