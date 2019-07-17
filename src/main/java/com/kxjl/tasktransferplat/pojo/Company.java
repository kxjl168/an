/**
 * @(#)Company.java  2019-02-18 13:28:52
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author 单肙
 * @version 1.0.0 2019-02-18 13:28:52
 * @since 1.0.0
 */
@Data
@TableName("t_company")
public class Company {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 公司名称
     */
    @TableField("CompanyName")
    private String companyName;

    /**
     * 公司电话
     */
    @TableField("CompanyPhone")
    private String companyPhone;

    /**
     * 公司类型
     */
    @TableField("CompanyType")
    private Integer companyType;

    /**
     * 公司所在地id，映射到t_address
     */
    @TableField("CompanyAddressId")
    private Long companyAddressId;

    /**
     * 公司详细地址
     */
    @TableField("CompanyAddressDetail")
    private String companyAddressDetail;

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

}
