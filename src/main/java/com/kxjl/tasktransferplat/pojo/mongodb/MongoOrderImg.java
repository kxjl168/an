/**
 * @(#)MongoOrderImg.java 2019/1/30 15:05
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo.mongodb;


import lombok.Data;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/30 15:05
 * @since 1.0.0
 */
@Data
public class MongoOrderImg {

    private String id;

    private String imgBase64;

    private String uploadTime;

    /**
     * 标识该图片是在什么情况下上传的，1：工单完成阶段，2：申请加钱阶段
     */
    private Integer imgType;

    private String imgPath;

    public MongoOrderImg(String id, String imgPath) {
        this.id=id;
        this.imgPath = imgPath;
    }

    public MongoOrderImg() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
