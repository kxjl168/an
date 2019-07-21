package com.kxjl.base.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.kxjl.base.service.ConfigService;
//import com.kxjl.video.service.ConfigService;
import com.kxjl.video.util.SnowflakeIdWorker;

public class UUIDUtil {

	/**
	 * 获取UUID
	 *
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * long型redis id
	 * 
	 * @return
	 * @author zj
	 * @date 2019年1月28日
	 */
	public static synchronized Long getLongUUID(RedisTemplate template) {

		SnowflakeIdWorker d = new SnowflakeIdWorker(0, 0);

		if (true)
			return SpringUtils.getBean(ConfigService.class).getNextAutoLong();
		

		if (u == null)
			u = new UUIDUtil(template);

		return u.LongId();
	}

	public UUIDUtil(RedisTemplate template) {
		redisTemplate = template;
	}

	private static UUIDUtil u = null;

	private RedisTemplate redisTemplate;

	/**
	 * 获取当前小时串-前缀标识
	 * 
	 * @param date
	 * @return
	 */
	public static String getOrderIdPrefix() {
		return DateUtil.getNowStr("yyMMddHH");

	}

	public Long getIncr(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		Long increment = entityIdCounter.getAndIncrement();

		entityIdCounter.expire(1, TimeUnit.DAYS);// 单位毫秒

		return increment;
	}

	public Long LongId() {
		String prefix = getOrderIdPrefix();
		String key = "ID_" + prefix;
		String orderId = null;
		try {
			Long incr = getIncr(key);
			// 往前补2位
			orderId = prefix + String.format("%1$06d", incr);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return Long.valueOf(orderId);
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
