package com.kxjl.tasktransferplat.pojo;

public class AppSms {
    /**
     * 
     */
    private String id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 验证码
     */
    private String checkCode;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 短息类型1-注册2-找回密码.....
     */
    private Byte sendType;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 0：有效，1:无效
     */
    private String isValidate;

    /**
     * 
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 手机号码
     * @return mobile 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 短信内容
     * @return content 短信内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 短信内容
     * @param content 短信内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 验证码
     * @return check_code 验证码
     */
    public String getCheckCode() {
        return checkCode;
    }

    /**
     * 验证码
     * @param checkCode 验证码
     */
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode == null ? null : checkCode.trim();
    }

    /**
     * 发送时间
     * @return send_time 发送时间
     */
    public Long getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     * @param sendTime 发送时间
     */
    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 短息类型1-注册2-找回密码.....
     * @return send_type 短息类型1-注册2-找回密码.....
     */
    public Byte getSendType() {
        return sendType;
    }

    /**
     * 短息类型1-注册2-找回密码.....
     * @param sendType 短息类型1-注册2-找回密码.....
     */
    public void setSendType(Byte sendType) {
        this.sendType = sendType;
    }

    /**
     * 请求ip
     * @return ip 请求ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 请求ip
     * @param ip 请求ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 0：有效，1:无效
     * @return is_validate 0：有效，1:无效
     */
    public String getIsValidate() {
        return isValidate;
    }

    /**
     * 0：有效，1:无效
     * @param isValidate 0：有效，1:无效
     */
    public void setIsValidate(String isValidate) {
        this.isValidate = isValidate == null ? null : isValidate.trim();
    }
}