/**
 * @(#)MobileOrderController.java 2019/1/28 15:23
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kxjl.base.base.PageInfo;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.OrderInfoOperateProxy;
import com.kxjl.tasktransferplat.service.OrderInfoOperateService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.util.TokenUtil;

import java.util.List;
import java.util.Map;

/** 锁企订单操作
 *
 * @author sr
 * @version 1.0.0 2019/4/15
 * @since 1.0.0
 */
@RestController
@RequestMapping("/interface/LCompanyOrder")
public class MobileLockCompanyOrderController extends AppBaseController {

    @Autowired
    private OrderinfoService orderinfoService;

    @Autowired
    private OrderInfoOperateService orderInfoOperateService;

    @Autowired
    private OrderInfoOperateProxy orderInfoOperateProxy;


    private static final Logger logger = LoggerFactory.getLogger(MobileLockCompanyOrderController.class);


    /** 锁企添加订单
     *
     * @param orderInfo （必填：预约时间，客户姓名，客户电话，客户详细地址，客户所在地区码 ,服务类别（安装 = 0,维修 = 1,开锁 = 2,特殊们改造 = 3,测量 = 4, 猫眼安装 = 5）       选填：创建备注）
     * @return
     */
    @RequestMapping("/createOrder")
    public BaseResponseDto createOrder(Orderinfo orderInfo) {
        //封装响应
        BaseResponseDto orderAcceptResponse = new BaseResponseDto();

        //获取当前用户ID，锁企ID，订单id
        Manager manager = TokenUtil.getCurrentManager();
        orderInfo.setSellerId(manager.getCompanyId());
        orderInfo.setId(UUIDUtil.getUUID());
        try {
            orderInfoOperateProxy.createOrder(orderInfo,manager);
            orderAcceptResponse.setErrMsg("成功");
            orderAcceptResponse.setErrCode(1);
        }catch (Exception e){
            e.printStackTrace();
            orderAcceptResponse.setErrMsg("失败");
            orderAcceptResponse.setErrCode(2);
        }
        return orderAcceptResponse;
    }


    /** 锁企查询订单列表
     *
     * @param orderinfo（type: 1 待审核阶段  2 待作业阶段  3 待回访阶段  4 待结账阶段  5 终止状态阶段）
     * @return
     */
    @RequestMapping("/selectOrderList")
    public BaseResponseDto selectOrderList(Orderinfo orderinfo, PageInfo pageInfo) {
        //封装响应
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        //获取当前用户的锁企ID,并添加订单所属锁企ID
       orderinfo.setSellerId(TokenUtil.getCurrentManager().getCompanyId());//添加订单所属锁企ID

        try {
            List<Orderinfo> orderinfos = orderinfoService.selectOrderinfoList(orderinfo);//锁企查询订单
            baseResponseDto.setData(orderinfos);
            baseResponseDto.setErrMsg("成功");
            baseResponseDto.setErrCode(1);
        }catch (Exception e){
            e.printStackTrace();
            baseResponseDto.setErrMsg("失败");
            baseResponseDto.setErrCode(2);
        }
        return baseResponseDto;
    }


    /** 锁企订单详情（包含订单流转记录）
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/selectOrderDetail")
    public BaseResponseDto selectOrderDetail(String orderId) {
        //封装响应
        BaseResponseDto baseResponseDto = new BaseResponseDto();

        //入参判断
        if(orderId == null || orderId.equals("")) {
            baseResponseDto.setErrMsg("缺少必要参数");
            baseResponseDto.setErrCode(2);
            return baseResponseDto;
        }

        try {
            Map map = orderinfoService.selectOrderinfoDetail(orderId);
            baseResponseDto.setData(map);
            baseResponseDto.setErrMsg("成功");
            baseResponseDto.setErrCode(1);
        }catch (Exception e){
            e.printStackTrace();
            baseResponseDto.setErrMsg("失败");
            baseResponseDto.setErrCode(2);
        }
        return baseResponseDto;
    }




    /** 锁企查询订单统计（月时间- 查询不同状态订单统计 / type: 1 待审核阶段  2 待作业阶段  3 待回访阶段  4 待结账阶段  5 终止状态阶段）
     *
     * @param month
     * @param orderinfo
     * @return
     */
/*    @RequestMapping("/selectOrderCount")
    public BaseResponseDto selectOrderCount(String month,Orderinfo orderinfo) {
        //封装响应
        BaseResponseDto orderAddPriceResponse = new BaseResponseDto();

        //获取当前用户的锁企ID
        //TODO
//        orderinfo.setCompanyId();//添加订单所属锁企ID

        try {

            orderAddPriceResponse.setErrMsg("成功");
            orderAddPriceResponse.setErrCode(1);
        }catch (Exception e){
            e.printStackTrace();
            orderAddPriceResponse.setErrMsg("失败");
            orderAddPriceResponse.setErrCode(2);
        }
        return orderAddPriceResponse;
    }*/


}
