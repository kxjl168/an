/**
 * @(#)ManagerRoleMapper.java  2018-11-14
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.dao;

import java.util.Map;

import com.kxjl.base.pojo.Manager;
import com.kxjl.base.pojo.ManagerRole;

/**
 * 后台管理员-role
 * ManagerRoleMapper.java.
 * 
 * @author zj
* @version 1.0.1 2018年11月14日
* @revision zj 2018年11月14日
* @since 1.0.1
 */
public interface ManagerRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ManagerRole record);

    int insertSelective(ManagerRole record);

    ManagerRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManagerRole record);

    int updateByPrimaryKey(ManagerRole record);
    
 
    int update(Map ManagerRole);
    
    
    /**
     * 删除用户的角色
     * @param Manager
     * @return
     * @author zj
     * @date 2018年5月10日
     */
    int deleteManagerRole(Manager Manager);
}