/*
 * @(#)PriceLockSmithBuildServiceImpl.java
 * @author: zhangyong
 * @Date: 2019年4月17日
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.plus.PriceLockSmithBuildMapper;
import com.kxjl.tasktransferplat.pojo.PriceLockSmithBuild;
import com.kxjl.tasktransferplat.service.PriceLockSmithBuildService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import java.util.List;

/**
 * 锁匠区域价格
 *
 * @author zhangyong
 * @version 1.0.1 2019年4月17日
 * @revision zhangyong 2019年4月15日
 * @since 1.0.1
 */
@Service
public class PriceLockSmithBuildServiceImpl implements PriceLockSmithBuildService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PriceLockSmithBuildMapper priceLockSmithBuildMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message insert(PriceLockSmithBuild priceLockSmithBuild) {
        Message message = new Message();
        try {
            priceLockSmithBuild.setId(snowflakeIdWorker.nextId());
            priceLockSmithBuildMapper.insert(priceLockSmithBuild);
            message.setBol(true);
            return message;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败", e);
            log.error(ExceptionUntil.getMessage(e));
            message.setMessage("新增失败");
            message.setBol(false);
            return message;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message deleteById(Long id) {
        Message message = new Message();
        try {
            int result = priceLockSmithBuildMapper.deleteById(id);
            message.setBol(result == 1 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
            message.setBol(false);
            message.setMessage("删除失败");
        }
        return message;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message updateById(PriceLockSmithBuild priceLockSmithBuild) {
        Message message = new Message();
        try {
            priceLockSmithBuildMapper.updateById(priceLockSmithBuild);
            message.setBol(true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            message.setBol(false);
            message.setMessage("更新出错");
        }
        return message;
    }

    @Override
    public PriceLockSmithBuild selectById(Long id) {
        PriceLockSmithBuild priceLockSmithBuild = null;
        try {
            priceLockSmithBuild = priceLockSmithBuildMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return priceLockSmithBuild;
    }

    @Override
    public List<PriceLockSmithBuild> selectByProvinceCode(String provinceCode) {
        List<PriceLockSmithBuild> priceLockSmithBuildList = null;
        try {
            priceLockSmithBuildList = priceLockSmithBuildMapper.selectByProvinceCode(provinceCode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return priceLockSmithBuildList;
    }

    @Override
    public PriceLockSmithBuild selectByCityCode(String cityCode, String provinceCode) {
        PriceLockSmithBuild priceLockSmithBuild = null;
        try {
            priceLockSmithBuild = priceLockSmithBuildMapper.selectByCityCode(cityCode, provinceCode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return priceLockSmithBuild;
    }

    @Override
    public List<PriceLockSmithBuild> selectList(PriceLockSmithBuild priceLockSmithBuild) {
        List<PriceLockSmithBuild> priceLockSmithBuildList = null;
        try {
            priceLockSmithBuildList = priceLockSmithBuildMapper.selectList(priceLockSmithBuild);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return priceLockSmithBuildList;
    }

}
