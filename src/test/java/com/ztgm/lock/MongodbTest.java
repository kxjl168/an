/**
 * @(#)MongodbTest.java 2019/1/30 15:39
 * <p>
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ztgm.lock;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import com.kxjl.tasktransferplat.dao.mongo.repository.MongoOrderInfoRepository;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImg;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImgList;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderInfo;
import com.kxjl.tasktransferplat.service.mongo.MongoOrderInfoService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import sun.misc.BASE64Encoder;

import static com.kxjl.tasktransferplat.util.DateUtil.nowDateFormatString;

import java.io.*;
import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/30 15:39
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Autowired
    private MongoOrderInfoRepository repository;

    @Autowired
    private MongoOrderInfoService mongoOrderInfoService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Bean
    public ResourceLoader createResourceLoader() {
        return new DefaultResourceLoader();
    }

    @Test
    public void mongoTest () throws IOException {
        Long orderId = 123456L;
        File file = new File("C:\\Users\\handsome Xiao\\Pictures\\Saved Pictures\\b5baff1c7b47638dd0f9f6c48d4c9519.jpg");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();

        List<MongoOrderImg> mongoOrderImgList = new MongoOrderImgList();
        String imgBase64Code = new BASE64Encoder().encode(bytes);
        MongoOrderImg mongoOrderImg = new MongoOrderImg();
        mongoOrderImg.setImgBase64(imgBase64Code);
        mongoOrderImg.setImgType(1);
        mongoOrderImg.setUploadTime(nowDateFormatString());
        mongoOrderImgList.add(mongoOrderImg);

        mongoOrderInfoService.saveOrderImages(orderId, mongoOrderImgList);
    }
}
