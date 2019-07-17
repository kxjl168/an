/**
 * @(#)GradeMoneyServiceImpl.java 2019/5/21
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.GradeMoneyMapper;
import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.GradeMoney;
import com.kxjl.tasktransferplat.service.GradeMoneyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/21
 */
@Service
@Slf4j
public class GradeMoneyServiceImpl implements GradeMoneyService{

    @Autowired
    private GradeMoneyMapper gradeMoneyMapper;

    @Override
    public List<GradeMoney> selectGradeMoneyList(GradeMoney gradeMoney) {
        List<GradeMoney> itemList = new ArrayList<>();
        try {
            itemList = gradeMoneyMapper.selectList(gradeMoney);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询评分奖励列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Override
    @Transactional
    public Map delete(String id ) {
        Map result = new HashMap<>();
        try {
            gradeMoneyMapper.deleteById(id);
            result.put("result",true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除单条评分奖励出错");
            log.error(ExceptionUntil.getMessage(e));
            result.put("result",false);
        }
        return result;
    }

    @Override
    public GradeMoney selectOneByScore(String score){
        try {
            GradeMoney gradeMoney = gradeMoneyMapper.selectOneByScore(score);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询单条评分奖励出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        GradeMoney gradeMoney = gradeMoneyMapper.selectOneByScore(score);
        return gradeMoney;
    }

    @Override
    @Transactional
    public Map addGradeMoney(GradeMoney gradeMoney){
        Map result = new HashMap<>();
        try {
            gradeMoney.setId(UUIDUtil.getUUID());
            gradeMoneyMapper.insert(gradeMoney);
            result.put("result",true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加单条评分奖励出错");
            log.error(ExceptionUntil.getMessage(e));
            result.put("result",false);
        }
        return result;
    }

    @Override
    @Transactional
    public Map changeGradeMoney(GradeMoney gradeMoney){
        Map result = new HashMap<>();
        try {
            gradeMoneyMapper.updateById(gradeMoney);
            result.put("result",true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改单条评分奖励出错");
            log.error(ExceptionUntil.getMessage(e));
            result.put("result",false);
        }
        return result;
    }

}
