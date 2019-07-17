package com.kxjl.tasktransferplat.pojo;

public class CustomerService {

    /**
     *锁企id
     */
    private String EnterpriseId;

    /**
     *创建时间
     */
    private String creatTime;

    /**
     *客服昵称
     */
    private String nickName;

    /**
     *回访时间阀值
     */
    private String inquireThreshold;

    /**
     *接单数
     */
    private String receiveCount;

    /**
     *完成订单数
     */
    private String doneCount;

    /**
     *超时接单数
     */
    private String receiveTimeOutCount;

    /**
     *超时派单数
     */
    private String despatchTimeOutCount;

    /**
     *超时回访数
     */
    private String inquireCount;

    /**
     *超时回访数
     */
    private String inquireTimeOutCount;

    /**
     *锁企投诉数
     */
    private String enComplainCount;

    /**
     *客户投诉数
     */
    private String cuComplainCount;


    public String getEnterpriseId() {
        return EnterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        EnterpriseId = enterpriseId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInquireThreshold() {
        return inquireThreshold;
    }

    public void setInquireThreshold(String inquireThreshold) {
        this.inquireThreshold = inquireThreshold;
    }

    public String getReceiveCount() {
        return receiveCount == null ? "0" : receiveCount;
    }

    public void setReceiveCount(String receiveCount) {
        this.receiveCount = receiveCount;
    }

    public String getDoneCount() {
        return doneCount == null ? "0" : doneCount;
    }

    public void setDoneCount(String doneCount) {
        this.doneCount = doneCount;
    }

    public String getReceiveTimeOutCount() {
        return receiveTimeOutCount == null ? "0" : receiveTimeOutCount;
    }

    public void setReceiveTimeOutCount(String receiveTimeOutCount) {
        this.receiveTimeOutCount = receiveTimeOutCount;
    }

    public String getDespatchTimeOutCount() {
        return despatchTimeOutCount == null ? "0" : despatchTimeOutCount;
    }

    public void setDespatchTimeOutCount(String despatchTimeOutCount) {
        this.despatchTimeOutCount = despatchTimeOutCount;
    }

    public String getInquireTimeOutCount() {
        return inquireTimeOutCount == null ? "0" : inquireTimeOutCount;
    }

    public void setInquireTimeOutCount(String inquireTimeOutCount) {
        this.inquireTimeOutCount = inquireTimeOutCount;
    }

    public String getEnComplainCount() {
        return enComplainCount == null ? "0" : enComplainCount;
    }

    public void setEnComplainCount(String enComplainCount) {
        this.enComplainCount = enComplainCount;
    }

    public String getCuComplainCount() {
        return cuComplainCount == null ? "0" : cuComplainCount;
    }

    public void setCuComplainCount(String cuComplainCount) {
        this.cuComplainCount = cuComplainCount;
    }

    public String getInquireCount() {
        return inquireCount == null ? "0" : inquireCount;
    }
    public void setInquireCount(String inquireCount) {
        this.inquireCount = inquireCount;
    }
}
