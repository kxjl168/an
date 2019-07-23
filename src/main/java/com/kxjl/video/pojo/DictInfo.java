package com.kxjl.video.pojo;

public class DictInfo {
	private int id; //主键
	private int type; //类型   1案件类型  2案件等级
	private String dict_name; //字典名称
	private String dict_value; //字典值
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getDict_value() {
		return dict_value;
	}
	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}
	
}
