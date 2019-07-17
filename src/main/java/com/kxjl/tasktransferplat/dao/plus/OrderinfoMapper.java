/**
 * @(#)OrderinfoMapper.java 2019-01-28 15:53:03
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoJobArea;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface OrderinfoMapper extends BaseMapper<Orderinfo> {

    /**
     * 更新工单合伙人信息，变为个人工单，清空compnayid
     * @param id
     * @return
     * @author zj
     * @date 2019年5月17日
     */
    int updateOrderParterNull(String id);

    /**
     * 更新工单当前锁匠信息，至null，清空LockerId
     * @param id
     * @return
     * @author zj
     * @date 2019年5月17日
     */
    int updateOrderLockerNull(String id);

    /**
     * 批量插入
     * @param orderinfoList
     * @return
     */
    Integer insertBatch(List<Orderinfo> orderinfoList);

    /**
     * method with xml
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 15:48:31
     */
    Orderinfo selectByPrimaryKey(String id);


    Orderinfo selectByOrderNo(String orderNo);

    /**
     * method with xml
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 15:48:31
     */
    int updateByPrimaryKeySelective(Orderinfo record);

    int insertSelective(Orderinfo record);

   /* *//**
     * method with xml
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 15:48:31
     *//*
    int updateByPrimaryKey(Orderinfo record);*/


    /**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Orderinfo> selectList(Orderinfo item);

    /**
     * 删除
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 15:48:31
     */
    int delete(Orderinfo item);


    /**
     * 分页查询,
     * @param page
     * @param type 1：待接单，2：处理中，3：已完成
     * @param userinfoJobAreaList
     * @return
     */
    IPage<Orderinfo> selectPage(@Param("page") Page page, @Param("type") Integer type,
                                @Param("lockerId") Long lockerId, @Param("companyId") Long companyId,
                                @Param("userinfoJobAreaList") List<UserinfoJobArea> userinfoJobAreaList);

    /**
     * 查询合伙人已分配的工单,
     * @param page
     * @param companyId
     * @return
     */
    IPage<Orderinfo> selectDistributePage(@Param("page") Page page, @Param("companyId") Long companyId);

    List<Orderinfo> selectListByComplete(Orderinfo item);

    int selectCountNum(String id);

    /**
     * 评分历史查询
     * @param id
     * @return
     */
    List<Orderinfo> selectPointList(Long id);


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
    List<Orderinfo> selectAreaStatisticsList(Orderinfo item);

    /**
     * 工单类型统计
     * @param item
     * @return
     */
    List<Orderinfo> selectTypeStatisticsList(Orderinfo item);

    /**
     * 源匠有合伙人代理人区域报表统计
     * @return
     */
    List<Map> selectPartnerFinancialStasticList(Orderinfo query);

    /**
     * 源匠无合伙人代理人区域报表统计
     * @return
     */
    List<Map> selectNoPartnerFinancialStasticList(Orderinfo query);

    /**
     * 源匠锁企报表统计
     * @return
     */
    List<Map> selectLockCompanyFinancialStatisticsList(Orderinfo query);

    /**
     * 工单详情
     * @param aLong
     * @return
     */
    Orderinfo selectorderinfoById(String aLong);

    /**
     * 查询待接单工单
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectTodoOrderInfoList(Orderinfo orderinfo);

    /**
     * 查询待作业工单
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectDoingOrderInfoList(Orderinfo orderinfo);

    /**
     * 根据类型查询工单
     * @param orderinfo
     * @return
     */
    List<Orderinfo> selectOrderInfoList(Orderinfo orderinfo);

    Orderinfo selectOrderinfoByIdAndLockId(String aLong);

    Integer selectCountAudit(Orderinfo query);


    /**
     * 锁匠未完成的工单数-删除时判断使用
     * @param lockerId
     * @return
     * @author zj
     * @date 2019年5月22日
     */
    List<Orderinfo> selectLockerUnFinishOrder(Long lockerId);

    /**
     * 添加订单，锁企添加
     * @param paramMap
     * @return
     */
    int insertByParamMap(Map<String, String> paramMap);


    int selectStateCount(Orderinfo item);

    List<Map<String, Object>> selectHuiFlockCount(Orderinfo item);

    List<Map<String, Object>> selectHuiFlockPassCount(Orderinfo item);

    List<Map> selectEnterpriseStatisticsList(Orderinfo item);

    List<Map> selectCompanyStatisticsList(Orderinfo item);

    int selectCountCompany(Orderinfo item);

    List<Map> selectAllocatedList(Orderinfo item);

    List<Map> selectStateList(Orderinfo item);

    /**
     * 锁企是否结账查询
     * @param item
     * @return
     */
    List<Orderinfo> selectOrderInfoLists(Orderinfo item);

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

    List<Orderinfo> selectAddPrice(Orderinfo item);


    /**
     * 工单综合查询，增加日志关联查询，可以关联首页跳转，查询操作状态及最新操作时间
     * @param item
     * @return
     * @author zj
     * @date 2019年5月6日
     */
    List<Orderinfo> selectALLList(Orderinfo item);

    /**
     * 查询可提现订单
     * @param userId
     * @param orderNo
     * @param name
     * @param phone
     * @return
     * @author zhangyong
     * @date 2019年6月6日
     */
    List<Map<String,Object>> selectListByWithdraw(@Param("userId") Long userId,
                                                @Param("orderNo") String orderNo,
                                                @Param("name") String name,
                                                @Param("phone") String phone,
                                                @Param("doneTime") String doneTime);

    /**
     * 查询移动端可提现订单列表
     * @param userId
     * @param orderNo
     * @param name
     * @param phone
     * @return
     * @author zhangyong
     * @date 2019年6月6日
     */
    IPage<Map<String,Object>> selectMobileListByWithdraw(@Param("page")Page page,
                                                         @Param("userId") Long userId,
                                                         @Param("orderNo") String orderNo,
                                                         @Param("name") String name,
                                                         @Param("phone") String phone,
                                                         @Param("doneTime") String doneTime);

    Object selectTotalMoneyByWithdraw(@Param("userId") Long userId,
                                      @Param("orderNo") String orderNo,
                                      @Param("name") String name,
                                      @Param("phone") String phone,
                                      @Param("doneTime") String doneTime,
                                      @Param("auditOrderFlag") String auditOrderFlag);

    /**
     * 锁企工单报表
     * @param item
     * @return
     */
    List<Map<String,Object>> selectLockerSmithEnterpriseFinancialList(Orderinfo item);

}
