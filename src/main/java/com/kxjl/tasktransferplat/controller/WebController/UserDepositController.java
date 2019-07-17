/**
 * @(#)UserDepositController.java 2019/3/11 14:53
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.PageUtil;
import com.kxjl.base.websocket.MyWebSocket;
import com.kxjl.tasktransferplat.dao.plus.UserDepositMapper;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.ManagerMessage;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.pojo.UserDeposit;
import com.kxjl.tasktransferplat.service.ManagerMessageService;
import com.kxjl.tasktransferplat.service.UserDepositService;
import com.mongodb.BasicDBObject;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.kxjl.tasktransferplat.util.DateUtil.getCellDateTime;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/3/11 14:53
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/userDeposit")
@Slf4j
public class UserDepositController {
	private static final Logger logger = LoggerFactory.getLogger(UserDepositController.class);

	@Autowired
	private UserDepositService userDepositService;

	@Autowired
	private ManagerMessageService managerMessageService;

	/**
	 * 首页
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			ManagerMessage managerMessage = new ManagerMessage();
			managerMessage.setUserId(manager.getId());
			managerMessage.setType(5 + "");
			managerMessageService.updateMessageReadByUserAndType(managerMessage);

			List<Map<String, String>> list = managerMessageService.selectUnReadMsgCountByUser(managerMessage);
			MyWebSocket.sendMessage(JSON.toJSONString(list), managerMessage.getUserId());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("websocket发送消息报错");
		}
		return "/backend/page/user_deposit/index.ftl";
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam String id, HttpServletRequest request) {

		UserDeposit tuserinfos = userDepositService.loadUserDepositById(id);
		return JSON.toJSONString(tuserinfos);
	}

	/**
	 * 提现申请列表
	 *
	 * @param pageCondition
	 * @param userDeposit
	 * @param request
	 * @return
	 */
	@RequestMapping("/depositList")
	@ResponseBody
	public String depositList(PageCondition pageCondition, UserDeposit userDeposit, HttpServletRequest request) {

		String rst = "";
		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		if (manager.getCompanyId() != null && manager.getCompanyId() != 0) {
			// userDeposit.setCompanyId(manager.getCompanyId());
		}

		Page page = PageUtil.getPage(pageCondition);

		try {
			List<UserDeposit> depositList = userDepositService.depositList(userDeposit);
			rst = PageUtil.packageTableData(page, depositList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}

	/**
	 * 审核通过或转账成功
	 *
	 * @param userDeposit
	 * @param request
	 * @return
	 */
	@RequestMapping("/pass")
	@ResponseBody
	public BaseResponseDto pass(UserDeposit userDeposit, HttpServletRequest request) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("成功");

		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));
			userDeposit.setAuditor(manager.getId());
			userDepositService.pass(userDeposit);
		} catch (Exception e) {
			log.error("操作失败", e);
			baseResponseDto.setErrCode(0);
			baseResponseDto.setErrMsg("失败");
		}
		return baseResponseDto;
	}

	/**
	 * 审核不通过或转账失败
	 *
	 * @param userDeposit
	 * @param request
	 * @return
	 */
	@RequestMapping("/fail")
	@ResponseBody
	public BaseResponseDto fail(UserDeposit userDeposit, HttpServletRequest request) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("成功");

		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));
			userDeposit.setAuditor(manager.getId());
			userDepositService.fail(userDeposit);
		} catch (Exception e) {
			log.error("操作失败", e);
			baseResponseDto.setErrCode(0);
			baseResponseDto.setErrMsg("失败");
		}
		return baseResponseDto;
	}

	/**
	 * 获取待样式的cell
	 *
	 * @param workbook
	 * @param row
	 * @param index
	 * @return
	 * @author zj
	 * @date 2019年6月21日
	 */
	private XSSFCell CreateCell(XSSFWorkbook workbook, XSSFRow row, int index, int indexWidth, int colwidth) {
		XSSFCell cell = row.createCell(index);

		if (indexWidth > 0) {
			/*
			 * for (int i = 1; i <= indexWidth; i++) { row.createCell(index + i); }
			 */
			// 合并单元格
			CellRangeAddress cra = new CellRangeAddress(row.getRowNum(), row.getRowNum(), index, index + indexWidth); // 起始行,
			// 终止行,
			// 起始列,
			// 终止列
			workbook.getSheetAt(0).addMergedRegion(cra);

			RegionUtil.setBorderBottom(1, cra, workbook.getSheetAt(0)); // 下边框
			RegionUtil.setBorderLeft(1, cra, workbook.getSheetAt(0)); // 左边框
			RegionUtil.setBorderRight(1, cra, workbook.getSheetAt(0)); // 有边框
			RegionUtil.setBorderTop(1, cra, workbook.getSheetAt(0)); // 上边框

		}

		cell.setCellStyle(getCellStyle(workbook));

		if (colwidth != 0) // 字符长度
			workbook.getSheetAt(0).setColumnWidth(index, (2 * colwidth) * 256);

		return cell;
	}

	private XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

		// 背景色
		// style.setFillForegroundColor(HSSFColor.YELLOW.index);
		// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// style.setFillBackgroundColor(HSSFColor.YELLOW.index);

		// 设置边框
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		// 自动换行
		style.setWrapText(true);

		XSSFColor color = new XSSFColor(new Color(0, 0, 0));
		style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);

		// 生成一个字体
		// XSSFFont font = workbook.createFont();
		// font.setFontHeightInPoints((short) 12);
		// font.setColor(HSSFColor.BLACK.index);
		// font.setBoldweig
		// font.setFontName("宋体");

		// 把字体 应用到当前样式
		// style.setFont(font);

		return style;
	}

	/**
	 * 获取table每一列的数据
	 * @param coldataname
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年6月27日
	 */
	private String getTableValue(String coldataname, Map<String, Object> item) {
		String rst = "";

		switch (coldataname) {
			case "serverValue":

				String serverValue = "";
				if (null != item.get("build") && !"".equals(item.get("build"))
						&& !"0.00".equals(item.get("build").toString())) {
					serverValue += "安装,";
				}
				if (null != item.get("repair") && !"".equals(item.get("repair"))
						&& !"0.00".equals(item.get("repair").toString())) {
					serverValue += "维修,";
				}
				if (null != item.get("openLock") && !"".equals(item.get("openLock"))
						&& !"0.00".equals(item.get("openLock").toString())) {
					serverValue += "开锁,";
				}
				if (null != item.get("measure") && !"".equals(item.get("measure"))
						&& !"0.00".equals(item.get("measure").toString())) {
					serverValue += "测量,";
				}
				if (null != item.get("catInstall") && !"".equals(item.get("catInstall"))
						&& !"0.00".equals(item.get("catInstall").toString())) {
					serverValue += "猫眼安装,";
				}
				if (null != item.get("changeLock") && !"".equals(item.get("changeLock"))
						&& !"0.00".equals(item.get("changeLock").toString())) {
					serverValue += "换锁,";
				}
				if (null != item.get("projectBuild") && !"".equals(item.get("projectBuild"))
						&& !"0.00".equals(item.get("projectBuild").toString())) {
					serverValue += "工程安装,";
				}
				if (null != item.get("projectRepair") && !"".equals(item.get("projectRepair"))
						&& !"0.00".equals(item.get("projectRepair").toString())) {
					serverValue += "工程维修,";
				}
				if (null != item.get("buildOther") && !"".equals(item.get("buildOther"))
						&& !"0.00".equals(item.get("buildOther").toString())) {
					serverValue += "其他";
				}

				rst=serverValue;

				break;

			case "orderFeeType":
				if(String.valueOf(item.get(coldataname)).equals("1"))
					rst="自费工单";
				else
					rst="标准工单";
				break;
		/*	case "num":
				rst="1";
				break;*/

			default:
				rst=item.get(coldataname) == null ? "" : item.get(coldataname) + "";
				break;
		}

		return rst;

	}

	@RequestMapping("/userDepositExcel")
	@ManagerActionLog(operateDescribe = "锁匠提现账单报表", operateFuncType = FunLogType.Query, operateModelClassName = UserDepositMapper.class)
	@ResponseBody
	public void partnerFinancialStatisticsExcel(UserDeposit userDeposit, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			userDeposit.setUserName("");//不查询名称
			
			List<Map<String, Object>> list = userDepositService.userDepositExcel(userDeposit);
			list = list == null ? new ArrayList<>() : list;

			BigDecimal totalPrice = new BigDecimal(0.00);
			for (Map<String, Object> map : list) {
				totalPrice = totalPrice.add((BigDecimal) map.get("lockerTotalPrice"));
			}

			BasicDBObject timeQuery = new BasicDBObject();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("锁匠对账单报表");

			XSSFRow row0 = sheet.createRow(0);
			XSSFCell row0_cell = CreateCell(workbook, row0, 0, 1, 10);
			row0_cell.setCellValue("源匠科技锁匠对账单");

			XSSFRow row1 = sheet.createRow(1);
			XSSFCell row1_cell = CreateCell(workbook, row1, 0, 0, 10);// row1.createCell(0);
			row1_cell.setCellValue("锁匠名称");
			row1_cell = CreateCell(workbook, row1, 1, 0, 10);// row1.createCell(0);
			row1_cell.setCellValue(userDeposit.getUserName());

			XSSFRow row2 = sheet.createRow(2);
			XSSFCell row2_cell = CreateCell(workbook, row2, 0, 0, 10);// row1.createCell(0);
			row2_cell.setCellValue("锁匠电话");
			row2_cell = CreateCell(workbook, row2, 1, 0, 10);// row1.createCell(0);
			row2_cell.setCellValue(userDeposit.getLockerPhone());

			XSSFRow row3 = sheet.createRow(3);
			XSSFCell row3_cell = CreateCell(workbook, row3, 0, 0, 10);// row1.createCell(0);
			row3_cell.setCellValue("开始时间");
			row3_cell = CreateCell(workbook, row3, 1, 0, 10);// row1.createCell(0);
			row3_cell.setCellValue(userDeposit.getStartTime());

			XSSFRow row4 = sheet.createRow(4);
			XSSFCell row4_cell = CreateCell(workbook, row4, 0, 0, 10);// row1.createCell(0);
			row4_cell.setCellValue("结束时间");
			row4_cell = CreateCell(workbook, row4, 1, 0, 10);// row1.createCell(0);
			row4_cell.setCellValue(userDeposit.getEndTime());

			XSSFRow row5 = sheet.createRow(5);
			XSSFCell row5_cell = CreateCell(workbook, row5, 0, 0, 10);// row1.createCell(0);
			row5_cell.setCellValue("总金额");
			row5_cell = CreateCell(workbook, row5, 1, 0, 10);// row1.createCell(0);
			row5_cell.setCellValue(totalPrice.doubleValue()+"");

			XSSFRow row7 = sheet.createRow(7);
			// sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
			XSSFCell row7_cell = CreateCell(workbook, row7, 0, 1, 10);// row6.createCell(0);
			row7_cell.setCellValue("锁匠对账明细表");
			String[] colheaders = new String[] { "工单号", "锁匠","工单类型","锁企名称", "锁数量", "下单时间", "完成时间", "客户姓名", "客户电话", "客户地址",
					"安装费","维修费", "开锁费", "测量费", "猫眼安装费", "换锁费", "工程安装费", "工程维修费", "其他费用","客服改价", "空跑费", "远途费",
					"改装费", "特殊门改造费", "加急费","假锁费", "小计", "锁匠结算金额" };

			XSSFRow row8 = sheet.createRow(8);
			XSSFCell row8_cell = null;
			for (int i = 0; i < colheaders.length; i++) {

				row8_cell = CreateCell(workbook, row8, i, 0, 10);// row7.createCell(0);
				row8_cell.setCellValue(colheaders[i]);
			}


			String[] coldata = new String[] { "orderNo", "userName","orderFeeType","enterpriseName", "num", "createTime", "doneTime", "customerName","customerPhone", "custAddress"
					, "build", "repair", "openLock", "measure", "catInstall","changeLock", "projectBuild", "projectRepair","projectOther", "buildOther","runEmpty","longDistance"
					,"changeBuild","specialDoor","hurry","prelock","lockerTotalPrice","lockerTotalPrice" };

			BasicDBObject query = new BasicDBObject();
			if (!timeQuery.isEmpty()) {
				query.append("downdate", timeQuery);
			}

			int rowCount = 9;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> item = list.get(i);
					if (item != null) {
						row8 = sheet.createRow(rowCount);
						for (int j = 0; j < coldata.length; j++) {
							row8_cell = CreateCell(workbook, row8, j, 0, 10);// row7.createCell(0);
							String value = getTableValue(coldata[j], item);
							row8_cell.setCellValue(value);
						}
						rowCount++;
					}
				}
			}

//			XSSFRow row7 = sheet.createRow(8);
//			XSSFCell row7_cell = row7.createCell(0);
//			row7_cell.setCellValue("工单号");
//			row7_cell = row7.createCell(1);
//			row7_cell.setCellValue("工单类型");
//			row7_cell = row7.createCell(2);
//			row7_cell.setCellValue("锁企名称");
//			row7_cell = row7.createCell(3);
//			row7_cell.setCellValue("锁数量");
//			row7_cell = row7.createCell(4);
//			row7_cell.setCellValue("下单时间");
//			row7_cell = row7.createCell(5);
//			row7_cell.setCellValue("完成时间");
//			row7_cell = row7.createCell(6);
//			row7_cell.setCellValue("客户姓名");
//			row7_cell = row7.createCell(7);
//			row7_cell.setCellValue("客户电话");
//			row7_cell = row7.createCell(8);
//			row7_cell.setCellValue("客户地址");
//			row7_cell = row7.createCell(9);
//
//			row7_cell = row7.createCell(10);
//			row7_cell.setCellValue("安装费");
//			row7_cell = row7.createCell(11);
//			row7_cell.setCellValue("维修费");
//			row7_cell = row7.createCell(12);
//			row7_cell.setCellValue("开锁费");
//			row7_cell = row7.createCell(13);
//			row7_cell.setCellValue("测量费");
//			row7_cell = row7.createCell(14);
//			row7_cell.setCellValue("猫眼安装费");
//			row7_cell = row7.createCell(15);
//			row7_cell.setCellValue("换锁费");
//			row7_cell = row7.createCell(16);
//			row7_cell.setCellValue("工程安装费");
//			row7_cell = row7.createCell(17);
//			row7_cell.setCellValue("工程维修费");
//			row7_cell = row7.createCell(18);
//			row7_cell.setCellValue("客户加价或其他");
//
//			row7_cell = row7.createCell(19);
//			row7_cell.setCellValue("空跑费");
//			row7_cell = row7.createCell(20);
//			row7_cell.setCellValue("远途费");
//			row7_cell = row7.createCell(21);
//			row7_cell.setCellValue("改装费");
//			row7_cell = row7.createCell(22);
//			row7_cell.setCellValue("特殊门改造费");
//			row7_cell = row7.createCell(23);
//			row7_cell.setCellValue("加急费");
//			row7_cell = row7.createCell(24);
//			row7_cell.setCellValue("其他费");
//
//			row7_cell = row7.createCell(25);
//			row7_cell.setCellValue("小计");
//			row7_cell = row7.createCell(26);
//			row7_cell.setCellValue("锁企结算金额");
//
//			BasicDBObject query = new BasicDBObject();
//			if (!timeQuery.isEmpty()) {
//				query.append("downdate", timeQuery);
//			}
//
//			int rowCount = 9;
//			if (list != null && list.size() > 0) {
//				for (int i = 0; i < list.size(); i++) {
//					Map<String, Object> item = list.get(i);
//					if (item != null) {
//						row7 = sheet.createRow(rowCount);
//						row7_cell = row7.createCell(0);
//						row7_cell.setCellValue(item.get("orderNo") + "");
//						String serverValue = "";
//
//						if (null != item.get("build")&& !"".equals(item.get("build")) && !"0.00".equals(item.get("build").toString())) {
//							serverValue += "安装,";
//						}
//						if (null != item.get("repair")&& !"".equals(item.get("repair")) && !"0.00".equals(item.get("repair").toString())) {
//							serverValue += "维修,";
//						}
//						if (null != item.get("openLock")&& !"".equals(item.get("openLock")) && !"0.00".equals(item.get("openLock").toString())) {
//							serverValue += "开锁,";
//						}
//						if (null != item.get("measure")&& !"".equals(item.get("measure")) && !"0.00".equals(item.get("measure").toString())) {
//							serverValue += "测量,";
//						}
//						if (null != item.get("catInstall")&& !"".equals(item.get("catInstall")) && !"0.00".equals(item.get("catInstall").toString())) {
//							serverValue += "猫眼安装,";
//						}
//						if (null != item.get("changeLock")&& !"".equals(item.get("changeLock")) && !"0.00".equals(item.get("changeLock").toString())) {
//							serverValue += "换锁,";
//						}
//						if (null != item.get("projectBuild")&& !"".equals(item.get("projectBuild")) && !"0.00".equals(item.get("projectBuild").toString())) {
//							serverValue += "工程安装,";
//						}
//						if (null != item.get("projectRepair")&& !"".equals(item.get("projectRepair")) && !"0.00".equals(item.get("projectRepair").toString())) {
//							serverValue += "工程维修,";
//						}
//						if (null != item.get("buildOther") && !"".equals(item.get("buildOther")) && !"0.00".equals(item.get("buildOther").toString())) {
//							serverValue += "其他";
//						}
//
//						row7_cell = row7.createCell(1);
//						row7_cell.setCellValue(serverValue);
//						row7_cell = row7.createCell(2);
//						row7_cell.setCellValue(item.get("enterpriseName") + "");
//						row7_cell = row7.createCell(3);
//						row7_cell.setCellValue(1);
//						row7_cell = row7.createCell(4);
//						row7_cell.setCellValue(item.get("createTime") + "");
//						row7_cell = row7.createCell(5);
//						row7_cell.setCellValue(item.get("doneTime") + "");
//						row7_cell = row7.createCell(6);
//						row7_cell.setCellValue(item.get("customerName") + "");
//						row7_cell = row7.createCell(7);
//						row7_cell.setCellValue(item.get("customerPhone") + "");
//						row7_cell = row7.createCell(8);
//						row7_cell.setCellValue(item.get("custAddress") + "");
//						row7_cell = row7.createCell(9);
//
//						row7_cell = row7.createCell(10);
//						row7_cell.setCellValue(item.get("build") == null ? "" : item.get("build") + "");
//						row7_cell = row7.createCell(11);
//						row7_cell.setCellValue(item.get("repair") == null ? "" : item.get("repair") + "");
//						row7_cell = row7.createCell(12);
//						row7_cell.setCellValue(item.get("openLock") == null ? "" : item.get("openLock") + "");
//						row7_cell = row7.createCell(13);
//						row7_cell.setCellValue(item.get("measure") == null ? "" : item.get("measure") + "");
//						row7_cell = row7.createCell(14);
//						row7_cell.setCellValue(item.get("catInstall") == null ? "" : item.get("catInstall") + "");
//						row7_cell = row7.createCell(15);
//						row7_cell.setCellValue(item.get("changeLock") == null ? "" : item.get("changeLock") + "");
//						row7_cell = row7.createCell(16);
//						row7_cell.setCellValue(item.get("projectBuild") == null ? "" : item.get("projectBuild") + "");
//						row7_cell = row7.createCell(17);
//						row7_cell.setCellValue(item.get("projectRepair") == null ? "" : item.get("projectRepair") + "");
//						row7_cell = row7.createCell(18);
//						row7_cell.setCellValue(item.get("buildOther") == null ? "" : item.get("buildOther") + "");
//						row7_cell = row7.createCell(19);
//						row7_cell.setCellValue(item.get("runEmpty") == null ? "" : item.get("runEmpty") + "");
//						row7_cell = row7.createCell(20);
//						row7_cell.setCellValue(item.get("longDistance") == null ? "" : item.get("longDistance") + "");
//						row7_cell = row7.createCell(21);
//						row7_cell.setCellValue(item.get("changeBuild") == null ? "" : item.get("changeBuild") + "");
//						row7_cell = row7.createCell(22);
//						row7_cell.setCellValue(item.get("specialDoor") == null ? "" : item.get("specialDoor") + "");
//						row7_cell = row7.createCell(23);
//						row7_cell.setCellValue(item.get("hurry") == null ? "" : item.get("hurry") + "");
//						row7_cell = row7.createCell(24);
//						row7_cell.setCellValue(item.get("serverOther") == null ? "" : item.get("serverOther") + "");
//						row7_cell = row7.createCell(25);
//						row7_cell.setCellValue(item.get("lockerTotalPrice") + "");
//						row7_cell = row7.createCell(26);
//						row7_cell.setCellValue(item.get("lockerTotalPrice") + "");
//						rowCount++;
//					}
//				}
//			}
			String fileName = (userDeposit.getUserName()==null?"":userDeposit.getUserName()) + "提现对账单.xlsx";

			if (workbook != null) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));// mod
				response.flushBuffer(); // by
				ServletOutputStream out = response.getOutputStream();
				workbook.write(out);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/importAuditOrderByExcel")
	@ResponseBody
	public BaseResponseDto importAuditOrderByExcel(HttpServletRequest request, MultipartFile excelFile) {
		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		String auditor=manager.getId();

		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("导入成功！");

		List<String> errList;
		try {
			int status = Integer.parseInt(request.getParameter("status"));
			errList = transformFileToData(excelFile, status,auditor);
			if (errList != null && errList.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < errList.size(); i++) {
					String msg = String.valueOf(errList.get(i));
					sb.append(msg + "<br>");
				}
				baseResponseDto.setErrCode(0);
				baseResponseDto.setErrMsg("部分数据导入失败！<br>" + sb.toString());
			}
		} catch (Exception e) {
			logger.error("导入数据失败！", e);
			baseResponseDto.setErrCode(0);
			baseResponseDto.setErrMsg("导入失败");
		}
		return baseResponseDto;
	}

	/**
	 * 根据xls文件或者xlsx文件生成订单对象集合
	 *
	 * @param excelFile
	 * @return
	 * @throws IOException
	 */
	private List<String> transformFileToData(MultipartFile excelFile, int state,String auditor) throws Exception {

		List<String> msgList = new ArrayList<>();
		InputStream inputStream = excelFile.getInputStream();
		String fileName = excelFile.getOriginalFilename();

		// 根据文件后缀生成对象
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		Workbook workbook = null;
		if (suffix.equals("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
		if (suffix.equals("xlsx")) {
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
				String orderNo;
				OrderinfoOperateLog operateLog = new OrderinfoOperateLog();
				try {
					
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					orderNo = getCellValue(row.getCell(0));
					if(orderNo==null||orderNo.trim().equals(""))
						continue;
					UserDeposit userDeposit = userDepositService.selectUserDepositByOrderNo(orderNo);
					if (null == userDeposit) {
						msgList.add(row.getRowNum() + "行 订单号["+orderNo+"]不存在");
						continue;
					}

					if (state == 1) {
						// 批量审核
						if (userDeposit.getDepositStatus() == 0) {
							// 正常
							userDeposit.setDepositStatus(state);
							userDeposit.setAuditor(auditor);
							//int result = userDepositService.updateUserDepositStatus(userDeposit);
							
							userDepositService.pass(userDeposit);
							
							//if (result == 0) {
							//	msgList.add(row.getRowNum() + "行"+"["+orderNo+"]"+" 提现工单审核失败");
							
						} else if (userDeposit.getDepositStatus() == 1)
							msgList.add(row.getRowNum() + "行 "+"["+orderNo+"]"+"提现工单已经审核完成,不能再审核");
						  else if (userDeposit.getDepositStatus() == 3)
							msgList.add(row.getRowNum() + "行"+"["+orderNo+"]"+"提现工单已经提现完成,不能再审核!");
					} else if (state == 3) {
						// 批量结账
						if (userDeposit.getDepositStatus() == 0) {
							msgList.add(row.getRowNum() + "行 "+"["+orderNo+"]"+"提现工单还未审核完成,不能提现");
						} else if (userDeposit.getDepositStatus() == 1) {
							// 正常
							userDeposit.setDepositStatus(state);
							userDeposit.setAuditor(auditor);
							//int result = userDepositService.updateUserDepositStatus(userDeposit);
							
							userDepositService.pass(userDeposit);
							
							//if (result == 0) {
							//	msgList.add(row.getRowNum() + "行 "+"["+orderNo+"]"+"提现工单提现失败");
							//}
						}else if (userDeposit.getDepositStatus() == 3)
							msgList.add(row.getRowNum() + "行 "+"["+orderNo+"]"+"提现工单已经提现完成,不能再提现!");

					}

				} catch (Exception e) {
					logger.error(row.getRowNum() + "行 出现问题", e);
					msgList.add(row.getRowNum() + "行 出现问题:工单出现异常");
					continue;
				}
			}
		}

		return msgList;
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

}
