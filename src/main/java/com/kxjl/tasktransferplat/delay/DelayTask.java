package com.kxjl.tasktransferplat.delay;

import static com.kxjl.tasktransferplat.config.Constants.DELAY_ORDER_KEY_PREFIX;
import static com.kxjl.tasktransferplat.config.Constants.NODE_IP_PORT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kxjl.tasktransferplat.pojo.Orderinfo;

import redis.clients.jedis.JedisCluster;

/**
 * @author handsomeXiao
 * @date 2018/9/20 16:07
 */
//@Component("delayTask")
public class DelayTask {

    private static Logger logger = LoggerFactory.getLogger(DelayTask.class);

    @Autowired
    private JedisCluster redisCluster;

    @Async
    public void delayOrderProcess () {
        logger.info("工单延时处理线程启动");
        while (true) {
            try {
                Orderinfo orderinfo = (Orderinfo) OrderDelayQueue.take();
                redisCluster.del(DELAY_ORDER_KEY_PREFIX + NODE_IP_PORT + orderinfo.getId());
                Integer orderState = orderinfo.getOrderState();
                switch (orderState) {

                }
            } catch (InterruptedException e) {
                logger.error("延时任务中断");
            } catch (RuntimeException e) {
                logger.error("", e);
            }
        }
    }
}
