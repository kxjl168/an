package com.kxjl.video.pojo;

import lombok.Data;

@Data
public class VideoAlarmErrorInfo {
	private int id; //序号
	private int type;//类型  1 坐席繁忙  2超时
	private String onlineseats_id;//接警人员ID
	private String userName; //报警者姓名
	private String idNumber; //身份证号
	private String area; //区域
	private String latitude; //纬度
	private String longitude; //经度
	private String address; //地理位置
	private String wechat_id; //微信账号ID
	private String phone; //报警用户电话号码
	private String alarm_time; //报警时间
}
