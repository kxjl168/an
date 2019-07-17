package com.kxjl.tasktransferplat.listener;

import com.google.gson.Gson;
import com.kxjl.tasktransferplat.delay.DelayTask;
import com.kxjl.tasktransferplat.delay.OrderDelayQueue;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.redis.RedisCacheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import static com.kxjl.tasktransferplat.config.Constants.DELAY_ORDER_KEY_PREFIX;
import static com.kxjl.tasktransferplat.config.Constants.NODE_IP_PORT;

import java.util.Set;

/**
 * @author handsomeXiao
 * @date 2018/6/13 17:01
 */
//@Component
public class SpirngDelayTaskInit implements ApplicationListener<ApplicationContextEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SpirngDelayTaskInit.class);

    @Autowired
    private DelayTask delayTask;

    @Autowired
    private JedisCluster redisCluster;

    @Autowired
    private RedisCacheService redisCacheService;


    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            if (event.getApplicationContext().getParent() == null) {
                Gson gson = new Gson();
                Set<String> orderSet = redisCacheService.keys(DELAY_ORDER_KEY_PREFIX + NODE_IP_PORT + "*");
                for (String s : orderSet) {
                    String orderJson = redisCluster.get(s);
                    Orderinfo orderinfo = gson.fromJson(orderJson, Orderinfo.class);
                    OrderDelayQueue.offer(orderinfo);
                }
                logger.info("启动延时队列检测线程");
                delayTask.delayOrderProcess();
            }
        }
    }
}
