package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.CustomerService;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

import java.util.List;

public interface StatisticsManagerMapper extends BaseMapper<Orderinfo> {

    List<CustomerService> selectCustomerServiceList(CustomerService item);
}
