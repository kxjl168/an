/**
 * @(#)OrderinfoService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kxjl.base.base.PageInfo;
import com.kxjl.base.pojo.Manager;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface OrderinfoService {
/*=========================================wrote by 单肙===============================================*/
    /**
     * 接口查询工单列表
     * @param orderListDto
     * @return
     */
    IPage orderList(BaseRequestDto orderListDto);

    /**
     * 接口查询合伙人已分配工单列表
     * @param orderListDto
     * @return
     */
    IPage orderDistributeList(BaseRequestDto orderListDto);

    /**
     * 接口查询工单详情
     * @param requestDto
     * @return
     */
    Orderinfo orderDetail(BaseRequestDto requestDto);

    /**
     * 查询工单详情
     * @param id
     * @return
     */
    Orderinfo loadOrderinfoById(String id);

    /**
     * 查询创建未确认的工单
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectCreateOrderInfoList(Orderinfo orderinfo);

    /**
     * 查询待接单工单
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectTodoOrderInfoList(Orderinfo orderinfo);

    /**
     * 查询待作业工单
     * @param item
     * @return
     */
    List<Orderinfo> selectDoingOrderInfoList(Orderinfo item);

    /**
     * 查询待审核工单
     * @param item
     * @return
     */
    List<Orderinfo> selectReviewOrderInfoList(Orderinfo item);





    /**
     * 查询全部综合工单
     * @param item
     * @return
     */
    List<Orderinfo> selectAllOrderInfoList(Orderinfo item);

    /**
     * 更新工单信息
     * @param orderinfo
     */
    void updateOrder(Orderinfo orderinfo);

    /**
     * 批量插入工单接口
     * @param packageList
     * @return
     */
    void importOrderInfo(List<List> packageList);

    /**
     * 根据锁匠id查询未完成工单列表
     * @param lockerId
     * @return
     */
    List<Orderinfo> selectOrderinfoByLockerId (Long lockerId);


/*=========================================wrote by 单肙===============================================*/

    JSONObject updateOrderinfo(Orderinfo query);
    
    JSONObject saveOrderinfo(Orderinfo query);


    List<Orderinfo> selectOrderinfoList(Orderinfo query);





    /**
     * 锁企查询订单详细（包括订单流转状态）
     *
      * @author sr
     * @param orderId
     * @return
     */
    Map selectOrderinfoDetail(String orderId);


    int deleteOrderinfo(Orderinfo query);



   	 Orderinfo selectOrderinfoById(String id) ;

   	 /**
   	  * oderNo查询
   	  * @param id
   	  * @return
   	  * @author zj
   	  * @date 2019年4月28日
   	  */
 	 Orderinfo selectOrderinfoByOrerNo(String id) ;


    /**
     * 已结账订单查询
     * @param item
     * @return
     */
    List<Orderinfo> selectOrderinfoListByComplete(Orderinfo item);

    
    /**
     * 完成回访操作，并记录日志
     * @param item
     * @return
     * @author zj
     * @date 2019年1月31日
     */
    JSONObject ComplateHuifOrderinfo(Orderinfo item) ;
    
    /**
     * 完成结账操作，并记录日志
     * @param item
     * @return
     * @author zj
     * @date 2019年1月31日
     */
    JSONObject ComplatePayOrderinfo(Orderinfo item) ;
    /**
     * 查询积分明细
     * @param id
     * @return
     */
    List<Orderinfo> selectPointList(Long id);

    void orderEdite(String operateUserId, BaseRequestDto requestDto);

    /**
     * 订单是否被抢
     * @param orderId
     * @return
     */
    Orderinfo selectOrderinfoByIdAndLockId(String orderId);

    int selectCount(String id);


    /**
     * 月账单统计查询
     * @param month
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectOrderCount(String month,Orderinfo orderinfo);

    /**
     * 锁企待结账工单
     * @param id
     * @return
     */
    int countCompanyPayOrder(Long id);

    /**
     * 已作业待回访
     * @param orderinfo
     * @return
     */
    int countHuifOrder(Orderinfo orderinfo);

    /**
     * 已回访待结账
     * @param orderinfo
     * @return
     */
    int countPayOrder(Orderinfo orderinfo);

    /**
     * 已结账
     * @param orderinfo
     * @return
     */
    int countCompletedOrder(Orderinfo orderinfo);

    /**
     * 工单年度统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countHuiFlockOrder(Orderinfo item);

    /**
     * 已作业工单
     * @param item
     * @return
     */
    List<Map<String,Object>> countHuiFlockOrderPass(Orderinfo item);


    /**
     * 合伙人当日新发订单数
     * @param orderinfo
     * @return
     */
    int countNumOrderCompany(Orderinfo orderinfo);


    /**
     * 合伙人订单数
     * @param item
     * @return
     */
    List<Map<String,Object>> countFinishLockOrder(Orderinfo item);


    /**
     *  锁企结账
     * @param torderinfo
     * @return
     */
    JSONObject checkoutOrderinfo(Orderinfo torderinfo);

    /**
     * 锁企结账list
     * @param item
     * @return
     */
    List<Orderinfo> selectOrderinfoLists(Orderinfo item);



    /**
     * 查询合伙人代理人未完成工单数量
     * @param companyId
     * @return
     */
    int selectPartnerUnFinishOrderCount(Long companyId);

    /**
     * 查询锁企未完成工单数量
     * @param sellerId
     * @return
     */
    int selecLockCompanyUnFinishOrderCount(Long sellerId);
    
    
    /**
     * 查询控制台工单分类统计
     * @param orderinfo
     * @return
     * @author zj
     * @date 2019年5月29日
     */
    public Map selectOrderNumInfo(Orderinfo orderinfo);


    /**
     * 工单废弃
     * @param operater
     * @param item
     * @return
     * @author zj
     * @date 2019年6月3日
     */
    public JSONObject NoUseOrderinfo(Manager operater, Orderinfo item) ;

    /**
     * 工单恢复
     * @param operater
     * @param item
     * @return
     * @author zj
     * @date 2019年6月3日
     */
    public JSONObject ReUseOrderinfo(Manager operater, Orderinfo item) ;

    /**
     *  通过orderNo 查询
     * @param orderNo
     * @return
     */
    Orderinfo selectOrderByOrerNo(String orderNo);

    /**
     * 查询可提现订单
     * @param orderListDto
     * @return
     * @author zhangyong
     * @date 2019年6月6日
     */
    JSONObject selectListByWithdraw(BaseRequestDto orderListDto);

    /**
     * 锁企工单报表
     * @param item
     * @return
     */
    List<Map<String,Object>> selectLockerSmithEnterpriseFinancialList(Orderinfo item);
}
