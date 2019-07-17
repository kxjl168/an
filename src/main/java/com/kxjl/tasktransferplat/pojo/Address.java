/**
 * @(#)Address.java  2019-01-28 14:59:06
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
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_address")
public class Address {

    /**
     * 主键，1-999省，1001-9999地级市，10001-99999区县
     */
    private Long id;

    /**
     * 名称，非空
     */
    private String name;

    /**
     * 排序字段，默认值0
     */
    private Long index;

    /**
     * 父ID，外键自己，可空
     */
    private Long fatherId;

    /**
     * 优先级，默认 0， 1是 重点区域，2非重点
     */
    private Long priority;

    /**
     * 层次，目前是3 层，1省，2地级市，3区县
     */
    private Long level;

    /**
     * 第一层id(省id,level为1，2,3的拥有)
     */
    private Long levelOneId;

    /**
     * 第二层id(市id，level为2，3的拥有)
     */
    private Long levelSecId;

    /**
     * 第三次id(区县id，level为3的拥有)
     */
    private Long levelThrId;

}
