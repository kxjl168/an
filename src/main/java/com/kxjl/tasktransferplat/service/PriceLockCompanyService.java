/*
 * @(#)PriceLockCompanyService.java
 * @author: zhangyong
 * @Date: 2019年4月15日
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;

import java.util.HashMap;
import java.util.List;

/**
 * 锁企价格
 *
 * @author zhangyong
 * @version 1.0.1 2019年4月15日
 * @revision zj 2019年4月15日
 * @since 1.0.1
 */
public interface PriceLockCompanyService {


    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int deleteByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    int deleteByLockCompanyId(Long lockCompanyId);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    Message insert(PriceLockCompany priceLockCompany);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    PriceLockCompany selectByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    Message updateByPrimaryKey(PriceLockCompany priceLockCompany);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    Message updateByLockCompanyIdAndType(PriceLockCompany priceLockCompany);


    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    List<PriceLockCompany> selectByLockCompanyId(Long lockCompanyId);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    LockCompany selectByLockCompanyIdAndType(Long lockCompanyId,Integer serverType,Integer parentType);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    List<LockCompany> selectList(LockCompany lockCompany);

    /**
     * 根据id和服务类型查询价格信息
     * @param lockCompanyId
     * @param serverType
     * @param parentType
     * @return
     */
    PriceLockCompany selectByTypeAndId(Long lockCompanyId, String serverType, int parentType);

}
