package com.kxjl.tasktransferplat.pojo;

public class OrderinfoMoneyQuestion {
    /**
     * id
     */
    private String id;

    /**
     * 发送这条消息的锁匠的id，关联t_userinfo表
     */
    private Long userId;

    /**
     * 消息关联的工单id
     */
    private String orderInfoId;

    /**
     * 消息体
     */
    private String messageContent;

    /**
     * 确认备注
     */
    private String backContent;

    /**
     * 完成备注
     */
    private String doneContent;

    /**
     * 时间
     */
    private String createTime;

    /**
     * 状态，0：已提交待处理，1：财务已处理，2：财务不予处理
     */
    private Integer serviceState;

    /**
     * id
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 发送这条消息的锁匠的id，关联t_userinfo表
     * @return userId 发送这条消息的锁匠的id，关联t_userinfo表
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 发送这条消息的锁匠的id，关联t_userinfo表
     * @param userId 发送这条消息的锁匠的id，关联t_userinfo表
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 消息关联的工单id
     * @return orderInfoId 消息关联的工单id
     */
    public String getOrderInfoId() {
        return orderInfoId;
    }

    /**
     * 消息关联的工单id
     * @param orderInfoId 消息关联的工单id
     */
    public void setOrderInfoId(String orderInfoId) {
        this.orderInfoId = orderInfoId == null ? null : orderInfoId.trim();
    }

    /**
     * 消息体
     * @return messageContent 消息体
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 消息体
     * @param messageContent 消息体
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    /**
     * 确认备注
     * @return backContent 确认备注
     */
    public String getBackContent() {
        return backContent;
    }

    /**
     * 确认备注
     * @param backContent 确认备注
     */
    public void setBackContent(String backContent) {
        this.backContent = backContent == null ? null : backContent.trim();
    }

    /**
     * 完成备注
     * @return doneContent 完成备注
     */
    public String getDoneContent() {
        return doneContent;
    }

    /**
     * 完成备注
     * @param doneContent 完成备注
     */
    public void setDoneContent(String doneContent) {
        this.doneContent = doneContent == null ? null : doneContent.trim();
    }

    /**
     * 时间
     * @return createTime 时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 时间
     * @param createTime 时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 状态，0：已提交待处理，1：财务已处理，2：财务不予处理
     * @return serviceState 状态，0：已提交待处理，1：财务已处理，2：财务不予处理
     */
    public Integer getServiceState() {
        return serviceState;
    }

    /**
     * 状态，0：已提交待处理，1：财务已处理，2：财务不予处理
     * @param serviceState 状态，0：已提交待处理，1：财务已处理，2：财务不予处理
     */
    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
    }
}