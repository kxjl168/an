/**
 * @(#)OrderListResponse.java 2019/1/28 15:33
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dto.response;


import lombok.Data;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.Orderinfo;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/28 15:33
 * @since 1.0.0
 */
@Data
public class OrderListResponse extends BaseResponseDto {

    private List<Orderinfo> data;
}
