package ${basepackageName}.service.impl;



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
<#if daosubfold==''>
import ${basepackageName}.dao.${modelClass}Mapper;
</#if>
<#if daosubfold!=''>
import ${basepackageName}.dao.${daosubfold}.${modelClass}Mapper;
</#if>
import ${basepackageName}.pojo.${modelClass};
import ${basepackageName}.service.${modelClass}Service;

import java.util.*;

@Service
public class ${modelClass}ServiceImpl implements ${modelClass}Service {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ${modelClass}Mapper itemMapper;
	
	 <#if idType=='1'>
	@Autowired
	private RedisTemplate redisTemplate;
		</#if>

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject save${modelClass}(${modelClass} item) {
		JSONObject rtn = new JSONObject();


		try {

			 <#if idType=='2'>
			item.setId(UUIDUtil.getUUID());
			</#if >
			 <#if idType=='1'>
			item.setId(	UUIDUtil. getLongUUID(redisTemplate));
				</#if>
			

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
	public JSONObject update${modelClass}(${modelClass} item) {
		JSONObject rtn = new JSONObject();

		 <#if idType=='1'>
		 if (item.getId()==null) 
		 </#if>
		 <#if idType=='2'>
		 if (null == item || null == item.getId()) 
		 </#if>
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
	public List<${modelClass}> select${modelClass}List(${modelClass} item) {
		List<${modelClass}> itemList = new ArrayList<>();
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
	public int delete${modelClass}(${modelClass} item) {
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

	 <#if idType=='2'>
	public ${modelClass} select${modelClass}ById(String id) {
	</#if >

	 <#if idType=='1'>
	public ${modelClass} select${modelClass}ById(Long id) {
	</#if >
		${modelClass} data = null;

		${modelClass} query = new ${modelClass}();
		query.setId(id);

		List<${modelClass}> datas = select${modelClass}List(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
