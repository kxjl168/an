/**
 * @(#)LockOrderCountService.java 2019/5/24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.kxjl.tasktransferplat.service;

import java.util.List;
import java.util.Map;

import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockMStatistics;

/**
 *
 *
 *@author shurui
 *@date 2019/5/24
 */
public interface LockOrderCountService {

    List<Map> selectLockOrderBadRatio(String createTime);

    List<LockMStatistics> selectLockMouthStatistics(LockCMStistics item);
}
