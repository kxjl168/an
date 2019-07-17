package com.kxjl.tasktransferplat.pojo;

public class Message {
    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 发送这条消息的人的id，关联manager表
     */
    private String sender;

    /**
     * 消息接收人id，关联userinfo表
     */
    private Long receiver;

    /**
     * 消息题目
     */
    private String messageTitle;

    /**
     * 消息体
     */
    private String messageContent;

    /**
     * 消息类型，1：系统消息，2：工单消息
     */
    private Integer messageType;

    /**
     * 消息关联的工单id
     */
    private String orderInfoId;

    /**
     * 消息发送时间
     */
    private String createTime;

    /**
     * 是否已读，0：否，1：是
     */
    private Integer hasRead;

    /**
     * 消息id
     * @return id 消息id
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * 消息id
     * @param id 消息id
     */
    public void setMessageId(Long id) {
        this.messageId = id;
    }

    /**
     * 发送这条消息的人的id，关联manager表
     * @return sender 发送这条消息的人的id，关联manager表
     */
    public String getSender() {
        return sender;
    }

    /**
     * 发送这条消息的人的id，关联manager表
     * @param sender 发送这条消息的人的id，关联manager表
     */
    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    /**
     * 消息接收人id，关联userinfo表
     * @return receiver 消息接收人id，关联userinfo表
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * 消息接收人id，关联userinfo表
     * @param receiver 消息接收人id，关联userinfo表
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * 消息题目
     * @return messageTitle 消息题目
     */
    public String getMessageTitle() {
        return messageTitle;
    }

    /**
     * 消息题目
     * @param messageTitle 消息题目
     */
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle == null ? null : messageTitle.trim();
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
     * 消息类型，1：系统消息，2：工单消息
     * @return messageType 消息类型，1：系统消息，2：工单消息
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 消息类型，1：系统消息，2：工单消息
     * @param messageType 消息类型，1：系统消息，2：工单消息
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
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
        this.orderInfoId = orderInfoId;
    }

    /**
     * 消息发送时间
     * @return createTime 消息发送时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 消息发送时间
     * @param createTime 消息发送时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 是否已读，0：否，1：是
     * @return hasRead 是否已读，0：否，1：是
     */
    public Integer getHasRead() {
        return hasRead;
    }

    /**
     * 是否已读，0：否，1：是
     * @param hasRead 是否已读，0：否，1：是
     */
    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }
}
