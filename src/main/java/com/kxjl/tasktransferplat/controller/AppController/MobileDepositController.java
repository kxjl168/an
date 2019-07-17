/**
 * @(#)MobileDepositController.java 2019/1/31 13:28
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAdd;
import com.kxjl.tasktransferplat.service.UserDepositService;
import com.kxjl.tasktransferplat.service.UserinfoAddService;
import com.kxjl.tasktransferplat.service.UserinfoMoneyDetailService;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.kxjl.tasktransferplat.service.UserDepositService.INSUFFICIENT_BALANCE;
import static com.kxjl.tasktransferplat.service.UserDepositService.SUCCESS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/31 13:28
 * @since 1.0.0
 */
@RestController
@RequestMapping("/interface/userDeposit")
public class MobileDepositController extends AppBaseController {

    @Autowired
    private UserDepositService userDepositService;

    @Autowired
    private UserinfoAddService userinfoAddService;

    @Autowired
    private UserinfoMoneyDetailService userinfoMoneyDetailService;

    private static final Logger logger = LoggerFactory.getLogger(MobileDepositController.class);

    /**
     * 根据余额申请提现
     *
     * @param requestDto
     * @return
     */
    @RequestMapping("/withdraw")
    public BaseResponseDto applyForDeposit(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto depositResponse = new BaseResponseDto();
        depositResponse.setErrCode(1);
        depositResponse.setErrMsg("成功");
        Userinfo user = TokenUtil.getCurrentUser();
        UserinfoAdd userinfoAdd;
        userinfoAdd = userinfoAddService.selectById(user.getId());
        if (userinfoAdd != null) {
            try {
                int code = userDepositService.applyForDeposit(requestDto, userinfoAdd);
                if (code == SUCCESS) {
                    return depositResponse;
                }
                if (code == INSUFFICIENT_BALANCE) {
                    depositResponse.setErrCode(0);
                    depositResponse.setErrMsg("可提现余额不足");
                }
            } catch (Exception e) {
                logger.error("申请提现失败", e);
                depositResponse.setErrMsg("失败");
                depositResponse.setErrCode(2);
            }
        } else {
            logger.error("未绑定银行卡");
            depositResponse.setErrMsg("未绑定银行卡");
            depositResponse.setErrCode(4);
        }

        return depositResponse;
    }

    /**
     * 根据订单申请提现
     *
     * @param requestDto
     * @return
     */
    @RequestMapping("/withdrawByOrder")
    public AppResult applyForDepositByOrder(BaseRequestDto requestDto) {
        AppResult appResult = new AppResult();
        Userinfo user = TokenUtil.getCurrentUser();
        UserinfoAdd userinfoAdd;
        userinfoAdd = userinfoAddService.selectById(user.getId());
        if (userinfoAdd != null) {
            try {
                appResult = userDepositService.applyForDepositByOrder(requestDto, userinfoAdd);
            } catch (Exception e) {
                logger.error("申请提现失败", e);
                appResult.setErrMsg("申请提现失败");
                appResult.setErrCode(2+"");
            }
        } else {
            logger.error("未绑定银行卡");
            appResult.setErrMsg("未绑定银行卡");
            appResult.setErrCode(4+"");
        }
        return appResult;
    }

    /**
     * 绑定银行卡
     *
     * @param requestDto
     * @return
     */
    @RequestMapping("/bindInfo")
    public BaseResponseDto bindInfo(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto depositResponse = new BaseResponseDto();
        depositResponse.setErrCode(1);
        depositResponse.setErrMsg("成功");
        try {
            int code = userDepositService.bindInfo(requestDto);
        } catch (Exception e) {
            logger.error("信息绑定失败", e);
            depositResponse.setErrMsg("信息绑定失败");
            depositResponse.setErrCode(2);
        }
        return depositResponse;
    }

    /**
     * 显示锁匠可提现金额
     *
     * @param requestDto
     * @return
     */
    @RequestMapping("/queryAvailableMoney")
    public BaseResponseDto queryAvailableMoney(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto responseDto = new BaseResponseDto();
        responseDto.setErrCode(1);
        responseDto.setErrMsg("成功");
        try {
            Map resultMap = userDepositService.queryAvailableMoney(requestDto);
            responseDto.setData(resultMap);
        } catch (Exception e) {
            logger.error("查询可提现金额失败", e);
            responseDto.setErrMsg("失败");
            responseDto.setErrCode(2);
        }
        return responseDto;
    }

    /**
     * 显示锁匠提现记录列表
     *
     * @return
     */
    @RequestMapping("/listDriverDeposit")
    public AppResult listDriverDeposit(HttpServletRequest request) {
        //封装响应
        BaseResponseDto responseDto = new BaseResponseDto();
        responseDto.setErrCode(1);
        responseDto.setErrMsg("成功");
        try {
            int pageSize = parseIntegerParam(request, "pageSize");
            int pageNum = parseIntegerParam(request, "pageNum");
            Page page = new Page();
            page.setSize(pageSize);
            page.setCurrent(pageNum);
            IPage<Map<String, Object>> userinfoMoneyDetails = userinfoMoneyDetailService.listDriver(page);
            List<Map<String, Object>> userinfoMoneyDetails1 = userinfoMoneyDetails.getRecords();
            HashMap mp = new HashMap<>();
            mp.put("records", userinfoMoneyDetails1);
            mp.put("current", page.getSize());
            mp.put("size", page.getCurrent());
            mp.put("pages", page.getPages());
            mp.put("total", page.getTotal());

            return AppResultUtil.success(mp);


        } catch (Exception e) {
            logger.error("查询提现记录失败", e);
            responseDto.setErrMsg("失败");
            responseDto.setErrCode(2);
        }
        return AppResultUtil.fail();
    }

    /**
     * 显示锁匠提现审核记录列表
     *
     * @return
     */
    @RequestMapping("/depositListByOrder")
    public AppResult depositListByOrder(HttpServletRequest request) {
        //封装响应
        BaseResponseDto responseDto = new BaseResponseDto();
        responseDto.setErrCode(1);
        responseDto.setErrMsg("成功");
        try {
            int pageSize = parseIntegerParam(request, "pageSize");
            int pageNum = parseIntegerParam(request, "pageNum");
            Page page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            Userinfo userinfo = TokenUtil.getCurrentUser();
            IPage<Map<String, Object>> data = userDepositService.depositListByOrder(page, userinfo.getId());

            if (null != data.getRecords() && data.getRecords().size() > 0) {
                for (int i = 0; i < data.getRecords().size(); i++) {
                    String serverDes = "";
                    if (null != data.getRecords().get(i).get("ServerType").toString()) {
                        String[] typeList = data.getRecords().get(i).get("ServerType").toString().split(",");
                        Arrays.sort(typeList); //首先对数组排序
                        if (Arrays.binarySearch(typeList, "0") > -1) {
                            serverDes += "安装,";
                        }
                        if (Arrays.binarySearch(typeList, "1") > -1) {
                            serverDes += "维修,";
                        }
                        if (Arrays.binarySearch(typeList, "2") > -1) {
                            serverDes += "开锁,";
                        }
                        if (Arrays.binarySearch(typeList, "3") > -1) {
                            serverDes += "特殊门改造,";
                        }
                        if (Arrays.binarySearch(typeList, "4") > -1) {
                            serverDes += "测量,";
                        }
                        if (Arrays.binarySearch(typeList, "5") > -1) {
                            serverDes += "猫眼安装,";
                        }
                        if (Arrays.binarySearch(typeList, "6") > -1) {
                            serverDes += "换锁,";
                        }
                        if (Arrays.binarySearch(typeList, "7") > -1) {
                            serverDes += "工程安装,";
                        }
                        if (Arrays.binarySearch(typeList, "8") > -1) {
                            serverDes += "工程维修,";
                        }
                        if (Arrays.binarySearch(typeList, "19") > -1) {
                            serverDes += "其他,";
                        }
                    }
                    if(serverDes.length()>0){
                        serverDes = serverDes.substring(0,serverDes.length()-1);
                    }
                    data.getRecords().get(i).put("serverDes",serverDes);
                }
            }

            HashMap mp = new HashMap<>();
            mp.put("records", data.getRecords());
            mp.put("total", data.getPages());
            return AppResultUtil.success(mp);
        } catch (Exception e) {
            logger.error("查询提现记录失败", e);
            responseDto.setErrMsg("失败");
            responseDto.setErrCode(2);
        }
        return AppResultUtil.fail();
    }

    /**
     * 申请提现
     *
     * @param requestDto
     * @return
     */
    @RequestMapping("/withdraw2")
    public BaseResponseDto withdraw(BaseRequestDto requestDto) {
        //封装响应
        BaseResponseDto depositResponse = new BaseResponseDto();
        depositResponse.setErrCode(1);
        depositResponse.setErrMsg("成功");
        Userinfo user = TokenUtil.getCurrentUser();
        try {
            int code = userDepositService.applicationForWithdraw(requestDto, user);
            if (code == SUCCESS) {
                return depositResponse;
            }
        } catch (Exception e) {
            logger.error("申请提现失败", e);
            depositResponse.setErrMsg("失败");
            depositResponse.setErrCode(2);
        }
        return depositResponse;
    }
}
