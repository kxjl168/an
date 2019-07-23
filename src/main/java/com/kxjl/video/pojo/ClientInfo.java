package com.kxjl.video.pojo;

import java.nio.channels.SocketChannel;

import javax.websocket.Session;

public class ClientInfo {
	private SocketChannel channel = null;//连接客户端Socket
	private Integer type;//客户端连接类型  1微信客户端   3 在线坐席
	private String identyID;//身份证
	private String name;//姓名
	private String sex;//性别
	private String userid;//用户ID
	private String mobilePhone;//手机号码
	private String area;//所属区域
	private String longitude;//初始位置经度
	private String latitude;//初始位置纬度
	private String note;//位置描述
	private String weichatID;//微信号
	private long msgTime; //记录当前消息的时间
	private Integer status = 0; //在线坐席状态 0:空闲 1:繁忙 2:离开 3:下线
	private Integer alarmStatus = 0; //0待接听  1接听 2通话中  3异常断开 4通话完成 5已出警 6拒接 7在线坐席忙
	private SocketChannel otherchannel = null;//连接客户端Socket
	private String rtmpPushUri;//推流地址
	private String rtmpPlayUri;//播放地址
	private String ecname;//紧急联系人姓名
	private String ecphone;//紧急联系人电话
	private Session session;//会话
	private long acceptTime;//接听时间
	private long closeTime = 0;//客户端关闭时间
	public String getIdentyID() {
		return identyID;
	}
	public void setIdentyID(String identyID) {
		this.identyID = identyID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getWeichatID() {
		return weichatID;
	}
	public void setWeichatID(String weichatID) {
		this.weichatID = weichatID;
	}
	public long getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(long msgTime) {
		this.msgTime = msgTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public SocketChannel getChannel() {
		return channel;
	}
	public void setChannel(SocketChannel channel) {
		this.channel = channel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public SocketChannel getOtherchannel() {
		return otherchannel;
	}
	public void setOtherchannel(SocketChannel otherchannel) {
		this.otherchannel = otherchannel;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getRtmpPushUri() {
		return rtmpPushUri;
	}
	public void setRtmpPushUri(String rtmpPushUri) {
		this.rtmpPushUri = rtmpPushUri;
	}
	public String getRtmpPlayUri() {
		return rtmpPlayUri;
	}
	public void setRtmpPlayUri(String rtmpPlayUri) {
		this.rtmpPlayUri = rtmpPlayUri;
	}
	public String getEcname() {
		return ecname;
	}
	public void setEcname(String ecname) {
		this.ecname = ecname;
	}
	public String getEcphone() {
		return ecphone;
	}
	public void setEcphone(String ecphone) {
		this.ecphone = ecphone;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public long getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(long acceptTime) {
		this.acceptTime = acceptTime;
	}
	public long getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
