package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.AppSms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppSmsMapper    extends BaseMapper<AppSms>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    int deleteByPrimaryKey(String id);

    

    /**
     * 查询用户最新的验证码
     * @param phone
     * @return
     */
    AppSms selectLatestCode(@Param("phone") String phone);
    
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    int insert(AppSms record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    int insertSelective(AppSms record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    AppSms selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    int updateByPrimaryKeySelective(AppSms record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-20 10:56:32
     */
    int updateByPrimaryKey(AppSms record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<AppSms> selectList(AppSms item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-05-20 10:56:32
     */
    int delete(AppSms item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<AppSms> selectPage(Page page, AppSms item);
}
