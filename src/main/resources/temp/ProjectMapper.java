package com.ztgm.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.ztgm.tasktransferplat.pojo.Project;

public interface ProjectMapper extends BaseMapper<Project>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    int deleteByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    int insert(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    int insertSelective(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    Project selectByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    int updateByPrimaryKeySelective(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:25:18
     */
    int updateByPrimaryKey(Project record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Project> selectList(Project item);
    
    /**
     * 删除
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:25:18
     */
    int delete(Project item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param item
     * @return
     */
    IPage<Project> selectPage(Page page, Project item);
}
