/**
 * @(#)ManagerMapper.java  2018-11-14
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.dao;


import org.apache.ibatis.annotations.Param;

import com.kxjl.base.pojo.Manager;

import java.util.List;
import java.util.Map;

/**
 * 后台管理mapper
 * ManagerMapper.java.
 * 
 * @author zj
* @version 1.0.1 2018年11月14日
* @revision zj 2018年11月14日
* @since 1.0.1
 */
public interface ManagerMapper {
	
	
	

	/**
	 * 登陆
	 * @param user
	 * @return
	 * @author zj
	 * @date 2018年11月9日
	 */
	Manager getLoginUser(Manager user);

    /**
     *
     * @param token
     * @return
     */
	Manager getLoginUserByToken(String token);


	
	
    int deleteLockCompanyByPrimaryKey(String id);

    int dropLockCompanyByPrimaryKey(String id);

    int resetLockCompanyByPrimaryKey(String id);

    int deleteByPrimaryKey(String id);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKeyWithBLOBs(Manager record);

    int updateByPrimaryKey(Manager record);

    List<Manager> selectManagerByManager(Manager Manager);

    List<Manager> selectLockCompanyByManager(Manager Manager);

    List<Manager> getAdminManagers();


    List<Manager> selectManagerByNamePwd(@Param("name") String name, @Param("password") String password);
    List<Manager> selectManagerByToken(String token);
    List<Manager> selectManagerByPhone(String phone);

    List<Map> selectManagerByMap(Map conditions);

    List<Map> selectManagerList();
    
    /**
     * 检查称呼是否重复
     * @param query
     * @return
     * @author zj
     * @date 2018年8月23日
     */
    int hasNickName(Manager query);
}