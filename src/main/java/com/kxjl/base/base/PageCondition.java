package com.kxjl.base.base;

import lombok.Data;

/**
 * @author handsomeXiao
 * @date 2018/6/9 10:25
 */
@Data
public class PageCondition {

	private String pageNum;

	private String offset;

	private String pageSize;
	
	private long total;
	
	private int pages;

	public PageCondition() {

	}

	public PageCondition(int pagenum, int pagesize) {
		this.pageNum = String.valueOf(pagenum);
		this.pageSize = String.valueOf(pagesize);
	}
	
	public PageCondition(int pagenum, int pagesize,int pages,long total) {
		this.pageNum = String.valueOf(pagenum);
		this.pageSize = String.valueOf(pagesize);
		this.pages=pages;
		this.total=total;
	}
}
