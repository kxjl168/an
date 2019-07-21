package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.VideoalarmInfo;

public interface VideoalarmInfoMapper    extends BaseMapper<VideoalarmInfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    int deleteByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    int insert(VideoalarmInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    int insertSelective(VideoalarmInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    VideoalarmInfo selectByPrimaryKey(Integer id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    int updateByPrimaryKeySelective(VideoalarmInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-19 22:24:08
     */
    int updateByPrimaryKey(VideoalarmInfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<VideoalarmInfo> selectList(VideoalarmInfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-07-19 22:24:08
     */
    int delete(VideoalarmInfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<VideoalarmInfo> selectPage(Page page, VideoalarmInfo item);
}
