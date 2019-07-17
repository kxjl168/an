package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.LocksmithEnterpriseComplain;

import java.util.List;

public interface LocksmithEnterpriseComplainMapper {
    int deleteByPrimaryKey(String id);

    int insert(LocksmithEnterpriseComplain record);

    int insertSelective(LocksmithEnterpriseComplain record);

    LocksmithEnterpriseComplain selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LocksmithEnterpriseComplain record);

    int updateByPrimaryKey(LocksmithEnterpriseComplain record);
/**
     * 列表查询
     * @param item
     * @return
     */
    List<LocksmithEnterpriseComplain> selectList(LocksmithEnterpriseComplain item);

    /**
     * 删除
     * @param item
     * @return
     */
    int delete(LocksmithEnterpriseComplain item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param item
     * @return
     */
    IPage<LocksmithEnterpriseComplain> selectPage(Page page, LocksmithEnterpriseComplain item);
}
