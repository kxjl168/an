/**
 * @(#)LockCompany.java  2019-04-11 13:28:52
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangyong
 * @version 1.0.0 2019-04-11 13:28:52
 * @since 1.0.0
 */
@Data
@TableName("t_locksmith_enterprise")
public class LockCompany {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 公司名称
     */
    @TableField("EnterpriseName")
    private String enterpriseName;

    /**
     * 公司电话
     */
    @TableField("EnterprisePhone")
    private String enterprisePhone;

    /**
     * 公司所在地id，映射到t_address
     */
    @TableField("EnterpriseAddressId")
    private Long enterpriseAddressId;

    /**
     * 公司详细地址
     */
    @TableField("EnterpriseAddressDetail")
    private String enterpriseAddressDetail;

    /**
     * 公司入驻平台时间
     */
    @TableField("CreateTime")
    private String createTime;

    /**
     * 1:可用，0：禁用，2：删除
     */
    @TableField("DataState")
    @TableLogic
    private Integer dataState;

    /**
     * 审核状态（-1未提交，0已提交，1审核通过,2不通过）
     */
    @TableField("AuditFlag")
    private String auditFlag;

    /**
     * 审核不通过原因
     */
    @TableField("AuditReason")
    private String auditReason;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在地市
     */
    private String city;

    /**
     * 所在区域
     */
    private String district;

    /**
     * 地市编码
     */
    @TableField("AreaCode")
    private String areaCode;

    private Long lockCompanyPriceId;
    private Integer serverType;
    private Integer parentType;
    private String typeName;
    private java.math.BigDecimal price;
    private String updateTime;

    @TableField(exist = false)
    private java.math.BigDecimal buildPrice;
    @TableField(exist = false)
    private java.math.BigDecimal fixPrice;
    @TableField(exist = false)
    private java.math.BigDecimal openLockPrice;
    @TableField(exist = false)
    private java.math.BigDecimal measutePrice;
    @TableField(exist = false)
    private java.math.BigDecimal catBuildPrice;
    @TableField(exist = false)
    private java.math.BigDecimal changeLockPrice;
    @TableField(exist = false)
    private java.math.BigDecimal engineeringInstallationPrice;
    @TableField(exist = false)
    private java.math.BigDecimal engineeringMaintenancePrice;
    @TableField(exist = false)
    private java.math.BigDecimal otherFee;

    @TableField(exist = false)
    private java.math.BigDecimal specialDoorPrice;//4
    @TableField(exist = false)
    private java.math.BigDecimal refitPrice;//3
    @TableField(exist = false)
    private java.math.BigDecimal hurryInVainPrice;//1
    @TableField(exist = false)
    private java.math.BigDecimal longDistancePrice;//2
    @TableField(exist = false)
    private java.math.BigDecimal urgentPrice;//5
    @TableField(exist = false)
    private java.math.BigDecimal falseLockPrice;//6



    @TableField("OrderManagerName")
    private String orderManagerName;

    private String marketManagerName;

    private String contackPersonName;

    private String techPersonName;

    private String techPersonPhone;

    private String customerManagerName;

    private String customerManagerPhone;
    @TableField("AgreenStartTime")
    private String agreenStartTime;

    private String agreenEndTime;

    private String taxBear;

    private String receiptType;

    private String receiptTitle;

    private String receiptEnable;

    private String taxPoint;

    public String getTaxPoint() {
        return taxPoint == null ? "0" : taxPoint;
    }

    public void setTaxPoint(String taxPoint) {
        this.taxPoint = taxPoint;
    }

    public BigDecimal getBuildPrice() {
        return buildPrice == null ? BigDecimal.valueOf(0) : buildPrice;
    }

    public void setBuildPrice(BigDecimal buildPrice) {
        this.buildPrice = buildPrice;
    }

    public BigDecimal getFixPrice() {
        return fixPrice == null ? BigDecimal.valueOf(0) : fixPrice;
    }


    public void setFixPrice(BigDecimal fixPrice) {
        this.fixPrice = fixPrice;
    }

    public BigDecimal getOpenLockPrice() {
        return openLockPrice == null ? BigDecimal.valueOf(0) : openLockPrice;
    }


    public void setOpenLockPrice(BigDecimal openLockPrice) {
        this.openLockPrice = openLockPrice;
    }

    public BigDecimal getMeasutePrice() {
        return measutePrice == null ? BigDecimal.valueOf(0) : measutePrice;
    }


    public void setMeasutePrice(BigDecimal measutePrice) {
        this.measutePrice = measutePrice;
    }

    public BigDecimal getCatBuildPrice() {
        return catBuildPrice == null ? BigDecimal.valueOf(0) : catBuildPrice;
    }


    public void setCatBuildPrice(BigDecimal catBuildPrice) {
        this.catBuildPrice = catBuildPrice;
    }

    public BigDecimal getChangeLockPrice() {
        return changeLockPrice == null ? BigDecimal.valueOf(0) : changeLockPrice;
    }


    public void setChangeLockPrice(BigDecimal changeLockPrice) {
        this.changeLockPrice = changeLockPrice;
    }

    public BigDecimal getEngineeringInstallationPrice() {
        return engineeringInstallationPrice == null ? BigDecimal.valueOf(0) : engineeringInstallationPrice;
    }


    public void setEngineeringInstallationPrice(BigDecimal engineeringInstallationPrice) {
        this.engineeringInstallationPrice = engineeringInstallationPrice;
    }

    public BigDecimal getEngineeringMaintenancePrice() {
        return engineeringMaintenancePrice == null ? BigDecimal.valueOf(0) : engineeringMaintenancePrice;
    }


    public void setEngineeringMaintenancePrice(BigDecimal engineeringMaintenancePrice) {
        this.engineeringMaintenancePrice = engineeringMaintenancePrice;
    }

    public BigDecimal getOtherFee() {
        return otherFee == null ? BigDecimal.valueOf(0) : otherFee;
    }


    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getSpecialDoorPrice() {
        return specialDoorPrice == null ? BigDecimal.valueOf(0) : specialDoorPrice;
    }


    public void setSpecialDoorPrice(BigDecimal specialDoorPrice) {
        this.specialDoorPrice = specialDoorPrice;
    }

    public BigDecimal getRefitPrice() {
        return refitPrice == null ? BigDecimal.valueOf(0) : refitPrice;
    }


    public void setRefitPrice(BigDecimal refitPrice) {
        this.refitPrice = refitPrice;
    }

    public BigDecimal getHurryInVainPrice() {
        return hurryInVainPrice == null ? BigDecimal.valueOf(0) : hurryInVainPrice;
    }


    public void setHurryInVainPrice(BigDecimal hurryInVainPrice) {
        this.hurryInVainPrice = hurryInVainPrice;
    }

    public BigDecimal getLongDistancePrice() {
        return longDistancePrice == null ? BigDecimal.valueOf(0) : longDistancePrice;
    }


    public void setLongDistancePrice(BigDecimal longDistancePrice) {
        this.longDistancePrice = longDistancePrice;
    }

    public BigDecimal getUrgentPrice() {
        return urgentPrice == null ? BigDecimal.valueOf(0) : urgentPrice;
    }


    public void setUrgentPrice(BigDecimal urgentPrice) {
        this.urgentPrice = urgentPrice;
    }

    public BigDecimal getFalseLockPrice() {
        return falseLockPrice == null ? BigDecimal.valueOf(0) : falseLockPrice;
    }


    public void setFalseLockPrice(BigDecimal falseLockPrice) {
        this.falseLockPrice = falseLockPrice;
    }
}
