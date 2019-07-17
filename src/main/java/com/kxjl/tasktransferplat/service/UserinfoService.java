/*
 * @(#)UserinfoService.java
 * @author: zhangJ
 * @Date: 2018/12/11 09:02
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;

import java.util.List;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 锁匠.
 * 
 * @author zj
* @version 1.0.1 2018年12月11日
* @revision zj 2018年12月11日
* @since 1.0.1
 */
public interface UserinfoService {
    
	
	/**
	 * 检查是否有未处理工单， 如果为合伙人检查是否有下属锁匠
	 * 
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年5月23日
	 */
	public int CheckOrderAndLocker(Userinfo item);
	
	/**
	 * 启用锁匠
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年5月23日
	 */
	public Message ReUseUserinfo(Userinfo item) ;
	/**
	 * 停用锁匠
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年5月15日
	 */
	public int NoUseUserinfo(Userinfo item);
	/**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-01-29 10:29:15
     */
    int updateByPrimaryKeySelective(Userinfo record);

    /**
     * 新增
     */
    JSONObject saveUserinfo(Userinfo query);

    /**
     * 更新信息
     */
    JSONObject updateUserinfo(Userinfo query);


    /**
     * 模糊查询合伙人/锁匠，支持各条件查询，包括所在区域，作业区域，
     * @param query
     * @return
     * @author zj
     * @date 2019年5月16日
     */
    List<Userinfo> selectUserinfoList(Userinfo query);

    int deleteUserinfo(Userinfo query);
    


    /**
     * 获取锁匠/合伙人个人信息。 使用selectList查询
     * @param id
     * @return
     * @author zj
     * @date 2019年5月17日
     */
   	 Userinfo selectUserinfoById(Long id) ;

   	 /**
      * 接口 token
      * @param token
      * @return
      * @author zj
      * @date 2018年6月20日
      */
   	Userinfo getUserByToken( String token);

    /**
     * 待审核锁匠
     * @param item
     * @return
     */
    List<Userinfo> selectUnAuditUserinfoList(Userinfo item);

    /**
     * 锁匠评分
     * @param item
     * @return
     */
    List<Userinfo> selectUserinfoListByPoint(Userinfo item);

    /**
     * 查询锁匠的费用信息，实时查询，根据工单及审核记录
     * @param item
     * @return
     * @author zj
     * @date 2019年6月11日
     */
    List<Userinfo> selectListByMoney(Userinfo item);
    
    
    /**
     * 根据工单，审核状态，刷新锁匠账户余额及冻结金额数据
     * @param item
     * @author zj
     * @date 2019年6月11日
     */
    public void refreshUserMoneyInfo(Userinfo item)  throws Exception;
    
    /**
     * 查询锁匠的费用信息，实时查询，根据工单及审核记录,获取单人的信息，用户更新账户余额及冻结金额
     * @param item
     * @return
     * @author zj
     * @date 2019年6月11日
     */
    Userinfo getUserMoneyInfo(Userinfo item);


    /**
     * 手机号确认
     * @param userinfo
     * @return
     */
    List<Userinfo> selectUserinfoByPhone(Userinfo userinfo);

    /**解绑
     * @param id
     * @return
     */
    int untyingUserinfoById(Long id);

    /**
     * 根据工单所属公司查询锁匠
     * @param orderinfo
     * @return
     */
    List<Userinfo> selectLockerByOrderId(Orderinfo orderinfo);

    /**
     * 身份证号确认
     * @param userinfo
     * @return
     */
    List<Userinfo> selectUserinfoByIdCard(Userinfo userinfo);

    /**
     * 查询锁匠
     * @return
     */
    int countNewLock(String startdate,String enddate);

    /**
     * 查询锁匠
     * @param tuserinfo
     * @return
     */
    List<Userinfo> selectUserinfoByPhoneAll(Userinfo tuserinfo);

    /**
     * 将合伙人下的所有锁匠均设为自由锁匠
     * @param companyId
     * @return
     * @author zj
     * @date 2019年5月16日
     */
    Message updateCompanyNull(Long companyId);
    
   
    /**
     * 更新锁匠为自由锁匠
     * @param lockerId
     * @return
     * @author zj
     * @date 2019年5月16日
     */
    Message  updateLockerParterNull(Long lockerId);

    /**
     * 身份证全部
     * @param tuserinfo
     * @return
     */
    List<Userinfo> selectUserinfoByIdCardAll(Userinfo tuserinfo);

    /** 物理删除
     * @param item
     * @return
     */
    int deleteTrueUserinfo(Userinfo item);
    

    /**
     * 锁匠审核
     * @param item
     * @return
     * @author zj
     * @date 2019年5月14日
     */
    int updateAuditState(Userinfo item);
    
    
    /**
     * 重新提交审核
     * @param item
     * @return
     * @author zj
     * @date 2019年6月6日
     */
    int reDoAudit(Userinfo item);
    
    
    /**
     * 提交锁匠类型变更申请， 同时添加锁匠日志
     * @param item ,from=1,后台变更，  from=2,app提交申请
     * @return
     * @author zj
     * @date 2019年6月6日
     */
    Message userTypeChange(UserinfoAudit item,int from);
    
    
    /**
     * 查询指定行政区域下是否已经存在非当前编辑合伙人的其他合伙人。
     * 一个行政区县 district只能有一个合伙人
     * @param tuserinfo
     * @return
     * @author zj
     * @date 2019年5月14日
     */
    public List<Userinfo> selectJobAreaByUserAndDistrictIds(Userinfo tuserinfo);


    /**
     * 根据手机号和姓名模糊搜索合伙人代理人
     *
     * @param searchValue
     * @return
     */
    List<Userinfo> selectPartnerLockerLikeNameAndPhone(String searchValue);
    
    
}
