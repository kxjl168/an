/*
 * @(#)OrderinfoOperateLogService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;

import java.util.List;
import java.util.Map;

/**
 * 工单历史.
 *
 * @author zj
 * @version 1.0.1 2018年12月11日
 * @revision zj 2018年12月11日
 * @since 1.0.1
 */
public interface OrderinfoOperateLogService {


    /**
     * 新增
     */
    JSONObject saveOrderinfoOperateLog(OrderinfoOperateLog query);

    /**
     * 更新信息
     */
    JSONObject updateOrderinfoOperateLog(OrderinfoOperateLog query);


    List<OrderinfoOperateLog> selectOrderinfoOperateLogList(OrderinfoOperateLog query);

    int deleteOrderinfoOperateLog(OrderinfoOperateLog query);


    OrderinfoOperateLog selectOrderinfoOperateLogById(Long id);


    /**
     * 包装操作日志
     * @param orderinfo
     * @param type
     * @param subType
     * @return
     */
    OrderinfoOperateLog packageOperateLog(Orderinfo orderinfo, int type, int subType);
    /**
     * 锁企当日新下发订单
     * @param companyId
     * @return
     */
    int countNumOrder(Long companyId,String start,String end);

    /**
     * 锁企当日新下发订单通过数
     * @param companyId
     * @return
     */
    int countPassOrder(Long companyId,String Id,String start,String end);


    /**
     * 下发单总数
     * @param item
     * @return
     */
    List<Map<String,Object>> countIssueOrder(OrderinfoOperateLog item);

    /**
     * 下发单通过数
     * @param item
     * @return
     */
    List<Map<String,Object>> countIssueOrderPass(OrderinfoOperateLog item);

    /**
     * 各锁企发单数
     * @param item
     * @return
     */
    List<Map<String,Object>> countEnterpriseOrder(OrderinfoOperateLog item);

    /**
     * 各锁企发单通过数
     * @param item
     * @return
     */
    List<Map<String,Object>> countEnterpriseOrderPass(OrderinfoOperateLog item);
}
