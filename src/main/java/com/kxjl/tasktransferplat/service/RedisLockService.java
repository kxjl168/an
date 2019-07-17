/**
 * @(#)redisLockService.java 2019/1/14 11:50
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/14 11:50
 * @since 1.0.0
 */
public interface RedisLockService {

    /**
     * 由于redis是单线程运行的，所以可以使用redis为项目加分布式锁
     * 使用redis加分布式锁
     * @param lockKey
     * @return
     */
    boolean lock(String lockKey);

    /**
     * 解锁
     * @param lockKey
     */
    void unlock(String lockKey);

    /**
     * 加阻塞锁
     * @param lockKey
     * @param version
     * @return
     */
    boolean blockLock(String lockKey, String version);

    /**
     * 解阻塞锁
     * @param lockKey
     * @param version
     */
    boolean blockUnlock(String lockKey, String version);

    /**
     * 获取版本
     * @return
     */
    Long getVersion();
}
