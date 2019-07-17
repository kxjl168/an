/**
 * @(#)MobileLockerServerService.java 2019/5/27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.kxjl.tasktransferplat.service;

import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;

/**
 *
 *
 *@author shurui
 *@date 2019/5/27
 */
public interface MobileLockerServerService {


    /**
     * 登记预约上门时间
     * @param requestDto
     */
    void registerAppointmentTime(BaseRequestDto requestDto);

    /**
     * 服务前签到
     * @param requestDto
     */
    void beforeSererSign(BaseRequestDto requestDto);


    /**
     * 安装前签到
     * @param requestDto
     */
    void beforeInstallSign(BaseRequestDto requestDto);


    /**
     * 安装后完工签到
     * @param requestDto
     */
    void afterInstallSign(BaseRequestDto requestDto);


}
