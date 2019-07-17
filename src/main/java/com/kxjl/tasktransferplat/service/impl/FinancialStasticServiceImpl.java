/**
 * @(#)FinancialStasticServiceImpl.java 2019-04-22 11:16:28
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.FinancialStasticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 财报统计
 * FinancialStasticServiceImpl.java.
 *
 * @author zhangyong
 * @version 1.0.1 2019年4月22日
 * @revision zhangyong 2019年4月22日
 * @since 1.0.1
 */
@Service
public class FinancialStasticServiceImpl implements FinancialStasticService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderinfoMapper orderinfoMapper;

    /**
     * 工单状态统计
     * @return
     * @author zj
     * @date 2019年2月12日
     */
    @Override
    public List<Map> selectPartnerFinancialStatistics(Orderinfo query) {
        try {
            List<Map> dataList = orderinfoMapper.selectPartnerFinancialStasticList(query);
            return dataList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> selectNoPartnerFinancialStatistics(Orderinfo item) {
        try {
            List<Map> dataList = orderinfoMapper.selectNoPartnerFinancialStasticList(item);
            return dataList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> selectLockCompanyFinancialStatistics(Orderinfo item) {
		List<Map> itemList = new ArrayList<>();
		try {
			itemList = orderinfoMapper.selectLockCompanyFinancialStatisticsList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
    }
}
