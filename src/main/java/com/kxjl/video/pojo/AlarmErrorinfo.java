package com.kxjl.video.pojo;

public class AlarmErrorinfo {
    /**
     * 序号
     */
    private Long id;

    /**
     * 类型  1 坐席繁忙  2超时
     */
    private Integer type;

    /**
     * 接警人员ID
     */
    private Integer onlineseatsId;

    /**
     * 报警者姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idnumber;

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
    private String wechatId;

    /**
     * 报警用户电话号码
     */
    private String phone;

    /**
     * 报警时间
     */
    private String alarmTime;

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
     * 类型  1 坐席繁忙  2超时
     * @return type 类型  1 坐席繁忙  2超时
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型  1 坐席繁忙  2超时
     * @param type 类型  1 坐席繁忙  2超时
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 接警人员ID
     * @return onlineseats_id 接警人员ID
     */
    public Integer getOnlineseatsId() {
        return onlineseatsId;
    }

    /**
     * 接警人员ID
     * @param onlineseatsId 接警人员ID
     */
    public void setOnlineseatsId(Integer onlineseatsId) {
        this.onlineseatsId = onlineseatsId;
    }

    /**
     * 报警者姓名
     * @return userName 报警者姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 报警者姓名
     * @param username 报警者姓名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 身份证号
     * @return idNumber 身份证号
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * 身份证号
     * @param idnumber 身份证号
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
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
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 微信账号ID
     * @param wechatId 微信账号ID
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
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
     * 报警时间
     * @return alarm_time 报警时间
     */
    public String getAlarmTime() {
        return alarmTime;
    }

    /**
     * 报警时间
     * @param alarmTime 报警时间
     */
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime == null ? null : alarmTime.trim();
    }
}
