package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.pojo.Manager;
import com.kxjl.tasktransferplat.pojo.UserinfoJobArea;

import java.util.List;

public interface UserinfoJobAreaMapper    extends BaseMapper<UserinfoJobArea>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    int insert(UserinfoJobArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    int insertSelective(UserinfoJobArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    UserinfoJobArea selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    int updateByPrimaryKeySelective(UserinfoJobArea record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 16:49:44
     */
    int updateByPrimaryKey(UserinfoJobArea record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<UserinfoJobArea> selectList(UserinfoJobArea item);
    
    /**
     * 获取作业区域下是否有其他合伙人
     * @param item
     * @return
     * @author zj
     * @date 2019年5月21日
     */
    List<UserinfoJobArea> selectParterList(UserinfoJobArea item);
    
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-05-13 16:49:44
     */
    int delete(UserinfoJobArea item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<UserinfoJobArea> selectPage(Page page, UserinfoJobArea item);
    
    
    
   /**
    * 清空锁匠作业区域
    * @param Manager
    * @return
    * @author zj
    * @date 2019年5月13日
    */
    int deleteLockJobArea(UserinfoJobArea Manager);
}
