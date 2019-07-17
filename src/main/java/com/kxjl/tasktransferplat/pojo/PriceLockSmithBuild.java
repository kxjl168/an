/**
 * @(#)PriceLockSmithBuild.java 2019-04-15 11:50
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangyong
 * @version 1.0.0 2019-04-17 11:50
 * @since 1.0.0
 */
@Data
@TableName("price_lock_smith_build")
public class PriceLockSmithBuild {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 省code
     */
    @TableField("provinceCode")
    private String provinceCode;
    /**
     * 省名
     */
    @TableField("provinceName")
    private String provinceName;

    /**
     * 市code
     */
    @TableField("cityCode")
    private String cityCode;
    /**
     * 市名
     */
    @TableField("cityName")
    private String cityName;

    /**
     * 区code
     */
    @TableField("areaCode")
    private String areaCode;
    /**
     * 区名
     */
    @TableField("areaName")
    private String areaName;

    /**
     * 地区安装费
     */
    @TableField("money")
    private BigDecimal money;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private String updateTime;

}
