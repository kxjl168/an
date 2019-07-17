/**
 * @(#)MobileOrderController.java 2019/1/28 15:23
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kxjl.base.controller.Common.MongoImgSvrCtroller;
import com.kxjl.base.excerption.BusinessException;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.StringUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/28 15:23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/interface/orderLocksmith")
public class MobileOrderController extends AppBaseController {

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private OrderInfoOperateService orderInfoOperateService;

    //@Autowired
    MongoImgSvrCtroller mongoImgSvrCtroller;


    @Autowired
    private UserPhoneService userPhoneService;

    @Autowired
    private OrderinfoAuditService orderinfoAuditService;

    @Autowired
    OrderinfoOperateLogService orderinfoOperateLogService;

    @Autowired
    private LockProductInfoService lockProductInfoService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private ManagerMessageService managerMessageService;

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Autowired
    private OrderinfoMoneyQuestionService orderinfoMoneyQuestionService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    
	@Autowired
	private OrderLockInfoService orderLockInfoService;


    private static final Logger logger = LoggerFactory.getLogger(MobileOrderController.class);

    /**
     * 查询工单列表
     *
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("成功");
        String resultString = "";
        try {
            IPage orderPage = orderinfoService.orderList(requestDto);
            orderListResponse.setData(orderPage);
        } catch (Exception e) {
            logger.error("查询工单列表失败", e);
            orderListResponse.setErrMsg("失败");
            orderListResponse.setErrCode(2);
        }
        resultString = JSON.toJSONString(orderListResponse);
        return resultString;
    }

    /**
     * 查询工单列表
     *
     * @return
     */
    @RequestMapping("/orderDistributeList")
    public String orderDistributeList(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("成功");
        String resultString = "";
        try {
            IPage orderPage = orderinfoService.orderDistributeList(requestDto);
            orderListResponse.setData(orderPage);
        } catch (Exception e) {
            logger.error("查询工单列表失败", e);
            orderListResponse.setErrMsg("失败");
            orderListResponse.setErrCode(2);
        }
        resultString = JSON.toJSONString(orderListResponse);
        return resultString;
    }

    /**
     * 查询工单详情
     *
     * @return
     */
    @RequestMapping("/orderDetail")
    public BaseResponseDto orderDetail(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto orderDetailResponse = new BaseResponseDto();
        orderDetailResponse.setErrCode(1);
        orderDetailResponse.setErrMsg("成功");
        try {
            Map<String, Object> map = new HashMap<>();
            Orderinfo orderinfo = orderinfoService.orderDetail(requestDto);
            LockProductInfo lockProductInfo = null;
            if (null != orderinfo && orderinfo.getProductId() != null) {
                lockProductInfo = lockProductInfoService.selectLockProductInfoById(orderinfo.getProductId());
            }

            OrderinfoAudit orderinfoAuditSearch = new OrderinfoAudit();
            orderinfoAuditSearch.setOrderInfoId(orderinfo.getId());
            orderinfoAuditSearch.setAuditStates(6);
            orderinfoAuditSearch.setProposType(4);
            List<OrderinfoAudit> orderinfoAuditList = orderinfoAuditService.selectOrderinfoAuditList(orderinfoAuditSearch);

            OrderinfoAudit orderinfoAudit = null;
            if (null != orderinfoAuditList && orderinfoAuditList.size() > 0) {
                orderinfoAudit = orderinfoAuditList.get(0);
                Userinfo userinfo = userinfoService.selectUserinfoById(Long.parseLong(orderinfoAudit.getProposer()));
                orderinfoAudit.setAuditorName(userinfo.getName());
            }
            map.put("orderInfo", orderinfo);
            map.put("productInfo", lockProductInfo);
            map.put("orderinfoAudit", orderinfoAudit);
            
            
            //加入imei及图片信息 //zj 190715
            OrderLockInfo oquery=new OrderLockInfo();
            oquery.setOrderNo(orderinfo.getOrderNo());
            List<OrderLockInfo> oinfos= orderLockInfoService.selectOrderLockInfoList(oquery);
            map.put("imeiInfo", oinfos);
            
            
            orderDetailResponse.setData(map);
        } catch (Exception e) {
            logger.error("查询工单详情失败", e);
            orderDetailResponse.setErrMsg("失败");
            orderDetailResponse.setErrCode(2);
        }
        return orderDetailResponse;
    }

    /**
     * 确认接单接口
     *
     * @return
     */
    @RequestMapping("/acceptOrder")
    public BaseResponseDto orderAccept(BaseRequestDto requestDto, HttpServletRequest request) {
        //封装响应
        BaseResponseDto orderAcceptResponse = new BaseResponseDto();
        orderAcceptResponse.setErrCode(1);
        orderAcceptResponse.setErrMsg("成功");
        try {
            String operateUserId = TokenUtil.getCurrentUser().getId().toString();
            //业务逻辑
            int acceptResult = orderInfoOperateService.orderAccept(operateUserId, requestDto);
            if (acceptResult == 0) {
                orderAcceptResponse.setErrMsg("该订单已被他人抢走");
                orderAcceptResponse.setErrCode(4);
            }
        } catch (Exception e) {
            //异常出口
            logger.error("接单失败", e);
            orderAcceptResponse.setErrMsg("失败");
            orderAcceptResponse.setErrCode(2);
        }
        return orderAcceptResponse;
    }


    /**
     * 申请加钱
     *
     * @return
     */
    @RequestMapping("/addPrice")
    public BaseResponseDto orderAddPrice(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto orderAddPriceResponse = new BaseResponseDto();
        orderAddPriceResponse.setErrCode(1);
        orderAddPriceResponse.setErrMsg("成功");
        try {
            String operateUserId = TokenUtil.getCurrentUser().getId().toString();
            //业务逻辑
            int result = orderInfoOperateService.orderAddPrice(operateUserId, requestDto);
            if (result == 0) {
                orderAddPriceResponse.setErrCode(4);
                orderAddPriceResponse.setErrMsg("已存在同类型的申请，请等待审核！");
            }
            else if(result<0)
            {
            	 orderAddPriceResponse.setErrCode(2);
                 orderAddPriceResponse.setErrMsg("申请失败!工单已经被修改,请稍后重试");
            }
        } catch (Exception e) {
            //异常出口
            logger.error("申请加钱失败", e);
            if (e instanceof BusinessException) {
                orderAddPriceResponse.setErrMsg("申请加钱失败！" + e.getMessage());
            } else {
                orderAddPriceResponse.setErrMsg("申请加钱失败！");
            }
            orderAddPriceResponse.setErrCode(2);
        }
        return orderAddPriceResponse;
    }

    /**
     * 合伙人审核加钱列表
     *
     * @return
     */
    @RequestMapping("/partnerAuditLockerAddPriceOrderList")
    public AppResult partnerAuditLockerAddPriceOrderList(BaseRequestDto requestDto) {
        try {
            Long operateUserId = TokenUtil.getCurrentUser().getId();
            List<Map<String, Object>> list = orderinfoAuditService.partnerAuditLockerAddPriceOrderList(operateUserId);
            return AppResultUtil.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询失败", e);
            return AppResultUtil.fail("查询失败");
        }
    }

    /**
     * 合伙人审核加钱审批
     *
     * @return
     */
    @RequestMapping("/partnerAuditLockerAddPriceOrderAudit")
    public AppResult partnerAuditLockerAddPriceOrderAudit(BaseRequestDto requestDto, HttpServletRequest request) {
        try {
            String data = requestDto.getData();
            Map<String, String> paramMap = (Map<String, String>) JSON.parse(data);
            Integer type = parseIntegerParam(request, "type");
            String auditReason = parseStringParam(request, "auditReason");

            OrderinfoAudit orderinfoAudit = new OrderinfoAudit();
            orderinfoAudit.setId(Long.parseLong(paramMap.get("id")));

            String orderId = paramMap.get("orderId");
            Orderinfo orderinfo = new Orderinfo();
            orderinfo.setId(orderId);

            if (type == 1) {
                orderinfoAudit.setProposType(1);
                orderinfoAudit.setAuditStates(0);

                orderinfo.setOrderState(1);
                orderinfoService.updateOrder(orderinfo);

                try {
                    List<Map<String, String>> managerIdList = managerMessageService.selectManagerMessageInsertUserList(3 + "");
                    for (Map<String, String> map : managerIdList) {
                        String managerId = map.get("managerId");
                        ManagerMessage managerMessage = new ManagerMessage();
                        managerMessage.setUserId(managerId);
                        managerMessage.setType(3 + "");
                        managerMessage.setTitle("加钱审核");
                        managerMessage.setContent("等待客服审核");
                        managerMessage.setIsRead(0);
                        managerMessage.setId(UUIDUtil.getUUID());
                        managerMessageService.insert(managerMessage);
                        List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
                        MyWebSocket.sendMessage(JSON.toJSONString(list), managerId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("websocket发送消息报错");
                }

            } else {
                orderinfoAudit.setProposType(4);
                orderinfoAudit.setAuditStates(7);
                orderinfoAudit.setAuditFailReason("合伙人拒绝");
                orderinfo.setOrderState(210);
                orderinfoService.updateOrder(orderinfo);

                orderinfo = orderinfoService.selectOrderinfoById(orderId);
                String messageStr = "工单号为" + orderinfo.getOrderNo() + "的工单加钱申请已被拒绝,拒绝原因为"+auditReason+",请及时核对！";
                //插入消息
                UserMessage um = new UserMessage();
                um.setId(snowflakeIdWorker.nextId());
                um.setMessageContent(messageStr);
                um.setMessageType(1L);
                um.setReceiver(orderinfo.getLockerId());
                um.setOrderInfoId(orderinfo.getId());
                um.setMessageTitle("拒绝加钱");
                userMessageMapper.insert(um);

            }
            JSONObject jsonObject = orderinfoAuditService.updateOrderinfoAudit(orderinfoAudit);

            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
            orderinfoOperateLog.setType(OrderinfoOperateLog.OperateType.OperateType_Locker.type);// 锁匠操作
            orderinfoOperateLog.setSubType(OrderinfoOperateLog.OperateSubType.OperateSubType_Lock_Shenhe.type);// 锁匠派单
            orderinfoOperateLog.setOperateUserId(TokenUtil.getCurrentUser().getId() + "");

            if (type == 1) {
                orderinfoOperateLog.setContent("通过:" + auditReason);
            } else {
                orderinfoOperateLog.setContent("拒绝:" + auditReason);
            }
            orderinfoOperateLogService.saveOrderinfoOperateLog(orderinfoOperateLog);

            Boolean bol = jsonObject.getBoolean("bol");
            if (bol) {
                return AppResultUtil.success("审核成功");
            } else {
                return AppResultUtil.success(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("审核失败", e);
            return AppResultUtil.fail("审核失败");
        }
    }

    /**
     * 确认完成接口
     *
     * @return
     */
    @RequestMapping("/doneOrder")
    public AppResult orderDone(BaseRequestDto requestDto) {
        //封装响应
        AppResult appResult = new AppResult();
        try {
            String operateUserId = TokenUtil.getCurrentUser().getId().toString();


            // OrderComplateCode   //工单确认码 //TODO


            //业务逻辑
            appResult = orderInfoOperateService.orderDone(operateUserId, requestDto);
        
            

        } catch (Exception e) {
            //异常出口
            logger.error("确认完成失败", e);
            appResult = AppResultUtil.fail("确认完成失败");
        }
        return appResult;
    }

    /**
     * 取消订单
     *
     * @return
     */
    @RequestMapping("/cancelOrder")
    public BaseResponseDto orderCancel(BaseRequestDto requestDto) {

        //封装响应
        BaseResponseDto orderCancelResponse = new BaseResponseDto();
        orderCancelResponse.setErrCode(1);
        orderCancelResponse.setErrMsg("成功");
        try {
            String operateUserId = TokenUtil.getCurrentUser().getId().toString();
            //业务逻辑
            int result=orderInfoOperateService.orderCancel(operateUserId, requestDto);
            
            if(result<0)
            {
            	orderCancelResponse.setErrCode(2);
            	orderCancelResponse.setErrMsg("申请失败!工单已经被修改,请稍后重试");
            }
            
            
        } catch (Exception e) {
            //异常出口
            logger.error("取消订单失败", e);
            orderCancelResponse.setErrMsg("失败");
            orderCancelResponse.setErrCode(2);
        }
        return orderCancelResponse;
    }


    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }


    /**
     * 修改订单详情
     */
    @ResponseBody
    @RequestMapping(value = "/EditOrderInfo")
    public BaseResponseDto editInfo(BaseRequestDto requestDto) {

        BaseResponseDto orderDoneResponse = new BaseResponseDto();

        orderDoneResponse.setErrCode(1);
        orderDoneResponse.setErrMsg("成功");

        try {
            String operateUserId = TokenUtil.getCurrentUser().getId().toString();
            //业务逻辑

            orderinfoService.orderEdite(operateUserId, requestDto);

        } catch (Exception e) {
            //异常出口
            logger.error("修改失败", e);
            orderDoneResponse.setErrMsg("失败");
            orderDoneResponse.setErrCode(2);
        }
        return orderDoneResponse;
    }

    /**
     * 合伙人分派订单给锁匠
     */
    @ResponseBody
    @RequestMapping(value = "/changeOrderToLocker")
    public AppResult changeOrderToLocker(BaseRequestDto requestDto, HttpServletRequest request) {
        try {

            String operateUserId = TokenUtil.getCurrentUser().getId().toString();
            Map<String, String> paramMap = new HashMap<>(16);
            paramMap.put("orderId", parseStringParam(request, "orderId"));
            paramMap.put("lockerId", parseStringParam(request, "lockerId"));
            paramMap.put("formId", parseStringParam(request, "formId"));
            paramMap.put("addMoney", "0");// String.valueOf(orderinfo.getAddMoney()));
            paramMap.put("addMoneyDesc", "");// String.valueOf(orderinfo.getAddMoneyDesc()));

            paramMap.put("useId", operateUserId);
            paramMap.put("from", String.valueOf("2"));// 1：后台分配 ，2：小程序合伙人分配

            BaseRequestDto baseRequestDto = new BaseRequestDto();
            baseRequestDto.setData(JSON.toJSONString(paramMap));
            int allocateResult = orderInfoOperateService.allocateOrder(operateUserId, baseRequestDto);
            if (allocateResult == 0) {
                return AppResultUtil.fail("分派失败");
                // baseResponseDto.setErrMsg("分配工单失败，该工单已经被抢走！");
            }
        } catch (Exception e) {
            logger.error("分派工单失败！", e);
            return AppResultUtil.fail("分派失败");
        }

        return AppResultUtil.success("分派成功");
    }

    /**
     * 记录电话
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/recorder")
    public AppResult insertOpinion(HttpServletRequest request) {

        try {
            String orderId = parseStringParam(request, "orderId");
            Userinfo eu = TokenUtil.getCurrentUser();
            UserPhoneLog userPhoneLog = new UserPhoneLog();
            userPhoneLog.setId(Long.parseLong(eu.getSessionKey()));
            userPhoneLog.setPhone(eu.getPhone());
            userPhoneLog.setOrderinfoId(orderId);
            userPhoneService.recordPhoneLog(userPhoneLog);
            return AppResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AppResultUtil.fail();
        }
    }

    /**
     * 锁匠工单
     */
    @ResponseBody
    @RequestMapping(value = "/upDateOrderDoing")
    public AppResult upDateOrderDoing(BaseRequestDto requestDto, HttpServletRequest request) {
        try {
            Orderinfo orderinfo = new Orderinfo();
            orderinfo.setId(parseStringParam(request, "orderId"));
            orderinfo.setOrderState(210);
            orderinfoService.updateOrder(orderinfo);
        } catch (Exception e) {
            //异常出口
            logger.error("更新失败", e);
            return AppResultUtil.fail("更新失败");
        }
        return AppResultUtil.success("更新成功");
    }

    /**
     * 可提现工单列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectListByWithdraw")
    public BaseResponseDto selectListByWithdraw(BaseRequestDto requestDto, HttpServletRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        try {
            JSONObject jsonObject = orderinfoService.selectListByWithdraw(requestDto);

            baseResponseDto.setErrCode(1);
            baseResponseDto.setErrMsg("查询成功");
            baseResponseDto.setData(jsonObject);
        } catch (Exception e) {
            //异常出口
            logger.error("查询失败", e);
            baseResponseDto.setErrCode(0);
            baseResponseDto.setErrMsg("查询失败");
        }
        return baseResponseDto;
    }

    /**
     * 提现列表申诉
     */
    @ResponseBody
    @RequestMapping(value = "/withdrawCompain")
    public BaseResponseDto withdrawCompain(BaseRequestDto requestDto, HttpServletRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        try {
            Userinfo userinfo = TokenUtil.getCurrentUser();
            JSONObject requestObject = JSONObject.parseObject(requestDto.getData());
            String content = requestObject.getString("content");
            String orderId = requestObject.getString("orderId");
            if (StringUtil.isNull(content)) {
                baseResponseDto.setErrCode(0);
                baseResponseDto.setErrMsg("申诉内容不能为空");
                return baseResponseDto;
            } else if (StringUtil.isNull(orderId)) {
                baseResponseDto.setErrCode(0);
                baseResponseDto.setErrMsg("订单号不能为空");
                return baseResponseDto;
            }
            OrderinfoMoneyQuestion orderinfoMoneyQuestion = new OrderinfoMoneyQuestion();
            orderinfoMoneyQuestion.setMessageContent(content);
            orderinfoMoneyQuestion.setOrderInfoId(orderId);
            orderinfoMoneyQuestion.setUserId(userinfo.getId());
            JSONObject jsonObject = orderinfoMoneyQuestionService.saveOrderinfoMoneyQuestion(orderinfoMoneyQuestion);

            baseResponseDto.setErrCode(1);
            baseResponseDto.setErrMsg("申诉成功");
            baseResponseDto.setData(jsonObject);
        } catch (Exception e) {
            //异常出口
            logger.error("查询失败", e);
            baseResponseDto.setErrCode(0);
            baseResponseDto.setErrMsg("查询失败");
        }
        return baseResponseDto;
    }

}
