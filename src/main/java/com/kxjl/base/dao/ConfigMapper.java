package com.kxjl.base.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.base.pojo.Config;

public interface ConfigMapper    extends BaseMapper<Config>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    int deleteByPrimaryKey(String ckey);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    int insert(Config record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    int insertSelective(Config record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    Config selectByPrimaryKey(String ckey);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    int updateByPrimaryKeySelective(Config record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 09:55:35
     */
    int updateByPrimaryKey(Config record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<Config> selectList(Config item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-20 09:55:35
     */
    int delete(Config item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Config> selectPage(Page page, Config item);
    
    
    /**
     * 根据key查询
     * @param item
     * @return
     * @author zj
     * @date 2019年7月20日
     */
    Config getValueByKey(Config item);
}
