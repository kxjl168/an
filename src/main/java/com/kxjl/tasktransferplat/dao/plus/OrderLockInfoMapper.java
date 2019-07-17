package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;

import java.util.List;

public interface OrderLockInfoMapper    extends BaseMapper<OrderLockInfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    int insert(OrderLockInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    int insertSelective(OrderLockInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    OrderLockInfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    int updateByPrimaryKeySelective(OrderLockInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-07-12 10:35:54
     */
    int updateByPrimaryKey(OrderLockInfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<OrderLockInfo> selectList(OrderLockInfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-12 10:35:54
     */
    int delete(OrderLockInfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<OrderLockInfo> selectPage(Page page, OrderLockInfo item);
    
    


}
