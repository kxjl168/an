package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserOpinion;

import java.util.List;

public interface UserOpinionMapper    extends BaseMapper<UserOpinion>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 09:26:34
     */
    int insert(UserOpinion record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-31 09:26:34
     */
    int insertSelective(UserOpinion record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<UserOpinion> selectList(UserOpinion item);

    /**
     * 查询客户列表
     * @param item
     * @return
     */
    List<UserOpinion> selectCustormerList(UserOpinion item);

    /**
     * 删除
     * @param item
     * @return
     * @author zj
     * @date 2019-01-31 09:26:34
     */
    int delete(UserOpinion item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param item
     * @return
     */
    IPage<UserOpinion> selectPage(Page page, UserOpinion item);


}
