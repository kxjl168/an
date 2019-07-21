package com.kxjl.video.service.impl;



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
import com.kxjl.video.dao.VideoalarmInfoMapper;
import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.service.VideoalarmInfoService;

import java.util.*;

@Service
public class VideoalarmInfoServiceImpl implements VideoalarmInfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoalarmInfoMapper itemMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveVideoalarmInfo(VideoalarmInfo item) {
		JSONObject rtn = new JSONObject();


		try {

			item.setId(	UUIDUtil. getLongUUID(redisTemplate));
			

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
	public JSONObject updateVideoalarmInfo(VideoalarmInfo item) {
		JSONObject rtn = new JSONObject();

		 if (item.getId()==null) 
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
	public List<VideoalarmInfo> selectVideoalarmInfoList(VideoalarmInfo item) {
		List<VideoalarmInfo> itemList = new ArrayList<>();
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
	public int deleteVideoalarmInfo(VideoalarmInfo item) {
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


	public VideoalarmInfo selectVideoalarmInfoById(Long id) {
		VideoalarmInfo data = null;

		VideoalarmInfo query = new VideoalarmInfo();
		query.setId(id);

		List<VideoalarmInfo> datas = selectVideoalarmInfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
