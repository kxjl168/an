/*
 * @(#)LockCompanyService.java
 * @author: zhangyong
 * @Date: 2019/04/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.LockCompany;

import java.util.List;

/**
 * 锁企管理.
 *
 * @author zhangyong
 * @version 1.0.1 2019年04月11日
 * @revision zhangyong 2019年04月11日
 * @since 1.0.1
 */
public interface LockCompanyService {


    /**
     * 新增
     */
    JSONObject saveCompany(LockCompany query);

    /**
     * 更新信息
     */
    JSONObject updateCompany(LockCompany query);


    List<LockCompany> selectCompanyList(LockCompany query);

    /**
     * 待审核公司
     *
     * @param query
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    List<LockCompany> selectUnAuditCompanyList(LockCompany query);


    Message deleteCompany(LockCompany query);

    Message dropCompany(LockCompany query);

    Message resetCompany(LockCompany query);


    LockCompany selectCompanyById(Long id);

    LockCompany selectLockCompanyByName(String enterpriseName);

    LockCompany selectLockCompanyByPhone(String enterprisePhone);

    /**
     * 查询所有审核通过的公司
     *
     * @param item
     * @return
     */
    List<LockCompany> allCompanyList(LockCompany item);
}
