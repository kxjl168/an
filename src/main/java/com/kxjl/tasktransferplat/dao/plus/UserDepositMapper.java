/**
 * @(#)UserDepositMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserDeposit;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface UserDepositMapper  extends BaseMapper<UserDeposit> {

    /**
     * 分页查询
     * @param page
     * @param userDeposit
     * @return
     */
    IPage<UserDeposit> selectPage(Page page, UserDeposit userDeposit);

    /**
     * 查询
     * @param userDeposit
     * @return
     */
    List<UserDeposit> depositList(UserDeposit userDeposit);

    /**
     * 锁匠报表导出
     * @param userDeposit
     * @return
     */
    List<Map<String,Object>> userDepositExcel(UserDeposit userDeposit);

    /**
     * 批量插入提现申请
     * @param userDeposit
     */
     Integer insertBatch(List<UserDeposit> userDeposit);

    /**
     * 查询工单提现列表
     * @param userId
     * @return
     */
    IPage<Map<String,Object>> depositListByOrder(@Param("page")Page page,@Param("userId") Long userId);
    
    /**
     * 修改工单相关价格后，根据工单实际价格，跟新对应的提现申请中的价格
     * @param userDeposit
     * @return
     * @author zj
     * @date 2019年6月11日
     */
    Integer updateDepositMoneyByOrder(UserDeposit userDeposit);

    /**
     * 根据订单号搜索提现信息
     * @param orderNo
     * @return
     */
    UserDeposit selectUserDepositByOrderNo(@Param("orderNo") String orderNo);

    Integer updateUserDepositStatus(UserDeposit userDeposit);
    
}
