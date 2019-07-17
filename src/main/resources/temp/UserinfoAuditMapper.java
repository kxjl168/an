package com.ztgm.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.ztgm.tasktransferplat.pojo.UserinfoAudit;

public interface UserinfoAuditMapper    extends BaseMapper<UserinfoAudit>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    int insert(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    int insertSelective(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    UserinfoAudit selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    int updateByPrimaryKeySelective(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:17:14
     */
    int updateByPrimaryKey(UserinfoAudit record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<UserinfoAudit> selectList(UserinfoAudit item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-06-05 13:17:14
     */
    int delete(UserinfoAudit item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<UserinfoAudit> selectPage(Page page, UserinfoAudit item);
}
