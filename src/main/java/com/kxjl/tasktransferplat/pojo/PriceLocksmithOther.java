/**
 * @(#)PriceLocksmithOther.java  2019-04-18 11:25:53
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
import org.springframework.data.annotation.CreatedDate;

/**
 * @author 单肙
 * @version 1.0.0 2019-04-18 11:25:53
 * @since 1.0.0
 */
@Data
@TableName("price_locksmith_other")
public class PriceLocksmithOther {

    /**
     * Id
     */
    private Long id;

    /**
     * 服务类型，同工单表serverType
     */
    @TableField("serverType")
    private String serverType;

    /**
     * 服务类型名称
     */
    @TableField("typeName")
    private String typeName;

    /**
     * 服务类型价格
     */
    private java.math.BigDecimal price;

    /**
     *  1 服务类  2加钱类
     */
    @TableField("parentType")
    private Integer parentType;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    /**
     * 修改时间
     */
    @TableField("updateTime")
    private String updateTime;

}
