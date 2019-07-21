package com.kxjl.video.pojo;

public class Phoneinfo {
    /**
     * 接警手机id(主键)，不能为0
     */
    private String id;

    /**
     * 接警手机手机号，也是接警手机（唯一约束）
     */
    private String phone;

    /**
     * 接警手机名称
     */
    private String name;

    /**
     * 创建时间（insert 触发器 确定）
     */
    private String createTime;

    /**
     * 上次更新时间（update 触发器 确定）
     */
    private String uptimestamp;

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     */
    private String dataState;

    /**
     * 手机标识号
     */
    private String IMEI;

    /**
     * 手机型号
     */
    private String phoneType;

    /**
     * 备注
     */
    private String des;

    /**
     * 关联的坐席id
     */
    private String seatId;

    /**
     * 所属单位id
     */
    private String unitId;

    /**
     * 接警手机状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    private String status;
    
    
    
    //query
    private String seatName;
    private String unitName;
    private String areaName;
    
    private String areaId;
    private String curUid;//当前登陆id
    

    /**
     * 接警手机id(主键)，不能为0
     * @return Id 接警手机id(主键)，不能为0
     */
    public String getId() {
        return id;
    }

    /**
     * 接警手机id(主键)，不能为0
     * @param id 接警手机id(主键)，不能为0
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 接警手机手机号，也是接警手机（唯一约束）
     * @return Phone 接警手机手机号，也是接警手机（唯一约束）
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 接警手机手机号，也是接警手机（唯一约束）
     * @param phone 接警手机手机号，也是接警手机（唯一约束）
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 接警手机名称
     * @return Name 接警手机名称
     */
    public String getName() {
        return name;
    }

    /**
     * 接警手机名称
     * @param name 接警手机名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 创建时间（insert 触发器 确定）
     * @return CreateTime 创建时间（insert 触发器 确定）
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间（insert 触发器 确定）
     * @param createTime 创建时间（insert 触发器 确定）
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @return Uptimestamp 上次更新时间（update 触发器 确定）
     */
    public String getUptimestamp() {
        return uptimestamp;
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @param uptimestamp 上次更新时间（update 触发器 确定）
     */
    public void setUptimestamp(String uptimestamp) {
        this.uptimestamp = uptimestamp == null ? null : uptimestamp.trim();
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @return DataState 数据状态，1：可用，0：禁用，2：删除
     */
    public String getDataState() {
        return dataState;
    }

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     * @param dataState 数据状态，1：可用，0：禁用，2：删除
     */
    public void setDataState(String dataState) {
        this.dataState = dataState == null ? null : dataState.trim();
    }

    /**
     * 手机标识号
     * @return IMEI 手机标识号
     */
    public String getIMEI() {
        return IMEI;
    }

    /**
     * 手机标识号
     * @param IMEI 手机标识号
     */
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI == null ? null : IMEI.trim();
    }

    /**
     * 手机型号
     * @return PhoneType 手机型号
     */
    public String getPhoneType() {
        return phoneType;
    }

    /**
     * 手机型号
     * @param phoneType 手机型号
     */
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType == null ? null : phoneType.trim();
    }

    /**
     * 备注
     * @return Des 备注
     */
    public String getDes() {
        return des;
    }

    /**
     * 备注
     * @param des 备注
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    /**
     * 关联的坐席id
     * @return SeatId 关联的坐席id
     */
    public String getSeatId() {
        return seatId;
    }

    /**
     * 关联的坐席id
     * @param seatId 关联的坐席id
     */
    public void setSeatId(String seatId) {
        this.seatId = seatId == null ? null : seatId.trim();
    }

    /**
     * 所属单位id
     * @return UnitId 所属单位id
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 所属单位id
     * @param unitId 所属单位id
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 接警手机状态  0:空闲 1:繁忙 2:离开 3:下线
     * @return Status 接警手机状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    public String getStatus() {
        return status;
    }

    /**
     * 接警手机状态  0:空闲 1:繁忙 2:离开 3:下线
     * @param status 接警手机状态  0:空闲 1:繁忙 2:离开 3:下线
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCurUid() {
		return curUid;
	}

	public void setCurUid(String curUid) {
		this.curUid = curUid;
	}
}