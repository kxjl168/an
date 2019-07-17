package com.kxjl.tasktransferplat.pojo;

import lombok.Data;

@Data
public class UserPhoneLog {
    /**
     * k
     */
    private Long id;
    /**
     * 用户号码
     */
    private String phone;

    /**
     * 工单Id
     */
    private String orderinfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderinfoId() {
        return orderinfoId;
    }

    public void setOrderinfoId(String orderinfoId) {
        this.orderinfoId = orderinfoId;
    }
}
