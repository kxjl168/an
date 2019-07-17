/**
 * @(#)MongoOrder.java 2019/1/30 14:35
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo.mongodb;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/30 14:35
 * @since 1.0.0
 */
@Data
public class MongoOrderInfo {

    /**
     * 此id就是工单的id
     */
    @Id
    private Long id;

    /**
     * 工单图片集合（完成图片）
     */
    private List<MongoOrderImg> orderImages;
}
