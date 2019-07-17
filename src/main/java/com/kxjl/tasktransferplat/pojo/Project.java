/**
 * @(#)Project.java  2019-02-15 16:05:29
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
 * @version 1.0.0 2019-02-15 16:05:29
 * @since 1.0.0
 */
@Data
@TableName("t_project")
public class Project {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private String id;

    /**
     * 工程名称
     */
    @TableField("ProjectName")
    private String projectName;

    /**
     * 地址id
     */
    @TableField("AddressId")
    private Long addressId;

    /**
     * 工程详细地址
     */
    @TableField("AddressDetail")
    private String addressDetail;

    /**
     * 工程创建时间
     */
    @TableField("CreateTime")
    private String createTime;

    /**
     * 工程所属公司id,若属于本平台则为null
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @TableField("CompanyId")
    private Long companyId;

    /**
     * 创建人id
     */
    @TableField("CreateUser")
    private String createUser;

    /**
     * 负责人id，指向manager表
     */
    private String principal;

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

    /**
     * 客户姓名
     */
    @TableField("CustName")
    private String custName;

    /**
     * 客户电话
     */
    @TableField("CustPhone")
    private String custPhone;

    /**
     * 工程状态，0：已删除，1：进行中，2：已完成
     */
    @TableField("ProjectState")
    private Long projectState;


    /**
     * 工单总量
     */
    @TableField(exist = false)
    private Integer num;

    /**
     * 负责人名
     */
    @TableField(exist = false)
    private String principalName;
}
