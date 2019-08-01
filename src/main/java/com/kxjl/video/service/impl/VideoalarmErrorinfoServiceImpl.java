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
import com.kxjl.video.dao.plus.VideoalarmErrorinfoMapper;
import com.kxjl.video.pojo.AlarmErrorinfo;
import com.kxjl.video.service.VideoalarmErrorinfoService;

import java.util.*;

@Service
public class VideoalarmErrorinfoServiceImpl implements VideoalarmErrorinfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoalarmErrorinfoMapper itemMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveVideoalarmErrorinfo(AlarmErrorinfo item) {
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
	public JSONObject updateVideoalarmErrorinfo(AlarmErrorinfo item) {
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
	public List<AlarmErrorinfo> selectVideoalarmErrorinfoList(AlarmErrorinfo item) {
		List<AlarmErrorinfo> itemList = new ArrayList<>();
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
	public int deleteVideoalarmErrorinfo(AlarmErrorinfo item) {
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


	public AlarmErrorinfo selectVideoalarmErrorinfoById(Long id) {
		AlarmErrorinfo data = null;

		AlarmErrorinfo query = new AlarmErrorinfo();
		query.setId(id);

		List<AlarmErrorinfo> datas = selectVideoalarmErrorinfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}
	
	  /**
     * 按天统计，每天的两种数据
     * @param item
     * @return
     * @author zj
     * @date 2019年8月1日
     */
   public List<HashMap> selectDayTotal(AlarmErrorinfo item)
    {
    	return itemMapper.selectDayTotal(item);
    }
    
    
    /**
     * 统计全部的两种数据数量
     * @param item
     * @return
     * @author zj
     * @date 2019年8月1日
     */
   public List<HashMap> selectTotal(AlarmErrorinfo item){
	   return itemMapper.selectTotal(item);
   }

}
