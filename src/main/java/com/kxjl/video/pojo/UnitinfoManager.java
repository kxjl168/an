package com.kxjl.video.pojo;

public class UnitinfoManager {
    /**
     * 
     */
    private String id;

    /**
     * 单位id
     */
    private String unitId;

    /**
     * manager id
     */
    private String managerId;
    
    
    private String name;//admin name

    /**
     * 
     * @return id 
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
     * 单位id
     * @return UnitId 单位id
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 单位id
     * @param unitId 单位id
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * manager id
     * @return ManagerId manager id
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * manager id
     * @param managerId manager id
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}