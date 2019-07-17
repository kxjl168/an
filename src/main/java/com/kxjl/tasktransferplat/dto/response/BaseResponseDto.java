/**
 * @(#)AppResultDto.java 2019/1/28 14:36
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dto.response;


import lombok.Data;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/28 14:36
 * @since 1.0.0
 */
@Data
public class BaseResponseDto {

    /**
     * 响应码
     */
    private Integer errCode;

    /**
     * 响应消息
     */
    private String errMsg;

    /**
     * 封装查询实体数据
     */
    private Object data;
}
