/**
 * @(#)UserinfoMoneyDetailServiceImpl.java  2019-01-28 11:26:26
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.dao.ManagerMapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMoneyDetailMapper;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail;
import com.kxjl.tasktransferplat.service.UserinfoMoneyDetailService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:26
 * @since 1.0.0
 */
@Service
public class UserinfoMoneyDetailServiceImpl implements UserinfoMoneyDetailService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserinfoMoneyDetailMapper userinfoMoneyDetailMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public IPage<Map<String,Object>> listDriver(Page page) {
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        IPage<Map<String,Object>> userinfoMoneyDetails= null;
        try {
            userinfoMoneyDetails = userinfoMoneyDetailMapper.selectDriverList(page,userId);
        } catch (Exception e) {
            throw new RuntimeException("查询司机账单记录失败，失败用户Id：" + userId, e);
        }
        // 返回
        return userinfoMoneyDetails;
    }

    @Override
    public List<UserinfoMoneyDetail> selectMoneyList(Userinfo user) {
        List<UserinfoMoneyDetail> itemList = new ArrayList<>();
        try {
          /*  QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("UserinfoId", id);
            wrapper.orderByDesc("operateTime");*/
            
            //itemList = userinfoMoneyDetailMapper.selectList(wrapper);
            
        	itemList=userinfoMoneyDetailMapper.selectDetailList(user);
            
          /*  for (UserinfoMoneyDetail userinfoMoneyDetail:itemList) {
                userinfoMoneyDetail.setOperateUserId(managerMapper.selectByPrimaryKey(userinfoMoneyDetail.getOperateUserId()).getNickname());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }
}
