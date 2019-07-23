package com.kxjl.video.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class SendPostRequest {

	private static Logger logger = Logger.getLogger(SendPostRequest.class);

	public static String sendHttpData(String url, String str) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);
   
		httpPost.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded;");
		httpPost.setRequestHeader("Connection", "close");
		// httpPost.setRequestHeader("Authorization", "Basic YWRtaW46MTIz");
		httpPost.setRequestBody(is);
		
		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			if (resStatusCode == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpPost.getResponseBodyAsStream(), "utf-8"));
				logger.info("HTTP Request CHARSET:"
						+ httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();
				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
			} else {
				logger.error("http请求失败 " + resStatusCode + ":"
						+ httpPost.getStatusText());
				exception = new Exception("[SerialHttpSender] HttpErrorCode:"
						+ resStatusCode);
			}
			if (exception != null) {
				throw exception;
			}
		} catch (java.net.ConnectException ex) {
			ex.printStackTrace();
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
			// org.apache.commons.httpclient.HttpRecoverableException:
			// java.net.SocketTimeoutException: Read timed out

			String message = ex.getMessage();
			if (message != null
					&& message.toLowerCase().indexOf("read timed") > -1) {
				throw new Exception(ex.getMessage());
			} else {
				ex.printStackTrace();
				throw ex;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			httpPost.releaseConnection();

		}

		logger.info("HTTP Request Result:" + responseData);
		return responseData;
	}

	/**
	 * 发起Get请求
	 * @param url
	 * @param charset
	 * @return String
	 * @throws Exception
	 */
	public static String sendGetData(String url)
			 {

		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		
		// 代理的主机
//		ProxyHost proxy = new ProxyHost("proxy.zte.com.cn", 80);
		// 使用代理
//		client.getHostConfiguration().setProxyHost(proxy);
		
		httpGet.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + "utf-8");
		String response = "";
		try {
			int statusCode = client.executeMethod(httpGet);
			if (statusCode == HttpStatus.SC_OK) {
				// 返回响应消息
				byte[] responseBody = httpGet.getResponseBodyAsString().getBytes(
						httpGet.getResponseCharSet());
				response = new String(responseBody, "utf-8");
				
			}else{
				logger.error("Method failed: " + httpGet.getStatusLine());
			}
			logger.info("------------------response:" + response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			// 释放连接
			httpGet.releaseConnection();
		}
		
		return response;
	}
	
	/**
	 * 发送get请求，请求的资源是互联网中的某一个url，取得请求的http接口返回所需要的值，非html字符串
	 * add by liudacheng at 2014/11/17
	 */
	       
	public static String sendGet(String url) {
	       String result = "";
	       BufferedReader in = null;
	       try {
	    	   //构造网络url
	         URL Url = new URL(url);
	          //打开和URL之间的连接
	         URLConnection conn = Url.openConnection();
	        //设置通用的请求属性
	         conn.setRequestProperty("accept", "*/*"); 
	         conn.setRequestProperty("connection", "close");  //mod by liudacheng at 2015/01/16 将请求头中的连接属性修改为close,避免服务器网络连接资源的占用和浪费
	         conn.setRequestProperty("user-agent", 
	                                                      "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
	        //建立实际的连接
	         conn.connect(); 
	      //定义BufferedReader输入流来读取URL的响应
	         in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         String res = null;
	         StringBuffer sb = new StringBuffer();
	         while ((res = in.readLine()) != null) {
		         sb.append(res);
	         }
	         result = sb.toString();
	         logger.info("result:" + sb.toString());
	       } catch(Exception e) {
	    	   logger.error("发送GET请求出现异常！" + e);
	          e.printStackTrace();
	        } finally {
	     //使用finally块来关闭输入流
	            try  {
	                 if (in != null) {
	                     in.close();
	                 }
	           } catch (IOException ex) {
	                   ex.printStackTrace();
	            } 
          }
	      return result;
    }

	/**
	 * 发送post请求，请求的资源是互联网中的某一个url，取得请求的http接口返回所需要的值，非html字符串
	 * add by liudacheng at 2015/01/16
	 */
	public static String sendPost(String url,String param) {
		 
		  PrintWriter out = null;
		  BufferedReader in = null;
	      String result = "";
	      
	       try {
	    	   //构造网络url
	         URL Url = new URL(url);
	          //打开和URL之间的连接
	         URLConnection conn = Url.openConnection();
	        //设置通用的请求属性
	         conn.setRequestProperty("accept", "*/*"); 
	         conn.setRequestProperty("connection", "close"); //mod by liudacheng at 2015/01/16 将请求头中的连接属性修改为close,避免服务器网络连接资源的占用和浪费
	         conn.setRequestProperty("user-agent", 
	                                                      "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
	         
	         //发送post请求必需设置如下两行
	         conn.setDoOutput(true);
	         conn.setDoInput(true);
	         // 获取URLConnection对象对应的输出流
	         out = new PrintWriter(conn.getOutputStream());
	        // 发送请求参数 
	         out.print(param);
	        // flush输出流的缓冲
	         out.flush();
	      //定义BufferedReader输入流来读取URL的响应
	         in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         String res = null;	        
	         while ((res = in.readLine()) != null) {
	        	 result += res;
	         }
	         logger.info("result:" +result);
	       } catch(Exception e) {
	    	   logger.error("发送POST请求出现异常！" + e);
	          e.printStackTrace();
	        } finally {
	     //使用finally块来关闭输入流
	            try  {
	            	if(out != null){
	            		out.close();
	            	}
	                if (in != null) {
	                     in.close();
	                 }
	           } catch (IOException ex) {
	                   ex.printStackTrace();
	            } 
       }
	      return result;
 }
	
}