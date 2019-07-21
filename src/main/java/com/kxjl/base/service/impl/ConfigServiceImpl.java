package com.kxjl.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.dao.ConfigMapper;
import com.kxjl.base.pojo.Config;
import com.kxjl.base.service.ConfigService;

import java.util.*;

@Service
public class ConfigServiceImpl implements ConfigService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfigMapper itemMapper;

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveConfig(Config item) {
		JSONObject rtn = new JSONObject();

		try {

			item.setCkey(UUIDUtil.getUUID());

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
	public JSONObject updateConfig(Config item) {
		JSONObject rtn = new JSONObject();

		if (null == item || null == item.getCkey()) {
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

	/**
	 * 从配置项中获取下一个long型 id
	 * 
	 * @return
	 * @author zj
	 * @date 2019年7月20日
	 */
	public Long getNextAutoLong() {
		Config cq = new Config();
		cq.setCkey("LongAuto");

		Config cLongAuto = itemMapper.getValueByKey(cq);
		if (cLongAuto == null) {
			cLongAuto = new Config();
			cLongAuto.setCkey("LongAuto");
			cLongAuto.setCvalue("1");
			cLongAuto.setDes("自增Long");
			itemMapper.insertSelective(cLongAuto);
		}

		Long rst = Long.parseLong(cLongAuto.getCvalue());
		cLongAuto.setCvalue(String.valueOf(rst + 1));
		itemMapper.updateByPrimaryKeySelective(cLongAuto);

		return rst;
	}

	@Override
	public List<Config> selectConfigList(Config item) {
		List<Config> itemList = new ArrayList<>();
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
	public int deleteConfig(Config item) {
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

	public Config selectConfigById(String id) {

		Config data = null;

		Config query = new Config();
		query.setCkey(id);

		List<Config> datas = selectConfigList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
