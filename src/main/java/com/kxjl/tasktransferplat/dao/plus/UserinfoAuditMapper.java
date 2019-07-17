package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;

import java.util.List;

public interface UserinfoAuditMapper    extends BaseMapper<UserinfoAudit>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
     */
    int insert(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
     */
    int insertSelective(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
     */
    UserinfoAudit selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
     */
    int updateByPrimaryKeySelective(UserinfoAudit record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:26
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
     * @date 2019-06-05 13:27:26
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
