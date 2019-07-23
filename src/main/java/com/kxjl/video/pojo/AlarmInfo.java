package com.kxjl.video.pojo;

import lombok.Data;

@Data
public class AlarmInfo {
	private int id;
	private String onlineSeatsId;//接警人员ID
	private int type;//类型  1 图文报警  2在线坐席上传
	private String userName;//报警者姓名
	private String idNumber;//身份证号
	private String area;//所属区域
	private String longitude;//初始位置经度
	private String latitude;//初始位置纬度
	private String note;//位置描述
	//private String address;//地理位置
	private String weChatId;//微信账号ID
	private String phone;//电话号码
	private String occurrenceTime;//警情发生时间
	private String occurrence_address; //警情发送地点
	private String description;//警情描述
	private String alarmTime;//报警时间
	private String pictureUrl;//图片Url地址 多个用，分割
	private String videoUrl;//视频Url地址 多个用，分割
	private String audioUrl;//语音Url地址 多个用，分割
	private String case_type;//案件类别
	private String case_level;//案件等级
	private String status;//报警状态  已报警、已受理、已出警、已结束
}
