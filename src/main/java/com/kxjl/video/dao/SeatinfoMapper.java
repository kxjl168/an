package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.Seatinfo;

public interface SeatinfoMapper    extends BaseMapper<Seatinfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    int insert(Seatinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    int insertSelective(Seatinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    Seatinfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    int updateByPrimaryKeySelective(Seatinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-21 16:24:56
     */
    int updateByPrimaryKey(Seatinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<Seatinfo> selectList(Seatinfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-21 16:24:56
     */
    int delete(Seatinfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Seatinfo> selectPage(Page page, Seatinfo item);
}
