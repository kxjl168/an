/*
 * @(#)CompanyService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.tasktransferplat.pojo.Company;

import java.util.List;

/**
 * 企业.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface CompanyService {


    /**
     * 新增
     */
    JSONObject saveCompany(Company query);

    /**
     * 更新信息
     */
    JSONObject updateCompany(Company query);


    List<Company> selectCompanyList(Company query);
    
    /**
     * 待审核公司
     * @param query
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    List<Company> selectUnAuditCompanyList(Company query);
    

    int deleteCompany(Company query);
    


   	 Company selectCompanyById(Long id) ;


    /**
     * 查询所有审核通过的公司
     * @param item
     * @return
     */
    List<Company> allCompanyList(Company item);

    /**
     * 查询公司，废弃， 合伙人使用tuserinfo
     * @param areaCode
     * @return
     * @author zj
     * @date 2019年5月16日
     */
    Company selectByAreaCode (String areaCode);

    /**
     * 通过代理人合伙人名查询
     * @param companyName
     * @return
     */
    Company selectByCompanyName(String companyName);

    Company selectByProvinceAndCityCode(String code);

    Company selectByCompanyPhone(String companyPhone);
}
