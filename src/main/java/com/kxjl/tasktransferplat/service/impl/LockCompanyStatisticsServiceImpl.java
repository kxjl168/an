package com.kxjl.tasktransferplat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kxjl.base.util.AliSMS.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.LockCompanyStatisticsMapper;
import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockCPStatistics;
import com.kxjl.tasktransferplat.pojo.MouthOrderStatistics;
import com.kxjl.tasktransferplat.service.LockCompanyStatisticsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LockCompanyStatisticsServiceImpl implements LockCompanyStatisticsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private LockCompanyStatisticsMapper itemMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LockCMStistics> selectLockProductInfoList(LockCMStistics query) {

        List<LockCMStistics> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectMouthList(query);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(com.kxjl.base.util.ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LockCMStistics> selectLockOrderList(LockCMStistics query) {

        List<LockCMStistics> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectOrderList(query);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(com.kxjl.base.util.ExceptionUntil.getMessage(e));
        }
        return itemList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MouthOrderStatistics> selectMouthOrderList(LockCMStistics query) {

        List<MouthOrderStatistics> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectYearList(query);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(com.kxjl.base.util.ExceptionUntil.getMessage(e));
        }

        return itemList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LockCPStatistics> selectProvinceOrderList(LockCMStistics query) {

        List<LockCPStatistics> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectProvinceList(query);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(com.kxjl.base.util.ExceptionUntil.getMessage(e));
        }

        return itemList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LockCPStatistics> selectTurnoverOrderList(LockCMStistics query) {

        List<LockCPStatistics> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectTurnoverList(query);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(com.kxjl.base.util.ExceptionUntil.getMessage(e));
        }

        return itemList;
    }
}
