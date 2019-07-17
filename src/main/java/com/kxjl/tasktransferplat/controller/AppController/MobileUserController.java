/**
 * @(#)MobileUserController.java 2019-01-24
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.AppController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.controller.Common.UploadFileController;
import com.kxjl.base.util.*;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.base.util.sendpost.HttpSendPost;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoJobAreaMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 登陆 MobileUserController.java.
 *
 * @author zj
 * @version 1.0.1 2019年1月24日
 * @revision zj 2019年1月24日
 * @since 1.0.1
 */
@Controller
@RequestMapping("/interface")
@PropertySource("classpath:project.properties")
public class MobileUserController extends AppBaseController {

	@Value("${HTTP_PATH}")
	private String FILE_SVR_HTTP_OUTER_PATH;

	@Value("${FILE_SVR_PATH}")
	private String FILE_SVR_PATH;

	@Value("${login.wxappid}")
	private String appid;

	@Value("${login.wxsecret}")
	private String secret;

	@Autowired
	private UserinfoService userService;

	@Autowired
	private UserinfoAuditService userinfoAuditService;

	@Autowired
	private AppSmsService appSmsService;

//	@Autowired
	UploadFileController uploadController;

	@Autowired
	OrderinfoService orderinfoService;

	@Autowired
	private UserinfoAddService userinfoAddService;

	@Autowired
	private UserinfoJobAreaMapper userinfoJobAreaMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    UserMessageMapper userMessageMapper;

	private Logger logger = Logger.getLogger(MobileUserController.class);

	@Autowired
	private AreaService areaService;

	@Autowired
	private OrderinfoMapper orderinfoMapper;

	/**
	 * 用户注册
	 *
	 * @param baseRequestDto
	 * @return
	 * @author zhangyong
	 */
	@RequestMapping("/loginLocksmith/regist")
	@ResponseBody
	@NoNeedAuthorization
	public AppResult regist(BaseRequestDto baseRequestDto, HttpServletRequest request, HttpServletResponse response) {
		try {
			com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject
					.parseObject(baseRequestDto.getData());
			Integer type = jsonObject.getInteger("type");
			String name = jsonObject.getString("name");
			String phone = jsonObject.getString("phone");
			String code = jsonObject.getString("code");
			String pass = jsonObject.getString("pass");
			String identy = jsonObject.getString("identy");

			Userinfo userinfo = new Userinfo();
			userinfo.setName(name);
			userinfo.setPhone(phone);
			userinfo.setPassword(PasswordUtil.encrypt(pass, phone));
			userinfo.setId(snowflakeIdWorker.nextId());
			userinfo.setAuditFlag(0 + "");
			userinfo.setCheckCode(code);
			userinfo.setIdCard(identy);

			AppResult appResult = appSmsService.checkCodeValid(new AppResult(), userinfo);
			if (!appResult.getErrCode().equals("1")) {
				return appResult;
			}

			List<Userinfo> userinfos = userService.selectUserinfoByPhone(userinfo);
			if (null != userinfos && userinfos.size() > 0) {
				if (userinfos.get(0).getDataState() == 1 ) { //&& userinfos.get(0).getAuditFlag().equals("1")
					return AppResultUtil.fail("该号码已注册");
				} else if (userinfos.get(0).getDataState() == 0 ) {
					return AppResultUtil.fail("该号码已被禁用");
				} else if (userinfos.get(0).getDataState() == 3 ) {
					return AppResultUtil.fail("该号码已被列入黑名单");
				} else { //2 删除
					userService.deleteTrueUserinfo(userinfos.get(0));// 删除废弃或者逻辑删除的数据
				}
			}
			Userinfo idCardUser = new Userinfo();
			idCardUser.setIdCard(identy);
			List<Userinfo> idCardUserList = userService.selectUserinfoList(idCardUser);
			if (null != idCardUserList && idCardUserList.size() > 0) {
				return AppResultUtil.fail("该身份证号已注册");
			}

			String districtIds = "";
			switch (type) {
			// 个人锁匠
			case 0:
				List<Integer> districtIdList = (List) jsonObject.getJSONArray("districtIdList");
				if (districtIdList == null || districtIdList.size() == 0) {
					return AppResultUtil.fail("请选择服务区域");
				}
				for (Integer districtId : districtIdList) {
					districtIds = districtIds + districtId + ",";
				}

				// 默认塞入作业区域作为锁匠所在区域
				Map mpdata = areaService.selectAll(districtIdList.get(0));
				if (mpdata != null) {
					userinfo.setAreaCode(String.valueOf(mpdata.get("areacode")));
				}
				userinfo.setUserType(1 + "");
				userinfo.setDistrictids(districtIds);
				userService.saveUserinfo(userinfo);
				return AppResultUtil.success("注册成功");
			// 合伙人下锁匠
			case 1:
				Long partnerId = jsonObject.getLong("partnerId");
				if (partnerId == null || partnerId == 0) {
					return AppResultUtil.fail("请选择合伙人");
				}
				userinfo.setUserType(4 + "");
				userinfo.setCompanyId(partnerId);
				userinfo.setParterAuditFlag(0 + "");
				UserinfoJobArea userinfoJobArea = new UserinfoJobArea();
				userinfoJobArea.setLockId(partnerId);
				List<UserinfoJobArea> userinfoJobAreaList = userinfoJobAreaMapper.selectList(userinfoJobArea);

				for (UserinfoJobArea item : userinfoJobAreaList) {
					districtIds = districtIds + item.getDistrictId() + ",";
				}

				// 默认塞入合伙人作业区域作为锁匠所在区域
				Map mpdata2 = areaService.selectAll(userinfoJobAreaList.get(0).getDistrictId());
				if (mpdata2 != null) {
                    userinfo.setAreaCode(String.valueOf(mpdata2.get("areacode")));
                }
				userinfo.setDistrictids(districtIds);
				userService.saveUserinfo(userinfo);

                String messageStr = userinfo.getName()+"请求加入您的团队,请及时审核！";
                //插入消息
                UserMessage um = new UserMessage();
                um.setId(snowflakeIdWorker.nextId());
                um.setMessageContent(messageStr);
                um.setMessageType(1L);
                um.setReceiver(partnerId);
                um.setMessageTitle("锁匠加入申请");
                userMessageMapper.insert(um);

				return AppResultUtil.success("注册成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.trace(e.getMessage());
			return AppResultUtil.fail("注册失败");
		}
		return AppResultUtil.fail("注册失败");
	}

	/**
	 * 服务端使用code 获取微信openid
	 *
	 * @param wxcode
	 * @return
	 * @author zj
	 * @date 2019年1月29日
	 */
	private String getOpenId(String wxcode) {
		//
		String openid = "";

		String wxurl = "https://api.weixin.qq.com/sns/jscode2session?1=1";

		String grant_type = "authorization_code";

		String url = wxurl += "&appid=" + appid + "&secret=" + secret + "&js_code=" + wxcode + "&grant_type="
				+ grant_type;
		try {

			// openid string 用户唯一标识
			// session_key string 会话密钥
			// unionid string 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
			// errcode number 错误码
			// errmsg string 错误信息

			String response = HttpSendPost.sendHttpData(url, "");
			if (response != null && !response.equals("")) {
				JSONObject jobj = new JSONObject(response);
				if (jobj != null) {
					String errcode = jobj.optString("errcode");
					if (errcode.equals("")) {
						openid = jobj.optString("openid");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code

		return openid;
	}

	/**
	 * 用户登录
	 *
	 * @param request
	 * @return
	 * @author handsomeXiao
	 */
	@RequestMapping("/loginLocksmith/login")
	@ResponseBody
	@NoNeedAuthorization
	public AppResult mobileLogin(HttpServletRequest request) {

		try {
			/*
			 * phone true string 收取短信验证码的手机号 pwd false string 密码登录时用的，登录密码，md5加密过的 wxId
			 * false string 微信登录时用的，微信id type true string 登录类型：1:密码登录，2:微信登录 location true
			 * string 经纬度例如：“32.234232，118.237434”
			 */
			String phone = parseStringParam(request, "phone");
			String pwd = parseStringParam(request, "pwd");
			String wxId = parseStringParam(request, "wxId");
			String type = parseStringParam(request, "type");
			String location = parseStringParam(request, "location");
			String phoneCode = parseStringParam(request, "phoneCode");

			Userinfo query = new Userinfo();
			if (null == phone || phone.equals("")) {
				String openid = getOpenId(wxId);
				query.setWxOpenId(openid);
			} else {
				query.setPhone(phone);
			}
			List<Userinfo> users = userService.selectUserinfoList(query);
			if (users != null && users.size() == 1) {
				Userinfo user = users.get(0);

				if (user.getDataState() != 1) {
					// 已删除，
					// 重新启用
					// user.setDataState(0);

					if (user.getDataState() == 0)
						return AppResultUtil.fail("该用户已禁用,请联系管理员");
					if (user.getDataState() == 2)
						return AppResultUtil.fail("该用户不存在,请重新注册");
					else if (user.getDataState() == 3)
						return AppResultUtil.fail("该用户已被拉黑处理,请联系管理员");

				} else {

					if (!user.getAuditFlag().equals("1")) {
						return AppResultUtil.fail("该用户暂未审核通过，请耐心等待");

					}
				}

				boolean isOk = false;
				if (type.equals("1")) {
					if (pwd.equals("")) {
						isOk = false;
					} else if (user.getPassword().equals(pwd)
							|| user.getPassword().equals((PasswordUtil.encrypt(pwd, phone)))) {
						isOk = true;

						// 密码加密 ,兼容原密码
						if (user.getPassword().length() < 8) {
							user.setPassword(PasswordUtil.encrypt(pwd, phone));

							// 更新pass，加密存储
							userService.updateUserinfo(user);
						}

					} else {
						return AppResultUtil.fail("用户不存在或密码错误");
					}
				} else if (type.equals("2")) {
				
					if (wxId.equals("")) {
						isOk = false;
					} else if (null != query.getWxOpenId() && !"".equals(query.getWxOpenId())) {
						//直接微信登陆
						isOk = true;
					} else {
						//绑定微信
						// 获取微信openid
						String openid = getOpenId(wxId);
						if (openid == null || openid.equals("")) {
							return AppResultUtil.fail("微信账号信息获取失败!");
						} else {
							if (user.getWxOpenId() == null || user.getWxOpenId().equals("")) {

								// 判断微信号是否绑定
								Userinfo query2 = new Userinfo();
								query2.setWxOpenId(openid);
								List<Userinfo> users2 = userService.selectUserinfoList(query2);
								if (users2 != null && users2.size() > 0) {
									// 该微信已绑定账号
									isOk = false;
									return AppResultUtil.fail("此微信号已绑定不同的手机,请联系管理员解绑!");
								} else {
									
									//验证绑定验证码
									user.setCheckCode(phoneCode);
									AppResult appResult = appSmsService.checkCodeValid(new AppResult(), user);
									if (appResult.getErrCode().equals("0")) {
										return appResult;
									}
									
									
									// 未绑定，第一次绑定微信id
									user.setWxOpenId(openid);
									isOk = true;
								}
							} else {

													
								
								// 校验 微信id是否相同
								if (!user.getWxOpenId().equals(openid)) {
									isOk = false;
									// 改
									return AppResultUtil.fail("此手机号已绑定不同的微信账号,请联系管理员解绑!");

								} else {
									isOk = true;
								}
							}
						}
					}
				}

				if (isOk) {
					String usertoken = UUID.randomUUID().toString();

					user.setSessionKey(usertoken);

					// 更新toke wxid
					userService.updateByPrimaryKeySelective(user);
					return AppResultUtil.success(user);
				} else {
					return AppResultUtil.fail("登陆失败");
				}
			} else if (type.equals("2")) {
				return AppResultUtil.fail("用户不存在");
			} else {
				return AppResultUtil.fail("用户不存在或密码错误");
			}

		} catch (Exception e) {
			e.printStackTrace();

			return AppResultUtil.fail("用户不存在或密码错误");
		}

	}
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/loginLocksmith/changeUserImg", method =
	 * RequestMethod.POST) public AppResult changeUserImg(MultipartFile[] imgFiles,
	 * HttpServletRequest request) {
	 * 
	 * String userId = request.getParameter("userId");
	 * commonModule.orderImgProcess(imgFiles, null); try { Userinfo eu =
	 * TokenUtil.getCurrentUser();
	 * 
	 * String saltOldPassword = PasswordUtil.encrypt(oldPassword, eu.getPhone());
	 * 
	 * if (!saltOldPassword.equals(eu.getPassword())) { return
	 * AppResultUtil.fail("原密码错误！"); }
	 * eu.setPassword(PasswordUtil.encrypt(newPassword, eu.getPhone()));
	 * userService.updateUserinfo(eu);
	 * 
	 * return AppResultUtil.success(); } catch (Exception e) { e.printStackTrace();
	 * return AppResultUtil.fail(); }
	 * 
	 * }
	 */

	@ResponseBody
	@RequestMapping(value = "/loginLocksmith/changePassword", method = RequestMethod.POST)
	public AppResult changePassword(HttpServletRequest request) {

		String oldPassword = parseStringParam(request, "oldPwd");
		String newPassword = parseStringParam(request, "newPwd");

		if (oldPassword == null || "".equals(oldPassword)) {
			return AppResultUtil.fail();
		} else if (newPassword == null || "".equals(newPassword)) {
			return AppResultUtil.fail();
		}
		try {
			Userinfo eu = TokenUtil.getCurrentUser();

			String saltOldPassword = PasswordUtil.encrypt(oldPassword, eu.getPhone());
			// String saltNewPassword = PasswordUtil.encrypt(eu.getPassword(),
			// eu.getPhone());

			if (!saltOldPassword.equals(eu.getPassword())) {
				return AppResultUtil.fail("原密码错误！");
			}
			eu.setPassword(PasswordUtil.encrypt(newPassword, eu.getPhone()));
			userService.updateUserinfo(eu);

			return AppResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AppResultUtil.fail();
		}

	}

	/**
	 * 更新用户信息
	 * <p>
	 *
	 * @return
	 * @author zj
	 * @date 2018年6月22日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/editUserInfo")
	public AppResult editInfo(HttpServletRequest request, HttpServletResponse response) {
		try {

			// address false string 用户地址
			// sex false string 用户性别
			// identityCardId false string 用户身份证号
			// userImage 用户头像md5

			Userinfo user = TokenUtil.getCurrentUser();

			String userImage = parseStringParam(request, "userImage");
			String userIdImage = parseStringParam(request, "userIdCardImage");
			//
			String name = parseStringParam(request, "name");
			String identityCardId = parseStringParam(request, "idCard");
			if (identityCardId != null && identityCardId != "") {
				user.setIdCard(identityCardId);
			}
			if (name != null && name != "") {
				user.setName(name);
			}
			if (userImage != null && userImage != "") {
				user.setAvatarUrl("upload/file/" + userImage);
			}
			if (userIdImage != null && userIdImage != "") {
				user.setImgIdCardUrl("upload/file/" + userIdImage);
			}

			userService.updateUserinfo(user);

			return AppResultUtil.success("修改完成!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail();
		}
	}

	/**
	 * 获取用户信息
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zj
	 * @date 2019年1月29日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/userInfo")
	public AppResult userInfo(HttpServletRequest request, HttpServletResponse response) {
		try {

			Userinfo user = TokenUtil.getCurrentUser();
			UserinfoAdd userinfoAdd = userinfoAddService.selectById(user.getId());
			if (userinfoAdd != null) {
				user.setBankId(userinfoAdd.getBankId());
				user.setBankUserName(userinfoAdd.getBankUserName());
				user.setBankUserIDCard(userinfoAdd.getBankUserIdCard());
				user.setBankCardId(userinfoAdd.getBankCardId());
			}
			if (null != user.getCompanyId()) {
				Userinfo partnerInfo = new Userinfo();
				partnerInfo = userService.selectUserinfoById(user.getCompanyId());
				user.setPartnerName(partnerInfo.getName());
			}
			Object objMoney = orderinfoMapper.selectTotalMoneyByWithdraw(user.getId(), null, null, null, null,null);
			Double money = objMoney==null?0.00:Double.parseDouble(objMoney.toString());
			user.setFreezeOrderMoney(money);
			return AppResultUtil.success(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail();
		}
	}

	/**
	 * 绑定银行卡
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zj
	 * @date 2019年1月29日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/bindBankCard")
	public AppResult bindBankCard(HttpServletRequest request, HttpServletResponse response) {
		try {

			String bankId = parseStringParam(request, "bankId");
			String bankCardId = parseStringParam(request, "bankCardId");
			String bankUserName = parseStringParam(request, "bankUserName");
			String bankUserIDCard = parseStringParam(request, "bankUserIDCard");
			Userinfo user = TokenUtil.getCurrentUser();

			UserinfoAdd userinfoAdd = new UserinfoAdd();
			UserinfoAdd userAdd = userinfoAddService.selectById(user.getId());
			userinfoAdd.setId(user.getId());
			userinfoAdd.setBankCardId(bankCardId);
			userinfoAdd.setBankId(bankId);
			userinfoAdd.setBankUserName(bankUserName);
			userinfoAdd.setBankUserIdCard(bankUserIDCard);
			if (userAdd != null) {
				userinfoAddService.updatebindBankCard(userinfoAdd);
				return AppResultUtil.success("更改成功!");
			} else {
				userinfoAddService.insertbindBankCard(userinfoAdd);
				return AppResultUtil.success("绑定成功!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail();
		}
	}

	/*  *//**
			 * 上传用户头像图片 {@link com.ztgm.mall.test#testuphead()}<br/>
			 *
			 * @param map
			 * @param req
			 * @param multiReq
			 * @param res
			 * @return
			 * @author zj
			 * @date 2018年6月22日
			 */
	/*
	 * @RequestMapping(value = "/updHeaderPic", method = RequestMethod.POST)
	 * public @ResponseBody AppResult updateHeaderPic(ModelMap map,
	 * HttpServletRequest req, MultipartHttpServletRequest multiReq,
	 * HttpServletResponse res) { try {
	 * 
	 * Date bdate = new Date();
	 * 
	 * 
	 * String rst = uploadController.UploadFileXhr(multiReq.getFile("picFile"), req,
	 * res, null);
	 * 
	 * JSONObject jobj = new JSONObject(rst); String ResponseCode =
	 * jobj.optString("ResponseCode"); if (ResponseCode.equals("200")) { String
	 * fileid = jobj.optString("fileid");
	 * 
	 * Userinfo u = TokenUtil.getCurrentUser();
	 * 
	 * // u.setHeadImg(fileid); // userService.updateByPrimaryKeySelective(u); // //
	 * Map<String, String> data = new HashMap<>(); // // // u =
	 * userService.selectByPrimaryKey(u.getId()); // // // String filehttppath =
	 * FILE_SVR_HTTP_OUTER_PATH + u.getFileinfo().getHttp_relative_path();
	 * 
	 * 
	 * // data.put("head", filehttppath);
	 * 
	 * 
	 * Date eDate = new Date();
	 * 
	 * double second = (eDate.getTime() - bdate.getTime()) / 1000.0;
	 * logger.info("done! use " + second + " s");
	 * 
	 * // 返回获取路径 return AppResultUtil.success(""); } else { return
	 * AppResultUtil.fail("upload failed!"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return AppResultUtil.fail(); }
	 * 
	 * 
	 * }
	 */
	/*
	*//**
		 * 获取用户头像 {@link com.ztgm.mall.test#testheaderpic()}<br/>
		 *
		 * @param req
		 * @param res
		 * @return
		 * @author zj
		 * @date 2018年6月22日
		 *//*
			 * @RequestMapping(value = "/headerpic") public @ResponseBody AppResult
			 * holdUserHeaderPic(HttpServletRequest req, HttpServletResponse res) {
			 * 
			 * String token = req.getParameter("Token"); // Userinfo u =
			 * userService.getUserByToken(token);
			 * 
			 * // String filehttppath = FILE_SVR_HTTP_OUTER_PATH +
			 * u.getFileinfo().getHttp_relative_path();
			 * 
			 * return AppResultUtil.success("");
			 * 
			 * }
			 * 
			 */

	/**
	 * 根据查询条件搜索合伙人列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyong
	 * @date 2019年5月15日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/partnerLockerList")
	@NoNeedAuthorization
	public AppResult partnerLockerList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String searchValue = parseStringParam(request, "searchValue");
			PageCondition pageCondition = new PageCondition();
			pageCondition.setPageSize(10 + "");
			pageCondition.setOffset(1 + "");
			Page page = PageUtil.getPage(pageCondition);
			List<Userinfo> userinfoList = userService.selectPartnerLockerLikeNameAndPhone(searchValue);
			return AppResultUtil.success(userinfoList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail("查询失败");
		}
	}

	/**
	 * 获取合伙人下锁匠列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyong
	 * @date 2019年5月16日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/partnerManagerLockerList")
	@NoNeedAuthorization
	public AppResult partnerManagerLockerList(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long userId = parseLongParam(request, "userId");
			String parterAuditFlag = parseStringParam(request, "parterAuditFlag");
			Userinfo userinfo = new Userinfo();
			userinfo.setCompanyId(userId);
			userinfo.setParterAuditFlag2(parterAuditFlag);
			
			List<Userinfo> userinfoList = userService.selectUserinfoList(userinfo);

			if(parterAuditFlag.equals("0")) //如果是审核接口，显示额外数据， 分配界面不显示.
			{
			// 合并待自己审核的锁匠 zj 190617 从锁匠类型变更表取
			UserinfoAudit uA = new UserinfoAudit();
			uA.setUserNewType(4);// 合伙人下锁匠
			uA.setCompanyId(userId);
			uA.setAuditStates(0);// 待审核
			List<UserinfoAudit> uas = userinfoAuditService.selectUserinfoAuditList(uA);
			for (UserinfoAudit userinfoAudit : uas) {
				Userinfo u = userService.selectUserinfoById(userinfoAudit.getUserInfoId());
				u.setParterAuditFlag("0");// 待审核标识
				userinfoList.add(u);
			}
			}

			return AppResultUtil.success(userinfoList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail("查询失败");
		}
	}

	/**
	 * 审核合伙人下锁匠
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyong
	 * @date 2019年5月16日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/partnerManagerLockerAudit")
	@NoNeedAuthorization
	public AppResult partnerManagerLockerAudit(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long userId = parseLongParam(request, "userId");
			Long lockerId = parseLongParam(request, "lockerId");
			String auditFailReason = parseStringParam(request, "auditFailReason");
			int type = parseIntegerParam(request, "type");
			Userinfo userinfo = userService.selectUserinfoById(lockerId);
			/*
			 * // 1同意 0拒绝 if (type == 1) { userinfo.setAuditFlag(1 + "");
			 * userinfo.setParterAuditFlag(1 + ""); } else { userinfo.setParterAuditFlag(2 +
			 * ""); userService.updateLockerParterNull(lockerId); }
			 */

			Userinfo curuser = TokenUtil.getCurrentUser();

			UserinfoAudit ua = new UserinfoAudit();// 审核信息
			ua.setAuditStates(0);// 待审核
			ua.setCompanyId(userId);
			ua.setUserInfoId(lockerId);

			List<UserinfoAudit> uas = userinfoAuditService.selectUserinfoAuditList(ua);
			if (uas != null && uas.size() > 0) {

				// 锁匠类型变更过来的，类型变更审核
				if (type == 0)
					type = 2;

				UserinfoAudit uadata = uas.get(0);
				uadata.setAuditStates(type);
				uadata.setAuditor(userId.toString());
				if (type != 1)
					uadata.setAuditFailReason("合伙人未通过:" + auditFailReason);

				userinfoAuditService.userTypeChangeDone(uadata);
			} else {
				// 直接注册，或者新建就是合伙人下锁匠的,普通审核
				if (type == 1) {
					userinfo.setAuditFlag(1 + "");
					userinfo.setParterAuditFlag(1 + "");
				} else {
					if (userinfo.getAuditFlag().equals("0"))// 新注册的直接不通过，其他状态的不做修改
						userinfo.setAuditFlag("2");
					userinfo.setParterAuditFlag(2 + "");
					// userService.updateLockerParterNull(lockerId);
				}
				userService.updateUserinfo(userinfo);
			}

			//
			return AppResultUtil.success("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail("操作失败");
		}
	}

	/**
	 * 合伙人删除旗下锁匠
	 *
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyong
	 * @date 2019年5月16日
	 */
	@ResponseBody
	@RequestMapping(value = "/userLocksmith/partnerManagerLockerDelete")
	@NoNeedAuthorization
	public AppResult partnerManagerLockerDelete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long lockerId = parseLongParam(request, "lockerId");

			Orderinfo orderinfo = new Orderinfo();
			orderinfo.setLockerId(lockerId);
			List<Orderinfo> orderinfoList = orderinfoService.selectDoingOrderInfoList(orderinfo);
			if (null != orderinfoList && orderinfoList.size() > 0) {
				return AppResultUtil.fail("存在待作业的单子，不允许删除该锁匠");
			}
            Userinfo userinfo = userService.selectUserinfoById(lockerId);
            userinfo.setCompanyId(null);
            userinfo.setUserType(6+"");
            userinfo.setDataState(2);
            userService.updateUserinfo(userinfo);
			return AppResultUtil.success("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail("删除失败");
		}
	}

	/**
	 * 发送短信验证码
	 *
	 * @return
	 * @author zj
	 * @date 2019年5月20日
	 */
	@RequestMapping("/loginLocksmith/getValidateCode")
	@ResponseBody
	@NoNeedAuthorization
	public AppResult getValidateCode(HttpServletRequest request) {
		try {
			String phone = parseStringParam(request, "phone");
			int state = parseIntegerParam(request, "state");
			if (state == 1) {
				Userinfo userinfo = new Userinfo();
				userinfo.setPhone(phone);
				List<Userinfo> userinfoList = userService.selectUserinfoByPhone(userinfo);
				if (null == userinfoList || userinfoList.size() == 0) {
					return AppResultUtil.fail("该手机号用户不存在");
				}
			}

			AppSms appSms = new AppSms();
			appSms.setMobile(phone);
			AppResult resultDto = appSmsService.sendValidateCode(appSms);
			return resultDto;
		} catch (Exception e) {
			logger.error("发送短信验证码失败", e);
			return AppResultUtil.fail("短信验证码发送失败，请稍后重试");
			// resultDto.setMessage("短信验证码发送失败，请稍后重试");
		}
	}

	/**
	 * 自由锁匠加入合伙人
	 *
	 * @param baseRequestDto
	 * @return
	 * @author zhangyong
	 */
	@RequestMapping("/loginLocksmith/addToPartner")
	@ResponseBody
	@NoNeedAuthorization
	public AppResult addToPartner(BaseRequestDto baseRequestDto, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObject = new JSONObject(baseRequestDto.getData());
			Long partnerId = jsonObject.getLong("partnerId");
			if (partnerId == null || partnerId == 0) {
				return AppResultUtil.fail("请选择合伙人");
			}
			Userinfo userinfo = TokenUtil.getCurrentUser();
			/*
			 * userinfo.setCompanyId(partnerId); userinfo.setParterAuditFlag(0 + "");
			 * UserinfoJobArea userinfoJobArea = new UserinfoJobArea();
			 * userinfoJobArea.setLockId(partnerId); List<UserinfoJobArea>
			 * userinfoJobAreaList = userinfoJobAreaMapper.selectList(userinfoJobArea);
			 * String districtIds = ""; for (UserinfoJobArea item : userinfoJobAreaList) {
			 * districtIds = districtIds + item.getDistrictId() + ","; }
			 * 
			 * // 默认塞入合伙人作业区域作为锁匠所在区域 Map mpdata2 =
			 * areaService.selectAll(userinfoJobAreaList.get(0).getDistrictId()); if
			 * (mpdata2 != null) {
			 * userinfo.setAreaCode(String.valueOf(mpdata2.get("areacode"))); }
			 * 
			 * userinfo.setDistrictids(districtIds); userService.updateUserinfo(userinfo);
			 */

			UserinfoAudit ua = new UserinfoAudit();

			ua.setUserInfoId(userinfo.getId());
			ua.setUserOldType(Integer.parseInt(userinfo.getUserType()));
			ua.setUserNewType(4);// 合伙人下锁匠
			ua.setCompanyId(partnerId);
			ua.setProposReason("锁匠小程序提交变更请求");
			ua.setProposer(userinfo.getId().toString());

			Message msg = userService.userTypeChange(ua, 2);

			Userinfo LockerParter = userService.selectUserinfoById(partnerId);
			String msgsms = "锁匠申请";
			// 、推送短信提醒
			AliSendSMS.sendMessageNoraml(LockerParter.getPhone(), msgsms);

			if (msg.isBol())
				return AppResultUtil.success("申请成功");
			else
				return AppResultUtil.fail(msg.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			logger.trace(e.getMessage());
			return AppResultUtil.fail("注册失败");
		}
	}

}
