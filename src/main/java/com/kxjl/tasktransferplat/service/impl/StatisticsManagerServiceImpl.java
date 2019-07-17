package com.kxjl.tasktransferplat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.StatisticsManagerMapper;
import com.kxjl.tasktransferplat.pojo.CustomerService;
import com.kxjl.tasktransferplat.service.StatisticsManagerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsManagerServiceImpl implements StatisticsManagerService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticsManagerMapper statisticsManagerMapper;

    @Override
    public List<CustomerService> selectCustomerServiceList(CustomerService query) {

        List<CustomerService> list = new ArrayList<>();

        try {

            list = statisticsManagerMapper.selectCustomerServiceList(query);
        }catch (Exception e){

            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }

        CustomerService totalItem = new CustomerService();

        for (CustomerService item : list){

            totalItem.setReceiveCount(String.valueOf(Integer.valueOf(item.getReceiveCount())+Integer.valueOf(totalItem.getReceiveCount())));
            totalItem.setDoneCount(String.valueOf(Integer.valueOf(item.getDoneCount())+Integer.valueOf(totalItem.getDoneCount())));
            totalItem.setReceiveTimeOutCount(String.valueOf(Integer.valueOf(item.getReceiveTimeOutCount())+Integer.valueOf(totalItem.getReceiveTimeOutCount())));
            totalItem.setDespatchTimeOutCount(String.valueOf(Integer.valueOf(item.getDespatchTimeOutCount())+Integer.valueOf(totalItem.getDespatchTimeOutCount())));
            totalItem.setInquireCount(String.valueOf(Integer.valueOf(item.getInquireCount())+Integer.valueOf(totalItem.getInquireCount())));
            totalItem.setInquireTimeOutCount(String.valueOf(Integer.valueOf(item.getInquireTimeOutCount())+Integer.valueOf(totalItem.getInquireTimeOutCount())));
            totalItem.setEnComplainCount(String.valueOf(Integer.valueOf(item.getEnComplainCount())+Integer.valueOf(totalItem.getEnComplainCount())));
            totalItem.setCuComplainCount(String.valueOf(Integer.valueOf(item.getCuComplainCount())+Integer.valueOf(totalItem.getCuComplainCount())));
        }

        totalItem.setCreatTime("合计");

        list.add(totalItem);

        return list;
    }
}
