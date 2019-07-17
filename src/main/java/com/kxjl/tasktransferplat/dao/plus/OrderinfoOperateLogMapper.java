package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;

import java.util.List;
import java.util.Map;

public interface OrderinfoOperateLogMapper    extends BaseMapper<OrderinfoOperateLog>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    int insert(OrderinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    int insertSelective(OrderinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    OrderinfoOperateLog selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    int updateByPrimaryKeySelective(OrderinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 14:43:03
     */
    int updateByPrimaryKey(OrderinfoOperateLog record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<OrderinfoOperateLog> selectList(OrderinfoOperateLog item);


    /** 通过订单ID 查询订单历史流转
     *
     * @param orderId
     * @return
     */
    List<OrderinfoOperateLog> selectListByOrderId(String orderId);

    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-02-01 14:43:03
     */
    int delete(OrderinfoOperateLog item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoOperateLog
     * @return
     */
    IPage<OrderinfoOperateLog> selectPage(Page page, OrderinfoOperateLog orderinfoOperateLog);

    OrderinfoOperateLog selectRecentReason(String aLong);

    /**
     * 批量插入日志
     * @param operateLogList
     * @return
     */
    Integer insertBatch(List<OrderinfoOperateLog> operateLogList);

    /**
     * count
     * @param orderinfoOperateLog
     * @return
     */
    int selectCountNum(OrderinfoOperateLog orderinfoOperateLog);

    List<Map<String,Object>> selectOrderCount(OrderinfoOperateLog item);

    List<Map<String,Object>> selectEnterpriseCount(OrderinfoOperateLog item);
}
