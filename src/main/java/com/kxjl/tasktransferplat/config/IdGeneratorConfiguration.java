/**
 * @(#)IdGeneratorConfiguration.java 2019/1/28 14:13
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/28 14:13
 * @since 1.0.0
 */
@Configuration
@PropertySource(value = {"classpath:config/snowflakeId.properties"},
        ignoreResourceNotFound = true,
        encoding = "UTF-8",
        name = "snowflakeId.properties")
@ConfigurationProperties(prefix = "snowflake")
public class IdGeneratorConfiguration {

    private Long dataCenterId;

    private Long workerId;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(workerId == null ? 0 : workerId,
                dataCenterId == null ? 0 : dataCenterId);
    }
}
