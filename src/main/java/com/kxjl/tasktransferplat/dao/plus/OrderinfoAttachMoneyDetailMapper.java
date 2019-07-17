/**
 * @(#)OrderinfoAttachMoneyDetailMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface OrderinfoAttachMoneyDetailMapper  extends BaseMapper<OrderinfoAttachMoneyDetail> {


    /**
	 * 工单金额明细
	 * @param query
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	  List<OrderinfoAttachMoneyDetail> selectOrderMoneyDetailList(OrderinfoAttachMoneyDetail query);
	  
	  /**
	   * 清除工单详情信息，修改工单时修改工单类型后，重新计算工单价格
	   * @param query
	   * @return
	   * @author zj
	   * @date 2019年5月20日
	   */
	  int deleteOrderDetail(OrderinfoAttachMoneyDetail query);
	  
	  
	 /**
	  * 从工单价格记录表中计算工单的当前总价
	  * @param query
	  * @return
	  * @author zj
	  * @date 2019年5月21日
	  */
	  OrderinfoAttachMoneyDetail GetOrderTotalPrice(OrderinfoAttachMoneyDetail query);
}
