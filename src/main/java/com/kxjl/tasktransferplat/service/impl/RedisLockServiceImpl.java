/**
 * @(#)redisLockServiceImpl.java 2019/1/14 11:52
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.service.RedisLockService;

import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/14 11:52
 * @since 1.0.0
 */

@Service("redisLockService")
public class RedisLockServiceImpl implements RedisLockService {

   // @Autowired
    private JedisCluster redisCluster;

    private static final Integer LOCK_EXPIRE_TIME = 10000;
    private static final Integer VERSION_EXPIRE_TIME = Integer.MAX_VALUE;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String VERSION_KEY = "version";

    /**
     * 由于redis是单线程运行的，所以可以使用redis为项目加分布式锁
     * 使用redis加分布式锁
     *
     * @param lockKey
     */
    @Override
    public boolean lock(String lockKey) {
        //setnx当且仅当key不存在时可以设置key并返回1
        long lockResult = redisCluster.setnx(lockKey, "true");
        if (lockResult == 1) {
            redisCluster.expire(lockKey, LOCK_EXPIRE_TIME);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockKey
     */
    @Override
    public void unlock(String lockKey) {
        redisCluster.del(lockKey);
    }

    /**
     * 使用redis加阻塞锁，
     *
     * @param lockKey
     */
    @Override
    public boolean blockLock(String lockKey, String version) {
        //setnx当且仅当key不存在时可以设置key并返回1
        String result = "";
        while (!LOCK_SUCCESS.equals(result)) {
            result = redisCluster.set(lockKey, version, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, LOCK_EXPIRE_TIME);
       }
        return true;
    }

    /**
     * 解锁
     * @param lockKey
     * @param version
     */
    @Override
    public boolean blockUnlock(String lockKey, String version) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        long result = (long) redisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(version));
        if (result == 1L) {
            return true;
        }
        return false;
    }

    /**
     * 获取自增版本号
     * @return
     */
    @Override
    public Long getVersion() {
        long setResult = redisCluster.setnx(VERSION_KEY, "0");
        if (setResult == 1L) {
            redisCluster.setnx(VERSION_KEY, String.valueOf(VERSION_EXPIRE_TIME));
        }
        long version = redisCluster.incr(VERSION_KEY);
        return version;
    }
}
