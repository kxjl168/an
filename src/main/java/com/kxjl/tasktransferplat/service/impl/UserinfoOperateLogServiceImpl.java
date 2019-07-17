package com.kxjl.tasktransferplat.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.UserinfoOperateLogMapper;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;
import com.kxjl.tasktransferplat.service.UserinfoOperateLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class UserinfoOperateLogServiceImpl implements UserinfoOperateLogService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserinfoOperateLogMapper itemMapper;
	

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveUserinfoOperateLog(UserinfoOperateLog item) {
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
	public JSONObject updateUserinfoOperateLog(UserinfoOperateLog item) {
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
	public List<UserinfoOperateLog> selectUserinfoOperateLogList(UserinfoOperateLog item) {
		List<UserinfoOperateLog> itemList = new ArrayList<>();
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
	public int deleteUserinfoOperateLog(UserinfoOperateLog item) {
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

	public UserinfoOperateLog selectUserinfoOperateLogById(String id) {

		UserinfoOperateLog data = null;

		UserinfoOperateLog query = new UserinfoOperateLog();
		query.setId(id);

		List<UserinfoOperateLog> datas = selectUserinfoOperateLogList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
