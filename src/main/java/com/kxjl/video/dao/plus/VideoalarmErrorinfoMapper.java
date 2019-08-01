package com.kxjl.video.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.AlarmErrorinfo;

public interface VideoalarmErrorinfoMapper    extends BaseMapper<AlarmErrorinfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    int deleteByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    int insert(AlarmErrorinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    int insertSelective(AlarmErrorinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    AlarmErrorinfo selectByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    int updateByPrimaryKeySelective(AlarmErrorinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator admin
    * @date 2019-08-01 16:31:45
     */
    int updateByPrimaryKey(AlarmErrorinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<AlarmErrorinfo> selectList(AlarmErrorinfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-08-01 16:31:45
     */
    int delete(AlarmErrorinfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<AlarmErrorinfo> selectPage(Page page, AlarmErrorinfo item);
}
