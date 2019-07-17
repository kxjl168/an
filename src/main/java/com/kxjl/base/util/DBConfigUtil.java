package com.kxjl.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.tasktransferplat.dao.plus.ConfigMapper;
import com.kxjl.tasktransferplat.pojo.Config;

import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DBConfigUtil {

	// 集群
	@Autowired
	ConfigMapper configMapper;

	/**
	 * 数据库t_config 获取key对应的value
	 * 
	 * @param key
	 * @return
	 * @author zj
	 * @date 2019年6月28日
	 */
	public String getParam(String key) {
		try {
			// return redisTemplate.opsForValue().get(key);

			
			Config query=new Config();
			query.setConfig_key(key);
			
			Config cfg = configMapper.getValueByKey(query);

			if (cfg != null)
				return cfg.getConfig_value();
			else
				return "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
