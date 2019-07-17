package com.kxjl.base.util.AliSMS;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.kxjl.base.util.DBConfigUtil;
import com.kxjl.base.util.PropertiesUtil;
import com.kxjl.base.util.RedisUtil;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

//import net.sf.json.JSONObject;

@Component
public class AliSendSMS {

	private static Logger logger = LoggerFactory.getLogger(AliSendSMS.class);

	@Autowired
	private DBConfigUtil dbConfigUtil;
	
	private static DBConfigUtil dbUtil;
	
	@PostConstruct
	public void init() {
		dbUtil=dbConfigUtil;
	}
	
	
	/**
	 * 产品名称:云通信短信API产品,开发者无需替换
	 */
	private static final String product = "Dysmsapi";
	/**
	 * 产品域名,开发者无需替换
	 */
	private static final String domain = "dysmsapi.aliyuncs.com";

	private static final String accessKeyId = AliSMSInfo.accessKeyId;
	private static final String accessKeySecret = AliSMSInfo.accessKeySecret;
	private static final String signName = AliSMSInfo.signName;
	private static final String smsCode = AliSMSInfo.smsCode;
	private static final String messageCode = AliSMSInfo.messageCode;
	private static final String messageNormal = AliSMSInfo.messageNormal;
	
	private static final String smsOrderCode = AliSMSInfo.smsOrderCode;
	private static final String smsOrderCode2 = AliSMSInfo.smsOrderCode2;
	
	private static final String smsPhone = AliSMSInfo.smsPhone;

	public static String validateCodeInfo(Integer validateCode) {
		return "尊敬的用户，您本次的验证码为：" + validateCode + "，3分钟内有效。如非本人操作请忽略本信息。";
	}

	static IAcsClient smsInIt() {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			
			
		
			
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error(ExceptionUntil.getMessage(e));
		}
		return new DefaultAcsClient(profile);
	}
	
	/**
	 * 发送工单核销码,name为null或空，则使用无公司信息模板
	 * @param phone
	 * @param code
	 * @return
	 * @author zj
	 * @date 2019年6月24日
	 */
	public static Boolean sendOrderComplateCodeSms(String phone,String name, String code) {

		String smsenable = dbUtil.getParam("sms.enable");
		if (smsenable.equals("false")) // 不启用短信，随便都通过
		{
			logger.info("验证短信:" + code, "后台配置不发送短信");
			return true;
		}
		
		
		//dbUtil.getParam("sms.test");
		
		String test =dbUtil.getParam("sms.test");// dbUtil.getParam("sms.test");
		if (test.equals("true")) {
			//测试模式，不在名单中则不发送
			String testPhones =dbUtil.getParam("sms.testPhones");// dbUtil.getParam("sms.testPhones");
			if (!testPhones.contains(phone))
				return false;
		}

		
		String a="1";
		 a="2";
		
		IAcsClient acsClient = smsInIt();
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到
			
		
			request.setTemplateCode(smsOrderCode);
			// 可选:模板中的变量替换JSON串,如 您的验证码为${code}"时,此处的值为
			Map<String, String> map = new HashMap<>();
			map.put("code", code);
			map.put("companyName", name);
			map.put("cphone", smsPhone);
			// request.setTemplateParam(JSONObject.fromObject(map).toString());
			request.setTemplateParam(JSONObject.toJSONString(map));

			// hint 此处可能会抛出异常，注意catch
			sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject obj = new JSONObject();
			obj.put("Code", sendSmsResponse.getCode());
			obj.put("Message", sendSmsResponse.getMessage());
			obj.put("RequestId", sendSmsResponse.getRequestId());
			obj.put("BizId", sendSmsResponse.getBizId());
			logger.info(obj.toString());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error("发送验证短信失败");
			logger.error(ExceptionUntil.getMessage(e));
		}
		return sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode());
	}


	public static Boolean sendCodeSms(String phone, String code) {

		String smsenable = dbUtil.getParam("sms.enable");
		if (smsenable.equals("false")) // 不启用短信，随便都通过
		{
			logger.info("验证短信:" + code, "后台配置不发送短信");
			return true;
		}
		

		
		//dbUtil.getParam("sms.test");
		
		String test =dbUtil.getParam("sms.test");// dbUtil.getParam("sms.test");
		if (test.equals("true")) {
			//测试模式，不在名单中则不发送
			String testPhones =dbUtil.getParam("sms.testPhones");// dbUtil.getParam("sms.testPhones");
			if (!testPhones.contains(phone))
				return false;
		}

		IAcsClient acsClient = smsInIt();
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(smsCode);
			// 可选:模板中的变量替换JSON串,如 您的验证码为${code}"时,此处的值为
			Map<String, String> map = new HashMap<>();
			map.put("code", code);
			// request.setTemplateParam(JSONObject.fromObject(map).toString());
			request.setTemplateParam(JSONObject.toJSONString(map));

			// hint 此处可能会抛出异常，注意catch
			sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject obj = new JSONObject();
			obj.put("Code", sendSmsResponse.getCode());
			obj.put("Message", sendSmsResponse.getMessage());
			obj.put("RequestId", sendSmsResponse.getRequestId());
			obj.put("BizId", sendSmsResponse.getBizId());
			logger.info(obj.toString());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error("发送验证短信失败");
			logger.error(ExceptionUntil.getMessage(e));
		}
		return sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode());
	}

	/**
	 * 发送锁匠普通文本短信--未通过审核消息通知 SMS_167961813 模版内容: 您的工单${orderNo}申请未通过审核,请及时处理
	 * 
	 * @param phone
	 * @param message
	 *            文本消息
	 * @return
	 * @author zj
	 * @date 2019年5月30日
	 */
	public static Boolean sendMessageSms(String phone, String message) {

		String smsenable = dbUtil.getParam("sms.enable");
		if (smsenable.equals("false")) // 不启用短信，随便都通过
		{
			logger.info("消息短信:" + message, "后台配置不发送短信");
			return true;
		}

		
		//dbUtil.getParam("sms.test");
		
		String test =dbUtil.getParam("sms.test");// dbUtil.getParam("sms.test");
		if (test.equals("true")) {
			//测试模式，不在名单中则不发送
			String testPhones =dbUtil.getParam("sms.testPhones");// dbUtil.getParam("sms.testPhones");
			if (!testPhones.contains(phone))
				return false;
		}

		IAcsClient acsClient = smsInIt();
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(messageCode);
			// 可选:模板中的变量替换JSON串,如 您的验证码为${code}"时,此处的值为
			// JSONObject jsonObject = JSONObject.parseObject(message);
			Map<String, String> map = new HashMap<>();
			// map.put("msg", jsonObject.getString("msg"));

			map.put("orderNo", message);

			request.setTemplateParam(JSONObject.toJSONString(map));

			// hint 此处可能会抛出异常，注意catch
			sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject obj = new JSONObject();
			obj.put("Code", sendSmsResponse.getCode());
			obj.put("Message", sendSmsResponse.getMessage());
			obj.put("RequestId", sendSmsResponse.getRequestId());
			obj.put("BizId", sendSmsResponse.getBizId());
			logger.info(obj.toString());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error("发送消息短信失败");
			logger.error(ExceptionUntil.getMessage(e));
		}
		return sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode());
	}

	/**
	 * 通用消息
	 * 
	 * 您有一条新的系统消息，请通过小程序查看
	 * 
	 * @param phone
	 * @param message
	 *            无用
	 * @return
	 * @author zj
	 * @date 2019年6月14日
	 */
	public static Boolean sendMessageNoraml(String phone, String message) {

		String smsenable = dbUtil.getParam("sms.enable");
		if (smsenable.equals("false")) // 不启用短信，随便都通过
		{
			logger.info("消息短信:" + message + ",后台配置不发送短信");
			return true;
		}

		
		//dbUtil.getParam("sms.test");
		
		String test =dbUtil.getParam("sms.test");// dbUtil.getParam("sms.test");
		if (test.equals("true")) {
			//测试模式，不在名单中则不发送
			String testPhones =dbUtil.getParam("sms.testPhones");// dbUtil.getParam("sms.testPhones");
			if (!testPhones.contains(phone))
				return false;
		}
		
		String a="1";
		

		IAcsClient acsClient = smsInIt();
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(messageNormal);
			// 可选:模板中的变量替换JSON串,如 您的验证码为${code}"时,此处的值为
			// JSONObject jsonObject = JSONObject.parseObject(message);
			Map<String, String> map = new HashMap<>();
			// map.put("msg", jsonObject.getString("msg"));

			// map.put("orderNo", message);

			request.setTemplateParam(JSONObject.toJSONString(map));

			// hint 此处可能会抛出异常，注意catch
			sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject obj = new JSONObject();
			obj.put("Code", sendSmsResponse.getCode());
			obj.put("Message", sendSmsResponse.getMessage());
			obj.put("RequestId", sendSmsResponse.getRequestId());
			obj.put("BizId", sendSmsResponse.getBizId());
			logger.info(obj.toString());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error("发送消息短信失败");
			logger.error(ExceptionUntil.getMessage(e));
		}
		return sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode());
	}

	public static Boolean sendZTZHPassSms(String phone, String code, String time, String date) {
		IAcsClient acsClient = smsInIt();
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode("SMS_162738628");
			// 可选:模板中的变量替换JSON串,如 您的验证码为${code}"时,此处的值为
			Map<String, String> map = new HashMap<>();
			map.put("code", code);
			map.put("time", time);
			map.put("newTime", date);
			request.setTemplateParam(JSONObject.toJSONString(map));

			// hint 此处可能会抛出异常，注意catch
			sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject obj = new JSONObject();
			obj.put("Code", sendSmsResponse.getCode());
			obj.put("Message", sendSmsResponse.getMessage());
			obj.put("RequestId", sendSmsResponse.getRequestId());
			obj.put("BizId", sendSmsResponse.getBizId());
			logger.info(obj.toString());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error("发送消息短信失败");
			logger.error(ExceptionUntil.getMessage(e));
		}
		return sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode());
	}

}
