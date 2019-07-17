package com.kxjl.tasktransferplat.pojo;

import lombok.Data;
import lombok.ToString;

@Data
public class UserinfoOperateLog {
    
	
	
	/**
	 * 锁匠日志类型
	 * UserinfoOperateLog.java.
	 * 
	 * @author zj
	* @version 1.0.1 2019年6月10日
	* @revision zj 2019年6月10日
	* @since 1.0.1
	 */
	public enum UserInfoType{
		
		OperateType_LockCompany(0,"锁企操作"),
		OperateType_Kefu(1,"客服操作"),
		OperateType_Locker(2,"锁匠操作");

		
		public int type;
		public String desc;
		UserInfoType(int type,String desc){
			this.type=type;
			this.desc=desc;
		}
		
		public String toString() {
			return String.valueOf(this.type);
		}
		
		
	}
	
	/**
     * id
     */
    private String id;

    /**
     * 锁匠id
     */
    private String userInfoId;

    /**
     * 详细内容，可空
     */
    private String content;

    /**
     * 操作人id,外键到User表或者manager表
     */
    private String operateUserId;
    
    
    
    

    /**
     * 操作时间（触发器）
     */
    private String operateTime;

    /**
     * 审核id，对应t_userinfo_auditid
     */
    private Long auditId;
    
    
    private String Type;
    
    
    
    
    //query
    private String operateUserName;
    
    
    
    
    

    /**
     * id
     * @return Id id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 锁匠id
     * @return UserInfoId 锁匠id
     */
    public String getUserInfoId() {
        return userInfoId;
    }

    /**
     * 锁匠id
     * @param userInfoId 锁匠id
     */
    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId == null ? null : userInfoId.trim();
    }

    /**
     * 详细内容，可空
     * @return Content 详细内容，可空
     */
    public String getContent() {
        return content;
    }

    /**
     * 详细内容，可空
     * @param content 详细内容，可空
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 操作人id,外键到User表或者manager表
     * @return OperateUserId 操作人id,外键到User表或者manager表
     */
    public String getOperateUserId() {
        return operateUserId;
    }

    /**
     * 操作人id,外键到User表或者manager表
     * @param operateUserId 操作人id,外键到User表或者manager表
     */
    public void setOperateUserId(String operateUserId) {
        this.operateUserId = operateUserId == null ? null : operateUserId.trim();
    }

    /**
     * 操作时间（触发器）
     * @return OperateTime 操作时间（触发器）
     */
    public String getOperateTime() {
        return operateTime;
    }

    /**
     * 操作时间（触发器）
     * @param operateTime 操作时间（触发器）
     */
    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime == null ? null : operateTime.trim();
    }

    /**
     * 审核id，对应t_userinfo_auditid
     * @return AuditId 审核id，对应t_userinfo_auditid
     */
    public Long getAuditId() {
        return auditId;
    }

    /**
     * 审核id，对应t_userinfo_auditid
     * @param auditId 审核id，对应t_userinfo_auditid
     */
    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }
}