package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.UnitinfoManager;

public interface UnitinfoManagerMapper    extends BaseMapper<UnitinfoManager>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    int insert(UnitinfoManager record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    int insertSelective(UnitinfoManager record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    UnitinfoManager selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    int updateByPrimaryKeySelective(UnitinfoManager record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:22:56
     */
    int updateByPrimaryKey(UnitinfoManager record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<UnitinfoManager> selectList(UnitinfoManager item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-19 22:22:56
     */
    int delete(UnitinfoManager item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<UnitinfoManager> selectPage(Page page, UnitinfoManager item);
}
