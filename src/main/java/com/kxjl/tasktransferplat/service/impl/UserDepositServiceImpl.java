/**
 * @(#)UserDepositServiceImpl.java 2019-01-28 11:26:27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.*;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail.MoneyChangeType;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.UserDepositService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.ParamUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:27
 * @since 1.0.0
 */
@Service
public class UserDepositServiceImpl implements UserDepositService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDepositMapper userDepositMapper;

    @Autowired
    OrderinfoOperateLogMapper orderinfoOperateLogMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private UserinfoAddMapper userinfoAddMapper;

    @Autowired
    private ManagerMessageService managerMessageService;

    @Autowired
    private UserinfoMoneyDetailMapper userinfoMoneyDetailMapper;

    @Autowired
    private UserMessageMapper userMessageMapper;


    @Autowired
    private OrderinfoMapper orderinfoMapper;

    /**
     * 用户申请根据余额提现
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applyForDeposit(BaseRequestDto requestDto, UserinfoAdd userinfoAdd) {
        // 处理json参数
        String depositRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(depositRequest);

        ParamUtil.checkArgsNull(paramMap);
        // 判断是否可提现
        //String userId = paramMap.get("userId");
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        try {
            BigDecimal wantedDepositMoney = new BigDecimal(paramMap.get("depositMoney"));

            Userinfo userinfo = userinfoMapper.selectById(userId);
            if (userinfo.getAccountMoney().compareTo(wantedDepositMoney) < 0) {
                return INSUFFICIENT_BALANCE;
            }

            // 将提现信息存储到数据库
            saveDepositInfo(paramMap, userinfo, userinfoAdd);
            userinfoService.refreshUserMoneyInfo(userinfo);
            try {
                List<Map<String, String>> managerIdList = managerMessageService.selectManagerMessageInsertUserList(5 + "");
                for (Map<String, String> map : managerIdList) {
                    String managerId = map.get("managerId");
                    ManagerMessage managerMessage = new ManagerMessage();
                    managerMessage.setUserId(managerId);
                    managerMessage.setType(5 + "");
                    managerMessage.setTitle("提现审核");
                    managerMessage.setContent("等待客服审核");
                    managerMessage.setIsRead(0);
                    managerMessage.setId(UUIDUtil.getUUID());
                    managerMessageService.insert(managerMessage);
                    List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
                    MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("websocket发送消息报错");
            }

        } catch (Exception e) {
            throw new RuntimeException("用户id为" + userId + "的用户申请提现失败，请求参数：" + depositRequest, e);
        }
        return SUCCESS;
    }

    /**
     * 用户申请根据订单提现
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppResult applyForDepositByOrder(BaseRequestDto requestDto, UserinfoAdd userinfoAdd) {
        // 处理json参数
        String depositRequest = requestDto.getData();
        Map<String, Object> paramMap = (Map<String, Object>) JSON.parse(depositRequest);

        ParamUtil.checkArgsNull(paramMap);
        // 判断是否可提现
        //String userId = paramMap.get("userId");
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        try {
            BigDecimal wantedDepositMoney = new BigDecimal(paramMap.get("depositMoney").toString());
            Userinfo userinfo = userinfoMapper.selectById(userId);
            if (userinfo.getAccountMoney().compareTo(wantedDepositMoney) < 0) {
                return AppResultUtil.fail("可提现余额不足");
            }

            // 将提现信息存储到数据库
            saveDepositInfoByOrder(paramMap, userinfo, userinfoAdd);
            userinfoService.refreshUserMoneyInfo(userinfo);
            try {
                List<Map<String, String>> managerIdList = managerMessageService.selectManagerMessageInsertUserList(5 + "");
                for (Map<String, String> map : managerIdList) {
                    String managerId = map.get("managerId");
                    ManagerMessage managerMessage = new ManagerMessage();
                    managerMessage.setUserId(managerId);
                    managerMessage.setType(5 + "");
                    managerMessage.setTitle("提现审核");
                    managerMessage.setContent("等待客服审核");
                    managerMessage.setIsRead(0);
                    managerMessage.setId(UUIDUtil.getUUID());
                    managerMessageService.insert(managerMessage);
                    List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
                    MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("websocket发送消息报错");
            }

        } catch (Exception e) {
            throw new RuntimeException("用户id为" + userId + "的用户申请提现失败，请求参数：" + depositRequest, e);
        }
        return AppResultUtil.success("提现成功");
    }

    /**
     * 根据余额将提现请求存储到数据库
     *
     * @param paramMap
     * @param userinfo
     */
    private void saveDepositInfo(Map<String, String> paramMap, Userinfo userinfo, UserinfoAdd userinfoAdd) {

        BigDecimal wantedDepositMoney = new BigDecimal(paramMap.get("depositMoney"));

        UserDeposit userDeposit = new UserDeposit();
        userDeposit.setId(UUIDUtil.getUUID());
        userDeposit.setUserId(userinfo.getId());

        if (userinfoAdd.getBankId() .equals("1002n") ) {
            userDeposit.setBankName("工商银行");
        } else if (userinfoAdd.getBankId() .equals("1005n") ) {
            userDeposit.setBankName("农业银行");
        } else if (userinfoAdd.getBankId() .equals("1026n") ) {
            userDeposit.setBankName("中国银行");
        } else if (userinfoAdd.getBankId() .equals("1026n") ) {
            userDeposit.setBankName("建设银行");
        } else if (userinfoAdd.getBankId() .equals("1001n") ) {
            userDeposit.setBankName("招商银行");
        } else if (userinfoAdd.getBankId() .equals("1066n") ) {
            userDeposit.setBankName("邮储银行");
        } else if (userinfoAdd.getBankId() .equals("1020n") ) {
            userDeposit.setBankName("交通银行");
        } else if (userinfoAdd.getBankId() .equals("1004n") ) {
            userDeposit.setBankName("浦发银行");
        } else if (userinfoAdd.getBankId() .equals("1006n") ) {
            userDeposit.setBankName("民生银行");
        } else if (userinfoAdd.getBankId() .equals("1009n") ) {
            userDeposit.setBankName("兴业银行");
        } else if (userinfoAdd.getBankId() .equals("1010n") ) {
            userDeposit.setBankName("平安银行");
        } else if (userinfoAdd.getBankId() .equals("1021n") ) {
            userDeposit.setBankName("中信银行");
        } else if (userinfoAdd.getBankId() .equals("1025n") ) {
            userDeposit.setBankName("华夏银行");
        } else if (userinfoAdd.getBankId() .equals("1027n") ) {
            userDeposit.setBankName("广发银行");
        } else if (userinfoAdd.getBankId() .equals("1022n") ) {
            userDeposit.setBankName("光大银行");
        } else if (userinfoAdd.getBankId() .equals("1032n") ) {
            userDeposit.setBankName("北京银行");
        } else if (userinfoAdd.getBankId() .equals("1056n") ) {
            userDeposit.setBankName("宁波银行");
        }
        userDeposit.setBankId(userinfoAdd.getBankId());
        userDeposit.setDepositNumber(userinfoAdd.getBankCardId());
        userDeposit.setDepositReceiver(userinfoAdd.getBankUserName());
        userDeposit.setDepositReceiverPhone(userinfoAdd.getVarphone());
        userDeposit.setDepositType(2);
        userDeposit.setDepositStatus(0);
        userDeposit.setDepositMoney(wantedDepositMoney);
        userDepositMapper.insert(userDeposit);
    }

    /**
     * 根据订单将提现请求存储到数据库
     *
     * @param paramMap
     * @param userinfo
     */
    @Transactional
    private void saveDepositInfoByOrder(Map<String, Object> paramMap, Userinfo userinfo, UserinfoAdd userinfoAdd) {
        

        Boolean isAll = (Boolean) paramMap.get("isAll");
        List<Map<String, Object>> orderinfoList = null;
        if (isAll) {
            orderinfoList = orderinfoMapper.selectListByWithdraw(userinfo.getId(), null, null, null, null);
        } else {
            orderinfoList = (List<Map<String, Object>>) JSON.parse(paramMap.get("orderSelectList").toString());
        }
        if (null != orderinfoList && orderinfoList.size() > 0) {
            for (Map<String, Object> orderinfo : orderinfoList) {
                Object moneyQuestionState = orderinfo.get("moneyQuestionState");
                if (null == moneyQuestionState || Integer.parseInt(moneyQuestionState.toString()) != 0) {
                	
                	UserDeposit userDeposit = new UserDeposit();


                    userDeposit.setUserId(userinfo.getId());

                    if (userinfoAdd.getBankId() .equals("1002n") ) {
                        userDeposit.setBankName("工商银行");
                    } else if (userinfoAdd.getBankId() .equals("1005n") ) {
                        userDeposit.setBankName("农业银行");
                    } else if (userinfoAdd.getBankId() .equals("1026n") ) {
                        userDeposit.setBankName("中国银行");
                    } else if (userinfoAdd.getBankId() .equals("1026n") ) {
                        userDeposit.setBankName("建设银行");
                    } else if (userinfoAdd.getBankId() .equals("1001n") ) {
                        userDeposit.setBankName("招商银行");
                    } else if (userinfoAdd.getBankId() .equals("1066n") ) {
                        userDeposit.setBankName("邮储银行");
                    } else if (userinfoAdd.getBankId() .equals("1020n") ) {
                        userDeposit.setBankName("交通银行");
                    } else if (userinfoAdd.getBankId() .equals("1004n") ) {
                        userDeposit.setBankName("浦发银行");
                    } else if (userinfoAdd.getBankId() .equals("1006n") ) {
                        userDeposit.setBankName("民生银行");
                    } else if (userinfoAdd.getBankId() .equals("1009n") ) {
                        userDeposit.setBankName("兴业银行");
                    } else if (userinfoAdd.getBankId() .equals("1010n") ) {
                        userDeposit.setBankName("平安银行");
                    } else if (userinfoAdd.getBankId() .equals("1021n") ) {
                        userDeposit.setBankName("中信银行");
                    } else if (userinfoAdd.getBankId() .equals("1025n") ) {
                        userDeposit.setBankName("华夏银行");
                    } else if (userinfoAdd.getBankId() .equals("1027n") ) {
                        userDeposit.setBankName("广发银行");
                    } else if (userinfoAdd.getBankId() .equals("1022n") ) {
                        userDeposit.setBankName("光大银行");
                    } else if (userinfoAdd.getBankId() .equals("1032n") ) {
                        userDeposit.setBankName("北京银行");
                    } else if (userinfoAdd.getBankId() .equals("1056n") ) {
                        userDeposit.setBankName("宁波银行");
                    }
                    userDeposit.setBankId(userinfoAdd.getBankId());
                    userDeposit.setDepositNumber(userinfoAdd.getBankCardId());
                    userDeposit.setDepositReceiver(userinfoAdd.getBankUserName());
                    userDeposit.setDepositReceiverPhone(userinfoAdd.getVarphone());
                    userDeposit.setDepositType(2);
                    userDeposit.setDepositStatus(0);
                	
                	
                    userDeposit.setOrderInfoId(orderinfo.get("Id").toString());
                    userDeposit.setDepositMoney(new BigDecimal(orderinfo.get("lockerTotalPrice").toString()));

                    String did = UUIDUtil.getUUID();
                    userDeposit.setId(did);
                    userDepositMapper.insert(userDeposit);


                    // 插入工单日志表
                    OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
                    orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
                    orderinfoOperateLog.setOrderinfoId(orderinfo.get("Id").toString());
                    orderinfoOperateLog.setType(2);
                    orderinfoOperateLog.setSubType(8);
                    
                    Orderinfo oinfo= orderinfoMapper.selectById(orderinfo.get("Id").toString());
                    
                    
                    orderinfoOperateLog.setContent("提现申请：工单" + oinfo.getOrderNo());
                    orderinfoOperateLog.setOperateUserId(userinfo.getId().toString());


                    orderinfoOperateLogMapper.insert(orderinfoOperateLog);
                }
            }
        }
    }

    /**
     * 司机提现记录显示
     *
     * @param requestDto
     * @return
     */
    @Override
    public List listDriverDeposit(BaseRequestDto requestDto) {
        // 处理json参数
        String depositRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(depositRequest);
        ParamUtil.checkArgsNull(paramMap);
        //String userId = paramMap.get("userId");
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        // 查询
        List<UserDeposit> appDepositList = null;
        try {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("userId", userId);
            wrapper.orderByDesc("createTime");

            appDepositList = userDepositMapper.selectList(wrapper);
            for (UserDeposit userDeposit : appDepositList) {
                userDeposit.setCreateTime(DateUtil.getSecondString(userDeposit.getCreateTime()));
            }
        } catch (Exception e) {
            throw new RuntimeException("查询司机提现记录失败，失败用户Id：" + userId, e);
        }
        // 返回
        return appDepositList;
    }

    /**
     * 绑定用户提现信息
     *
     * @param requestDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer bindInfo(BaseRequestDto requestDto) {
        // 处理json参数
        String bindRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(bindRequest);
        ParamUtil.checkArgsNull(paramMap);

        String bankId = paramMap.get("bankId");
        String bankNum = paramMap.get("bankNum");
        String cardName = paramMap.get("cardName");
        String cardUserPhone = paramMap.get("cardUserPhone");

        UserinfoAdd userinfoAdd = new UserinfoAdd();
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        userinfoAdd.setId(userId);
        userinfoAdd.setBankId(bankId);
        userinfoAdd.setVarphone(cardUserPhone);
        userinfoAdd.setBankCardId(bankNum);
        userinfoAdd.setBankUserName(cardName);

        return userinfoAddMapper.insertIfNotExist(userinfoAdd);
    }

    /**
     * 根据条件查询提现列表
     *
     * @param userDeposit
     * @return
     */
    @Override
    public List<UserDeposit> depositList(UserDeposit userDeposit) {
        List<UserDeposit> depositList = userDepositMapper.depositList(userDeposit);
        return depositList;
    }

    @Override
    public List<Map<String, Object>> userDepositExcel(UserDeposit userDeposit) {
        List<Map<String, Object>> depositList = userDepositMapper.userDepositExcel(userDeposit);
        return depositList;
    }

    /**
     * 提现审核通过或者转账成功
     *
     * @param userDeposit
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pass(UserDeposit userDeposit) throws Exception {
        String id = userDeposit.getId();
        String auditor = userDeposit.getAuditor();
        userDeposit = userDepositMapper.selectById(id);
        Integer depositStatus = userDeposit.getDepositStatus();

        //如果申请是未审核状态
        if (depositStatus == 0) {
            userDeposit.setDepositStatus(1);
            userDeposit.setAuditor(auditor);
            userDeposit.setUpdateTime(null);
            userDepositMapper.updateById(userDeposit);


            // 插入工单日志表
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(userDeposit.getOrderInfoId());
            orderinfoOperateLog.setType(3);
            orderinfoOperateLog.setSubType(1);
            orderinfoOperateLog.setContent("审核完成");
            orderinfoOperateLog.setOperateUserId(TokenUtil.getWebLoginUser().getId());

            orderinfoOperateLogMapper.insert(orderinfoOperateLog);
        }
        //如果申请是审核通过状态
        else if (depositStatus == 1) {
            userDeposit.setDepositStatus(3);
            userDeposit.setUpdateTime(null);
            userDepositMapper.updateById(userDeposit);

            //更新锁匠钱包余额和当前正在审核金额，并插入余额明细
            updateUserInfoMoney(userDeposit, true);
        }
    }

    /**
     * 提现审核不通过或者转账失败
     *
     * @param userDeposit
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fail(UserDeposit userDeposit) throws Exception {
        String id = userDeposit.getId();
        String auditor = userDeposit.getAuditor();
        String failCause = userDeposit.getFailCause();
        userDeposit = userDepositMapper.selectById(id);
        Integer depositStatus = userDeposit.getDepositStatus();

        //如果申请是未审核状态
        if (depositStatus == 0) {
            userDeposit.setDepositStatus(2);
            userDeposit.setAuditOpinion(failCause);
            userDeposit.setAuditor(auditor);
            userDeposit.setUpdateTime(null);
            userDepositMapper.updateById(userDeposit);
        }
        //如果申请是审核通过状态
        else if (depositStatus == 1) {
            userDeposit.setDepositStatus(4);
            userDeposit.setFailCause(failCause);
            userDeposit.setUpdateTime(null);
            userDepositMapper.updateById(userDeposit);
        }
        //更新锁匠钱包余额和当前正在审核金额
        updateUserInfoMoney(userDeposit, false);
    }

    @Override
    public int applicationForWithdraw(BaseRequestDto requestDto, Userinfo user) {
        // 处理json参数
        String bindRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(bindRequest);
        ParamUtil.checkArgsNull(paramMap);

        String orderInfos = paramMap.get("orderInfoIds");
        List<UserDeposit> orderInfoIds = new ArrayList<>();
        String[] orders = orderInfos.substring(1, orderInfos.length()).split(",");
        for (String order : orders) {
            UserDeposit userDeposit = new UserDeposit();

            userDeposit.setId(UUIDUtil.getUUID());
            userDeposit.setOrderInfoId(order);
            userDeposit.setDepositStatus(0);
            userDeposit.setUserId(user.getId());

            orderInfoIds.add(userDeposit);
        }
        return userDepositMapper.insertBatch(orderInfoIds);
    }

    /**
     * 更新锁匠钱包余额和当前正在审核金额
     *
     * @param userDeposit
     * @param pass
     */
    @Transactional
    private void updateUserInfoMoney(UserDeposit userDeposit, boolean pass) throws Exception {
        Userinfo userinfo = userinfoMapper.selectById(userDeposit.getUserId());
        BigDecimal accountMoney = userinfo.getAccountMoney();
        BigDecimal freezeMoney = userinfo.getFreezeMoney();
        //如果是成功那么减去用户的账户余额，并且插入用户余额明细

        // 更新用户钱包
        userinfoService.refreshUserMoneyInfo(userinfo);
         userinfo = userinfoMapper.selectById(userDeposit.getUserId());
        if (pass) {
           // userinfo.setAccountMoney(accountMoney.subtract(userDeposit.getDepositMoney()));
            //插入用户余额明细
            UserinfoMoneyDetail detail = new UserinfoMoneyDetail();
            detail.setId(snowflakeIdWorker.nextId());
            detail.setMoneyNo(DateUtil.timestampOrderNo());
            detail.setUserinfoId(userDeposit.getUserId());
            detail.setReasonType(MoneyChangeType.OperateType_Tixian.type);
            detail.setReasonDes("提现成功");
            detail.setChangeValue(new BigDecimal(0).subtract(userDeposit.getDepositMoney()));
            detail.setOperateUserId(userDeposit.getAuditor());
            detail.setTotalPrice(userinfo.getAccountMoney().add(userinfo.getFreezeMoney()));
           // detail.setEffectiveId(userDeposit.getId());
            
            UserDeposit ud=loadUserDepositById(userDeposit.getId());
            
            detail.setEffectiveId(ud.getOrderNo()); //统一使用工单OrderNo
            userinfoMoneyDetailMapper.insert(detail);


            //Userinfo Locker=userinfoService.selectUserinfoById(orderinfoAudit.getLockerId());

            userDeposit = loadUserDepositById(userDeposit.getId());

            String msg = userDeposit.getOrderNo();
            // todo 3、推送
            AliSendSMS.sendMessageNoraml(userinfo.getPhone(), msg);


            String messageStr = "工单号为" + msg + "的工单已提现成功,请及时核对！";
            //插入消息
            UserMessage um = new UserMessage();
            um.setId(snowflakeIdWorker.nextId());
            um.setMessageContent(messageStr);
            um.setMessageType(1L);
            um.setReceiver(userinfo.getId());
            um.setOrderInfoId(userDeposit.getOrderInfoId());
            um.setMessageTitle("提现成功");
            userMessageMapper.insert(um);


            // 插入工单日志表
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(userDeposit.getOrderInfoId());
            orderinfoOperateLog.setType(3);
            orderinfoOperateLog.setSubType(2);
            orderinfoOperateLog.setContent("提现打款完成");
            orderinfoOperateLog.setOperateUserId(TokenUtil.getWebLoginUser().getId());

            orderinfoOperateLogMapper.insert(orderinfoOperateLog);

        }
     /*   //无论成功与否都需要扣除用户的正在审核金额。
        userinfo.setFreezeMoney(freezeMoney.subtract(userDeposit.getDepositMoney()));
        userinfoMapper.updateById(userinfo);*/
    }

    /**
     * 显示司机可提现金额
     *
     * @param requestDto
     * @return
     */
    @Override
    public Map queryAvailableMoney(BaseRequestDto requestDto) {
        // 处理json参数
        String depositRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(depositRequest);
        ParamUtil.checkArgsNull(paramMap);
        // 锁匠id
        //String userId = paramMap.get("userId");
        Userinfo user = TokenUtil.getCurrentUser();
        Long userId = user.getId();
        Map resultMap = null;
        try {
            Userinfo userinfo = userinfoMapper.selectById(userId);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("userId", userId);
            queryWrapper.eq("depositStatus", 0);
            Integer count = userDepositMapper.selectCount(queryWrapper);

            resultMap = new HashMap(16);

            resultMap.put("availableMoney",
                    userinfo.getAccountMoney().subtract(userinfo.getFreezeMoney()).setScale(2).toString());
            resultMap.put("auditMoney", userinfo.getFreezeMoney().setScale(2).toString());
            resultMap.put("auditCount", count);

        } catch (Exception e) {
            throw new RuntimeException("查询司机可提现金额失败，失败用户Id：" + userId, e);
        }
        return resultMap;
    }

    @Override
    public IPage<Map<String, Object>> depositListByOrder(Page page, Long userId) {
        IPage<Map<String, Object>> list = null;
        try {
            list = userDepositMapper.depositListByOrder(page, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public UserDeposit loadUserDepositById(String id) {
        UserDeposit data = new UserDeposit();

        UserDeposit query = new UserDeposit();
        query.setId(id);

        List<UserDeposit> datas = userDepositMapper.depositList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }

        return data;
    }

    @Override
    public UserDeposit selectUserDepositByOrderId(String orderId) {
        UserDeposit data = new UserDeposit();

        UserDeposit query = new UserDeposit();
        query.setOrderInfoId(orderId);

        List<UserDeposit> datas = userDepositMapper.depositList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }

        return data;
    }


    @Override
    public UserDeposit selectUserDepositByOrderNo(String orderNo) {
        UserDeposit userDeposit = null;
        try {
            userDeposit = userDepositMapper.selectUserDepositByOrderNo(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDeposit;
    }

    @Override
    public int updateUserDepositStatus(UserDeposit userDeposit) {
        int result = 0;
        try {
            result = userDepositMapper.updateUserDepositStatus(userDeposit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
