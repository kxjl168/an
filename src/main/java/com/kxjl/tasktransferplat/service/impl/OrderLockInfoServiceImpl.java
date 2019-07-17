package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.RedisUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderLockInfoMapper;
import com.kxjl.tasktransferplat.pojo.OrderLockInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.OrderLockInfoService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.RedisLockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class OrderLockInfoServiceImpl implements OrderLockInfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderLockInfoMapper itemMapper;

	@Autowired
	private OrderinfoService orderinfoService;

	@Autowired
	private RedisLockService redisLockService;

	/**
	 * 查询指定工单号第几把锁的图片信息
	 * 
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年7月12日
	 */
	public OrderLockInfo loadOrderLockInfo(String orderNo, String index) {
		OrderLockInfo oquery = new OrderLockInfo();

		try {
			// Pattern pattern = Pattern.compile("\\d+");
			// if(!pattern.matcher(orderNo).matches())
			// {
			// 为id
			// Orderinfo oinfo=orderinfoService.loadOrderinfoById(orderNo);
			// orderNo=oinfo.getOrderNo();

			// }

		} catch (Exception e) {
			// TODO: handle exception
		}

		oquery.setOrderNo(orderNo);
		oquery.setLockIndex(index);

		List<OrderLockInfo> infos = selectOrderLockInfoList(oquery);
		if (infos == null || infos.size() == 0) {
			return null;
		} else
			return infos.get(0);

	}

	public AppResult cleanOrderLockPicInfoAndSetIMEI(String orderNo, String index, String imei) {
		AppResult rst = new AppResult();
		rst.setErrCode(AppResultUtil.fail_code);

		try {

			OrderLockInfo info = loadOrderLockInfo(orderNo, index);
			if (info == null) {// String imgType//1前，2中，3后
				OrderLockInfo infonew = new OrderLockInfo();

				infonew = new OrderLockInfo();
				infonew.setOrderNo(orderNo);
				infonew.setLockIndex(index);
				saveOrderLockInfo(infonew);

			} else {
				info.setStartimgs("");
				info.setLockimgs("");
				info.setDoneimgs("");
				updateOrderLockInfo(info);
			}

			// imei 设置密码接口单独处理
			// info.setImeiNum(imei);

			rst = AppResultUtil.success();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;
	}

	/**
	 * 更新锁IMEI信息
	 * 
	 * @param orderinfo
	 * @author zj
	 * @date 2019年7月15日
	 */
	public AppResult updateOrderLockImeiInfo(String orderNo, String index, String imei) {

		AppResult rst = new AppResult();
		rst.setErrCode(AppResultUtil.fail_code);

		try {

			OrderLockInfo info = loadOrderLockInfo(orderNo, index);
			// String imgType//1前，2中，3后
			OrderLockInfo infonew = new OrderLockInfo();
			if (info == null) {
				infonew = new OrderLockInfo();
				infonew.setOrderNo(orderNo);
				infonew.setLockIndex(index);

			} else
				infonew = info;

			infonew.setImeiNum(imei);

			if (info != null)
				updateOrderLockInfo(infonew);
			else
				saveOrderLockInfo(infonew);

			rst = AppResultUtil.success();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;

	}

	public AppResult updateOrderLockPicInfo(String orderNo, String index, String md5, String imgType) {
		AppResult rst = new AppResult();
		rst.setErrCode(AppResultUtil.fail_code);

		try {
			String version = String.valueOf(redisLockService.getVersion());
			// 加锁
			boolean lockResult = redisLockService.blockLock("updateOrderLockImg_" + orderNo, version);
			if (lockResult) {

				OrderLockInfo info = loadOrderLockInfo(orderNo, index);
				// String imgType//1前，2中，3后
				OrderLockInfo infonew = new OrderLockInfo();
				if (info == null) {
					infonew = new OrderLockInfo();
					infonew.setOrderNo(orderNo);
					infonew.setLockIndex(index);

				} else
					infonew = info;

				// redisUtil

				switch (imgType) {
				case "1":
					infonew.setStartimgs((infonew.getStartimgs() == null ? "" : infonew.getStartimgs()) + md5 + ",");
					break;
				case "2":
					infonew.setLockimgs((infonew.getLockimgs() == null ? "" : infonew.getLockimgs()) + md5 + ",");
					break;
				case "3":
					infonew.setDoneimgs((infonew.getDoneimgs() == null ? "" : infonew.getDoneimgs()) + md5 + ",");
					break;
				default:
					break;
				}

				if (info != null)
					updateOrderLockInfo(infonew);
				else
					saveOrderLockInfo(infonew);

				rst = AppResultUtil.success();

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			redisLockService.unlock("updateOrderLockImg_" + orderNo);
		}

		return rst;
	}

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveOrderLockInfo(OrderLockInfo item) {
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
	public JSONObject updateOrderLockInfo(OrderLockInfo item) {
		JSONObject rtn = new JSONObject();

		if (null == item || null == item.getId()) {
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
	public List<OrderLockInfo> selectOrderLockInfoList(OrderLockInfo item) {
		List<OrderLockInfo> itemList = new ArrayList<>();
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
	public int deleteOrderLockInfo(OrderLockInfo item) {
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

	public OrderLockInfo selectOrderLockInfoById(String id) {

		OrderLockInfo data = null;

		OrderLockInfo query = new OrderLockInfo();
		query.setId(id);

		List<OrderLockInfo> datas = selectOrderLockInfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
