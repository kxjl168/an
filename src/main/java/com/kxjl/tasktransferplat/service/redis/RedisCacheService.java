package com.kxjl.tasktransferplat.service.redis;

import java.util.TreeSet;

/**
 * @author 单肙
 * @date 2018/9/26 11:04
 */
public interface RedisCacheService {
    TreeSet<String> keys(String pattern);
}
