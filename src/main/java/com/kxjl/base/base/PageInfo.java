package com.kxjl.base.base;

import lombok.Data;

/**
 * 接口页面信息 PageInfo.java.
 * 
 * @author zj
 * @version 1.0.1 2019年1月29日
 * @revision zj 2019年1月29日
 * @since 1.0.1
 */
@Data
public class PageInfo {

	private int pageNum;

	private int pageSize;

	private long total;

	private int pages;//页数

	public PageInfo() {

	}

	public PageInfo(int pagenum, int pagesize,int pages,long total) {
		this.pageNum = pagenum;
		this.pageSize =pagesize;
		this.pages=pages;
		this.total=total;
	}
}
