package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Company;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CompanyMapper extends BaseMapper<Company>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
     */
    int deleteByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
     */
    @Override
    int insert(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
     */
    int insertSelective(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
     */
    Company selectByPrimaryKey(Long id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
     */
    int updateByPrimaryKeySelective(Company record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-02-01 13:39:13
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
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int delete(Company item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param item
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

    /**
     * 查询公司列表
     * @return
     */
    List<Company> allCompanyList();

    /**
     * 根据公司名查询公司
     * @return
     */
    Company selectByCompanyName(String companyName);
    /**
     * 根据公司电话查询公司
     * @return
     */
    Company selectByCompanyPhone(String companyPhone);
    /**
     * 根据省市查询公司
     * @return
     */
    Company selectByProvinceAndCityCode(String provinceCityCode);

    /**
     * 根据省市编码查询合伙人
     * @param provinceCityCode
     * @return
     */
    Company selectByAreaCode(@Param("provinceCityCode") String provinceCityCode);
}
