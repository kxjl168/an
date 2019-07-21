package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.AreainfoArea;

public interface AreainfoAreaMapper    extends BaseMapper<AreainfoArea>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    int insert(AreainfoArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    int insertSelective(AreainfoArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    AreainfoArea selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    int updateByPrimaryKeySelective(AreainfoArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:23:26
     */
    int updateByPrimaryKey(AreainfoArea record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<AreainfoArea> selectList(AreainfoArea item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-19 22:23:26
     */
    int delete(AreainfoArea item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<AreainfoArea> selectPage(Page page, AreainfoArea item);
}
