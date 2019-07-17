/**
 * @(#)PriceLocksmithOtherServiceImpl.java 2019-04-16 16:16:14
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.plus.PriceLocksmithOtherMapper;
import com.kxjl.tasktransferplat.pojo.PriceLocksmithOther;
import com.kxjl.tasktransferplat.service.PriceLocksmithOtherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @version 1.0.0 2019-04-16 16:16:14
 * @since 1.0.0
 */
@Service
public class PriceLocksmithOtherServiceImpl implements PriceLocksmithOtherService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PriceLocksmithOtherMapper priceLocksmithOtherMapper;


    @Override
    public PriceLocksmithOther selectRule(String serverType, int parentType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("serverType", serverType);
        queryWrapper.eq("parentType", parentType);
        return priceLocksmithOtherMapper.selectOne(queryWrapper);
    }

    @Override
    public Message deleteByPrimaryKey(Long id) {
        Message message = new Message();
        try {
            int result = priceLocksmithOtherMapper.deleteByPrimaryKey(id);
            message.setBol(result == 1 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("删除出错");
        }

        return message;
    }

    @Override
    public Message insert(PriceLocksmithOther priceLocksmithOther) {
        Message message = new Message();
        try {
            int result = priceLocksmithOtherMapper.insert(priceLocksmithOther);
            message.setBol(result == 1 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增出错");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("新增失败");
        }
        return message;
    }

    @Override
    public PriceLocksmithOther selectByPrimaryKey(Long id) {
        PriceLocksmithOther priceLocksmithOther = null;
        try {
            priceLocksmithOther = priceLocksmithOtherMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败");
            log.error(ExceptionUntil.getMessage(e));
        }
        return priceLocksmithOther;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message updateByPrimaryKey(PriceLocksmithOther priceLocksmithOther) {
        Message message = new Message();
        try {
            int result = priceLocksmithOtherMapper.updateByPrimaryKey(priceLocksmithOther);
            message.setBol(result == 1 ? true : false);
            message.setMessage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新失败");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("更新失败");
        }
        return message;
    }

    @Override
    public List<PriceLocksmithOther> selectList(PriceLocksmithOther priceLocksmithOther) {
        List<PriceLocksmithOther> priceLocksmithOtherList = new ArrayList<>();
        try {
            priceLocksmithOtherList = priceLocksmithOtherMapper.selectList(priceLocksmithOther);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return priceLocksmithOtherList;
    }

    @Override
    public PriceLocksmithOther selectByType(String serverType,Integer parentType){
        PriceLocksmithOther priceLocksmithOther = null;
        try {
            priceLocksmithOther = priceLocksmithOtherMapper.selectByType(serverType,parentType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return priceLocksmithOther;
    }
}
