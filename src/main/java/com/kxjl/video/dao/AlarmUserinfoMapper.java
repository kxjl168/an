package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.AlarmUserinfo;

public interface AlarmUserinfoMapper    extends BaseMapper<AlarmUserinfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    int insert(AlarmUserinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    int insertSelective(AlarmUserinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    AlarmUserinfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    int updateByPrimaryKeySelective(AlarmUserinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 10:57:07
     */
    int updateByPrimaryKey(AlarmUserinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<AlarmUserinfo> selectList(AlarmUserinfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-07-20 10:57:07
     */
    int delete(AlarmUserinfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<AlarmUserinfo> selectPage(Page page, AlarmUserinfo item);
}
