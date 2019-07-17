package com.kxjl.tasktransferplat.delay;

import com.google.gson.Gson;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import static com.kxjl.tasktransferplat.config.Constants.DELAY_ORDER_KEY_PREFIX;
import static com.kxjl.tasktransferplat.config.Constants.NODE_IP_PORT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * @author handsomeXiao
 * @date 2018/9/19 9:16
 * 延时队列，用于存储需要经过延时处理的运单
 */
//@Component
public class OrderDelayQueue {

    private static final Logger logger = LoggerFactory.getLogger(OrderDelayQueue.class);
    /**
     * 延时队列，用于存储
     */
    private static final DelayQueue delayQueue = new DelayQueue();

    private static final Object lock = new Object();

    @Autowired
    private JedisCluster redisCluster;

    private Gson gson = new Gson();

    /**
     * 将元素存入队列和redis
     * @param delayed
     * @return
     */
    public static boolean offer (Delayed delayed) {
        return delayQueue.offer(delayed);
    }

    /**
     * 阻塞直到从队列中获取元素
     * @return
     * @throws InterruptedException
     */
    public static Delayed take () throws InterruptedException {
        return delayQueue.take();
    }

    /**
     * 删除队列中的元素
     * @param orderId
     */
    public static boolean remove (String orderId) {
        Iterator iterator;
        //防止并发删除异常
        boolean removeResult;
        synchronized (lock) {
            iterator = delayQueue.iterator();
            removeResult = false;
            while (iterator.hasNext()) {
                Orderinfo orderinfo = (Orderinfo) iterator.next();
                if (orderId.equals(orderinfo.getId().toString())) {
                    iterator.remove();
                    removeResult = true;
                }
            }
        }
        return removeResult;
    }

    /**
     * 在线程安全的情况下删除该运单原有的延时值并附上新的延时值
     * @param orderinfo
     * @param milliseconds
     */
    private boolean updateDelayWaybill(Orderinfo orderinfo, Long milliseconds) {
        boolean removeResult;
        try {
            removeResult = remove(String.valueOf(orderinfo.getId()));
        } catch (ConcurrentModificationException e) {
            removeResult = false;
            logger.info("该元素已被延时处理线程取出");
        }
        if (removeResult) {
            orderinfo.setMilliseconds(System.currentTimeMillis() + milliseconds);
            offer(orderinfo);
            redisCluster.set(DELAY_ORDER_KEY_PREFIX + NODE_IP_PORT + orderinfo.getId(), gson.toJson(orderinfo));
        }
        return removeResult;
    }
}
