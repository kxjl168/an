/**
 * @(#)OrderCreateAndAuditController.java 2019/4/16 14:18
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.excerption.BusinessException;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kxjl.tasktransferplat.util.DateUtil.getCellDateTime;
import static com.kxjl.tasktransferplat.util.DateUtil.timestampOrderNo;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author 单肙
 * @version 1.0.0 2019/4/16 14:18
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/orderInfo")
@SuppressWarnings("all")
public class OrderCreateAndAuditController {

	private static final Logger logger = LoggerFactory.getLogger(OrderCreateAndAuditController.class);

	@Autowired
	private OrderinfoService orderinfoService;

	@Autowired
	private OrderInfoOperateService orderInfoOperateService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private PriceLockCompanyService priceLockCompanyService;

	@Autowired
	private PriceLocksmithOtherService priceLocksmithOtherService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ManagerMessageService managerMessageService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private OrderinfoAuditService orderinfoAuditService;

	@Autowired
	private OrderinfoOperateLogService orderinfoOperateLogService;

	@Autowired
	private OrderInfoOperateProxy orderInfoOperateProxy;

	@Autowired
	private LockCompanyService lockCompanyService;

	@Autowired
	private LockProductInfoService lockProductInfoService;

	private static final String XLS = "xls";

	private static final String XLSX = "xlsx";

	@RequestMapping("/index")
	public String manager(Model model, HttpServletRequest request) {

		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			ManagerMessage managerMessage = new ManagerMessage();
			managerMessage.setUserId(manager.getId());
			managerMessage.setType(6 + "");
			managerMessageService.updateMessageReadByUserAndType(managerMessage);

			List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
			MyWebSocket.sendMessage(JSON.toJSONString(list), managerMessage.getUserId());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("websocket发送消息报错");
		}

		return "/backend/page/orderInfo_create/index.ftl";
	}

	@RequestMapping("/importDataByExcel")
	@ResponseBody
	public BaseResponseDto importDataByExcel(HttpServletRequest request, MultipartFile excelFile) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("导入成功！");

		String managerId = ((Manager) request.getSession().getAttribute("user")).getId();
		String companyId = request.getParameter("sellerId");
		List<List> packageList = new ArrayList<>();
		try {
			packageList = transformFileToData(managerId, companyId, excelFile);

			if (packageList != null && packageList.get(2) != null && packageList.get(2).size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < packageList.get(2).size(); i++) {
					String msg = String.valueOf(packageList.get(2).get(i));
					sb.append(msg + "<br>");
				}
				baseResponseDto.setErrMsg("导入数据失败！<br>" + sb.toString());
				baseResponseDto.setErrCode(0);
				return baseResponseDto;
			}

			orderinfoService.importOrderInfo(packageList);
		} catch (Exception e) {
			logger.error("导入数据失败！", e);
			baseResponseDto.setErrCode(0);

			baseResponseDto.setErrMsg("导入数据失败！"+e.getMessage());

		}
		return baseResponseDto;
	}

	/**
	 * 根据xls文件或者xlsx文件生成订单对象集合
	 *
	 * @param managerId
	 * @param companyId
	 * @param excelFile
	 * @return
	 * @throws IOException
	 */
	private List<List> transformFileToData(String managerId, String companyId, MultipartFile excelFile)
			throws Exception {

		List<List> resultList = new ArrayList<>();
		// 返回的工单对象集合
		List<Orderinfo> orderResultList = new ArrayList<>();
		// 操作日志集合
		List<OrderinfoOperateLog> operateLogList = new ArrayList<>();

		List<String> msg = new ArrayList<>();

		resultList.add(orderResultList);
		resultList.add(operateLogList);
		resultList.add(msg);

		InputStream inputStream = excelFile.getInputStream();
		String fileName = excelFile.getOriginalFilename();

		// 根据文件后缀生成对象
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		Workbook workbook = null;
		if (suffix.equals(XLS)) {
			workbook = new HSSFWorkbook(inputStream);
		}
		if (suffix.equals(XLSX)) {
			workbook = new XSSFWorkbook(inputStream);
		}
		Sheet sheet = workbook.getSheetAt(0);
		// 迭代sheet中的所有行
		for (Row row : sheet) {
			// 第一行为表头，不需要
			if (row.getRowNum() == 0) {
				continue;
			}
			if (row != null) {
				Orderinfo orderinfo;
				OrderinfoOperateLog operateLog = new OrderinfoOperateLog();
				try {
					try {
						Thread.sleep(300);
						//System.out.println(timestampOrderNo());
						//orderNo 处理，重复
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					orderinfo = packageOrderInfo(managerId, companyId, row);
					int type, subType;
					if (isBlank(companyId)) {
						type = 1;
						subType = 0;
					} else {
						type = 0;
						subType = 1;
					}
					operateLog = orderinfoOperateLogService.packageOperateLog(orderinfo, type, subType);
				} catch (Exception e) {
					logger.error(row.getRowNum() + "行出现问题", e);
					msg.add(row.getRowNum() + "行出现问题:" + e.getMessage());
					continue;
				}
				orderResultList.add(orderinfo);
				operateLogList.add(operateLog);
			}
		}

		return resultList;
	}

	/**
	 * 根据行包装orderinfo对象
	 *
	 * @param companyId
	 * @param row
	 * @param priceLockCompany
	 * @return
	 */
	private Orderinfo packageOrderInfo(String managerId, String companyId, Row row) {

		Integer cellIndex = 0;
		
		String oid="";
		try {
			Thread.currentThread().sleep(100);
			oid=timestampOrderNo();
			//orderNo 处理，重复
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Orderinfo orderinfo = new Orderinfo();
		orderinfo.setId(UUIDUtil.getUUID());
		orderinfo.setOrderNo(oid);
		orderinfo.setCreateUserId(managerId);
		orderinfo.setPaymentType(0);
		// 批量导入的需要进行审核
		orderinfo.setOrderState(3);
		orderinfo.setSellerId(Long.valueOf(companyId));

		// //锁企名
		// orderinfo.setEnterpriseName(getCellValue(row.getCell(cellIndex++)));
		// LockCompany lockCompany=
		// lockCompanyService.selectLockCompanyByName(orderinfo.getEnterpriseName());
		// orderinfo.setSellerId(lockCompany.getId());

		// 产品型号
		orderinfo.setProType(getCellValue(row.getCell(cellIndex++)));
		LockProductInfo lockProductInfo = lockProductInfoService.selectLockProductByprotype(orderinfo);
		if (lockProductInfo == null) {
			throw new RuntimeException("选择的锁企未找到该产品型号");
		}
		orderinfo.setProductId(lockProductInfo.getId());

		// 工单类型
		String orderFeeTypeString = getCellValue(row.getCell(cellIndex++));
		String orderFeeType = "";
		if ("标准工单".equals(orderFeeTypeString)) {
			orderFeeType = "0";
		} else if ("自费工单".equals(orderFeeTypeString)) {
			orderFeeType = "1";
		} else {

			throw new RuntimeException("工单费用类型错误,应该为标准工单/自费工单");

		}

		orderinfo.setOrderFeeType(orderFeeType);

		// 工单备注
		String createRemark = getCellValue(row.getCell(cellIndex++));
		if (createRemark != null) {
			orderinfo.setCreateRemark(createRemark);
		}

		/*
		 * //工单安装注意事项 String installRemark=getCellValue(row.getCell(cellIndex++)); if
		 * (installRemark!=null) { orderinfo.setInstallRemark(installRemark); }
		 * 
		 * //工单客户门信息 String doorInfo=getCellValue(row.getCell(cellIndex++)); if
		 * (doorInfo!=null) { orderinfo.setDoorInfo(doorInfo); }
		 */

		// 服务类型
		String serverTypeString = getCellValue(row.getCell(cellIndex++));
		String[] serverTypeSplit = new String[0];
		String serverType = "";

		if(serverTypeString.contains("，")) {
			serverTypeString = serverTypeString.replaceAll("，",",");
		}
		if (serverTypeString.contains(",")) {
			serverTypeSplit = serverTypeString.split(",");
		}else if(serverTypeString.contains("、")) {
			serverTypeSplit = serverTypeString.split("、");
		}else{
			if (serverTypeString.contains("安装")&&!(serverTypeString.contains("工程安装"))&&!(serverTypeString.contains("猫眼安装"))) {
				serverType += "0,";
			}
			if (serverTypeString.contains("维修")&&!(serverTypeString.contains("工程维修"))) {
				serverType += "1,";
			}
			if (serverTypeString.contains("开锁")) {
				serverType += "2,";
			}
			if (serverTypeString.contains("特殊门改造")) {
				serverType += "3,";
			}
			if (serverTypeString.contains("测量")) {
				serverType += "4,";
			}
			if (serverTypeString.contains("猫眼安装")) {
				serverType += "5,";
			}
			if (serverTypeString.contains("换锁")) {
				serverType += "6,";
			}
			if (serverTypeString.contains("工程安装")) {
				serverType += "7,";
			}
			if (serverTypeString.contains("工程维修")) {
				serverType += "8,";
			}
			if (serverTypeString.contains("其他")) {
				serverType += "19,";
			}
		}
		for (int i = 0; i < serverTypeSplit.length; i++) {
			if (serverTypeSplit[i].equals("安装")&&!serverType.contains("0")){
				serverType += "0,";
			}
			if (serverTypeSplit[i].equals("维修")&&!serverType.contains("1")){
				serverType += "1,";
			}
			if (serverTypeSplit[i].equals("开锁")&&!serverType.contains("2")){
				serverType += "2,";
			}
			if (serverTypeSplit[i].equals("特殊门改造")&&!serverType.contains("3")){
				serverType += "3,";
			}
			if (serverTypeSplit[i].equals("测量")&&!serverType.contains("4")){
				serverType += "4,";
			}
			if (serverTypeSplit[i].equals("猫眼安装")&&!serverType.contains("5")){
				serverType += "5,";
			}
			if (serverTypeSplit[i].equals("换锁")&&!serverType.contains("6")){
				serverType += "6,";
			}
			if (serverTypeSplit[i].equals("工程安装")&&!serverType.contains("7")){
				serverType += "7,";
			}
			if (serverTypeSplit[i].equals("工程维修")&&!serverType.contains("8")){
				serverType += "8,";
			}
			if (serverTypeSplit[i].equals("其他")&&!serverType.contains("9")){
				serverType += "9,";
			}
		}

		if(serverType.contains("9")){
			serverType=serverType.replace("9","19");
		}
		if (serverType.equals("")) {
			throw new RuntimeException("工单服务类型错误,请参考模板说明输入");
		}

		orderinfo.setServerType(serverType);

		// 是否加急
		String urgentString = getCellValue(row.getCell(cellIndex++));
		String urgent = "0";
		if ("否".equals(urgentString)) {
			urgent = "0";
		} else if ("是".equals(urgentString)) {
			urgent = "1";
		}
		orderinfo.setUrgent(urgent);

		// 省市区转code并赋值
		String province = getCellValue(row.getCell(cellIndex++));
		String city = getCellValue(row.getCell(cellIndex++));
		String district = getCellValue(row.getCell(cellIndex++));
		if(province!=null&&province!=""&&city!=null&&city!=""&&district!=null&&district!="") {
			// 通过省市区名称获取省市区编码
			AreaDistrict areaDistrict = areaService.getAreaCodeByNameInfo(province, city, district);
			if (areaDistrict == null) {
				throw new RuntimeException("地市区域填写错误,请参考模板说明或者填入系统中可选的地市区域");
			}
			orderinfo.setAreaCode(areaDistrict.getDistrictCode());
			orderinfo.setDistrictId(areaDistrict.getId());
		}else {
			throw new RuntimeException("客户省市区不能为空!");
		}
		// 地址详情
		String addressDetaliString=getCellValue(row.getCell(cellIndex++));
		if (addressDetaliString!=null&&addressDetaliString!=""){
			orderinfo.setAddressDetail(addressDetaliString);
		}else {
			throw new RuntimeException("详细地址不能为空!");
		}

		orderinfo.setLockNum(1);

		String atime = getCellValue(row.getCell(cellIndex++));

		try {
			if (atime != null && !atime.trim().equals("")) {
				com.kxjl.base.util.DateUtil.getDate(atime, "yyyy-MM-dd HH:mm:ss");
				orderinfo.setAppointmentTime(atime);
			}
		} catch (Exception e) {
			throw new RuntimeException("预约时间格式错误!");
		}

		orderinfo.setDataState(1);
		// 客户类型
		String isRoomNessaryString = getCellValue(row.getCell(cellIndex++));
		Integer isRoomNessary = 0;
		if ("个人客户".equals(isRoomNessaryString)) {
			isRoomNessary = 0;
		} else if ("企业客户".equals(isRoomNessaryString)) {
			isRoomNessary = 1;
		}
		orderinfo.setIsRoomNessary(isRoomNessary);

		// 客户信息
		String customerNameString=getCellValue(row.getCell(cellIndex++));
		if (customerNameString!=null&&customerNameString!="") {
			orderinfo.setCustomerName(customerNameString);
		}else {
			throw new RuntimeException("客户姓名不能为空!");
		}
		String customerPhoneString = getCellValue(row.getCell(cellIndex++));
		String regex = "^1(3|4|5|7|8|9)\\d{9}$";
		if (customerPhoneString!=null&&customerPhoneString!="") {
			if (customerPhoneString.length() != 11) {
				throw new RuntimeException("手机号应为11位数!");
			} else {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(customerPhoneString);
				boolean isMatch = m.matches();
				if (!isMatch) {
					throw new RuntimeException("手机号格式错误!");
				}
			}
		}else {
			throw new RuntimeException("客户电话不能为空!");
		}
		orderinfo.setCustomerPhone(customerPhoneString);
		// 客户电话2
		String customerPhoneString2 = getCellValue(row.getCell(cellIndex++));
		if (customerPhoneString2!=null&&customerPhoneString2!=""){
			if(customerPhoneString2.length() != 11){
				throw new RuntimeException("手机号2应为11位数!");
			}else{
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(customerPhoneString2);
				boolean isMatch = m.matches();
				if(!isMatch){
					throw new RuntimeException("手机号2格式错误!");
				}
			}
			orderinfo.setCustomerPhone2(customerPhoneString2);
		}
		
		
		//zj 190715 增加工单导入锁数量
		String lockNum = getCellValue(row.getCell(cellIndex++));
		if(lockNum==null||lockNum.equals(""))
			lockNum="1";
		else {
			try {
				int num= Integer.parseInt(lockNum);
				if(num>10)
					throw new RuntimeException("工单上的锁数量太大,目前仅支持一个工单最多10把锁!");
				if(num<1)
					throw new RuntimeException("工单上的锁数量错误,最少为1把锁!");
			} catch (Exception e) {
				throw new RuntimeException("锁数量格式错误!");
			}
		}
		
		orderinfo.setLockNum(Integer.parseInt(lockNum));
		
		
		
		
		//费用计算在后续service中

	/*	// 计算费用，由于导入工单没有加急一说，所以base和total的价格是一样的
		List<OrderinfoAttachMoneyDetail> moneydetails = orderInfoOperateService.calculatePriceNew(orderinfo);
		BigDecimal lockerPrice = new BigDecimal(0);
		BigDecimal sellerPrice = new BigDecimal(0);
		for (OrderinfoAttachMoneyDetail orderinfoAttachMoneyDetail : moneydetails) {
			lockerPrice = lockerPrice.add(orderinfoAttachMoneyDetail.getChangeValueLocker());
			sellerPrice = sellerPrice.add(orderinfoAttachMoneyDetail.getChangeValueSeller());
		}
		orderinfo.setLockerBasePrice(lockerPrice);
		orderinfo.setLockerTotalPrice(lockerPrice);
		orderinfo.setSellerBasePrice(sellerPrice);
		orderinfo.setSellerTotalPrice(sellerPrice);
*/
		
		
		
		try {
			List<Map<String, String>> managerIdList = managerMessageService.selectManagerMessageInsertUserList(6 + "");
			for (Map<String, String> map : managerIdList) {
				String id = map.get("managerId");
				ManagerMessage managerMessage = new ManagerMessage();
				managerMessage.setUserId(id);
				managerMessage.setType(6 + "");
				managerMessage.setTitle("待确认");
				managerMessage.setContent("锁企创建工单，等待源匠确认");
				managerMessage.setIsRead(0);
				managerMessage.setId(UUIDUtil.getUUID());
				managerMessageService.insert(managerMessage);
				List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
				MyWebSocket.sendMessage(JSON.toJSONString(list), id);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("websocket发送消息报错");
		}

		return orderinfo;
	}

	/**
	 * 根据cell类型获得对应类型的值
	 *
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		if (null == cell) {
			return null;
		}
		// 获取cellType的类型
		CellType cellType = cell.getCellTypeEnum();
		String cellValueString;
		if (cellType == CellType.BOOLEAN) {
			cellValueString = String.valueOf(cell.getBooleanCellValue());
		} else if (cellType == CellType.NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				cellValueString = getCellDateTime(cell);
			} else {
				double cellValue = cell.getNumericCellValue();
				DecimalFormat format = new DecimalFormat("#.##");
				cellValueString = format.format(cellValue);
			}
		} else if (cellType == CellType.FORMULA) {
			cellValueString = cell.getCellFormula();
		} else {
			cellValueString = cell.getStringCellValue();
		}
		return cellValueString;
	}

	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe = "查询创建待审核工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public JSONObject torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
		List<Orderinfo> torderinfos = new ArrayList<>();

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		item.setSellerId(manager.getCompanyId());
		JSONObject result = null;
		try {
			Page page = PageUtil.getPage(pageCondition);
			torderinfos = orderinfoService.selectCreateOrderInfoList(item);
			result = PageUtil.packageTableDataObject(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除待接单工单", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message delete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = orderinfoService.deleteOrderinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam String id, HttpServletRequest request) {
		Orderinfo torderinfos = orderinfoService.loadOrderinfoById(id);
		return JSON.toJSONString(torderinfos);
	}

	/**
	 * 更新或创建工单
	 *
	 * @param orderinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "修改待接单工单", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public BaseResponseDto saveOrUpdate(Orderinfo orderinfo, HttpServletRequest request) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("创建工单成功");
		JSONObject jsonObject = null;

		if (orderinfo.getAppointmentTime() != null && orderinfo.getAppointmentTime().equals("")) {
			orderinfo.setAppointmentTime(null);
		}

		Manager manager = (Manager) request.getSession().getAttribute("user");
		try {
			if (null == orderinfo.getId() || orderinfo.getId().equals("")) {

				// orderinfo.setId(UUIDUtil.getUUID());
				orderInfoOperateProxy.createOrder(orderinfo, manager);
			} else {
				orderInfoOperateProxy.updateOrder(orderinfo, manager);

				/*
				 * jsonObject = orderinfoService.updateOrderinfo(orderinfo); if (jsonObject ==
				 * null || !(Boolean) jsonObject.get("bol")) { throw new
				 * RuntimeException("更新失败"); }
				 */
				baseResponseDto.setErrMsg("工单更新成功");
			}
		} catch (Exception e) {
			logger.error("操作失败！", e);
			baseResponseDto.setErrCode(0);
			if (e instanceof BusinessException) {
				baseResponseDto.setErrMsg(e.getMessage());
			} else {
				baseResponseDto.setErrMsg("操作失败！");
			}
		}
		return baseResponseDto;
	}

	@RequestMapping("/selecttorderinfo")
	@ResponseBody
	public List<Orderinfo> selecttorderinfo(Orderinfo item) {
		return orderinfoService.selectOrderinfoList(item);
	}

	@RequestMapping("/selectCreateAudit")
	@ResponseBody
	public String selectCreateAudit(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {

		String rst = "";
		Page page = PageUtil.getPage(pageCondition);
		try {
			List<OrderinfoAudit> auditList = orderinfoAuditService.selectCreateAudit(item);
			rst = PageUtil.packageTableData(page, auditList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	/**
	 * 源匠确认工单
	 *
	 * @param orderinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月20日
	 */
	@RequestMapping("/updateCreateOrderInfo")
	@ResponseBody
	public BaseResponseDto updateCreateOrderInfo(Orderinfo orderinfo, HttpServletRequest request) {

		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("操作成功");

		Manager manager = (Manager) request.getSession().getAttribute("user");

		// 封装方法参数
		String operateUserId = manager.getId();
		BaseRequestDto requestDto = new BaseRequestDto();
		Map<String, String> paramMap = new HashMap<>(16);

		// 只更新状态，其他信息均不从界面获取
		orderinfo = orderinfoService.selectOrderinfoById(orderinfo.getId());

		paramMap.put("orderInfo", JSON.toJSONString(orderinfo));
		paramMap.put("orderId", String.valueOf(orderinfo.getId()));
		paramMap.put("type", String.valueOf(manager.getType()));
		requestDto.setData(JSON.toJSONString(paramMap));
		try {
			orderInfoOperateProxy.updateCreateOrderInfo(operateUserId, requestDto);
		} catch (Exception e) {
			logger.error("操作成功", e);
			baseResponseDto.setErrMsg("操作失败");
			baseResponseDto.setErrCode(0);
		}
		return baseResponseDto;
	}

	/**
	 * 后台主动取消工单分配，设为自由锁匠待抢单
	 * 
	 * @param orderinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月29日
	 */
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public BaseResponseDto cancelOrder(Orderinfo orderinfo, HttpServletRequest request) {

		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("操作成功");
		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			Map<String, String> paramMap = new HashMap<>(16);
			paramMap.put("orderId", orderinfo.getId());
			paramMap.put("mark", request.getParameter("mark"));
			paramMap.put("type", "back"); // 后台取消操作
			BaseRequestDto baseRequestDto = new BaseRequestDto();
			baseRequestDto.setData(JSON.toJSONString(paramMap));

			int rst=orderInfoOperateService.orderCancel(manager.getId(), baseRequestDto);
			if(rst<0)
			{
				baseResponseDto.setErrMsg("操作失败");
				baseResponseDto.setErrCode(0);
			}

		} catch (Exception e) {
			logger.error("操作成功", e);
			baseResponseDto.setErrMsg("操作失败");
			baseResponseDto.setErrCode(0);
		}
		return baseResponseDto;
	}
}
