package com.kxjl.video.dao;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.kxjl.video.pojo.VideoalarmTalkinfo;

public interface VideoalarmTalkinfoMapper    extends BaseMapper<VideoalarmTalkinfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    int insert(VideoalarmTalkinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    int insertSelective(VideoalarmTalkinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    VideoalarmTalkinfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    int updateByPrimaryKeySelective(VideoalarmTalkinfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator zj
    * @date 2019-07-20 11:00:52
     */
    int updateByPrimaryKey(VideoalarmTalkinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<VideoalarmTalkinfo> selectList(VideoalarmTalkinfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-07-20 11:00:51
     */
    int delete(VideoalarmTalkinfo item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<VideoalarmTalkinfo> selectPage(Page page, VideoalarmTalkinfo item);
}
