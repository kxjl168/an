package com.kxjl.video.pojo;

public class AreainfoArea {
    /**
     * 
     */
    private String id;

    /**
     * 片区id
     */
    private String areaId;

    /**
     * 行政区域id -t_area_district-id
     */
    private Integer districtId;

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
     * 片区id
     * @return AreaId 片区id
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 片区id
     * @param areaId 片区id
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    /**
     * 行政区域id -t_area_district-id
     * @return districtId 行政区域id -t_area_district-id
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * 行政区域id -t_area_district-id
     * @param districtId 行政区域id -t_area_district-id
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}