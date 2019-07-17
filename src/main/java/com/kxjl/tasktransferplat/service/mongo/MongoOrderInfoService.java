/**
 * @(#)MongoOrderInfo.java 2019/1/31 10:06
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.mongo;

import java.util.List;

import com.kxjl.base.controller.mongo.NormalImg2Mongo;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImg;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderInfo;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 10:06
 * @since 1.0.0
 */
public interface MongoOrderInfoService {

    /**
     * 上传完成图片
     * @param orderId
     * @param imgList
     */
    void saveOrderImages (Long orderId, List<MongoOrderImg> imgList);

    /**
     * 获取图片
     * @param orderId
     * @return
     */
    MongoOrderInfo getImgPath(Long orderId);

    NormalImg2Mongo getImg(String imgId);
}
