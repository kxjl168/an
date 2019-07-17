package com.kxjl.tasktransferplat.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.UserOpinionMapper;
import com.kxjl.tasktransferplat.pojo.UserOpinion;
import com.kxjl.tasktransferplat.service.UserOpinionService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import java.util.*;

@Service
public class UserOpinionServiceImpl implements UserOpinionService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserOpinionMapper itemMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	@Override
	public List<UserOpinion> selectUserOpinionList(UserOpinion item) {
		List<UserOpinion> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Override
	public List<UserOpinion> selectCustormerOpinionList(UserOpinion item) {
		List<UserOpinion> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectCustormerList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询客户列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteUserOpinion(UserOpinion item) {
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


	public UserOpinion selectUserOpinionById(Long id) {
		UserOpinion data = null;

		UserOpinion query = new UserOpinion();
		query.setId(id);

		List<UserOpinion> datas = selectUserOpinionList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

	@Override
	public int submitOpinion(UserOpinion data) {
//			data.setId(UUIDUtil. getLongUUID(redisTemplate));
			data.setId(snowflakeIdWorker.nextId());

			return itemMapper.insertSelective(data);

	}

}
