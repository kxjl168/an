/**
 * @(#)AddressMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.util.AppResult;
import com.kxjl.tasktransferplat.pojo.Address;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyong
 * @version 1.0.0 2019-05-28 15:53:03
 * @since 1.0.0
 */
public interface ManagerMessageMapper extends BaseMapper<Address> {

    /**
     * 插入消息
     * @param managerMessage
     * @return
     */
    int insert(ManagerMessage managerMessage);

    /**
     * 根据消息id物理删除消息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据消息id更新消息
     * @param managerMessage
     * @return
     */
    int updateByPrimaryKey(ManagerMessage managerMessage);
    /**
     * 根据根据用户和消息类型更新消息为已读消息
     * @param managerMessage
     * @return
     */
    int updateMessageReadByUserAndType(ManagerMessage managerMessage);
    /**
     * 根据用户分类查询未读消息数量
     * @param managerMessage
     * @return
     */
    List<Map<String,String>> selectUnReadMsgCountByUser(ManagerMessage managerMessage);
    /**
     * 根据消息类型查询对应用户id集合
     * @param messageType
     * @return
     */
    List<Map<String,String>> selectManagerMessageInsertUserList(String messageType);

}
