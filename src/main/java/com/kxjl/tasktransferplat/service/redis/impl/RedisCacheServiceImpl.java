package com.kxjl.tasktransferplat.service.redis.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.tasktransferplat.service.redis.RedisCacheService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author 单肙
 */
//@Service("redisCacheService")
public class RedisCacheServiceImpl implements RedisCacheService {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

	private JedisCluster redisCluster;

	@Autowired
	public void setRedis(JedisCluster redis) {
		this.redisCluster = redis;
	}

    /**
     * redis模糊查询key
     * @param pattern
     * @return
     */
    @Override
    public TreeSet<String> keys(String pattern) {
        TreeSet<String> keys = new TreeSet<>();
        Map<String, JedisPool> clusterNodes = redisCluster.getClusterNodes();
        for(String k : clusterNodes.keySet()){
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch(Exception e){
                logger.error("查询失败", e);
            } finally{
                connection.close();//用完一定要close这个链接！！！
            }
        }
        return keys;
    }

}
