package com.kxjl.video.pojo;

public class Areainfo {
    /**
     * 片区id(主键)
     */
    private String id;

    /**
     * 片区名称
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
     * 所属单位id
     */
    private String unitId;
    
    private String des;
    
    //query
    
    private String unitName;//单位名称



    private String seatListStr;//坐席json
    private String curUid;//当前登陆id
    
    /**
     * 片区id(主键)
     * @return Id 片区id(主键)
     */
    public String getId() {
        return id;
    }

    /**
     * 片区id(主键)
     * @param id 片区id(主键)
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 片区名称
     * @return Name 片区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 片区名称
     * @param name 片区名称
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getSeatListStr() {
		return seatListStr;
	}

	public void setSeatListStr(String seatListStr) {
		this.seatListStr = seatListStr;
	}

	public String getCurUid() {
		return curUid;
	}

	public void setCurUid(String curUid) {
		this.curUid = curUid;
	}

	
}