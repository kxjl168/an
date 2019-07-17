package com.ztgm.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import com.ztgm.tasktransferplat.pojo.Company;

public interface CompanyMapper    extends BaseMapper<Company>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    int insert(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    int insertSelective(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    Company selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    int updateByPrimaryKeySelective(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 13:22:20
     */
    int updateByPrimaryKey(Company record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Company> selectList(Company item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 13:22:20
     */
    int delete(Company item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<Company> selectPage(Page page, Company item);
    
    /**
     * 待审核公司
     * @param query
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    List<Company> selectUnAuditCompanyList(Company query);
}
