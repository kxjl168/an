package com.kxjl.tasktransferplat.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.dao.ManagerMapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.ProjectMapper;
import com.kxjl.tasktransferplat.pojo.Project;
import com.kxjl.tasktransferplat.service.ProjectService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ProjectServiceImpl implements ProjectService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProjectMapper itemMapper;
	
	 @Autowired
		private RedisTemplate redisTemplate;

	 @Autowired
	 private OrderinfoMapper orderinfoMapper;

	 @Autowired
	 private ManagerMapper managerMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveProject(Project item) {
		JSONObject rtn = new JSONObject();


		try {

				item.setId(UUIDUtil.getUUID());
				item.setProjectState(Long.valueOf(1));

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
	public JSONObject updateProject(Project item) {
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
	public List<Project> selectProjectList(Project item) {
		List<Project> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
			for (Project project:itemList) {
				project.setCreateTime(DateUtil.getDayString(project.getCreateTime()));
				project.setPrincipalName(managerMapper.selectByPrimaryKey(project.getPrincipal()).getNickname());
				project.setNum(orderinfoMapper.selectCountNum(String.valueOf(project.getId())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteProject(Project item) {
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


	public Project selectProjectById(String id) {
		Project data = null;

		Project query = new Project();
		query.setId(id);

		List<Project> datas = selectProjectList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);
			String createTime = data.getCreateTime();
			if (!isBlank(createTime)) {
				data.setCreateTime(createTime.split("[.]")[0]);
			}
		}
		return data;

	}

}
