package com.kxjl.video.pojo;

public class VideoalarmInfo {
    /**
     * 序号
     */
    private Long id;

    /**
     * 类型  1 图文报警  2在线坐席上传
     */
    private Integer type;

    /**
     * 接警人员ID
     */
    private String onlineseats_id;
    
    
    //query
    private String onlineseats_name;//接警人员名称
    
    
    private String seat_id;// 接警人员坐席id
    
    
    private String onlineseats_unitid;//接警人员id
    private String onlineseats_unitname;//接警人员单位
    
    private String onlineseats_areaid;//接警人员片区id
    private String onlineseats_areaname;//接警人员片区
    
    private String onlineseats_no;//接警人员工号
    
    private String case_typename;//事件类型名称
    
    private String case_levelname;//事件级别名称
    
    private String startDate;//查询开始
    private String endDate;//查询开始
    
    private String curUid;//当前登陆id
    
    private String hasNewInfo;//1 新消息， 2无新消息；

    //query end
    
    /**
     * 报警者姓名
     */
    private String userName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 区域
     */
    private String area;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 地理位置
     */
    private String address;

    /**
     * 微信账号ID
     */
    private String wechat_id;

    /**
     * 微信账号openID
     */
    private String wechat_OpenId;

    /**
     * 报警用户电话号码
     */
    private String phone;

    /**
     * 警情发生时间
     */
    private String occurrence_time;

    /**
     * 警情发生地址
     */
    private String occurrence_address;

    /**
     * 警情描述
     */
    private String description;

    /**
     * 报警时间
     */
    private String alarm_time;

    /**
     * 警情状态 1已报警、2已受理、3已出警、4已关闭
     */
    private String status;

    /**
     * 图片Url地址 多个用，分割
     */
    private String picture_url;

    /**
     * 视频Url地址 多个用，分割
     */
    private String video_url;

    /**
     * 语音Url地址 多个用，分割
     */
    private String audio_url;

    /**
     * 案件类别
     */
    private String case_type;

    /**
     * 案件等级
     */
    private String case_level;

    /**
     * 序号
     * @return id 序号
     */
    public Long getId() {
        return id;
    }

    /**
     * 序号
     * @param id 序号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 类型  1 图文报警  2在线坐席上传
     * @return type 类型  1 图文报警  2在线坐席上传
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型  1 图文报警  2在线坐席上传
     * @param type 类型  1 图文报警  2在线坐席上传
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 接警人员ID
     * @return onlineseats_id 接警人员ID
     */
    public String getOnlineseats_id() {
        return onlineseats_id;
    }

    /**
     * 接警人员ID
     * @param onlineseats_id 接警人员ID
     */
    public void setOnlineseats_id(String onlineseats_id) {
        this.onlineseats_id = onlineseats_id == null ? null : onlineseats_id.trim();
    }

    /**
     * 报警者姓名
     * @return userName 报警者姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 报警者姓名
     * @param userName 报警者姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 身份证号
     * @return idNumber 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 身份证号
     * @param idNumber 身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * 区域
     * @return area 区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 区域
     * @param area 区域
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 纬度
     * @return latitude 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * 经度
     * @return longitude 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 经度
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * 地理位置
     * @return address 地理位置
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地理位置
     * @param address 地理位置
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 微信账号ID
     * @return wechat_id 微信账号ID
     */
    public String getWechat_id() {
        return wechat_id;
    }

    /**
     * 微信账号ID
     * @param wechat_id 微信账号ID
     */
    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id == null ? null : wechat_id.trim();
    }

    /**
     * 微信账号openID
     * @return wechat_OpenId 微信账号openID
     */
    public String getWechat_OpenId() {
        return wechat_OpenId;
    }

    /**
     * 微信账号openID
     * @param wechat_OpenId 微信账号openID
     */
    public void setWechat_OpenId(String wechat_OpenId) {
        this.wechat_OpenId = wechat_OpenId == null ? null : wechat_OpenId.trim();
    }

    /**
     * 报警用户电话号码
     * @return phone 报警用户电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 报警用户电话号码
     * @param phone 报警用户电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 警情发生时间
     * @return occurrence_time 警情发生时间
     */
    public String getOccurrence_time() {
        return occurrence_time;
    }

    /**
     * 警情发生时间
     * @param occurrence_time 警情发生时间
     */
    public void setOccurrence_time(String occurrence_time) {
        this.occurrence_time = occurrence_time == null ? null : occurrence_time.trim();
    }

    /**
     * 警情发生地址
     * @return occurrence_address 警情发生地址
     */
    public String getOccurrence_address() {
        return occurrence_address;
    }

    /**
     * 警情发生地址
     * @param occurrence_address 警情发生地址
     */
    public void setOccurrence_address(String occurrence_address) {
        this.occurrence_address = occurrence_address == null ? null : occurrence_address.trim();
    }

    /**
     * 警情描述
     * @return description 警情描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 警情描述
     * @param description 警情描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 报警时间
     * @return alarm_time 报警时间
     */
    public String getAlarm_time() {
        return alarm_time;
    }

    /**
     * 报警时间
     * @param alarm_time 报警时间
     */
    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time == null ? null : alarm_time.trim();
    }

    /**
     * 警情状态 1已报警、2已受理、3已出警、4已关闭
     * @return status 警情状态 1已报警、2已受理、3已出警、4已关闭
     */
    public String getStatus() {
        return status;
    }

    /**
     * 警情状态 1已报警、2已受理、3已出警、4已关闭
     * @param status 警情状态 1已报警、2已受理、3已出警、4已关闭
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 图片Url地址 多个用，分割
     * @return picture_url 图片Url地址 多个用，分割
     */
    public String getPicture_url() {
        return picture_url;
    }

    /**
     * 图片Url地址 多个用，分割
     * @param picture_url 图片Url地址 多个用，分割
     */
    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url == null ? null : picture_url.trim();
    }

    /**
     * 视频Url地址 多个用，分割
     * @return video_url 视频Url地址 多个用，分割
     */
    public String getVideo_url() {
        return video_url;
    }

    /**
     * 视频Url地址 多个用，分割
     * @param video_url 视频Url地址 多个用，分割
     */
    public void setVideo_url(String video_url) {
        this.video_url = video_url == null ? null : video_url.trim();
    }

    /**
     * 语音Url地址 多个用，分割
     * @return audio_url 语音Url地址 多个用，分割
     */
    public String getAudio_url() {
        return audio_url;
    }

    /**
     * 语音Url地址 多个用，分割
     * @param audio_url 语音Url地址 多个用，分割
     */
    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url == null ? null : audio_url.trim();
    }

    /**
     * 案件类别
     * @return case_type 案件类别
     */
    public String getCase_type() {
        return case_type;
    }

    /**
     * 案件类别
     * @param case_type 案件类别
     */
    public void setCase_type(String case_type) {
        this.case_type = case_type == null ? null : case_type.trim();
    }

    /**
     * 案件等级
     * @return case_level 案件等级
     */
    public String getCase_level() {
        return case_level;
    }

    /**
     * 案件等级
     * @param case_level 案件等级
     */
    public void setCase_level(String case_level) {
        this.case_level = case_level == null ? null : case_level.trim();
    }

	public String getOnlineseats_name() {
		return onlineseats_name;
	}

	public void setOnlineseats_name(String onlineseats_name) {
		this.onlineseats_name = onlineseats_name;
	}

	public String getOnlineseats_unitname() {
		return onlineseats_unitname;
	}

	public void setOnlineseats_unitname(String onlineseats_unitname) {
		this.onlineseats_unitname = onlineseats_unitname;
	}

	public String getOnlineseats_areaname() {
		return onlineseats_areaname;
	}

	public void setOnlineseats_areaname(String onlineseats_areaname) {
		this.onlineseats_areaname = onlineseats_areaname;
	}

	public String getOnlineseats_no() {
		return onlineseats_no;
	}

	public void setOnlineseats_no(String onlineseats_no) {
		this.onlineseats_no = onlineseats_no;
	}

	public String getCase_typename() {
		return case_typename;
	}

	public void setCase_typename(String case_typename) {
		this.case_typename = case_typename;
	}

	public String getCase_levelname() {
		return case_levelname;
	}

	public void setCase_levelname(String case_levelname) {
		this.case_levelname = case_levelname;
	}

	public String getOnlineseats_unitid() {
		return onlineseats_unitid;
	}

	public void setOnlineseats_unitid(String onlineseats_unitid) {
		this.onlineseats_unitid = onlineseats_unitid;
	}

	public String getOnlineseats_areaid() {
		return onlineseats_areaid;
	}

	public void setOnlineseats_areaid(String onlineseats_areaid) {
		this.onlineseats_areaid = onlineseats_areaid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurUid() {
		return curUid;
	}

	public void setCurUid(String curUid) {
		this.curUid = curUid;
	}

	public String getHasNewInfo() {
		return hasNewInfo;
	}

	public void setHasNewInfo(String hasNewInfo) {
		this.hasNewInfo = hasNewInfo;
	}

	public String getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
}

