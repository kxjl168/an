package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.OrderinfoAfterService;

import java.util.List;

public interface OrderinfoAfterServiceMapper    extends BaseMapper<OrderinfoAfterService>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    int insert(OrderinfoAfterService record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    int insertSelective(OrderinfoAfterService record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    OrderinfoAfterService selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    int updateByPrimaryKeySelective(OrderinfoAfterService record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-23 13:18:36
     */
    int updateByPrimaryKey(OrderinfoAfterService record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<OrderinfoAfterService> selectList(OrderinfoAfterService item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-05-23 13:18:34
     */
    int delete(OrderinfoAfterService item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<OrderinfoAfterService> selectPage(Page page, OrderinfoAfterService item);
}
