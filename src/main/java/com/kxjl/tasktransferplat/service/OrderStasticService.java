/**
 * @(#)OrderStasticService.java  2019-02-12 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.*;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 工单统计
 * OrderStasticService.java.
 * 
 * @author zj
* @version 1.0.1 2019年2月12日
* @revision zj 2019年2月12日
* @since 1.0.1
 */
public interface OrderStasticService {

   
	  /**
     * 工单状态统计
     * @return
     * @author zj
     * @date 2019年2月12日
     */
    List<Orderinfo> selectOrderStatusStasticList(Orderinfo query);

    /**
     * 工单区域统计
     * @param item
     * @return
     */
    List<Orderinfo> selectAreaStatistics(Orderinfo item);

    /**
     * 工单服务统计
     * @param item
     * @return
     */
    List<Orderinfo> selectTypeStatistics(Orderinfo item);

    /**
     * 锁企财务报表
     * @param item
     * @return
     */
    List<Map> selectEnterpriseStatistics(Orderinfo item);

    /**
     * 合伙人财务报表
     * @param item
     * @return
     */
    List<Map> selectCompanyStatistics(Orderinfo item);

    /**
     * 各锁匠工单状态
     * @param item
     * @return
     */
    Map selectStatistics(Orderinfo item);
}
