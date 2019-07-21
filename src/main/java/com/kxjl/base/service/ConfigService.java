/*
 * @(#)ConfigService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.service;



import com.alibaba.fastjson.JSONObject;

import java.util.List;

import com.kxjl.base.pojo.Config;

/**
 * 配置项.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface ConfigService {

	/**
	 * 从配置项中获取下一个long型 id
	 * 
	 * @return
	 * @author zj
	 * @date 2019年7月20日
	 */
	public Long getNextAutoLong();
    /**
     * 新增
     */
    JSONObject saveConfig(Config query);

    /**
     * 更新信息
     */
    JSONObject updateConfig(Config query);


    List<Config> selectConfigList(Config query);

    int deleteConfig(Config query);
    

   	 Config selectConfigById(String id);



}
