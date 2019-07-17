package com.kxjl.tasktransferplat.pojo;

import lombok.Data;

@Data
public class UserOpinion {
    /**
     * 用户反馈意见id
     */
    private Long id;

    /**
     * 反馈人id（用户），外键t_userinfo表
     */
    private Long userId;

    /**
     * 反馈人id(客户)，外键t_customerinfo表
     */
    private String customerId;

    /**
     * '反馈类型 0:锁匠反馈，1：客户反馈'
     */
    private String utype;


    /**
     * 反馈时间
     */
    private String feedbackTime;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈人名
     */
    private String userName;


    /**
     * 手机号
     */
    private String phone;


    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 用户反馈意见id
     * @return id 用户反馈意见id
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户反馈意见id
     * @param id 用户反馈意见id
     */
    public void setId(Long id) {
        this.id = id == null ? null : id;
    }

    /**
     * 反馈人id，外键t_userinfo表
     * @return UserId 反馈人id，外键t_userinfo表
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 反馈人id，外键t_userinfo表
     * @param userId 反馈人id，外键t_userinfo表
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 反馈时间
     * @return feedbackTime 反馈时间
     */
    public String getFeedbackTime() {
        return feedbackTime;
    }

    /**
     * 反馈时间
     * @param feedbackTime 反馈时间
     */
    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime == null ? null : feedbackTime.trim();
    }

    /**
     * 反馈内容
     * @return Content 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 反馈内容
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
