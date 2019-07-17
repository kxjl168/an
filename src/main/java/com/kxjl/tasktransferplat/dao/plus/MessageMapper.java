package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Message;

import java.util.List;

public interface MessageMapper    extends BaseMapper<Message>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    int insert(Message record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    int insertSelective(Message record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    Message selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    int updateByPrimaryKeySelective(Message record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 14:54:44
     */
    int updateByPrimaryKey(Message record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Message> selectList(Message item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 14:54:44
     */
    int delete(Message item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Message> selectPage(Page page, Message item);

    int selectCountUnread(Long Id);
}
