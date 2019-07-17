package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.PropertiesUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.tasktransferplat.dao.plus.AppSmsMapper;
import com.kxjl.tasktransferplat.pojo.AppSms;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.AppSmsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AppSmsServiceImpl implements AppSmsService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppSmsMapper itemMapper;

	/**
	 * 发送短信验证码
	 *
	 * @param appSms
	 * @return
	 */
	@Override
	public AppResult sendValidateCode(AppSms appSms) {
		AppResult resultDto = new AppResult();
		resultDto.setErrCode(AppResultUtil.success_code);

		String phone = appSms.getMobile();

		// 生成验证码
		Random random = new Random();
		Integer validateCode = random.nextInt(899999);
		validateCode = validateCode + 100000;

		// 发送短信
		// 云之讯
		// SendSmsUtils.templateSMS(phone, String.valueOf(validateCode));

		String smsenable = PropertiesUtil.getProperty("sms.enable");
		if (smsenable.equals("true")) // 不启用短信，随便都通过
		{
			// 阿里短信
			com.kxjl.base.util.AliSMS.AliSendSMS.sendCodeSms(phone, String.valueOf(validateCode));
		}

		String content = AliSendSMS.validateCodeInfo(validateCode);

		// 插入数据库记录
		appSms.setId(UUIDUtil.getUUID());
		appSms.setContent(content);
		appSms.setSendTime(System.currentTimeMillis());
		appSms.setCheckCode("" + validateCode);
		itemMapper.insert(appSms);

		resultDto=AppResultUtil.success("短信发送成功!");
		
		return resultDto;
	}

	/**
	 * @param resultDto
	 * @param user
	 * @return
	 */
	@Override
	public AppResult checkCodeValid(AppResult resultDto, Userinfo user) {
		/*
		 * if(true) return resultDto;
		 */

		resultDto=AppResultUtil.success("短信验证成功!");
		
		String smsenable = PropertiesUtil.getProperty("sms.enable");
		if (smsenable.equals("false")) { // 不启用短信，随便都通过
			return resultDto;
		}
		// 验证码是否正确
		AppSms appSms = itemMapper.selectLatestCode(user.getPhone());
		if (appSms == null || !appSms.getCheckCode().equals(user.getCheckCode())) {
			//resultDto.setData(-3);
			//resultDto.setErrCode("-1");
			//resultDto.setErrMsg("验证码不正确");

			resultDto=AppResultUtil.fail("验证码不正确");
			return resultDto;
		}

		// 验证码是否过期
		long sendTime = appSms.getSendTime();
		long currentTime = System.currentTimeMillis();
		long difMinutes = TimeUnit.MINUTES.convert(currentTime - sendTime, TimeUnit.MILLISECONDS);
		// 若大于5分钟有效期，则验证码过期
		if (difMinutes > 5) {
			resultDto.setErrCode("-4");
			resultDto.setData(-4);
			resultDto.setErrMsg("验证码已失效");
			
			resultDto=AppResultUtil.fail("验证码已失效");
		}
		return resultDto;
	}

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveAppSms(AppSms item) {
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
	public JSONObject updateAppSms(AppSms item) {
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
	public List<AppSms> selectAppSmsList(AppSms item) {
		List<AppSms> itemList = new ArrayList<>();
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
	public int deleteAppSms(AppSms item) {
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

	public AppSms selectAppSmsById(String id) {

		AppSms data = null;

		AppSms query = new AppSms();
		query.setId(id);

		List<AppSms> datas = selectAppSmsList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
