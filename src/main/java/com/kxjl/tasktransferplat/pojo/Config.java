/**
 * @(#)Config.java  2019-01-28 14:59:06
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
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 14:59:06
 * @since 1.0.0
 */
@Data
@TableName("t_config")
public class Config {

    /**
     * 配置表key,主键，非空
     */
	
	@TableField("config_key")
    private String config_key;

    /**
     * 配置表value，非空
     */
	@TableField("config_value")
    private String config_value;

    /**
     * 备注说明本配置项的作用，非空
     */
	@TableField("des")
    private String des;

}
