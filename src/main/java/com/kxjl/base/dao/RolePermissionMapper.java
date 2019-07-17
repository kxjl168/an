/**
 * @(#)RolePermissionMapper.java  2018-11-14
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.dao;

import com.kxjl.base.pojo.Role;
import com.kxjl.base.pojo.RolePermission;

/**
 * 角色权限
 * RolePermissionMapper.java.
 * 
 * @author zj
* @version 1.0.1 2018年11月14日
* @revision zj 2018年11月14日
* @since 1.0.1
 */
public interface RolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
    
    /***
     * 删除角色的所有权限
     * @param role
     * @author zj
     * @date 2018年5月10日
     */
   int  deleteRolePerssion(Role role);
}