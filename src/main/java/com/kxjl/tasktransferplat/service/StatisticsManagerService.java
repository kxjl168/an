package com.kxjl.tasktransferplat.service;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.CustomerService;

public interface StatisticsManagerService {

    List<CustomerService> selectCustomerServiceList(CustomerService query);
}
