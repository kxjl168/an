package com.kxjl.video.pojo;

public class Receivepoliceinfo {
    /**
     * 接警人员id(主键)，不能为0
     */
    private String id;

    /**
     * 接警人员手机号，也是登陆接警人员名（唯一约束）
     */
    private String phone;

    /**
     * 接警人员密码
     */
    private String password;

    /**
     * 会话id
     */
    private String sessionKey;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间（insert 触发器 确定）
     */
    private String createTime;

    /**
     * 上次更新时间（update 触发器 确定）
     */
    private String uptimestamp;

    /**
     * 创建人（外键manager）
     */
    private String createUser;

    /**
     * 上次更新人（外键manager）
     */
    private String updateUser;

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     */
    private String dataState;

    /**
     * 性别（男，女），默认 男
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 工号
     */
    private String idNo;

    /**
     * 备注
     */
    private String des;

    /**
     * 头像md5
     */
    private String avatarUrl;

    /**
     * 关联的坐席id
     */
    private String seatId;

    /**
     * 所属单位id
     */
    private String unitId;

    /**
     * 接警人员状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    private String status;

    /**
     * 接警人员id(主键)，不能为0
     * @return Id 接警人员id(主键)，不能为0
     */
    public String getId() {
        return id;
    }

    /**
     * 接警人员id(主键)，不能为0
     * @param id 接警人员id(主键)，不能为0
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 接警人员手机号，也是登陆接警人员名（唯一约束）
     * @return Phone 接警人员手机号，也是登陆接警人员名（唯一约束）
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 接警人员手机号，也是登陆接警人员名（唯一约束）
     * @param phone 接警人员手机号，也是登陆接警人员名（唯一约束）
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 接警人员密码
     * @return Password 接警人员密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 接警人员密码
     * @param password 接警人员密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 会话id
     * @return SessionKey 会话id
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * 会话id
     * @param sessionKey 会话id
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    /**
     * 名称
     * @return Name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * @return Uptimestamp 上次更新时间（update 触发器 确定）
     */
    public String getUptimestamp() {
        return uptimestamp;
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @param uptimestamp 上次更新时间（update 触发器 确定）
     */
    public void setUptimestamp(String uptimestamp) {
        this.uptimestamp = uptimestamp == null ? null : uptimestamp.trim();
    }

    /**
     * 创建人（外键manager）
     * @return CreateUser 创建人（外键manager）
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人（外键manager）
     * @param createUser 创建人（外键manager）
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 上次更新人（外键manager）
     * @return UpdateUser 上次更新人（外键manager）
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 上次更新人（外键manager）
     * @param updateUser 上次更新人（外键manager）
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @return DataState 数据状态，1：可用，0：禁用，2：删除
     */
    public String getDataState() {
        return dataState;
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @param dataState 数据状态，1：可用，0：禁用，2：删除
     */
    public void setDataState(String dataState) {
        this.dataState = dataState == null ? null : dataState.trim();
    }

    /**
     * 性别（男，女），默认 男
     * @return Sex 性别（男，女），默认 男
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别（男，女），默认 男
     * @param sex 性别（男，女），默认 男
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 身份证号
     * @return IdCard 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 身份证号
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * 工号
     * @return IdNo 工号
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * 工号
     * @param idNo 工号
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    /**
     * 备注
     * @return Des 备注
     */
    public String getDes() {
        return des;
    }

    /**
     * 备注
     * @param des 备注
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    /**
     * 头像md5
     * @return AvatarUrl 头像md5
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 头像md5
     * @param avatarUrl 头像md5
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * 关联的坐席id
     * @return SeatId 关联的坐席id
     */
    public String getSeatId() {
        return seatId;
    }

    /**
     * 关联的坐席id
     * @param seatId 关联的坐席id
     */
    public void setSeatId(String seatId) {
        this.seatId = seatId == null ? null : seatId.trim();
    }

    /**
     * 所属单位id
     * @return UnitId 所属单位id
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 所属单位id
     * @param unitId 所属单位id
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 接警人员状态  0:空闲 1:繁忙 2:离开 3:下线
     * @return Status 接警人员状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    public String getStatus() {
        return status;
    }

    /**
     * 接警人员状态  0:空闲 1:繁忙 2:离开 3:下线
     * @param status 接警人员状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}