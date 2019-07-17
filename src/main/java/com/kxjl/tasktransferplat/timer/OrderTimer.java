package com.kxjl.tasktransferplat.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kxjl.base.util.DateUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

import java.util.*;

/**
 * 工单超时检查 OrderTimer.java.
 * 
 * @author zj
 * @version 1.0.1 2019年5月17日
 * @revision zj 2019年5月17日
 * @since 1.0.1
 */
@Component
public class OrderTimer {

	@Autowired
	private OrderinfoMapper orderinfoMapper;

	/**
	 * 锁记录定时任务 ,10分钟检查一次
	 */
	//@Scheduled(cron = "0 */10 * * * ?")
	@Scheduled(cron = "0 */10 * * * ?")
	@Async("Async")
	public void OrderTimeOutCheck() {

		System.out.println("OrderTimeOutCheck run... ");
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);

		check(calendar.getTime());
	}

	/**
	 * 根据时间计算
	 * 
	 * @param date
	 * @author zj
	 * @date 2019年4月15日
	 */
	@Async("Async")
	public void check(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.DATE, -1);

		// 超时工单
		List<Orderinfo> timeoutList = new ArrayList<>();

		Orderinfo querytodo = new Orderinfo();
		List<Orderinfo> todos = orderinfoMapper.selectTodoOrderInfoList(querytodo);

		Long time = 0L;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -2); // 两小时超时
		
		//c.add(Calendar.MINUTE, -5); // 5分钟检查
		time = c.getTime().getTime();

		for (Orderinfo orderinfo : todos) {

			// 待接工单 没有合伙人 检查创建时间是否大于两小时
			if (orderinfo.getCompanyId() == null) {
				if (DateUtil.getDate(orderinfo.getCreateTime(), "").getTime() < time) {
					// 超时
					timeoutList.add(orderinfo);
				}
			}

			else {
				// 有合伙人， 检查分单时间是否大于两小时
				if(orderinfo.getReceiveTime()==null)
					// 超时兼容
					timeoutList.add(orderinfo);
				else if (DateUtil.getDate(orderinfo.getReceiveTime(), "").getTime() < time) {
					// 超时
					timeoutList.add(orderinfo);
				}

			}

		}

		List<Orderinfo> doings = orderinfoMapper.selectDoingOrderInfoList(querytodo);

		for (Orderinfo orderinfo : doings) {

			// 待处理工单

		
			// 检查分单时间>两个小时的， 工单状态还不为210，即还没有确认开始工作的
			if (orderinfo.getReceiveTime()==null|| (DateUtil.getDate(orderinfo.getReceiveTime(), "").getTime() < time)) {
				if (!orderinfo.getOrderState().equals(210))
					// 超时
					timeoutList.add(orderinfo);
			}

		}
		
		System.out.println(timeoutList.size()+"各工单超时...");
		
		for (Orderinfo orderinfo : timeoutList) {
			
			Orderinfo o=new Orderinfo();
			o.setId(orderinfo.getId());
			o.setTimeout("1");//超时
			orderinfoMapper.updateByPrimaryKeySelective(o);
		}

		

	}
}
