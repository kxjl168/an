package com.kxjl.tasktransferplat.pojo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_orderinfo_audit")
public class OrderinfoAudit {
    /**
     * 
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 工单id
     */
    private String orderInfoId;

    /**
     * 申请人id
     */
    private String proposer;

    /**
     * 申请类型，1：申请加钱，2：申请退单
     */
    private Integer proposType;

    /**
     * 申请加钱的子类型，1：空跑，2：远途，3：改装，4：特殊门，5：其它
     */
    private Integer subType;

    /**
     * 申请加钱金额
     */
    private BigDecimal proposMoney;

    /**
     * 申请时间
     */
    private String proposTime;

    /**
     * 锁企
     */
    @TableField(exist = false)
    private Long enterpriseId;


    /**
     * 合伙人
     */
    @TableField(exist = false)
    private Long companyId;

    /**
     * 类型
     */
    @TableField(exist = false)
    private int type;



    /**
     * 申请原因
     */
    private String proposReason;

    /**
     * 审核状态，0：未审核，1：审核通过，2：审核不通过
     */
    private Integer auditStates;

    /**
     * 审核不通过原因
     */
    private String auditFailReason;

    /**
     * 审核人Id，映射到manager表
     */
    private String auditor;

    /**
     * 审核时间
     */
    private String auditTime;
    
    
    @TableField(exist=false)
    private String filenames;
    
    @TableField(exist=false)
    private String filemd5;
    
    
    //query 审核人姓名
    @TableField(exist=false)
    private String auditorName;


    /**
     * 图片md5集合
     */
    private String icons;

    @TableField(exist = false)
    private List<String> imgBase64List;

    @TableField(exist = false)
    private Long lockerId;
    /**
     * 开始时间
     */
    @TableField(exist=false)
    private String startTime;

    /**
     * 结束时间
     */
    @TableField(exist=false)
    private String endTime;
    @TableField(exist=false)
    private String doneflag;// '审核状态标识，0：未完成,1：已完成';
}
