/**
 * @(#)BaseRequestDto.java 2019/1/28 15:34
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dto.request;


import lombok.Data;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/28 15:34
 * @since 1.0.0
 */
@Data
public class BaseRequestDto {

    /**
     * token
     */
    private String k;

    private String data;
}
