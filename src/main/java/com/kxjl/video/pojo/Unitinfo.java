package com.kxjl.video.pojo;

public class Unitinfo {
    /**
     * 单位id(主键)
     */
    private String id;

    /**
     * 单位名称
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
     * 备注
     */
    private String des;

    /**
     * 单位联系人
     */
    private String contactPerson;

    /**
     * 单位联系电话
     */
    private String contactPhone;

    /**
     * 单位地址
     */
    private String address;
    
    private String unitType;
    
    private String parentUnit;
    
    
    //query
    private String adminlist; //单位管理员 逗号分隔
    
    private String unitListStr;//下级单位json
    
    
    private String curUid;//当前登陆id
    
    private String parentUnitName;

    /**
     * 单位id(主键)
     * @return Id 单位id(主键)
     */
    public String getId() {
        return id;
    }

    /**
     * 单位id(主键)
     * @param id 单位id(主键)
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 单位名称
     * @return Name 单位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 单位名称
     * @param name 单位名称
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
     * 单位联系人
     * @return ContactPerson 单位联系人
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * 单位联系人
     * @param contactPerson 单位联系人
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    /**
     * 单位联系电话
     * @return ContactPhone 单位联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 单位联系电话
     * @param contactPhone 单位联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * 单位地址
     * @return Address 单位地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 单位地址
     * @param address 单位地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

	public String getAdminlist() {
		return adminlist;
	}

	public void setAdminlist(String adminlist) {
		this.adminlist = adminlist;
	}

	public String getCurUid() {
		return curUid;
	}

	public void setCurUid(String curUid) {
		this.curUid = curUid;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getParentUnit() {
		return parentUnit;
	}

	public void setParentUnit(String parentUnit) {
		this.parentUnit = parentUnit;
	}

	public String getParentUnitName() {
		return parentUnitName;
	}

	public void setParentUnitName(String parentUnitName) {
		this.parentUnitName = parentUnitName;
	}

	public String getUnitListStr() {
		return unitListStr;
	}

	public void setUnitListStr(String unitListStr) {
		this.unitListStr = unitListStr;
	}
}