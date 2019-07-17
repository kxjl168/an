package com.kxjl.tasktransferplat.controller.AppController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.kxjl.base.util.aes.AesHelper;

public class AppBaseController {

	private Logger loggerBase = Logger.getLogger(AppBaseController.class);

	
	/**
	 * body体读取 已经AES加密的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zj
	 * @date 2018年6月29日
	 */
	public String handerAesRequestData(HttpServletRequest request) {
		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			data = sb.toString();

			
			String realData = AesHelper.decrypt(data, AesHelper.aesPass);
			return realData;

		} catch (Exception e) {
			loggerBase.error("解密失败",e);
		}
		return data;
	}

	/**
	 * body体读取
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zj
	 * @date 2018年6月29日
	 */
	public String handerRequestData(HttpServletRequest request) {
		String data = null;
		try {
			InputStream instream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();

			instream.close();
			data = sb.toString();

		} catch (Exception e) {

		}
		return data;
	}

	/**
	 * 解析参数，兼容普通parameter, data=,json in body
	 * 
	 * @param request
	 * @param paramname
	 * @return
	 * @author zj
	 * @date 2019年2月12日
	 */
	public String parseStringParam(HttpServletRequest request, String paramname) {
		String rst = "";

		try {
			/*
			 * Enumeration<String> keys= request.getParameterNames();
			 * while(keys.hasMoreElements()) { System.out.println(keys.nextElement()); } rst
			 * = request.getParameter(paramname);
			 */
			rst = request.getParameter(paramname);
			if (rst == null || rst.equals("")) {
				String data = request.getParameter("data");
				if (data != null && !data.equals("")) {
					JSONObject jsonIN = new JSONObject(data);
					if (jsonIN != null)
						rst = jsonIN.optString(paramname);
				} else {
					String bodyStr = handerRequestData(request);
					if (bodyStr != null && !bodyStr.equals("")) {
						JSONObject jsonIN = new JSONObject(bodyStr);
						if (jsonIN != null)
							rst = jsonIN.optString(paramname);
					}
				}
			}

		} catch (Exception e) {
			rst = "";
		}

		return rst;

	}

	/**
	 * 兼容解析参数，兼容普通parameter, data=,json in body输入参数
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2018-1-4
	 */
	public int parseIntegerParam(HttpServletRequest request, String paramname) {
		int rst = 0;
		String rst_s = "";

		try {

			rst_s = request.getParameter(paramname);
			if (rst_s != null && !rst_s.equals("")) {
				try {
					rst = Integer.parseInt(rst_s);
				} catch (Exception e) {

				}

			} else {
				String data = request.getParameter("data");
				if (data != null && !data.equals("")) {
					JSONObject jsonIN = new JSONObject(data);
					if (jsonIN != null)
						rst = jsonIN.optInt(paramname);
				} else {
					String bodyStr = handerRequestData(request);
					if (bodyStr != null && !bodyStr.equals("")) {
						JSONObject jsonIN = new JSONObject(bodyStr);
						if (jsonIN != null)
							rst = jsonIN.optInt(paramname);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;

	}

	/**
	 * 兼容解析参数，兼容普通parameter, data=,json in body输入参数
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2018-1-4
	 */
	public long parseLongParam(HttpServletRequest request, String paramname) {
		long rst = 0;
		String rst_s = "";

		try {

			rst_s = request.getParameter(paramname);
			if (rst_s != null && !rst_s.equals("")) {
				try {
					rst = Long.parseLong(rst_s);
				} catch (Exception e) {

				}

			} else {
				String data = request.getParameter("data");
				if (data != null && !data.equals("")) {
					JSONObject jsonIN = new JSONObject(data);
					if (jsonIN != null)
						rst = jsonIN.optLong(paramname);

				} else {
					String bodyStr = handerRequestData(request);
					if (bodyStr != null && !bodyStr.equals("")) {
						JSONObject jsonIN = new JSONObject(bodyStr);
						if (jsonIN != null)
							rst = jsonIN.optLong(paramname);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return rst;

	}
}
