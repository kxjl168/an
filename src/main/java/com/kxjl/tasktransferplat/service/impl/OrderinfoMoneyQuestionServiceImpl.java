package com.kxjl.tasktransferplat.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.OrderInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMoneyQuestionMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoMoneyQuestion;
import com.kxjl.tasktransferplat.pojo.UserMessage;
import com.kxjl.tasktransferplat.service.OrderinfoMoneyQuestionService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderinfoMoneyQuestionServiceImpl implements OrderinfoMoneyQuestionService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderinfoMoneyQuestionMapper itemMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private UserMessageMapper userMessageMapper;


    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveOrderinfoMoneyQuestion(OrderinfoMoneyQuestion item) {
        JSONObject rtn = new JSONObject();


        try {

            item.setId(UUIDUtil.getUUID());


            itemMapper.insertSelective(item);

            rtn.put("bol", true);

            return rtn;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败", e);
            rtn.put("bol", false);
            rtn.put("message", "新增失败");
            log.error(ExceptionUntil.getMessage(e));
            return rtn;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateOrderinfoMoneyQuestion(OrderinfoMoneyQuestion item) {
        JSONObject rtn = new JSONObject();

        if (null == item || null == item.getId()) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            itemMapper.updateByPrimaryKeySelective(item);
            item = itemMapper.selectByPrimaryKey(item.getId());
            Orderinfo orderInfo = orderInfoMapper.selectById(item.getOrderInfoId());

            String messageStr = "";
            if (item.getServiceState() == 1) {
                messageStr = "工单号为" + orderInfo.getOrderNo() + "的工单申诉已同意，通过原因为"+item.getDoneContent()+",请及时核对！";
            } else if (item.getServiceState() == 2) {
                messageStr = "工单号为" + orderInfo.getOrderNo() + "的工单申诉已被拒绝，拒绝原因为" + item.getDoneContent() + ",请及时核对！";
            }
            UserMessage um = new UserMessage();
            um.setId(snowflakeIdWorker.nextId());
            um.setMessageContent(messageStr);
            um.setMessageType(1L);
            um.setReceiver(item.getUserId());
            um.setOrderInfoId(item.getOrderInfoId());
            um.setMessageTitle("工单申诉");
            userMessageMapper.insert(um);

            rtn.put("bol", true);

            return rtn;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            rtn.put("bol", false);
            rtn.put("message", "更新出错");
            return rtn;
        }
    }

    @Override
    public List<Map<String, Object>> selectOrderinfoMoneyQuestionList(String orderNo, String phone,String serviceState) {
        List<Map<String, Object>> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectList(orderNo, phone,serviceState);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteOrderinfoMoneyQuestion(OrderinfoMoneyQuestion item) {
        int result = 0;
        try {

            result = itemMapper.delete(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override

    public OrderinfoMoneyQuestion selectOrderinfoMoneyQuestionById(String id) {
        OrderinfoMoneyQuestion orderinfoMoneyQuestion = selectOrderinfoMoneyQuestionById(id);
        return orderinfoMoneyQuestion;
    }

}
