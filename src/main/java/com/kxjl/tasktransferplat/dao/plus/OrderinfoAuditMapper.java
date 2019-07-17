package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderinfoAuditMapper extends BaseMapper<OrderinfoAudit>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    int insertOrderinfoAudit(OrderinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    int insertSelective(OrderinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    OrderinfoAudit selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    int updateByPrimaryKeySelective(OrderinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 15:53:39
     */
    int updateByPrimaryKey(OrderinfoAudit record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<OrderinfoAudit> selectOwnList(OrderinfoAudit item);

    /**
     * 查询指定工单的通过的审核
     * @param item
     * @return
     */
    List<OrderinfoAudit> selectOrderInfoAudit(OrderinfoAudit item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-01-31 15:53:39
     */
    int delete(OrderinfoAudit item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<OrderinfoAudit> selectPage(Page page, OrderinfoAudit item);

    /**
     * 查询加钱记录
     * @param orderinfoAudit
     * @return
     */

    OrderinfoAudit selectAddMoney(OrderinfoAudit orderinfoAudit);

    /**
     * 批量插入审核
     * @param orderinfoAuditList
     * @return
     */
    Integer insertBatch(List<OrderinfoAudit> orderinfoAuditList);

    /**
     * 订单审核统计
     * @param item
     * @return
     */
    List<Map<String,Object>> selectOrderCount(OrderinfoAudit item);

    /**
     * 各锁企发单月统计
     * @param item
     * @return
     */
    List<Map<String,Object>> selectEnterpriseCount(OrderinfoAudit item);

    /**
     * count
     * @param orderinfoAudit
     * @return
     */
    int selectNumCount(OrderinfoAudit orderinfoAudit);



    /**
     * 合伙人新收工单数
     * @param item
     * @return
     */
    int selectCompanyCount(OrderinfoAudit item);

    /**
     * 源匠待(已)处理的订单数
     * @param item
     * @return
     */
    int selectCompanyAllCount(OrderinfoAudit item);

    /**
     * 源匠待审核锁匠的订单
     * @param item
     * @return
     */
    int selectUserInfoAllCount(OrderinfoAudit item);

    /**
     * 源匠带处理的订单
     * @param item
     * @return
     */
    List<Map<String,Object>> selectEnterpriseOrderCount(OrderinfoAudit item);

    /**
     * 源匠待处理的订单数列表
     * @param item
     * @return
     */
    List<Map<String,Object>> selectUserInfoOrderCount(OrderinfoAudit item);

    /**
     * 源匠已处理的订单数列表
     * @param item
     * @return
     */
    List<Map<String,Object>> selectUserInfoOrderCountPass(OrderinfoAudit item);
    /**
     * 合伙人查询锁匠加钱审核列表
     * @param partnerId
     * @return
     */
    List<Map<String,Object>> partnerAuditLockerAddPriceOrderList(Long partnerId);
    
    /**
     * 将指定工单的全部审核数据更新为完成
     * @param orderInfoId
     * @author zj
     * @date 2019年6月24日
     */
    void updateAllAuditDone(String orderInfoId);
}
