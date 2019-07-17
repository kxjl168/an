/*
 * @(#)OrderinfoAuditService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.pojo.Manager;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单审核记录.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface OrderinfoAuditService {


    /**
     * 新增
     */
    JSONObject saveOrderinfoAudit(OrderinfoAudit query);

    /**
     * 更新信息
     */
    JSONObject updateOrderinfoAudit(OrderinfoAudit query);


    List<OrderinfoAudit> selectOrderinfoAuditList(OrderinfoAudit query);

    int deleteOrderinfoAudit(OrderinfoAudit query);
    


   	 OrderinfoAudit selectOrderinfoAuditById(Long id) ;


    /**
     * 根据订单id查询审核记录
     * @param item
     * @param manager
     * @return
     */
    List<OrderinfoAudit> selectAuditByOrderId(Orderinfo item, Manager manager);

    /**
     * 锁企审核
     * @param managerId
     * @param requestDto
     * @param orderinfoAudit
     * @param orderinfo
     * @param passState
     * @param reason
     */
    void lockCompanyAudit(String managerId,  BaseRequestDto requestDto, OrderinfoAudit orderinfoAudit, Orderinfo orderinfo, Integer passState, String reason);

    /**
     * 平台审核
     * @param managerId
     * @param requestDto
     * @param orderinfoAudit
     * @param orderinfo
     * @param passState
     * @param reason
     * @param proposMoney
     */
    void platAudit(String managerId,  BaseRequestDto requestDto, OrderinfoAudit orderinfoAudit, Orderinfo orderinfo, Integer passState, String reason, String proposMoney);

    /**
     * 查询当前工单当前锁匠是否存在本次申请类型的未完成的审核
     * @param orderinfo
     * @param operateType
     * @return
     */
    OrderinfoAudit selectUndoneAudit(Orderinfo orderinfo, Integer operateType);

    /**
     * 包装工单审核对象
     * @param orderinfo
     * @param proposType
     * @param auditStates
     * @return
     */
    OrderinfoAudit packageOrderInfoAudit(Orderinfo orderinfo, int proposType, int auditStates);

    /**
     * 查找创建审核
     * @param orderinfo
     * @return
     */
    List<OrderinfoAudit> selectCreateAudit(Orderinfo orderinfo);

    /**
     * 查询公司加钱金额
     * @param item
     * @return
     */
    BigDecimal selectCompanyProposMoney(OrderinfoAudit item);


    /**
     * 申请加钱
     * @param item
     * @return
     */
    List<Map<String,Object>> countPlusOrder(OrderinfoAudit item);

    /**
     * 申请加钱通过
     * @param item
     * @return
     */
    List<Map<String,Object>> countPlusOrderPass(OrderinfoAudit item);




    /**
     * 申请加钱订单数
     * @param companyId
     * @return
     */
    int countNumPlusOrder(Long companyId);



    /**
     * 源匠待处理的工单总数
     * @param orderinfoAudit
     * @return
     */
    int countOrderCompany(OrderinfoAudit orderinfoAudit);

    /**
     * 源匠客服已处理的锁企工单
     * @param orderinfo
     * @return
     */
    int countCompanyOrderOne(OrderinfoAudit orderinfo);

    /**
     * 源匠待处理的锁匠申请工单
     * @param orderinfoAudit
     * @return
     */
    int countUserInfo(OrderinfoAudit orderinfoAudit);

    /**
     * 源匠已处理的锁匠申请工单
     * @param orderinfoAudit
     * @return
     */
    int countUserInfoPass(OrderinfoAudit orderinfoAudit);

    /**
     * 源匠处理的工单统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countEnterpriseOrderAll(OrderinfoAudit item);

    /**
     * 源匠客服已处理的锁企工单数
     * @param item
     * @return
     */
    List<Map<String,Object>> countEnterpriseOrderAllPass(OrderinfoAudit item);

    /**
     * 源匠处理的锁匠申请工单
     * @param item
     * @return
     */
    List<Map<String,Object>> countUserInfoOrder(OrderinfoAudit item);

    /**
     * 源匠已处理的锁匠申请工单
     * @param item
     * @return
     */
    List<Map<String,Object>> countUserInfoOrderPass(OrderinfoAudit item);

    /**
     * 锁企待处理的源匠工单
     * @param orderinfoAudit
     * @return
     */
    int countOrderEnterprise(OrderinfoAudit orderinfoAudit);

    /**
     * 锁企已处理的源匠工单
     * @param orderinfoAudit
     * @return
     */
    int countEnterpriseOrderOne(OrderinfoAudit orderinfoAudit);

    /**
     * 锁企待处理的源匠申请工单
     * @param orderinfoAudit
     * @return
     */
    int countEpUserInfo(OrderinfoAudit orderinfoAudit);

    /**
     * 锁企已处理的源匠申请工单
     * @param orderinfoAudit
     * @return
     */
    int countEpUserInfoPass(OrderinfoAudit orderinfoAudit);

    /**
     * 锁企处理的源匠工单量统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countNormalOrderAll(OrderinfoAudit item);

    /**
     * 锁企已处理的源匠工单量统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countNormalOrderAllPass(OrderinfoAudit item);



    /**
     * 锁企处理的锁匠工单量统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countNUserInfoOrder(OrderinfoAudit item);

    /**
     * 锁企已处理的锁匠工单量统计
     * @param item
     * @return
     */
    List<Map<String,Object>> countNUserInfoOrderPass(OrderinfoAudit item);


    /**
     * 查询通过的审核
     * @param item
     * @return
     */
    List<OrderinfoAudit> selectOrderInfoAudit(OrderinfoAudit item);

    /**
     * 合伙人查询锁匠加钱审核列表
     * @param partnerId
     * @return
     */
    List<Map<String,Object>> partnerAuditLockerAddPriceOrderList(Long partnerId);
}
