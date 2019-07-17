/**
 * @(#)LockOrderCountMapper.java 2019/5/24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.kxjl.tasktransferplat.dao.plus;

import org.apache.ibatis.annotations.Param;

import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockMStatistics;

import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/24
 */
public interface LockOrderCountMapper {


    /**
     * 查询锁匠工单不良率
     * @param createTime
     * @return
     */
    List<Map> selectLockOrderBadRatio(@Param("createTime") String createTime);


    List<LockMStatistics> selectLockMouthStatisticsList(LockCMStistics item);
}
