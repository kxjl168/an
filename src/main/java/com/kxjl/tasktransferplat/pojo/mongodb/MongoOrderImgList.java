/**
 * @(#)MongoImgList.java 2019/1/31 10:59
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo.mongodb;


import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 10:59
 * @since 1.0.0
 */
public class MongoOrderImgList extends ArrayList<MongoOrderImg> {

    @Override
    public boolean add(MongoOrderImg img) {
        if (img == null) {
            throw new RuntimeException("不能添加空图片对象");
        }
        if (img.getImgType() == null || isBlank(img.getImgBase64())) {
            throw new RuntimeException("图片对象必传参数为空！");
        }
        return super.add(img);
    }
}
