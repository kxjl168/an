package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_userinfo_audit")
public class UserinfoAudit {
    /**
     * 
     */
    private String id;

    /**
     * 锁匠id
     */
    private Long userInfoId;

    /**
     * 变更申请人id
     */
    private String proposer;

    /**
     * 锁匠变更前类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6      
     */
    private Integer userOldType;

    /**
     * 锁匠变更后类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6     
     */
    private Integer userNewType;

    /**
     * 申请时间
     */
    private String proposTime;

    /**
     * 申请原因
     */
    private String proposReason;

    /**
     * 申请原因图片说明, mongodb ids,对应sys_file md5
     */
    private String icons;

    /**
     * 审核不通过原因
     */
    private String auditFailReason;

    /**
     * 审核人Id，映射到manager表
     */
    private String auditor;

    /**
     * 审核状态，0：未审核，1：审核通过，2：审核不通过
     */
    private Integer auditStates;

    /**
     * 审核时间
     */
    private String auditTime;
    
	private Long companyId;//合伙人
    
    /**
     * 审核状态，0：未审核，1：审核通过，2：审核不通过
     */

	@TableField(exist = false)
	private String companyName;

	@TableField(exist = false)
	private String companyPhone;
	@TableField(exist = false)
	private String companyidCard;

    
    
    @TableField(exist=false)
    private String files;
    
    
    
    @TableField(exist=false)
    private String userInfoName;

    @TableField(exist=false)
    private String userPhone;
    
    
    @TableField(exist=false)
    private String proposerName;

    @TableField(exist=false)
    private String filenames;
    
    @TableField(exist=false)
    private String filemd5;
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
     * 锁匠id
     * @return UserInfoId 锁匠id
     */
    public Long getUserInfoId() {
        return userInfoId;
    }

    /**
     * 锁匠id
     * @param userInfoId 锁匠id
     */
    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    /**
     * 变更申请人id
     * @return Proposer 变更申请人id
     */
    public String getProposer() {
        return proposer;
    }

    /**
     * 变更申请人id
     * @param proposer 变更申请人id
     */
    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    /**
     * 锁匠变更前类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6      
     * @return UserOldType 锁匠变更前类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6      
     */
    public Integer getUserOldType() {
        return userOldType;
    }

    /**
     * 锁匠变更前类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6      
     * @param userOldType 锁匠变更前类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6      
     */
    public void setUserOldType(Integer userOldType) {
        this.userOldType = userOldType;
    }

    /**
     * 锁匠变更后类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6     
     * @return UserNewType 锁匠变更后类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6     
     */
    public Integer getUserNewType() {
        return userNewType;
    }

    /**
     * 锁匠变更后类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6     
     * @param userNewType 锁匠变更后类型，用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6     
     */
    public void setUserNewType(Integer userNewType) {
        this.userNewType = userNewType;
    }

    /**
     * 申请时间
     * @return ProposTime 申请时间
     */
    public String getProposTime() {
        return proposTime;
    }

    /**
     * 申请时间
     * @param proposTime 申请时间
     */
    public void setProposTime(String proposTime) {
        this.proposTime = proposTime == null ? null : proposTime.trim();
    }

    /**
     * 申请原因
     * @return ProposReason 申请原因
     */
    public String getProposReason() {
        return proposReason;
    }

    /**
     * 申请原因
     * @param proposReason 申请原因
     */
    public void setProposReason(String proposReason) {
        this.proposReason = proposReason == null ? null : proposReason.trim();
    }

    /**
     * 申请原因图片说明, mongodb ids,对应sys_file md5
     * @return icons 申请原因图片说明, mongodb ids,对应sys_file md5
     */
    public String getIcons() {
        return icons;
    }

    /**
     * 申请原因图片说明, mongodb ids,对应sys_file md5
     * @param icons 申请原因图片说明, mongodb ids,对应sys_file md5
     */
    public void setIcons(String icons) {
        this.icons = icons == null ? null : icons.trim();
    }

    /**
     * 审核不通过原因
     * @return AuditFailReason 审核不通过原因
     */
    public String getAuditFailReason() {
        return auditFailReason;
    }

    /**
     * 审核不通过原因
     * @param auditFailReason 审核不通过原因
     */
    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason == null ? null : auditFailReason.trim();
    }

    /**
     * 审核人Id，映射到manager表
     * @return Auditor 审核人Id，映射到manager表
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 审核人Id，映射到manager表
     * @param auditor 审核人Id，映射到manager表
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    /**
     * 审核状态，0：未审核，1：审核通过，2：审核不通过
     * @return AuditStates 审核状态，0：未审核，1：审核通过，2：审核不通过
     */
    public Integer getAuditStates() {
        return auditStates;
    }

    /**
     * 审核状态，0：未审核，1：审核通过，2：审核不通过
     * @param auditStates 审核状态，0：未审核，1：审核通过，2：审核不通过
     */
    public void setAuditStates(Integer auditStates) {
        this.auditStates = auditStates;
    }

    /**
     * 审核时间
     * @return AuditTime 审核时间
     */
    public String getAuditTime() {
        return auditTime;
    }

    /**
     * 审核时间
     * @param auditTime 审核时间
     */
    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime == null ? null : auditTime.trim();
    }
}