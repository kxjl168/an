/*
 * @(#)AreainfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.service;



import com.alibaba.fastjson.JSONObject;

import java.util.List;

import com.kxjl.video.pojo.Areainfo;

/**
 * 片区信息.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface AreainfoService {
	/**
	 * 构造单位-片区树
	 * @param level
	 * @param isopen
	 * @return
	 * @author zj
	 * @date 2019年7月21日
	 */
	public List<String> buildAreaTree( String level, boolean isopen);
	/**
	 * 单位、区域二级select group
	 * @param dict_type
	 * @return
	 * @author zj
	 * @date 2019年7月21日
	 */
	public List<String> getAreaTreeSelectSecond(Areainfo item,String level);
    /**
     * 新增
     */
    JSONObject saveAreainfo(Areainfo query);

    /**
     * 更新信息
     */
    JSONObject updateAreainfo(Areainfo query);


    List<Areainfo> selectAreainfoList(Areainfo query);

    int deleteAreainfo(Areainfo query);
    

   	 Areainfo selectAreainfoById(String id);



}
