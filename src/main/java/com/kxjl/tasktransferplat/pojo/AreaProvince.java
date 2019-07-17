/**
 * @(#)AreaProvince.java  2019-02-15 14:09:52
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
@TableName("t_area_province")
public class AreaProvince {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 省份名称
     */
    @TableField("ProvinceName")
    private String provinceName;

    /**
     * 省份编码
     */
    @TableField("ProvinceCode")
    private String provinceCode;

}
