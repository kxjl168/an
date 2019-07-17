/**
 * @(#)OrderStasticService.java  2019-02-12 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.*;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.OrderStasticService;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单统计
 * OrderStasticService.java.
 * 
 * @author zj
* @version 1.0.1 2019年2月12日
* @revision zj 2019年2月12日
* @since 1.0.1
 */
@Service
public class OrderStasticServiceImpl implements OrderStasticService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
   
	@Autowired
	OrderinfoMapper orderinfoMapper;
	
	  /**
     * 工单状态统计
     * @return
     * @author zj
     * @date 2019年2月12日
     */
    public List<Orderinfo> selectOrderStatusStasticList(Orderinfo query){
		List<Orderinfo> orderinfos =orderinfoMapper.selectOrderStatusStasticList(query);
		for (Orderinfo orderinfo:orderinfos) {
			if (orderinfo.getType()==2){
				query.setType(2);
				orderinfo.setNum(orderinfoMapper.selectCountAudit(query));
			}
		}
		return orderinfos;
    }
	@Override
	public List<Orderinfo> selectAreaStatistics(Orderinfo item) {
		List<Orderinfo> itemList = new ArrayList<>();
		try {
			itemList = orderinfoMapper.selectAreaStatisticsList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Override
	public List<Orderinfo> selectTypeStatistics(Orderinfo item) {
		List<Orderinfo> itemList = new ArrayList<>();
		try {
			itemList = orderinfoMapper.selectTypeStatisticsList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Override
	public List<Map> selectEnterpriseStatistics(Orderinfo item) {
		List<Map> mapList=new ArrayList<>();
		try {
			mapList = orderinfoMapper.selectEnterpriseStatisticsList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return mapList;
	}

	@Override
	public List<Map> selectCompanyStatistics(Orderinfo item) {
		List<Map> mapList=new ArrayList<>();
		try {
			mapList = orderinfoMapper.selectCompanyStatisticsList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return mapList;
	}

    @Override
    public Map selectStatistics(Orderinfo item) {
		Map map=new LinkedHashMap();
		try {
			List<Map> mapList3 = orderinfoMapper.selectAllocatedList(item);
			map.put("allocated",mapList3);
			item.setType(4);
			List<Map> mapList1 = orderinfoMapper.selectStateList(item);
			map.put("completed",mapList1);
			item.setType(5);
			List<Map> mapList2 = orderinfoMapper.selectStateList(item);
			map.put("pay",mapList2);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
    }
}
