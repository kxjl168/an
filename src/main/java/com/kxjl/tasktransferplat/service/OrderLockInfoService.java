/*
 * @(#)OrderLockInfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.AppResult;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;

import java.util.List;

/**
 * 工单上多把锁的安装图片信息.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface OrderLockInfoService {


    /**
     * 新增
     */
    JSONObject saveOrderLockInfo(OrderLockInfo query);

    /**
     * 更新信息
     */
    JSONObject updateOrderLockInfo(OrderLockInfo query);


    List<OrderLockInfo> selectOrderLockInfoList(OrderLockInfo query);

    int deleteOrderLockInfo(OrderLockInfo query);
    

   	 OrderLockInfo selectOrderLockInfoById(String id);


   	 /**
   	  * 查询指定工单号第几把锁的图片信息
   	  * @param item 
   	  * @return
   	  * @author zj
   	  * @date 2019年7月12日
   	  */
   	 OrderLockInfo loadOrderLockInfo(String orderNo,String index);
   	 
   	 
   	 /**
   	  * 清空工单上指定锁的图片信息，并设置imei号信息，<br>
   	  * 小程序段开始上传前调用
   	  * 
   	  * @param orderNo
   	  * @param index
   	  * @param imei
   	  * @return
   	  * @author zj
   	  * @date 2019年7月12日
   	  */
   	public AppResult cleanOrderLockPicInfoAndSetIMEI(String orderNo, String index, String imei);
   	
   	/**
   	 * 更新工单上指定锁的图片信息，每上传一张图片都会更新.
   	 * @param orderNo
   	 * @param index
   	 * @param md5
   	 * @param imgType
   	 * @return
   	 * @author zj
   	 * @date 2019年7月12日
   	 */
   	public AppResult updateOrderLockPicInfo(String orderNo, String index, String md5, String imgType) ;
   	
   	/**
   	 * 更新工单上指定锁的imei信息
   	 * @param orderNo
   	 * @param index
   	 * @param imei
   	 * @return
   	 * @author zj
   	 * @date 2019年7月15日
   	 */
   	public AppResult updateOrderLockImeiInfo(String orderNo, String index, String imei);
   	 

}
