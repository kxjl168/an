/**
 * @(#)MongoOrderInfoImpl.java 2019/1/31 10:08
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.mongo.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kxjl.base.controller.mongo.NormalImg2Mongo;
import com.kxjl.tasktransferplat.dao.mongo.repository.MongoOrderInfoRepository;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImg;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderInfo;
import com.kxjl.tasktransferplat.service.mongo.MongoOrderInfoService;
import com.kxjl.tasktransferplat.util.ParamUtil;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 10:08
 * @since 1.0.0
 */
@Service
public class MongoOrderInfoServiceImpl implements MongoOrderInfoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOrderInfoRepository mongoOrderInfoRepository;

    /**
     * 上传工单图片
     *
     * @param orderId
     * @param imgList
     */
    @Override
    public void saveOrderImages(Long orderId, List<MongoOrderImg> imgList) {
        //检查入参是否合法
        ParamUtil.checkArgsNull(orderId);
        ParamUtil.checkArgsNull(imgList);

        //查看mongodb中是否有该文档，没有则创建
        MongoOrderInfo mongoOrderInfo = mongoOrderInfoRepository.findOne(orderId);
        if (mongoOrderInfo == null) {
            mongoOrderInfo = new MongoOrderInfo();
            mongoOrderInfo.setId(orderId);
            mongoOrderInfo.setOrderImages(imgList);
            mongoOrderInfoRepository.save(mongoOrderInfo);
        } else {
            Query query = new Query(new Criteria("id").is(orderId));
            Update update = new Update().pushAll("orderImages", imgList.toArray());
            mongoTemplate.updateFirst(query, update, MongoOrderInfo.class);
        }
    }

    @Override
    public MongoOrderInfo getImgPath(Long orderId) {
        Query query = new Query(Criteria.where("_id").is(orderId));
        MongoOrderInfo orderImages = mongoTemplate.findOne(query, MongoOrderInfo.class,"mongoOrderInfo");
        return orderImages;
    }

    /**
     * 查询图片
     * @param imgId
     * @return
     */
    @Override
    public NormalImg2Mongo getImg(String imgId) {
        Query query = new Query(Criteria.where("_id").is(imgId));
        NormalImg2Mongo orderImages = mongoTemplate.findOne(query, NormalImg2Mongo.class, "account_normal_img");
        return orderImages;
    }
}
