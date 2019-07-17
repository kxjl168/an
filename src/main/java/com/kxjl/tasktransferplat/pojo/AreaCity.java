/**
 * @(#)AreaCity.java  2019-02-15 14:09:52
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author 单肙
 * @version 1.0.0 2019-02-15 14:09:52
 * @since 1.0.0
 */
@Data
@TableName("t_area_city")
public class AreaCity {


    private Integer id;

    /**
     * 城市名称

     */
    @TableField("CityName")
    private String cityName;

    /**
     * 城市编码
     */
    @TableField("CityCode")
    private String cityCode;


    @TableField("ProvinceId")
    private Integer provinceId;

}
