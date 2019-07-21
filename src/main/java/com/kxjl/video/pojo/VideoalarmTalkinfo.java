package com.kxjl.video.pojo;

public class VideoalarmTalkinfo {
    /**
     * 序号
     */
    private String id;

    /**
     * 报警id
     */
    private Integer alarmId;

    /**
     * 聊天状态 1:报警人-》接警人， 2:接警人-》报警人
     */
    private String talkType;

    /**
     * 时间
     */
    private String ctime;

    /**
     * 消息类型 1:文本
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 非文本文件路径
     */
    private String fileUrl;
    
    //query
    private String mintime; //查询的最小时间，查看历史聊天

    /**
     * 序号
     * @return id 序号
     */
    public String getId() {
        return id;
    }

    /**
     * 序号
     * @param id 序号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 报警id
     * @return alarmId 报警id
     */
    public Integer getAlarmId() {
        return alarmId;
    }

    /**
     * 报警id
     * @param alarmId 报警id
     */
    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    /**
     * 聊天状态 1:报警人-》接警人， 2:接警人-》报警人
     * @return talkType 聊天状态 1:报警人-》接警人， 2:接警人-》报警人
     */
    public String getTalkType() {
        return talkType;
    }

    /**
     * 聊天状态 1:报警人-》接警人， 2:接警人-》报警人
     * @param talkType 聊天状态 1:报警人-》接警人， 2:接警人-》报警人
     */
    public void setTalkType(String talkType) {
        this.talkType = talkType == null ? null : talkType.trim();
    }

    /**
     * 时间
     * @return ctime 时间
     */
    public String getCtime() {
        return ctime;
    }

    /**
     * 时间
     * @param ctime 时间
     */
    public void setCtime(String ctime) {
        this.ctime = ctime == null ? null : ctime.trim();
    }

    /**
     * 消息类型 1:文本
     * @return msgType 消息类型 1:文本
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * 消息类型 1:文本
     * @param msgType 消息类型 1:文本
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    /**
     * 消息内容
     * @return msgContent 消息内容
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * 消息内容
     * @param msgContent 消息内容
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    /**
     * 非文本文件路径
     * @return fileUrl 非文本文件路径
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 非文本文件路径
     * @param fileUrl 非文本文件路径
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

	public String getMintime() {
		return mintime;
	}

	public void setMintime(String mintime) {
		this.mintime = mintime;
	}
}