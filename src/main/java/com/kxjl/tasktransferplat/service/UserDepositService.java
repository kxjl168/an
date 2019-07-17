/**
 * @(#)UserDepositService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.util.AppResult;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.UserDeposit;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAdd;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface UserDepositService {

    /**
     * 申请成功
     */
    Integer SUCCESS = 1;

    /**
     * 余额不足
     */

    Integer INSUFFICIENT_BALANCE = 2;

    /**
     * 用户根据余额申请提现
     */
    Integer applyForDeposit (BaseRequestDto requestDto, UserinfoAdd userinfoAdd);

    /**
     * 用户根据订单申请提现
     */
    AppResult applyForDepositByOrder (BaseRequestDto requestDto, UserinfoAdd userinfoAdd);

    /**
     * 显示司机可提现金额
     * @param requestDto
     * @return
     */
    Map queryAvailableMoney (BaseRequestDto requestDto);


    /**
     * 司机提现记录显示
     * @param requestDto
     * @return
     */
    List listDriverDeposit (BaseRequestDto requestDto);

    /**
     * 绑定用户提现信息
     * @param requestDto
     * @return
     */
    Integer bindInfo(BaseRequestDto requestDto);

    /**
     * 根据条件查询提现列表
     * @param userDeposit
     * @return
     */
    List<UserDeposit> depositList(UserDeposit userDeposit);

    /**
     * 提现审核通过或者转账成功
     * @param userDeposit
     */
    void pass(UserDeposit userDeposit) throws Exception;

    /**
     * 提现审核不通过或者转账失败
     * @param userDeposit
     */
    void fail(UserDeposit userDeposit) throws Exception;

    /**
     * 锁匠提现
     * @param requestDto
     * @param user
     * @return
     */
    int applicationForWithdraw(BaseRequestDto requestDto, Userinfo user);

    /**
     * 根据订单提现审核列表
     * @param userId
     * @return
     */
    IPage<Map<String,Object>> depositListByOrder(Page page, Long userId);
    
    
   /**
    * 提现详情
    * @param id
    * @return
    * @author zj
    * @date 2019年6月12日
    */
    UserDeposit loadUserDepositById(String id);

    /**
     * 根据orderNo获取提现
     * @param orderNo
     * @return
     * @author zj
     * @date 2019年6月20日
     */
    UserDeposit selectUserDepositByOrderNo(String orderNo);

    
    /**
     * 根据orderid获取提现
     * @param orderId
     * @return
     * @author zj
     * @date 2019年6月20日
     */
    public UserDeposit selectUserDepositByOrderId(String orderId) ;
    
    int updateUserDepositStatus(UserDeposit userDeposit);

    /**
     * 锁匠报表导出
     * @param userDeposit
     * @return
     */
    List<Map<String,Object>> userDepositExcel(UserDeposit userDeposit);
}
