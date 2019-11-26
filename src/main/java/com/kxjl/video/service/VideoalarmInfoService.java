/*
 * @(#)VideoalarmInfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.video.service;



import com.alibaba.fastjson.JSONObject;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kxjl.video.pojo.VideoalarmInfo;

/**
 * 报警事件.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface VideoalarmInfoService {


    /**
     * 新增
     */
    JSONObject saveVideoalarmInfo(VideoalarmInfo query);

    /**
     * 更新信息
     */
    JSONObject updateVideoalarmInfo(VideoalarmInfo query);


    List<VideoalarmInfo> selectVideoalarmInfoList(VideoalarmInfo query);

    int deleteVideoalarmInfo(VideoalarmInfo query);
    


   	 VideoalarmInfo selectVideoalarmInfoById(Long id) ;
   	 
   	/**
 	 * 新增或者删除附件url。  附件通过picture_url 字段传入。
 	 * 比较    /**

   picture_url;


    video_url;

   
   audio_url; 数据
 	 * @param actionType add,remove
 	 * @param item
 	 * @return
 	 * @author zj
 	 * @date 2019年11月26日
 	 */
 
 	public JSONObject editVideoalarmFile(String actionType,VideoalarmInfo item);


}
