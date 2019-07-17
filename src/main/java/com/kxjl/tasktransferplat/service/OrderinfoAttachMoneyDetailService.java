/**
 * @(#)OrderinfoAttachMoneyDetailService.java  2019-01-28 11:16:28
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;

import java.util.List;
import java.util.Map;

import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 11:16:28
 * @since 1.0.0
 */
public interface OrderinfoAttachMoneyDetailService {
	
	/**
	 * 工单金额明细
	 * @param query
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	  List<OrderinfoAttachMoneyDetail> selectOrderMoneyDetailList(OrderinfoAttachMoneyDetail query);
	  
	  
	  /**
	   * 工单改价
	   * @param datas  价格数据，id,价格
	   * @param reason 说明
	   * @param user 用户
	   * @return
	   * @author zj
	   * @date 2019年6月4日
	   */
	  Message ChangeOrderMoneydetail(Map<String,String> datas,String reason,String orderId,Manager user) throws Exception; 
}
