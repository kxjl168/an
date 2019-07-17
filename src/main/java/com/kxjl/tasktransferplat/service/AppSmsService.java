/*
 * @(#)AppSmsService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.AppResult;
import com.kxjl.tasktransferplat.pojo.AppSms;
import com.kxjl.tasktransferplat.pojo.Userinfo;

import java.util.List;

/**
 * 短信验证表.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface AppSmsService {


    /**
     * 新增
     */
    JSONObject saveAppSms(AppSms query);

    /**
     * 更新信息
     */
    JSONObject updateAppSms(AppSms query);


    List<AppSms> selectAppSmsList(AppSms query);

    int deleteAppSms(AppSms query);
    

   	 AppSms selectAppSmsById(String id);


   /**
    * 发送短信验证码
    * @param appSms
    * @return
    * @author zj
    * @date 2019年5月20日
    */
 	public AppResult sendValidateCode(AppSms appSms);
 	
	/**
	 * 验证短信验证码
	 * @param resultDto
	 * @param user
	 * @return
	 * @author zj
	 * @date 2019年5月20日
	 */
	public AppResult checkCodeValid(AppResult resultDto, Userinfo user);

}
