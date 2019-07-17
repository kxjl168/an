package com.kxjl.tasktransferplat.pojo;


public class UserinfoJobArea {
    /**
     * 
     */
    private String id;

    /**
     * 锁匠id
     */
    private Long lockId;

    /**
     * 区域id
     */
    private Integer districtId;
    
    
    private String districtName;
    
    private String parterName;

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
     * 锁匠id
     * @return lockId 锁匠id
     */
    public Long getLockId() {
        return lockId;
    }

    /**
     * 锁匠id
     * @param lockId 锁匠id
     */
    public void setLockId(Long lockId) {
        this.lockId = lockId;
    }

    /**
     * 区域id
     * @return districtId 区域id
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * 区域id
     * @param districtId 区域id
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getParterName() {
		return parterName;
	}

	public void setParterName(String parterName) {
		this.parterName = parterName;
	}
}