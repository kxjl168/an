package com.kxjl.tasktransferplat.controller.AppController;

import com.alibaba.fastjson.JSON;
import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.controller.Common.MongoImgSvrCtroller;

import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.aes.AesHelper;
import com.kxjl.base.util.sendpost.HttpSendPost;
import com.kxjl.tasktransferplat.controller.WebController.AreaController;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Bank;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.UserOpinion;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.service.AreaService;
import com.kxjl.tasktransferplat.service.OrderLockInfoService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.kxjl.tasktransferplat.service.UserOpinionService;
import com.kxjl.tasktransferplat.util.TokenUtil;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interface/commonModule")
@PropertySource("classpath:project.properties")
public class CommonModule extends AppBaseController {

	//@Autowired
	private MongoImgSvrCtroller mongoImgSvrCtroller;

	@Autowired
	private UserOpinionService userOpinionService;


	@Autowired
	private OrderinfoService orderinfoService;

	@Autowired
	private OrderLockInfoService orderLockInfoService;

	@Autowired
	private AreaService areaService;

	@Value("${weblock.url}")
	private String url;

	private static final Logger logger = LoggerFactory.getLogger(CommonModule.class);

	/**
	 * 小程序开始上传图片前调用，清空已有图片信息，并设置imei信息
	 * 
	 * @param mFile
	 * @return
	 * @author zj
	 * @date 2019年7月12日
	 */
	@ResponseBody
	@RequestMapping(value = "/resetDoneImg")
	public AppResult resetDoneImg(HttpServletRequest request) {
		try {

			String index = parseStringParam(request, "lockIndex");// 第几把锁
			String orderNo = parseStringParam(request, "orderNo");// 工单号

			String imei = "";// request.getParameter("imei"); //

			if (orderNo == null || orderNo.equals(""))
				return AppResultUtil.fail("参数错误");

			// 关联图片与工单
			return orderLockInfoService.cleanOrderLockPicInfoAndSetIMEI(orderNo, index, imei);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return AppResultUtil.fail("操作失败！");
		}
	}

	/**
	 * 工单完成时提交图片特殊处理，增加工单多锁支持
	 * 
	 * @param imgFiles
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年7月12日
	 */
	@RequestMapping("/uploadDoneImg")
	public String uploadDoneImg(MultipartFile[] imgFiles, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		BaseResponseDto responseDto = new BaseResponseDto();
		responseDto.setErrCode(1);
		String responsedata = "";
		try {
			String md5 =parseStringParam(request,"md5"); // 之前已经上传的图片

			String index = parseStringParam(request,"lockIndex");// 第几把锁
			String orderNo = parseStringParam(request,"orderNo");// 工单号

			String imgType = parseStringParam(request,"imgType"); // 1前，2中，3后
			String imgNo = parseStringParam(request,"imgNo"); // 1-6

			if (md5 != null && md5.equals("localfile")) {

				MultipartFile mfile = imgFiles[0];
				String value = mfile.getOriginalFilename();// .getName();//
				// item.getName();

				String extension = value.substring(value.lastIndexOf(".") + 1, value.length());

				// 非头像
				if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")
						|| extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("jpeg")) {

					// 图片最小尺寸判断 800x800
					ImageIcon ii = new ImageIcon(mfile.getBytes());
					Image image = ii.getImage();
					Image resizedImage = null;

					int iWidth = image.getWidth(null);
					int iHeight = image.getHeight(null);
					if (iWidth < 300 || iHeight < 600) {

						responseDto.setErrCode(0);
						responseDto.setData(imgNo);
						responseDto.setErrMsg("传分辨率为1920*1080以上的图!");

						list.add("-1");
						resultMap.put("imgNo", imgNo);
						responseDto.setData(resultMap);

						return JSON.toJSONString(responseDto);
					}
				}

				responsedata = mongoImgSvrCtroller.upload(imgFiles, null, null);
				org.json.JSONObject jsonRes = new org.json.JSONObject(responsedata);

				md5 = jsonRes.optString("md5");
			}

			// 关联图片与工单
			orderLockInfoService.updateOrderLockPicInfo(orderNo, index, md5, imgType);

			list.add(md5);
			resultMap.put("imgNo", imgNo);
			responseDto.setData(resultMap);
			return JSON.toJSONString(responseDto);
		} catch (Exception e) {
			responseDto.setErrCode(0);
			throw new RuntimeException("上传图片失败！");
		}
	}

	/**
	 * 工单图片处理
	 *
	 * @param imgFiles
	 * @return
	 */
	@RequestMapping("/uploadImg")
	public String orderImgProcess(MultipartFile[] imgFiles, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		BaseResponseDto responseDto = new BaseResponseDto();
		responseDto.setErrCode(1);
		String responsedata = "";
		try {

			MultipartFile mfile = imgFiles[0];
			String value = mfile.getOriginalFilename();// .getName();//
			// item.getName();

			String extension = value.substring(value.lastIndexOf(".") + 1, value.length());

			String type = request.getParameter("type");

			if (type == null || (type != null && !type.equals("1"))) {

				// 非头像
				if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")
						|| extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("jpeg")) {

					// 图片最小尺寸判断 800x800
					ImageIcon ii = new ImageIcon(mfile.getBytes());
					Image image = ii.getImage();
					Image resizedImage = null;

					int iWidth = image.getWidth(null);
					int iHeight = image.getHeight(null);
					if (iWidth < 300 || iHeight < 600) {

						responseDto.setErrCode(0);
						responseDto.setErrMsg("请上传分辨率为1920*1080以上的图片!");

						list.add("-1");
						resultMap.put("data", list);
						responseDto.setData(resultMap);

						return JSON.toJSONString(responseDto);
					}
				}
			}

			responsedata = mongoImgSvrCtroller.upload(imgFiles, null, null);
			org.json.JSONObject jsonRes = new org.json.JSONObject(responsedata);

			list.add(jsonRes.optString("md5"));
			resultMap.put("data", list);
			responseDto.setData(resultMap);
			return JSON.toJSONString(responseDto);
		} catch (Exception e) {
			responseDto.setErrCode(0);
			throw new RuntimeException("上传图片失败！");
		}
	}



	@ResponseBody
	@RequestMapping(value = "/community")
	public AppResult communityList(HttpServletRequest request) {
		try {
			String curl = url + "/interface/provide/communityList";

			String pageSize = parseStringParam(request, "pageSize");
			String pageNum = parseStringParam(request, "pageNum");
			String name = parseStringParam(request, "name");

			JSONObject jobj = new JSONObject();
			jobj.put("pageSize", pageSize);
			jobj.put("pageNum", pageNum);
			jobj.put("name", name);

			String commetdata = jobj.toString();
			String aesSendData = AesHelper.encrypt(commetdata, AesHelper.aesPass);

			String jStr = HttpSendPost.sendHttpData(curl, aesSendData);
			String aesReceiveData = AesHelper.decrypt(jStr, AesHelper.aesPass);
			JSONObject data = JSONObject.fromObject(aesReceiveData);
			return AppResultUtil.success(data);
		} catch (Exception e) {
			return AppResultUtil.fail("错误原因" + e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/building")
	public AppResult buildingList(HttpServletRequest request) {
		try {
			String curl = url + "/interface/provide/buildingList";
			String communityId = parseStringParam(request, "communityId");
			String pageSize = parseStringParam(request, "pageSize");
			String pageNum = parseStringParam(request, "pageNum");
			String name = parseStringParam(request, "name");

			JSONObject jobj = new JSONObject();
			jobj.put("communityId", communityId);
			jobj.put("pageSize", pageSize);
			jobj.put("pageNum", pageNum);
			jobj.put("name", name);

			String commetdata = jobj.toString();
			String aesSendData = AesHelper.encrypt(commetdata, AesHelper.aesPass);
			String jStr = HttpSendPost.sendHttpData(curl, aesSendData);
			String aesReceiveData = AesHelper.decrypt(jStr, AesHelper.aesPass);
			JSONObject data = JSONObject.fromObject(aesReceiveData);
			return AppResultUtil.success(data);
		} catch (Exception e) {
			return AppResultUtil.fail("错误原因" + e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/room")
	public AppResult roomList(HttpServletRequest request) {
		try {
			String curl = url + "/interface/provide/roomList";
			String buildingId = parseStringParam(request, "buildingId");
			String pageSize = parseStringParam(request, "pageSize");
			String pageNum = parseStringParam(request, "pageNum");
			String name = parseStringParam(request, "name");

			JSONObject jobj = new JSONObject();
			jobj.put("buildingId", buildingId);
			jobj.put("pageSize", pageSize);
			jobj.put("pageNum", pageNum);
			jobj.put("name", name);

			String commetdata = jobj.toString();
			String aesSendData = AesHelper.encrypt(commetdata, AesHelper.aesPass);
			String jStr = HttpSendPost.sendHttpData(curl, aesSendData);
			String aesReceiveData = AesHelper.decrypt(jStr, AesHelper.aesPass);
			JSONObject data = JSONObject.fromObject(aesReceiveData);
			return AppResultUtil.success(data);
		} catch (Exception e) {
			return AppResultUtil.fail("错误原因" + e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/setPass")
	public AppResult setPass(HttpServletRequest request) {
		try {
			String curl = url + "/interface/provide/setPass";
			String roomId = parseStringParam(request, "roomId");
			// 0无需上传房号 1需要上传房号
			Integer isRoomNessary = parseIntegerParam(request, "isRoomNessary");
			String imei = parseStringParam(request, "imei");

			// 锁序列号
			String lockIndex = parseStringParam(request, "lockIndex");

			// 工单号
			String orderId = parseStringParam(request, "orderId");
			Orderinfo oInfo = orderinfoService.selectOrderinfoById(orderId);
			String name = oInfo.getCustomerName();
			String phone = oInfo.getCustomerPhone();
			String address = oInfo.getCustAddressDetail();
			if (roomId == null) {
				roomId = "";
			}

			// 查询客户信息
			JSONObject jobj = new JSONObject();
			jobj.put("roomId", roomId);
			jobj.put("imei", imei);
			jobj.put("name", name);
			jobj.put("phone", phone);
			jobj.put("isRoomNessary", isRoomNessary);
			jobj.put("address", address);

			String commetdata = jobj.toString();
			String aesSendData = AesHelper.encrypt(commetdata, AesHelper.aesPass);
			String jStr = HttpSendPost.sendHttpData(curl, aesSendData);
			String aesReceiveData = AesHelper.decrypt(jStr, AesHelper.aesPass);
			JSONObject data = JSONObject.fromObject(aesReceiveData);
			if (data.getBoolean("bol")) {
				Orderinfo orderinfo = new Orderinfo();
				orderinfo.setId(orderId);
				orderinfo.setIsUploadImei(1);
				orderinfo.setImeiNum(imei);
				orderinfoService.updateOrder(orderinfo);

				if (lockIndex != null && !lockIndex.equals(""))
					// 关联图片与工单
					orderLockInfoService.updateOrderLockImeiInfo(oInfo.getOrderNo(), lockIndex, imei);

				return AppResultUtil.success(data);
			} else {
				return AppResultUtil.fail(data.getString("message"));
			}
		} catch (Exception e) {
			return AppResultUtil.fail("错误原因" + e);
		}
	}

	/**
	 * 个人信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/baseData")
	public AppResult baseData(HttpServletRequest request) {

		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Bank> bank = new ArrayList<>();
			bank.add(new Bank("1002n", "工商银行"));
			bank.add(new Bank("1005n", "农业银行"));
			bank.add(new Bank("1026n", "中国银行"));
			bank.add(new Bank("1003n", "建设银行"));
			bank.add(new Bank("1001n", "招商银行"));
			bank.add(new Bank("1066n", "邮储银行"));
			bank.add(new Bank("1020n", "交通银行"));
			bank.add(new Bank("1004n", "浦发银行"));
			bank.add(new Bank("1006n", "民生银行"));
			bank.add(new Bank("1009n", "兴业银行"));
			bank.add(new Bank("1010n", "平安银行"));
			bank.add(new Bank("1021n", "中信银行"));
			bank.add(new Bank("1025n", "华夏银行"));
			bank.add(new Bank("1027n", "广发银行"));
			bank.add(new Bank("1022n", "光大银行"));
			bank.add(new Bank("1032n", "北京银行"));
			bank.add(new Bank("1056n", "宁波银行"));
			resultMap.put("data", bank);
			return AppResultUtil.success(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return AppResultUtil.fail();
		}
	}

	/**
	 * 意见反馈提交
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/feedback")
	public AppResult insertOpinion(HttpServletRequest request) {

		try {
			Userinfo eu = TokenUtil.getCurrentUser();
			String content = parseStringParam(request, "content");
			UserOpinion opinion = new UserOpinion();
			opinion.setContent(content);
			opinion.setUserId(eu.getId());
			userOpinionService.submitOpinion(opinion);
			return AppResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AppResultUtil.fail();
		}
	}
}
