package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;

import java.util.List;

public interface UserinfoOperateLogMapper    extends BaseMapper<UserinfoOperateLog>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    int insert(UserinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    int insertSelective(UserinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    UserinfoOperateLog selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    int updateByPrimaryKeySelective(UserinfoOperateLog record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-06-05 13:27:01
     */
    int updateByPrimaryKey(UserinfoOperateLog record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<UserinfoOperateLog> selectList(UserinfoOperateLog item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-06-05 13:27:01
     */
    int delete(UserinfoOperateLog item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<UserinfoOperateLog> selectPage(Page page, UserinfoOperateLog item);
}