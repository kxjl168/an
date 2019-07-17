/**
 * @(#)TempGaode.java 2019/2/15 14:32
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;


import lombok.Data;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/15 14:32
 * @since 1.0.0
 */
@Data
public class TempGaode {

    private String name;

    private String cityCode;

    private String adCode;

    private List<TempGaode> beanList;
}
