/**
 * @(#)PriceLockCompany.java  2019-04-18 11:25:52
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
 * @version 1.0.0 2019-04-18 11:25:52
 * @since 1.0.0
 */
@Data
@TableName("price_lock_company")
public class PriceLockCompany {

    /**
     * 锁企费用类型id
     */
    private Long id;

    /**
     * 关联锁企id
     */
    @TableField("lockCompanyId")
    private Long lockCompanyId;

    /**
     * 服务类型，同工单表的serverType
     */
    @TableField("serverType")
    private Integer serverType;

    /**
     * 服务类型名称
     */
    @TableField("typeName")
    private String typeName;

    /**
     * 该类型对应价格
     */
    private java.math.BigDecimal price;

    /**
     * 1服务类   2加钱类
     */
    @TableField("parentType")
    private int parentType;

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
