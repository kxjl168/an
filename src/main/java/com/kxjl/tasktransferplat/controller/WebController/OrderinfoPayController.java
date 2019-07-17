/*
 * @(#)OrderinfoController.java
 * @author: KAutoGenerator
 * @Date: 2019-01-31 13:20:58
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
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dto.response.BaseResponseDto;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAttachMoneyDetail;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.service.OrderinfoAttachMoneyDetailService;
import com.kxjl.tasktransferplat.service.OrderinfoService;
import com.mongodb.BasicDBObject;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待结账工单管理 OrderinfoController.java.
 *
 * @author KAutoGenerator
 * @version 1.0.1 2019-01-31 13:20:58
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/torderinfo_pay")
public class OrderinfoPayController {

	private static final Logger logger = LoggerFactory.getLogger(OrderinfoPayController.class);

	@Autowired
	private OrderinfoService torderinfoService;

	@Autowired
	private OrderinfoAttachMoneyDetailService orderinfoAttachMoneyDetailService;

	@Autowired
	CompanyService HehuorenService;

	@RequestMapping("/manager")
	public String manager(Model model) {

		return "/backend/page/torderinfo_pay/index.ftl";
	}

	@RequestMapping("/manager1")
	public String manager1(Model model) {

		return "/backend/page/enterprise_pay/index.ftl";
	}

	/**
	 * 订单锁匠费用明细
	 *
	 * @param item
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@RequestMapping("/torderinfoMoneyList")
	// @ManagerActionLog(operateDescribe="查询订单审核记录",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public String torderinfoMoneyList(OrderinfoAttachMoneyDetail item, HttpServletRequest request,
			PageCondition pageCondition) {

		String rst = "";
		List<OrderinfoAttachMoneyDetail> torderinfoaudits = new ArrayList<>();

		pageCondition.setPageSize("30");

		Page page = PageUtil.getPage(pageCondition);
		if (item.getOrderinfoId() != null && !item.getOrderinfoId().equals("")) {
			torderinfoaudits = orderinfoAttachMoneyDetailService.selectOrderMoneyDetailList(item);
		}
		OrderinfoAttachMoneyDetail total = new OrderinfoAttachMoneyDetail();
		BigDecimal totalnum = new BigDecimal(0);
		BigDecimal totalSellernum = new BigDecimal(0);
		for (OrderinfoAttachMoneyDetail mdetail : torderinfoaudits) {
			totalnum = totalnum.add(mdetail.getChangeValueLocker());
			totalSellernum = totalSellernum.add(mdetail.getChangeValueSeller());
		}
		total.setChangeValueLocker(totalnum);
		total.setChangeValueSeller(totalSellernum);
		total.setReasonDes("总计");

		torderinfoaudits.add(total);
		try {
			rst = PageUtil.packageTableData(page, torderinfoaudits);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	/**
	 * 更新工单价格，工单改价
	 *
	 * @param request
	 * @param pageCondition
	 * @return
	 * @author zj
	 * @date 2019年6月4日
	 */
	@RequestMapping("/ModifyMoneyList")
	// @ManagerActionLog(operateDescribe="查询订单审核记录",operateFuncType=FunLogType.Query,operateModelClassName=OrderinfoAuditMapper.class)
	@ResponseBody
	public Message ModifyMoneyList(HttpServletRequest request, PageCondition pageCondition) {

		Message rst = new Message();
		rst.setBol(false);
		try {

			List<OrderinfoAttachMoneyDetail> torderinfoaudits = new ArrayList<>();

			String reason = request.getParameter("reason");
			String id = request.getParameter("id");

			String datajson = request.getParameter("data");

			JSONArray ja = new JSONArray(datajson);

			Map<String, String> mdata = new HashMap<String, String>();

			for (int i = 0; i < ja.length(); i++) {
				org.json.JSONObject jdata = ja.getJSONObject(i);
				mdata.put(jdata.getString("id"), jdata.getString("val"));
			}

			Manager manager = ((Manager) request.getSession().getAttribute("user"));

			rst = orderinfoAttachMoneyDetailService.ChangeOrderMoneydetail(mdata, reason, id, manager);

			rst.setBol(true);

		} catch (Exception e) {
			rst.setBol(false);
		}
		return rst;
	}

	@RequestMapping("/torderinfoList")
	@ManagerActionLog(operateDescribe = "源匠查询待结账工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String torderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		if (type == 1) {
			item.setCompanyId(manager.getCompanyId());

			Company hehuoren = HehuorenService.selectCompanyById(manager.getCompanyId());

			if (hehuoren.getAreaCode() != null && !hehuoren.getAreaCode().equals("")) {
				// zj 190507
				// 设置查询区域
				item.setAreaCode(hehuoren.getAreaCode().substring(0, 4));

			} else
				item.setAreaCode("888888"); // 合伙人没有区域，直接查询不到工单

		} else if (type == 2) {
			item.setSellerId(manager.getCompanyId());
		}
		String rst = "";
		List<Orderinfo> torderinfos = new ArrayList<>();

		item.setType(4);

		Page page = PageUtil.getPage(pageCondition);
		torderinfos = torderinfoService.selectOrderinfoList(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/orderinfoList")
	@ManagerActionLog(operateDescribe = "锁企查询待结账工单", operateFuncType = FunLogType.Query, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String orderinfoList(Orderinfo item, HttpServletRequest request, PageCondition pageCondition) {
		HashMap m = (HashMap) request.getAttribute("principal");

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();
		if (type == 2) {
			item.setSellerId(manager.getCompanyId());
		}
		String rst = "";
		List<Orderinfo> torderinfos = new ArrayList<>();

		Page page = PageUtil.getPage(pageCondition);
		torderinfos = torderinfoService.selectOrderinfoLists(item);

		try {
			rst = PageUtil.packageTableData(page, torderinfos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rst;
	}

	@RequestMapping("/delete")
	@ManagerActionLog(operateDescribe = "删除待结账工单", operateFuncType = FunLogType.Del, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public Message delete(Orderinfo item, HttpServletRequest request) {

		Message msg = new Message();

		int result = torderinfoService.deleteOrderinfo(item);
		if (result == 1) {
			msg.setBol(true);
		}
		return msg;
	}

	@RequestMapping("/loadbyOrderNo")
	@ResponseBody
	public String loadbyOrderNo(@RequestParam String id, HttpServletRequest request) {

		Orderinfo torderinfos = torderinfoService.selectOrderinfoByOrerNo(id);

		Manager manager = ((Manager) request.getSession().getAttribute("user"));
		int type = manager.getType();

		// 填入用户类型
		// torderinfos.setManagerType(String.valueOf(type));

		return JSON.toJSONString(torderinfos);
	}

	@RequestMapping("/load")
	@ResponseBody
	public String load(@RequestParam String id, HttpServletRequest request) {

		Orderinfo torderinfos = torderinfoService.selectOrderinfoById(id);
		return JSON.toJSONString(torderinfos);
	}

	/**
	 * 新增普通用户请求 demo
	 *
	 * @param torderinfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ManagerActionLog(operateDescribe = "工单结账操作", operateFuncType = FunLogType.SaveOrUpdate, operateModelClassName = OrderinfoMapper.class)
	@ResponseBody
	public String saveOrUpdate(Orderinfo torderinfo) {

		JSONObject jsonObject = null;
		try {
			jsonObject = torderinfoService.ComplatePayOrderinfo(torderinfo);
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("bol", false);
			jsonObject.put("message", e.getMessage());
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	/**
	 * 结账
	 *
	 * @param torderinfo
	 * @return
	 */
	@RequestMapping("/checkout")
	@ResponseBody
	public String checkout(Orderinfo torderinfo) {

		JSONObject jsonObject = null;
		try {
			torderinfo.setFinishedState(1);
			jsonObject = torderinfoService.checkoutOrderinfo(torderinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert jsonObject != null;
		return jsonObject.toString();
	}

	@RequestMapping("/selecttorderinfo")
	@ResponseBody
	public List<Orderinfo> selecttorderinfo(Orderinfo item) {
		return torderinfoService.selectOrderinfoList(item);
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
		style.setBorderColor(BorderSide.BOTTOM, color);
		style.setBorderColor(BorderSide.TOP, color);
		style.setBorderColor(BorderSide.LEFT, color);
		style.setBorderColor(BorderSide.RIGHT, color);

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

	private XSSFCell CreateCell(XSSFWorkbook workbook, XSSFRow row, int index) {
		return CreateCell(workbook, row, index, 1, 0);
	}

	/**
	 * 锁企对账单导出
	 *
	 * @param orderinfo
	 * @param request
	 * @return
	 * @author zj
	 * @date 2019年5月17日
	 */
	@RequestMapping("/enterpriseBillExcelExport")
	@ResponseBody
	public void enterpriseBillExcelExport(Orderinfo orderinfo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Manager manager = ((Manager) request.getSession().getAttribute("user"));
			int type = manager.getType();
			if (type == 2) {
				orderinfo.setSellerId(manager.getCompanyId());
			}
			
			orderinfo.setEnterpriseName("");//不查询名字
			
			
			
			List<Map<String, Object>> torderinfos = torderinfoService
					.selectLockerSmithEnterpriseFinancialList(orderinfo);
			torderinfos = torderinfos == null ? new ArrayList<>() : torderinfos;
			BigDecimal totalPrice = new BigDecimal(0.00);
			for (Map<String, Object> map : torderinfos) {
				totalPrice = totalPrice.add((BigDecimal) map.get("sellerTotalPrice"));
			}

			// 样式

			BasicDBObject timeQuery = new BasicDBObject();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("锁企对账单报表");

			XSSFRow row0 = sheet.createRow(0);
			// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
			// XSSFCell row0_cell = row0.createCell(0);
			XSSFCell row0_cell = CreateCell(workbook, row0, 0, 1, 10);

			row0_cell.setCellValue("源匠科技锁企对账单");

			XSSFRow row1 = sheet.createRow(1);
			XSSFCell row1_cell = CreateCell(workbook, row1, 0, 0, 10);// row1.createCell(0);
			row1_cell.setCellValue("锁企名称");

			row1_cell = CreateCell(workbook, row1, 1, 0, 10);// row1.createCell(1);
			row1_cell.setCellValue(orderinfo.getEnterpriseName());

			XSSFRow row2 = sheet.createRow(2);
			XSSFCell row2_cell = CreateCell(workbook, row2, 0, 0, 10);// row2.createCell(0);
			row2_cell.setCellValue("开始时间");

			row2_cell = CreateCell(workbook, row2, 1, 0, 10);// row2.createCell(1);
			row2_cell.setCellValue(orderinfo.getStartTime());

			XSSFRow row3 = sheet.createRow(3);
			XSSFCell row3_cell = CreateCell(workbook, row3, 0, 0, 10);// row3.createCell(0);
			row3_cell.setCellValue("结束时间");
			row3_cell = CreateCell(workbook, row3, 1, 0, 10);// row3.createCell(1);
			row3_cell.setCellValue(orderinfo.getEndTime());

			XSSFRow row4 = sheet.createRow(4);
			XSSFCell row4_cell = CreateCell(workbook, row4, 0, 0, 10);// row4.createCell(0);
			row4_cell.setCellValue("总金额");
			row4_cell = CreateCell(workbook, row4, 1, 0, 10);// row4.createCell(1);
			row4_cell.setCellValue(totalPrice.doubleValue() + "");

			XSSFRow row6 = sheet.createRow(6);
			// sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
			XSSFCell row6_cell = CreateCell(workbook, row6, 0, 1, 10);// row6.createCell(0);
			row6_cell.setCellValue("锁企对账明细表");

		
			/*
			 * 
			 * XSSFRow row7 = sheet.createRow(7); XSSFCell row7_cell = CreateCell(workbook,
			 * row7, 0, 0, 10);// row7.createCell(0); row7_cell.setCellValue("工单号");
			 * row7_cell = CreateCell(workbook, row7, 1, 0, 10);// row7.createCell(1);
			 * row7_cell.setCellValue("工单类型"); row7_cell = CreateCell(workbook, row7, 2, 0,
			 * 10);// row7.createCell(2); row7_cell.setCellValue("锁数量"); row7_cell =
			 * CreateCell(workbook, row7, 3, 0, 10);// row7.createCell(3);
			 * row7_cell.setCellValue("下单时间"); row7_cell = CreateCell(workbook, row7, 4, 0,
			 * 10);// row7.createCell(4); row7_cell.setCellValue("完成时间"); row7_cell =
			 * row7.createCell(5); row7_cell.setCellValue("客户姓名"); row7_cell =
			 * row7.createCell(6); row7_cell.setCellValue("客户电话"); row7_cell =
			 * row7.createCell(7); row7_cell.setCellValue("客户地址");
			 * 
			 * row7_cell = row7.createCell(8); row7_cell.setCellValue("锁企安装费"); row7_cell =
			 * row7.createCell(9); row7_cell.setCellValue("锁企维修费"); row7_cell =
			 * row7.createCell(10); row7_cell.setCellValue("锁企开锁费"); row7_cell =
			 * row7.createCell(11); row7_cell.setCellValue("锁企测量费"); row7_cell =
			 * row7.createCell(12); row7_cell.setCellValue("锁企猫眼安装费"); row7_cell =
			 * row7.createCell(13); row7_cell.setCellValue("锁企换锁费"); row7_cell =
			 * row7.createCell(14); row7_cell.setCellValue("锁企工程安装费"); row7_cell =
			 * row7.createCell(15); row7_cell.setCellValue("锁企工程维修费"); row7_cell =
			 * row7.createCell(16); row7_cell.setCellValue("锁企客户加价或其他");
			 * 
			 * row7_cell = row7.createCell(17); row7_cell.setCellValue("空跑费"); row7_cell =
			 * row7.createCell(18); row7_cell.setCellValue("远途费"); row7_cell =
			 * row7.createCell(19); row7_cell.setCellValue("改装费"); row7_cell =
			 * row7.createCell(20); row7_cell.setCellValue("特殊门改造费"); row7_cell =
			 * row7.createCell(21); row7_cell.setCellValue("加急费"); row7_cell =
			 * row7.createCell(22); row7_cell.setCellValue("其他费");
			 * 
			 * row7_cell = row7.createCell(23); row7_cell.setCellValue("小计"); row7_cell =
			 * row7.createCell(24); row7_cell.setCellValue("锁企结算金额");
			 */

			String[] colheaders = new String[] { "锁企名称","工单号", "工单类型", "锁数量", "下单时间", "完成时间", "客户姓名", "客户电话", "客户地址",
					 "锁企安装费","锁企维修费", "锁企开锁费", "锁企测量费", "锁企猫眼安装费", "锁企换锁费", "锁企工程安装费", "锁企工程维修费", "锁企其他费用","客服改价", "空跑费", "远途费",
					"改装费", "特殊门改造费", "加急费","假锁费", "小计", "锁企结算金额" };

			XSSFRow row7 = sheet.createRow(7);
			XSSFCell row7_cell = null;
			for (int i = 0; i < colheaders.length; i++) {

				row7_cell = CreateCell(workbook, row7, i, 0, 10);// row7.createCell(0);
				row7_cell.setCellValue(colheaders[i]);
			}

			
			String[] coldata = new String[] { "enterpriseName","orderNo", "orderFeeType", "num", "createTime", "doneTime", "customerName","customerPhone", "custAddress"
					, "build", "repair", "openLock", "measure", "catInstall","changeLock", "projectBuild", "projectRepair","projectOther", "buildOther","runEmpty","longDistance"
					,"changeBuild","specialDoor","hurry","prelock","sellerTotalPrice","sellerTotalPrice" };

			BasicDBObject query = new BasicDBObject();
			if (!timeQuery.isEmpty()) {
				query.append("downdate", timeQuery);
			}

			int rowCount = 8;
			if (torderinfos != null && torderinfos.size() > 0) {
				for (int i = 0; i < torderinfos.size(); i++) {
					Map<String, Object> item = torderinfos.get(i);
					if (item != null) {
						row7 = sheet.createRow(rowCount);

					

						for (int j = 0; j < coldata.length; j++) {
							row7_cell = CreateCell(workbook, row7, j, 0, 10);// row7.createCell(0);
							String value = getTableValue(coldata[j], item);

							row7_cell.setCellValue(value);
						}
						
					

						
						/*
						row7_cell = row7.createCell(0);
						row7_cell.setCellValue(item.get("orderNo") + "");
						
						row7_cell = row7.createCell(1);
						row7_cell.setCellValue(serverValue);

						row7_cell = row7.createCell(2);
						row7_cell.setCellValue(1);
						row7_cell = row7.createCell(3);
						row7_cell.setCellValue(item.get("createTime") + "");
						row7_cell = row7.createCell(4);
						row7_cell.setCellValue(item.get("doneTime") + "");
						row7_cell = row7.createCell(5);
						row7_cell.setCellValue(item.get("customerName") + "");
						row7_cell = row7.createCell(6);
						row7_cell.setCellValue(item.get("customerPhone") + "");
						row7_cell = row7.createCell(7);
						row7_cell.setCellValue(item.get("custAddress") + "");
						row7_cell = row7.createCell(8);
					
						row7_cell = row7.createCell(9);
						row7_cell.setCellValue(item.get("repair") == null ? "" : item.get("repair") + "");
						row7_cell = row7.createCell(10);
						row7_cell.setCellValue(item.get("openLock") == null ? "" : item.get("openLock") + "");
						row7_cell = row7.createCell(11);
						row7_cell.setCellValue(item.get("measure") == null ? "" : item.get("measure") + "");
						row7_cell = row7.createCell(12);
						row7_cell.setCellValue(item.get("catInstall") == null ? "" : item.get("catInstall") + "");
						row7_cell = row7.createCell(13);
						row7_cell.setCellValue(item.get("changeLock") == null ? "" : item.get("changeLock") + "");
						row7_cell = row7.createCell(14);
						row7_cell.setCellValue(item.get("projectBuild") == null ? "" : item.get("projectBuild") + "");
						row7_cell = row7.createCell(15);
						row7_cell.setCellValue(item.get("projectRepair") == null ? "" : item.get("projectRepair") + "");
						row7_cell = row7.createCell(16);
						row7_cell.setCellValue(item.get("buildOther") == null ? "" : item.get("buildOther") + "");
						row7_cell = row7.createCell(17);
						row7_cell.setCellValue(item.get("runEmpty") == null ? "" : item.get("runEmpty") + "");
						row7_cell = row7.createCell(18);
						row7_cell.setCellValue(item.get("longDistance") == null ? "" : item.get("longDistance") + "");
						row7_cell = row7.createCell(19);
						row7_cell.setCellValue(item.get("changeBuild") == null ? "" : item.get("changeBuild") + "");
						row7_cell = row7.createCell(20);
						row7_cell.setCellValue(item.get("specialDoor") == null ? "" : item.get("specialDoor") + "");
						row7_cell = row7.createCell(21);
						row7_cell.setCellValue(item.get("hurry") == null ? "" : item.get("hurry") + "");
						row7_cell = row7.createCell(22);
						row7_cell.setCellValue(item.get("serverOther") == null ? "" : item.get("serverOther") + "");
						row7_cell = row7.createCell(23);
						row7_cell.setCellValue(item.get("sellerTotalPrice") + "");
						row7_cell = row7.createCell(24);
						row7_cell.setCellValue(item.get("sellerTotalPrice") + "");*/
						rowCount++;
					}
				}
			}

			String fileName = (orderinfo.getEnterpriseName()==null?"":orderinfo.getEnterpriseName()) + "锁企对账单报表.xlsx";

			
			if (workbook != null) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(fileName, "utf-8"));// mod
				response.flushBuffer(); // by
				ServletOutputStream out = response.getOutputStream();
				workbook.write(out);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		/*case "num":
			rst="1";
			break;*/

		default:
			rst=item.get(coldataname) == null ? "" : item.get(coldataname) + "";
			break;
		}

		return rst;

	}

	@RequestMapping("/importSettleOrderBillByExcel")
	@ResponseBody
	public BaseResponseDto importSettleOrderBillByExcel(HttpServletRequest request, MultipartFile excelFile) {
		BaseResponseDto baseResponseDto = new BaseResponseDto();
		baseResponseDto.setErrCode(1);
		baseResponseDto.setErrMsg("导入成功！");

		List<String> errList;
		try {
			errList = transformFileToData(excelFile);
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
	private List<String> transformFileToData(MultipartFile excelFile) throws Exception {
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
					orderNo = getCellValue(row.getCell(0));
					Orderinfo orderinfo = torderinfoService.selectOrderByOrerNo(orderNo);
					orderinfo.setFinishedState(1);
					JSONObject jsonObject = torderinfoService.checkoutOrderinfo(orderinfo);
					if (jsonObject.getBoolean("bol") == false) {
						msgList.add(row.getRowNum() + "行 更新出现问题");
						continue;
					}
				} catch (Exception e) {
					logger.error(row.getRowNum() + "行 出现问题", e);
					msgList.add(row.getRowNum() + "行 出现问题:工单存在异常");
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