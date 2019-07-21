package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.Phoneinfo;

public interface PhoneinfoMapper    extends BaseMapper<Phoneinfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    int insert(Phoneinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    int insertSelective(Phoneinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    Phoneinfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    int updateByPrimaryKeySelective(Phoneinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:21:49
     */
    int updateByPrimaryKey(Phoneinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<Phoneinfo> selectList(Phoneinfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-19 22:21:49
     */
    int delete(Phoneinfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Phoneinfo> selectPage(Page page, Phoneinfo item);
}
