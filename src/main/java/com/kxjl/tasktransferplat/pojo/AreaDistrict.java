/**
 * @(#)AreaDistrict.java  2019-02-15 14:08:55
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
 * @version 1.0.0 2019-02-15 14:08:55
 * @since 1.0.0
 */
@Data
@TableName("t_area_district")
public class AreaDistrict {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 区域名称
     */
    @TableField("DistrictName")
    private String districtName;

    /**
     * 区域编码
     */
    @TableField("DistrictCode")
    private String districtCode;

    /**
     * 城市id
     */
    @TableField("CityId")
    private Integer cityId;

}
