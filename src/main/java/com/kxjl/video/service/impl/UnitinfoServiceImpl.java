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
import com.kxjl.video.dao.UnitinfoManagerMapper;
import com.kxjl.video.dao.UnitinfoMapper;
import com.kxjl.video.pojo.Unitinfo;
import com.kxjl.video.pojo.UnitinfoManager;
import com.kxjl.video.service.UnitinfoService;



import java.util.*;

@Service
public class UnitinfoServiceImpl implements UnitinfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UnitinfoMapper itemMapper;
	
	@Autowired
	UnitinfoManagerMapper unitinfoManagerMapper;
	
	
	 /**
	  * 更新单位管理员
	  * @param role_id
	  * @param ids
	  * @return
	  * @author zj
	  * @date 2019年7月21日
	  */
  	public int updateUnitManagerList(String unitId, String ids) {
  		

		int rst = -1;
		try {

			Unitinfo query = new Unitinfo();
			query.setId(unitId);

			unitinfoManagerMapper.deleteUnitManager(query);

			// 添加
			String[] menus = ids.split(",");
			for (int i = 0; i < menus.length; i++) {
				if (menus[i].trim().equals(""))
					continue;

				UnitinfoManager item = new UnitinfoManager();
				item.setId(UUIDUtil.getUUID());
				item.setUnitId(unitId);
				item.setManagerId(menus[i]);

				unitinfoManagerMapper.insertSelective(item);
			}

			rst = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;
  		
  	}
    

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveUnitinfo(Unitinfo item) {
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
	public JSONObject updateUnitinfo(Unitinfo item) {
		JSONObject rtn = new JSONObject();

		 if (null == item || null == item.getId()) 
		 {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			
			if(item.getParentUnit()==null||item.getParentUnit().equals(""))
				item.setParentUnit("-1"); //默认为市级别的
			
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
	public List<Unitinfo> selectUnitinfoList(Unitinfo item) {
		List<Unitinfo> itemList = new ArrayList<>();
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
	public int deleteUnitinfo(Unitinfo item) {
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

	public Unitinfo selectUnitinfoById(String id) {

		Unitinfo data = null;

		Unitinfo query = new Unitinfo();
		query.setId(id);

		List<Unitinfo> datas = selectUnitinfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
