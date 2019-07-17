/*
 * @(#)PriceLockSmithBuildService.java
 * @author: zhangyong
 * @Date: 2019年4月17日
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockSmithBuild;

import java.util.List;

/**
 * 锁匠区域价格
 *
 * @author zhangyong
 * @version 1.0.1 2019年4月17日
 * @revision zhangyong 2019年4月15日
 * @since 1.0.1
 */
public interface PriceLockSmithBuildService {


    /**
     * 插入新数据
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    Message insert(PriceLockSmithBuild priceLockSmithBuild);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    Message deleteById(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    Message updateById(PriceLockSmithBuild entity);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    PriceLockSmithBuild selectById(Long id);
    /**
     * 根据省编号查询
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    List<PriceLockSmithBuild> selectByProvinceCode(String provinceCode);



    /**
     * 根据省和城市编号查询锁匠安装费用
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    PriceLockSmithBuild selectByCityCode(String cityCode, String provinceCode);

    /**
     * 选择列表
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-17 13:39:13
     */
    List<PriceLockSmithBuild> selectList(PriceLockSmithBuild priceLockSmithBuild);

}
