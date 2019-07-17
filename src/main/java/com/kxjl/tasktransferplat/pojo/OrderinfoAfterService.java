package com.kxjl.tasktransferplat.pojo;

public class OrderinfoAfterService {
    /**
     * 主键
     */
    private String id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户密码，
     */
    private String password;

    /**
     * 创建时间（insert 触发器 确定）
     */
    private String createTime;

    //开始结束时间（用于查询）
    private String startTime;
    private String endTime;


    /**
     * 上次更新时间（update 触发器 确定）
     */
    private String updateTime;

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     */
    private Integer dataState;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系人称呼
     */
    private String nickname;

    /**
     * 联系人地址
     */
    private String address;

    /**
     * 售后状态，0：已提交，1：客服已确认，2：已安排师傅处理，3：处理完成
     */
    private Integer serviceState;

    /**
     * 主键
     * @return Id 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 订单id
     * @return OrderId 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单id
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 用户密码，
     * @return Password 用户密码，
     */
    public String getPassword() {
        return password;
    }

    /**
     * 用户密码，
     * @param password 用户密码，
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 创建时间（insert 触发器 确定）
     * @return CreateTime 创建时间（insert 触发器 确定）
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间（insert 触发器 确定）
     * @param createTime 创建时间（insert 触发器 确定）
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @return UpdateTime 上次更新时间（update 触发器 确定）
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @param updateTime 上次更新时间（update 触发器 确定）
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @return DataState 数据状态，1：可用，0：禁用，2：删除
     */
    public Integer getDataState() {
        return dataState;
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @param dataState 数据状态，1：可用，0：禁用，2：删除
     */
    public void setDataState(Integer dataState) {
        this.dataState = dataState;
    }

    /**
     * 问题描述
     * @return description 问题描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 问题描述
     * @param description 问题描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 联系电话
     * @return telephone 联系电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 联系电话
     * @param telephone 联系电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * 联系人称呼
     * @return nickname 联系人称呼
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 联系人称呼
     * @param nickname 联系人称呼
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 联系人地址
     * @return address 联系人地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 联系人地址
     * @param address 联系人地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 售后状态，0：已提交，1：客服已确认，2：已安排师傅处理，3：处理完成
     * @return serviceState 售后状态，0：已提交，1：客服已确认，2：已安排师傅处理，3：处理完成
     */
    public Integer getServiceState() {
        return serviceState;
    }

    /**
     * 售后状态，0：已提交，1：客服已确认，2：已安排师傅处理，3：处理完成
     * @param serviceState 售后状态，0：已提交，1：客服已确认，2：已安排师傅处理，3：处理完成
     */
    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}