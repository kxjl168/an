/**
 * @(#)MongoOrderInfoDao.java 2019/1/30 15:27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.mongo.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImg;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderInfo;

import java.util.List;


/**
 * @author 单肙
 * @version 1.0.0 2019/1/30 15:27
 * @since 1.0.0
 */
public interface MongoOrderInfoRepository extends MongoRepository<MongoOrderInfo, Long> {

}
