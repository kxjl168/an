/**
 * @(#)AddressServiceImpl.java 2019-01-28 11:26:27
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.plus.ManagerMessageMapper;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.service.ManagerMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:26:27
 * @since 1.0.0
 */
@Service
public class ManagerMessageServiceImpl implements ManagerMessageService {

    @Autowired
    private ManagerMessageMapper managerMessageMapper;

    @Override
    public Message insert(ManagerMessage managerMessage) {
        Message message = new Message();
        try {
            int result = managerMessageMapper.insert(managerMessage);
            message.setBol(result == 1 ? true : false);
            message.setMessage("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setBol(false);
            message.setMessage("新增失败");
        }
        return message;
    }

    @Override
    public Message deleteByPrimaryKey(String id) {
        Message message = new Message();
        try {
            int result = managerMessageMapper.deleteByPrimaryKey(id);
            message.setBol(result == 1 ? true : false);
            message.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setBol(false);
            message.setMessage("删除失败");
        }
        return message;
    }

    @Override
    public Message updateByPrimaryKey(ManagerMessage managerMessage) {
        Message message = new Message();
        try {
            int result = managerMessageMapper.updateByPrimaryKey(managerMessage);
            message.setBol(result == 1 ? true : false);
            message.setMessage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setBol(false);
            message.setMessage("更新失败");
        }
        return message;
    }

    @Override
    public Message updateMessageReadByUserAndType(ManagerMessage managerMessage) {
        Message message = new Message();
        try {
            int result = managerMessageMapper.updateMessageReadByUserAndType(managerMessage);
            message.setBol(result == 1 ? true : false);
            message.setMessage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setBol(false);
            message.setMessage("更新失败");
        }
        return message;
    }

    @Override
    public List<Map<String, String>> selectUnReadMsgCountByUser(ManagerMessage managerMessage) {
        List<Map<String, String>> list;
        try {
            list = managerMessageMapper.selectUnReadMsgCountByUser(managerMessage);
            return list == null ? new ArrayList<Map<String, String>>() : list;
        } catch (Exception e) {
            return new ArrayList<Map<String, String>>();
        }
    }

    @Override
    public List<Map<String, String>> selectManagerMessageInsertUserList(String messageType) {
        List<Map<String, String>> list;
        try {
            list = managerMessageMapper.selectManagerMessageInsertUserList(messageType);
            return list == null ? new ArrayList<Map<String, String>>() : list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Map<String, String>>();
        }
    }
}
