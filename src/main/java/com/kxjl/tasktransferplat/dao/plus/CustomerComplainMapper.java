package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.CustomerComplain;

import java.util.List;

public interface CustomerComplainMapper {

    int deleteByPrimaryKey(String id);

    int insert(CustomerComplain record);

    int insertSelective(CustomerComplain record);

    CustomerComplain selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerComplain record);

    int updateByPrimaryKey(CustomerComplain record);

    /**
     * 列表查询
     * @param item
     * @return
     */
    List<CustomerComplain> selectList(CustomerComplain item);
    
    /**
     * 删除
     * @param item
     * @return
     */
    int delete(CustomerComplain item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param item
     * @return
     */
    IPage<CustomerComplain> selectPage(Page page, CustomerComplain item);
}
