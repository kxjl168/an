package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.LockProductAttach;

import java.util.List;

public interface LockProductAttachMapper    extends BaseMapper<LockProductAttach>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    int insert(LockProductAttach record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    int insertSelective(LockProductAttach record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    LockProductAttach selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    int updateByPrimaryKeySelective(LockProductAttach record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:03:10
     */
    int updateByPrimaryKey(LockProductAttach record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<LockProductAttach> selectList(LockProductAttach item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-05-20 10:03:10
     */
    int delete(LockProductAttach item);

    /**
     * 删除ProId
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-05-20 10:03:10
     */
    int deleteProId(LockProductAttach item);
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<LockProductAttach> selectPage(Page page, LockProductAttach item);
}
