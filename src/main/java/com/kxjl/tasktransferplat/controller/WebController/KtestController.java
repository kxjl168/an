/**
 * @(#)AreaController.java 2019/2/18 14:07
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kxjl.base.aopAspect.NoNeedAuthorization;

import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;
import com.kxjl.tasktransferplat.service.AreaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/18 14:07
 * @since 1.0.0
 */
@Controller
@RequestMapping("/ktest/")
public class KtestController {

	private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;
	//@Autowired
	//private OpencvHelper opencvHelper;

/*	@NoNeedAuthorization
	@ResponseBody
	@RequestMapping(value = "/imeiocr")
	public AppResult imeiocr(HttpServletRequest request,
			@RequestParam(value = "mFile", required = false) MultipartFile mFile) {
		try {
			String imei = opencvHelper.ImeiOCR(mFile);

			if (imei != null && !imei.equals("") && imei.indexOf("86") > -1) {

				
				String mei = imei.replaceAll("\n", "");
				
				mei=mei.substring(mei.indexOf("86"));
				
				//String mei = imei.replaceAll("\n", "").substring(imei.indexOf("86")-1);
				// opencvHelper.ImeiOCR(MultipartFile)
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("imei", mei);
				return AppResultUtil.success(data);
			} else {
				return AppResultUtil.fail("图片识别失败,请重新拍照或手动输入");
			}
		} catch (Exception e) {
			// logger.error(e.getMessage());
			return AppResultUtil.fail("错误原因" + e);
		}
	}*/

	/**
	 * 查询所有省
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		List<AreaProvince> provinces;

		request.setAttribute("base", "base");

		return "/backend/page/ktest/index.ftl";
	}

}
