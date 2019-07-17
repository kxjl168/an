/**
 * @(#)FinancialStasticService.java 2019-04-22 11:16:28
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import java.util.List;
import java.util.Map;

import com.kxjl.tasktransferplat.pojo.Orderinfo;

/**
 * 财务报表统计
 * FinancialStasticService.java.
 *
 * @author zj
 * @version 1.0.1 2019年4月22日
 * @revision zhangyong 2019年4月22日
 * @since 1.0.1
 */
public interface FinancialStasticService {


    /**
     * 代理人合伙人财报统计
     * @return
     * @author zhangyong
     * @date 2019年4月22日
     */
    List<Map> selectPartnerFinancialStatistics(Orderinfo query);

    /**
     * 无代理人合伙人财报统计
     * @param item
     * @return
     */
    List<Map> selectNoPartnerFinancialStatistics(Orderinfo item);

    /**
     * 内部财报统计
     * @param item
     * @return
     */
    List<Map> selectLockCompanyFinancialStatistics(Orderinfo item);
}
