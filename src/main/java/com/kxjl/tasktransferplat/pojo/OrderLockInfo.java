package com.kxjl.tasktransferplat.pojo;

import lombok.Data;

@Data
public class OrderLockInfo {
    /**
     * 
     */
    private String id;

    /**
     * 锁序列号，第几把
     */
    private String lockIndex;

    /**
     * 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     */
    private String orderNo;

    private String picIndex;//序列
    
    /**
     * 
     */
    private String imeiNum;

    /**
     * 锁安装前图片，逗号分隔的mongodbid 
     */
    private String startimgs;

    /**
     * 锁安装中产品图片，逗号分隔的mongodbid
     */
    private String lockimgs;

    /**
     * 锁安装后完工图片，逗号分隔的mongodbid
     */
    private String doneimgs;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 
     * @return Id 
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
     * 锁序列号，第几把
     * @return lockIndex 锁序列号，第几把
     */
    public String getLockIndex() {
        return lockIndex;
    }

    /**
     * 锁序列号，第几把
     * @param lockIndex 锁序列号，第几把
     */
    public void setLockIndex(String lockIndex) {
        this.lockIndex = lockIndex == null ? null : lockIndex.trim();
    }

    /**
     * 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     * @return OrderNo 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     * @param orderNo 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 
     * @return imeiNum 
     */
    public String getImeiNum() {
        return imeiNum;
    }

    /**
     * 
     * @param imeiNum 
     */
    public void setImeiNum(String imeiNum) {
        this.imeiNum = imeiNum == null ? null : imeiNum.trim();
    }

    /**
     * 锁安装前图片，逗号分隔的mongodbid 
     * @return startimgs 锁安装前图片，逗号分隔的mongodbid 
     */
    public String getStartimgs() {
        return startimgs;
    }

    /**
     * 锁安装前图片，逗号分隔的mongodbid 
     * @param startimgs 锁安装前图片，逗号分隔的mongodbid 
     */
    public void setStartimgs(String startimgs) {
        this.startimgs = startimgs == null ? null : startimgs.trim();
    }

    /**
     * 锁安装中产品图片，逗号分隔的mongodbid
     * @return lockimgs 锁安装中产品图片，逗号分隔的mongodbid
     */
    public String getLockimgs() {
        return lockimgs;
    }

    /**
     * 锁安装中产品图片，逗号分隔的mongodbid
     * @param lockimgs 锁安装中产品图片，逗号分隔的mongodbid
     */
    public void setLockimgs(String lockimgs) {
        this.lockimgs = lockimgs == null ? null : lockimgs.trim();
    }

    /**
     * 锁安装后完工图片，逗号分隔的mongodbid
     * @return doneimgs 锁安装后完工图片，逗号分隔的mongodbid
     */
    public String getDoneimgs() {
        return doneimgs;
    }

    /**
     * 锁安装后完工图片，逗号分隔的mongodbid
     * @param doneimgs 锁安装后完工图片，逗号分隔的mongodbid
     */
    public void setDoneimgs(String doneimgs) {
        this.doneimgs = doneimgs == null ? null : doneimgs.trim();
    }

    /**
     * 创建时间
     * @return CreateTime 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}