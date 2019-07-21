package com.kxjl.video.pojo;

public class AlarmUserinfo {
    /**
     * 报警者ID
     */
    private String id;

    /**
     * 报警者姓名
     */
    private String username;

    /**
     * 报警者昵称
     */
    private String nickname;

    /**
     * 性别（男，女），默认 男
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 住址
     */
    private String address;

    /**
     * 手机账号
     */
    private String mobile_phone;

    /**
     * 微信号
     */
    private String wechatId;

    /**
     * 微信号openid
     */
    private String wechatOpenId;
    
    private String regesterDate;//注册时间

    /**
     * 报警者ID
     * @return id 报警者ID
     */
    public String getId() {
        return id;
    }

    /**
     * 报警者ID
     * @param id 报警者ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 报警者姓名
     * @return username 报警者姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 报警者姓名
     * @param username 报警者姓名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 报警者昵称
     * @return nickname 报警者昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 报警者昵称
     * @param nickname 报警者昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
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
     * 住址
     * @return address 住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 住址
     * @param address 住址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 手机账号
     * @return mobile_phone 手机账号
     */
    public String getMobile_phone() {
        return mobile_phone;
    }

    /**
     * 手机账号
     * @param mobile_phone 手机账号
     */
    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone == null ? null : mobile_phone.trim();
    }

    /**
     * 微信号
     * @return wechatId 微信号
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 微信号
     * @param wechatId 微信号
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * 微信号openid
     * @return wechatOpenId 微信号openid
     */
    public String getWechatOpenId() {
        return wechatOpenId;
    }

    /**
     * 微信号openid
     * @param wechatOpenId 微信号openid
     */
    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId == null ? null : wechatOpenId.trim();
    }

	public String getRegesterDate() {
		return regesterDate;
	}

	public void setRegesterDate(String regesterDate) {
		this.regesterDate = regesterDate;
	}
}