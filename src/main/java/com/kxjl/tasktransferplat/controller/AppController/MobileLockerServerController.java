/**
 * @(#)MobileLockerServerController.java 2019/5/27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.controller.AppController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.service.MobileLockerServerService;

/**
 * 锁匠上门服务
 *
 *@author shurui
 *@date 2019/5/27
 */
@Slf4j
@RestController
@RequestMapping("/interface/LockerServer")
public class MobileLockerServerController {


    @Autowired
    private MobileLockerServerService mobileLockerServerService;

    /**
     * 登记预约上门时间
     * @param requestDto
     * @return
     */
    @RequestMapping("/registerAppointmentTime")
    public String registerAppointmentTime(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("登记成功");
        String resultString = "";
        try {
            mobileLockerServerService.registerAppointmentTime(requestDto);
        } catch (Exception e) {
            log.error("登记预约上门时间", e);
            orderListResponse.setErrMsg("登记失败");
            orderListResponse.setErrCode(2);
        }
        return resultString;
    }


    /**
     * 锁匠工单开始签到打卡上传着装照片（服务前签到）
     * @param requestDto
     * @return
     */
    @RequestMapping("/beforeSererSign")
    public String beforeSererSign(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("签到成功");
        String resultString = "";
        try {
            mobileLockerServerService.beforeSererSign(requestDto);
        } catch (Exception e) {
            log.error("锁匠工单服务前签到失败", e);
            orderListResponse.setErrMsg("签到失败");
            orderListResponse.setErrCode(2);
        }
        return resultString;
    }


    /**
     * 锁匠工单安装前签到打卡上传产品图片（安装前签到）
     * @param requestDto
     * @return
     */
    @RequestMapping("/beforeInstallSign")
    public String beforeInstallSign(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("签到成功");
        String resultString = "";
        try {
            mobileLockerServerService.beforeInstallSign(requestDto);
        } catch (Exception e) {
            log.error("锁匠工单安装前签到失败", e);
            orderListResponse.setErrMsg("签到失败");
            orderListResponse.setErrCode(2);
        }
        return resultString;
    }


    /**
     * 锁匠工单安装后完工签到打卡上传产品图片（安装后完工签到）
     * @param requestDto
     * @return
     */
    @RequestMapping("/afterInstallSign")
    public String afterInstallSign(BaseRequestDto requestDto) {
        BaseResponseDto orderListResponse = new BaseResponseDto();
        orderListResponse.setErrCode(1);
        orderListResponse.setErrMsg("签到成功");
        String resultString = "";
        try {
            mobileLockerServerService.afterInstallSign(requestDto);
        } catch (Exception e) {
            log.error("锁匠工单安装后完工签到失败", e);
            orderListResponse.setErrMsg("签到失败");
            orderListResponse.setErrCode(2);
        }
        return resultString;
    }
}
