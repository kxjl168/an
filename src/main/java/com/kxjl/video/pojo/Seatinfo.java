package com.kxjl.video.pojo;

public class Seatinfo {
    /**
     * 坐席id(主键)
     */
    private String id;

    /**
     * 坐席名称
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
     * 所属片区id
     */
    private String areaId;

    /**
     * 所属单位id
     */
    private String unitId;

    
    //query 
    private String unitName;
    private String areaName;
    
    private Integer personNumReal;
    private String curUid;//当前登陆id
    
    private String personName;//当前坐席人员名称
    private String status;//当前坐席人员状态  '接警人员状态  0:空闲 1:繁忙 2:离开 3:下线',
    private String IdNo;//当前坐席人员工号
    /**
     * 
     */
    private String personNum;

    /**
     * 坐席id(主键)
     * @return Id 坐席id(主键)
     */
    public String getId() {
        return id;
    }

    /**
     * 坐席id(主键)
     * @param id 坐席id(主键)
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 坐席名称
     * @return Name 坐席名称
     */
    public String getName() {
        return name;
    }

    /**
     * 坐席名称
     * @param name 坐席名称
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
     * 所属片区id
     * @return AreaId 所属片区id
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 所属片区id
     * @param areaId 所属片区id
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
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
     * 
     * @return PersonNum 
     */
    public String getPersonNum() {
        return personNum;
    }

    /**
     * 
     * @param personNum 
     */
    public void setPersonNum(String personNum) {
        this.personNum = personNum == null ? null : personNum.trim();
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

	public Integer getPersonNumReal() {
		return personNumReal;
	}

	public void setPersonNumReal(Integer personNumReal) {
		this.personNumReal = personNumReal;
	}

	public String getCurUid() {
		return curUid;
	}

	public void setCurUid(String curUid) {
		this.curUid = curUid;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
}