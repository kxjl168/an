package com.kxjl.tasktransferplat.service.impl;



import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoAfterServiceMapper;
import com.kxjl.tasktransferplat.pojo.OrderinfoAfterService;
import com.kxjl.tasktransferplat.service.OrderinfoAfterServiceService;

import java.util.*;

@Service
public class OrderinfoAfterServiceServiceImpl implements OrderinfoAfterServiceService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderinfoAfterServiceMapper itemMapper;
	

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveOrderinfoAfterService(OrderinfoAfterService item) {
		JSONObject rtn = new JSONObject();


		try {

			item.setId(UUIDUtil.getUUID());
			

			itemMapper.insertSelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrderinfoAfterService(OrderinfoAfterService item) {
		JSONObject rtn = new JSONObject();

		 if (null == item || null == item.getId()) 
		 {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			itemMapper.updateByPrimaryKeySelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新出错", e);
			rtn.put("bol", false);
			rtn.put("message", "更新出错");
			return rtn;
		}
	}

	@Override
	public List<OrderinfoAfterService> selectOrderinfoAfterServiceList(OrderinfoAfterService item) {
		List<OrderinfoAfterService> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteOrderinfoAfterService(OrderinfoAfterService item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override

	public OrderinfoAfterService selectOrderinfoAfterServiceById(String id) {

		OrderinfoAfterService data = null;

		OrderinfoAfterService query = new OrderinfoAfterService();
		query.setId(id);

		List<OrderinfoAfterService> datas = selectOrderinfoAfterServiceList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
