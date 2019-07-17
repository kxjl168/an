/**
 * @(#)MongoImgController.java 2019/3/4 9:39
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kxjl.base.controller.mongo.MongoGeoDao;
import com.kxjl.base.controller.mongo.NormalImg2Mongo;
import com.kxjl.tasktransferplat.service.mongo.MongoOrderInfoService;

import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author 单肙
 * @version 1.0.0 2019/3/4 9:39
 * @since 1.0.0
 */
@RestController
@RequestMapping("/mongo")
@Slf4j
public class MongoImgController {

    @Autowired
    private MongoOrderInfoService mongoOrderInfoService;

    //@Autowired
    private MongoGeoDao mongoGeoDao;

    @RequestMapping("/getAuditImg/{iconId}")
    public void getAuditImg(@PathVariable String iconId, HttpServletRequest request, HttpServletResponse response) {
        NormalImg2Mongo img = mongoOrderInfoService.getImg(iconId);

        NormalImg2Mongo normalImg = mongoGeoDao.getNormalImg(iconId);


        OutputStream outputStream = null;
        try {
            if (img == null) {
                throw new RuntimeException("没有该图片:" + iconId);
            }
            String imgBase64 = img.getImg_file();
            if (isBlank(imgBase64)) {
                throw new RuntimeException("没有该图片:" + iconId);
            }
            //将图片转换成字节
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imgBytes = decoder.decodeBuffer(imgBase64.replaceAll("\r\n", ""));

            //设置请求头为图片
            response.setContentType("image/*");
            //获取写入流
            outputStream = response.getOutputStream();
            //将图片写入请求
            outputStream.write(imgBytes);
            //将字节刷入管道
            outputStream.flush();
        } catch (Exception e) {
            log.error("获取图片失败", e);
        } finally {
            //关闭流
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("流关闭失败", e);
            }
        }
    }
}
