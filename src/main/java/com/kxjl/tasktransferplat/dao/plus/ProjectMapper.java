package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Project;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
     */
    int insert(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
     */
    int insertSelective(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
     */
    Project selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
     */
    int updateByPrimaryKeySelective(Project record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:42:42
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
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:42:42
     */
    int delete(Project item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Project> selectPage(Page page, Project item);

    /**
     * 批量插入
     * @param projectList
     */
    void insertBatch(List<Project> projectList);
}
