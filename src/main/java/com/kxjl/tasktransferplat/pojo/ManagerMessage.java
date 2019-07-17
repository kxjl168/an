package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: zhangyong
 * @Description:
 * @Data: Created in 15:55 2019/5/28
 */
@Data
@TableName("manager_message")
public class ManagerMessage {

    /**
     * 消息Id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已读 0未读  1已读
     */
    private int isRead;
    /**
     * 消息类型  0：系统消息  1：待接单  2：待回访  3：审核加钱  4：取消工单  5：审核提现
     */
    private String type;
    /**
     * 创建者id
     */
    private String createrId;
    /**
     * 消息状态 1:可用，0：禁用，2：删除
     */
    private String dataState;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 已读时间
     */
    private String readTime;

}
