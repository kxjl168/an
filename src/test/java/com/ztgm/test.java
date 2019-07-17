
package com.ztgm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kxjl.base.base.SysConst;
import com.kxjl.base.util.aes.AesHelper;
import com.kxjl.base.util.sendpost.FormFieldKeyValuePair;
import com.kxjl.base.util.sendpost.HttpPostEmulator;
import com.kxjl.base.util.sendpost.UploadFileItem;

import javax.websocket.*;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
public class test {

	public static void main(String[] args) throws Exception {

		 //testLogin();
//		test1();
		//UserInfo();

		//UserInfo();

		 //MessageList();
		 
		 //MessageRead();
		// testupdatepw();
		
		//----------------------------
		
		
		// testOrder();

		// testWuliu();

		// testCommet();

		// testExchange();

		//
		// testexchange2();

		// testExchangeList();

		// testExchangeListDetail();

		// testOrderUpdate();

		// testuphead();

		// testheaderpic();

		// testupdateUserInfo();

		// testMobileIndex();

		// testMobileAddressList();

		// testMobileAddressSaveOrUpdate();

		// testMobileAddressLoad();

		// testMobileAddressDelete();

		// testBanner();

		// testHotgoods();

		// testsearch();

		// testLevels();

		// testLevelDetail();

		 //testGdDetail();

		// testvalidateCode();
		// testRegister();



		// testvalidateCode();

		// testforgetpw();

		// testLogout();

		// testattentionlist();

		// testBrowerlist();

		// testDelBrowerlist();

		// testQuestionlist();

		//testCommitlist();

		// 上传通用图片
		// testupImg();
		
		//base64();

//		testSetLockPass();
		//testWebSocket();
		
		
		testOrderLockImg();
	}
	
	public static void testOrderLockImg()
	{
		testCleanPic("20190625133925536","1");
		
		String orderNo="20190625133925536";
		
		testOrderLockUploadImg("1",orderNo,"1","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		testOrderLockUploadImg("1",orderNo,"1","C:\\Users\\admin\\Pictures\\lock\\ceq-5-nosupport.jpg");
		testOrderLockUploadImg("1",orderNo,"2","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		testOrderLockUploadImg("1",orderNo,"2","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		testOrderLockUploadImg("1",orderNo,"3","C:\\Users\\admin\\Pictures\\lock\\liulu-1-nosupport.jpg");
		testOrderLockUploadImg("1",orderNo,"3","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		
		testCleanPic("20190625133925536","2");
		testOrderLockUploadImg("2",orderNo,"1","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		testOrderLockUploadImg("2",orderNo,"1","C:\\Users\\admin\\Pictures\\lock\\liulu-6-nosupport.jpg");
		testOrderLockUploadImg("2",orderNo,"2","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		testOrderLockUploadImg("2",orderNo,"2","C:\\Users\\admin\\Pictures\\lock\\ceq-5-nosupport.jpg");
		testOrderLockUploadImg("2",orderNo,"3","C:\\Users\\admin\\Pictures\\lock\\liulu-1-nosupport.jpg");
		testOrderLockUploadImg("2",orderNo,"3","C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
	}
	
	public static void testOrderLockUploadImg(String index,String orderNo,String imgType,String pic) {

		String serverUrl = "http://127.0.0.1:9999/ttfpzj/interface/commonModule/uploadDoneImg";
		ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

		FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("lockIndex", index);
		fds.add(p1);
		FormFieldKeyValuePair p2 = new FormFieldKeyValuePair("orderNo", orderNo);
		fds.add(p2);
		FormFieldKeyValuePair p3 = new FormFieldKeyValuePair("imgType", imgType);
		fds.add(p3);
		
		FormFieldKeyValuePair p4 = new FormFieldKeyValuePair("k", "9868f06e-3354-48b7-b902-91be36ba9998");
		fds.add(p4);
		

		ArrayList<UploadFileItem> filesToBeUploaded = new ArrayList<>();
		UploadFileItem file = new UploadFileItem("imgFiles",pic);// "C:\\Users\\admin\\Pictures\\lock\\cdd-6.jpg");
		filesToBeUploaded.add(file);

		try {
			String responsedata = HttpPostEmulator.sendHttpPostRequest(serverUrl, fds, filesToBeUploaded);
			System.out.println(responsedata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	public static void testCleanPic(String orderNo,String index) {

		String serverUrl = "http://127.0.0.1:9999/ttfpzj/interface/commonModule/resetDoneImg";


		//String serverUrl = "http://ztgmwl.com:7501/account-web-oauth/user/createUser";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("lockIndex", index);
			jobj.put("imei", "1");
			jobj.put("orderNo", orderNo);//"20190625133925536");

			//System.out.println(AesHelper.decrypt("922D235FC89E833DB3A1BABB49F7039B009503506B04650C0D7CF53E413AEA679C2F401A7CE4E9F078253A710E5692BB30F5200CAB01AB24741E35B3ECB56AA17FEE20AFAA7FBAB14F0EE1230BA8AAD5B6E6A432EEB8A39F8973C326AA8D9DCF070CEF639FDF608101B3159E7D8C63115590BA54735F8A12A81B14E499E4E46D48444BA4B26DBB3D0173302182B25B34C9291E021C9B363F3C1B15CAC434E053", AesHelper.aesPass));


			String commetdata = jobj.toString();

			String data2="";
			for (String key : jobj.keySet()) {
				data2+=key+"="+jobj.optString(key)+"&";
			}
			if(!data2.equals(""))
			data2=data2.substring(0,data2.length()-1);
			


			responsedata=sendHttpData("9868f06e-3354-48b7-b902-91be36ba9998",serverUrl, data2);

			//responsedata = sendHttpJSONData("fdbe463c-3e69-4a94-8416-f798ee7eae1e",serverUrl, "imei=861931044982621&orderId=12312313");

			//String rdata = AesHelper.decrypt(responsedata, AesHelper.aesPass);

			System.out.println("返回:" + responsedata);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * 统一认证用户接口
	 *
	 * @author zj
	 * @date 2019年4月24日
	 */
	public static void testSetLockPass() {

		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/commonModule/setPass";


		//String serverUrl = "http://ztgmwl.com:7501/account-web-oauth/user/createUser";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("imei", "861931044982621");
			jobj.put("orderId", "12312313");

			System.out.println(AesHelper.decrypt("922D235FC89E833DB3A1BABB49F7039B009503506B04650C0D7CF53E413AEA679C2F401A7CE4E9F078253A710E5692BB30F5200CAB01AB24741E35B3ECB56AA17FEE20AFAA7FBAB14F0EE1230BA8AAD5B6E6A432EEB8A39F8973C326AA8D9DCF070CEF639FDF608101B3159E7D8C63115590BA54735F8A12A81B14E499E4E46D48444BA4B26DBB3D0173302182B25B34C9291E021C9B363F3C1B15CAC434E053", AesHelper.aesPass));


			String commetdata = jobj.toString();

			//String data = AesHelper.encrypt(commetdata, AesHelper.aesPass);


			responsedata=sendHttpData("fdbe463c-3e69-4a94-8416-f798ee7eae1e",serverUrl, "imei=861931044982621&orderId=547358902887383040");

			//responsedata = sendHttpJSONData("fdbe463c-3e69-4a94-8416-f798ee7eae1e",serverUrl, "imei=861931044982621&orderId=12312313");

			//String rdata = AesHelper.decrypt(responsedata, AesHelper.aesPass);

			System.out.println("返回:" + responsedata);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static String sendHttpJSONData(String token,String url, String str) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);

		 httpPost.setRequestHeader("Content-type", "application/json");
		//httpPost.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

		httpPost.setRequestHeader("Accept", "application/json");
		httpPost.setRequestHeader("Connection", "close");
		 httpPost.setRequestHeader("Token",token);
		httpPost.setRequestBody(is);

		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			if (resStatusCode == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpPost.getResponseBodyAsStream(), "utf-8"));
				logger.info("HTTP Request CHARSET:" + httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();
				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
			} else {
				logger.error("http请求失败 " + resStatusCode + ":" + httpPost.getStatusText());
				exception = new Exception("[SerialHttpSender] HttpErrorCode:" + resStatusCode);
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
			if (message != null && message.toLowerCase().indexOf("read timed") > -1) {
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
	
//	private static String token="3e3ed329-4d09-4511-b3b0-9ed74e65998b";
	private static String token="ebfe5bc1-138e-4557-992a-88f3dba93277";

	
	private static void UserInfo() {
		String driverid = "1";
		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/userLocksmith/userInfo";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			jobj.put("pageSize", 4);
			jobj.put("pageNum", 4);

			String commetdata = jobj.toString();

			responsedata = sendHttpData(token, serverUrl,"data="+ commetdata);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("code") == 100) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void DirverMessageDel() {

		String driverid = "1";
		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/message/message/deleteMessage/1";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			// jobj.put("hasRead",1);

			String commetdata = jobj.toString();

			responsedata = sendHttpData(token, serverUrl, commetdata);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("code") == 100) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void MessageRead() {
		String driverid = "1";
		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/message/read";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			 jobj.put("messagId","1901291501");

			String commetdata = jobj.toString();

			responsedata = sendHttpData(token, serverUrl, "data="+commetdata);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("code") == 100) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void MessageList() {
		String driverid = "1";
		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/message/list";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			jobj.put("pageSize", 4);
			jobj.put("pageNum", 4);

			String commetdata = jobj.toString();

			responsedata = sendHttpData(token, serverUrl,"data="+ commetdata);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("code") == 100) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static  void base64() {
		  // 对图像进行base64编码
      String imgBase64 = "";
      try {
          File file = new File("F:\\ztgm\\0009-ocr\\test\\input\\name.jpg");
          byte[] content = new byte[(int) file.length()];
          FileInputStream finputstream = new FileInputStream(file);
          finputstream.read(content);
          finputstream.close();
          imgBase64 = new String(encodeBase64(content));
          System.out.println(imgBase64);
      } catch (IOException e) {
          e.printStackTrace();
          return;
      }
	}

	public static Object testi() {

		try {
			JSONArray ja = new JSONArray();

			JSONArray all = new JSONArray();

			JSONObject d = new JSONObject();
			d.put("id", "111");
			d.put("val", "aaa");

			// 全部选项组合
			for (int j = 0; j < all.length(); j++) {

				// 当前组合是否满足
				boolean iscurInstance = false;
				
				int num=0;//符合的选项数
				// 选择的选项组合
				for (int i = 0; i < ja.length(); i++) {

					// 选项
					JSONObject selectAttr = ja.optJSONObject(i);

					// 当前选项是否满足
					boolean iscurattr = false;

					// 具体此组合中的所有选项
					for (int j2 = 0; j2 < all.optJSONObject(j).optJSONArray("cols").length(); j2++) {

						// 具体选项
						JSONObject curAttr = all.optJSONObject(j).optJSONArray("cols").optJSONObject(j2);

						if (selectAttr.optString("id").equals(curAttr.optString("id"))
								&& selectAttr.optString("val").equals(curAttr.optString("val"))) {
							iscurattr = true;
							break;
						}
					}
					
					
					//当前选择不满足，跳出，下一组合实例
					if(!iscurattr)
					{
						break;
					}
					else
						num++;//选项数符合
				}
				
				
				//检查符合的选项数，跟选择的选择数是否相同
				if(num==ja.length())
				{
					iscurInstance=true;
				
					return all.opt(j);
				}
				

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;

	}

	public static void testQuestionlist() {

		String serverUrl = "http://127.0.0.1:8889/interface/index/questionlist";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testCommitlist() {

		String serverUrl = "http://127.0.0.1:8888/interface/index/commetlist?id=449098ff2fbd4f939ad7be434d0cbd71";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testBrowerlist() {

		String serverUrl = "http://127.0.0.1:8888/interface/brower/list?Token=111";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDelBrowerlist() {

		String serverUrl = "http://127.0.0.1:8888/interface/brower/delete?Token=111&id=57a24235aa1be5f267bfc48c50882339&all=1";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testattentionlist() {

		String serverUrl = "http://127.0.0.1:8888/interface/attention/attentionlist?Token=111";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-登陆
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testvalidateCode() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/validateCode";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "phone=13815429446");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-忘记密码-重设新密码
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testforgetpw() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/forgetpw";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "username=1&password=111&validCode=928302");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-修改密码
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testupdatepw() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/updatepw?Token=111";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "oldPassword=1&newPassword=222");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-登陆
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testLogin() {

		String serverUrl = "http://127.0.0.1:9999/ttfp/interface/loginLocksmith/login";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("phone", "13815429446");
			jobj.put("pwd", "123456");
			jobj.put("type", "1");

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "data="+commetdata);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-登出
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testLogout() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/logout?Token=111";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-地址列表
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testRegister() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/register";
		String validCode = "928302";
		String nickname = "前台用户-李逵";

		String password = "1";

		String phone = "13815429446";

		String data = "nickname=" + nickname + "&phone=" + phone + "&password=" + password + "&validCode=" + validCode;

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, data);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-地址列表
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testMobileAddressList() {

		String serverUrl = "http://127.0.0.1:8888/interface/address/list?Token=111&offset=0&pageSize=10";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-详情
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testMobileAddressLoad() {

		String serverUrl = "http://127.0.0.1:8888/interface/address/load?Token=111";
		// "isDefault\":1,\"address\":\"dddddd\",\"receiver\":\"xxxx\",\"phone\":\"111111\",\"id\":\"1\",\"userId\":\"1\"

		String data = "id=d7b68c48874f46518e8d0dc32d48b7bb";
		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, data);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-详情
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testMobileAddressDelete() {

		String serverUrl = "http://127.0.0.1:8888/interface/address/delete?Token=111";
		// "isDefault\":1,\"address\":\"dddddd\",\"receiver\":\"xxxx\",\"phone\":\"111111\",\"id\":\"1\",\"userId\":\"1\"

		String data = "id=1";
		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, data);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-地址新增/更新
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testMobileAddressSaveOrUpdate() {

		String serverUrl = "http://127.0.0.1:8888/interface/address/saveOrUpdate?Token=40681e93-a734-4349-b820-d712a0c9f7f2";
		// "isDefault\":1,\"address\":\"dddddd\",\"receiver\":\"xxxx\",\"phone\":\"111111\",\"id\":\"1\",\"userId\":\"1\"

		String data = "id=230b899591184abdbae6a5d90a85c370&receiver=啊啊啊&phone=123123123&address=南京市中山北路21903号2楼&isDefault=1&userId=1";
		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, data);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void test1() {

		System.out.println("==========test1=================");
		String serverUrl = "http://localhost:9999/ttfp/interface/commonModule/setPass";
//		String serverUrl = "http://localhost:9999/ttfp/interface/commonModule/room";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

//			jobj.put("buildingId", "021f798f7e3d43a8961399df9580d38a");
//			jobj.put("communityId", "0b84fedb2acf4d2cb7651eae1c0dd412");
//			jobj.put("pageSize", "5");
//			jobj.put("pageNum", "1");
			jobj.put("roomId", "1_1_0");
			jobj.put("imei", "869976030155734");

			String commetdata = jobj.toString();

			responsedata = sendHttpData(token, serverUrl,"data="+ commetdata);


			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void testBanner() {

		System.out.println("==========testBanner=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/bannerlist";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-热门商品
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testHotgoods() {

		System.out.println("==========testHotgoods=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/hotgoods";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-搜索
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testsearch() {
		System.out.println("==========testsearch=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/search";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "keyword=灯");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-一级分类
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testLevels() {
		System.out.println("==========testLevels=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/levels";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动接口-二三级分类
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testLevelDetail() {
		System.out.println("==========testLevelDetail=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/level_detail";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "id=1");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 商品详情
	 * 
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testGdDetail() {
		System.out.println("==========testGdDetail=================");
		String serverUrl = "http://127.0.0.1:8888/interface/index/gd_detail?id=33021f890dac485e8581d1b68a770008";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testMobileIndex() {

		String serverUrl = "http://127.0.0.1:8888/interface/index/topClassify";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			String commetdata = jobj.toString();

			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testupdateUserInfo() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/updateUserInfo?Token=111";

		String responsedata = "";
		try {
			JSONObject jobj = new JSONObject();

			jobj.put("nickname", "namename~~");
			jobj.put("email", "123@123.com.cn");

			String commetdata = jobj.toString();

			String d1 = "data=" + commetdata;

			responsedata = sendHttpData(serverUrl, d1);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testuphead() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/updHeaderPic?Token=111";
		ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

		FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("path", "testformPara");
		fds.add(p1);

		ArrayList<UploadFileItem> filesToBeUploaded = new ArrayList<>();
		UploadFileItem file = new UploadFileItem("picFile", "F:\\IMG\\5ac1ee0fN8bde63b2.jpg");
		filesToBeUploaded.add(file);

		try {
			String responsedata = HttpPostEmulator.sendHttpPostRequest(serverUrl, fds, filesToBeUploaded);
			System.out.println(responsedata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void testupImg() {

		String serverUrl = "http://127.0.0.1:8888/upload/UploadFileXhr";
		ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

		FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("path", "testformPara");
		fds.add(p1);

		ArrayList<UploadFileItem> filesToBeUploaded = new ArrayList<>();
		UploadFileItem file = new UploadFileItem("fileUploadURL", "F:\\IMG\\5ac1ee0fN8bde63b2.jpg");
		filesToBeUploaded.add(file);

		try {
			String responsedata = HttpPostEmulator.sendHttpPostRequest(serverUrl, fds, filesToBeUploaded);
			System.out.println(responsedata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void testheaderpic() {

		String serverUrl = "http://127.0.0.1:8888/interface/user/headerpic?Token=111";

		String responsedata = "";
		try {
			responsedata = sendHttpData(serverUrl, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				System.out.println(j.optString("data"));
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testexchange2() throws JSONException {

		String url = "http://127.0.0.1:8888/interface/exchange/exchange2";
		// http://127.0.0.1:8888/interface/order/commet?data=[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"odid\":\"6\",\"star\":\"3\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"isanony\":\"1\",\"oid\":\"201801017\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\"}]
		String commetdata = "{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"oid\":\"201801017\",\"odid\":\"6\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\",\"star\":\"3\",\"isanony\":\"1\"}";

		JSONObject jobj = new JSONObject();

		jobj.put("id", "73874fad756d47979c0c19357526c04b"); // 退换货id
		jobj.put("order_detail_id", "1"); // 订单详细id
		jobj.put("remark", "已发货，物流号：11112312");

		commetdata = jobj.toString();

		System.out.println(commetdata);

		String d1 = "data=" + commetdata;
		String data = "?Token=111";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, d1);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				// JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(j.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	public static void testExchange() throws JSONException {

		String url = "http://127.0.0.1:8888/interface/exchange/exchange";
		// http://127.0.0.1:8888/interface/order/commet?data=[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"odid\":\"6\",\"star\":\"3\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"isanony\":\"1\",\"oid\":\"201801017\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\"}]
		String commetdata = "{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"oid\":\"201801017\",\"odid\":\"6\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\",\"star\":\"3\",\"isanony\":\"1\"}";

		JSONObject jobj = new JSONObject();

		jobj.put("order_detail_id", "1");
		jobj.put("remark", "产品说明不符合");
		jobj.put("type", "1");

		commetdata = jobj.toString();

		System.out.println(commetdata);

		String d1 = "data=" + commetdata;
		String data = "?Token=111";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, d1);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(jdata.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	/**
	 * 订单确认收货/删除
	 * 
	 * @throws JSONException
	 * @author zj
	 * @date 2018年6月27日
	 */
	public static void testOrderUpdate() throws JSONException {

		String url = "http://127.0.0.1:8888/interface/order/update";
		// http://127.0.0.1:8888/interface/order/commet?data=[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"odid\":\"6\",\"star\":\"3\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"isanony\":\"1\",\"oid\":\"201801017\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\"}]
		String commetdata = "[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"oid\":\"201801017\",\"odid\":\"6\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\",\"star\":\"3\",\"isanony\":\"1\"}]";

		// JSONArray ja=new JSONArray(commetdata);

		String d1 = "id=201801013&state=7";
		String data = "?Token=111";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, d1);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(jdata.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @throws JSONException
	 * @date 2017-11-14
	 */

	public static void testCommet() throws JSONException {

		String url = "http://127.0.0.1:8888/interface/order/commet";
		// http://127.0.0.1:8888/interface/order/commet?data=[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"odid\":\"6\",\"star\":\"3\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"isanony\":\"1\",\"oid\":\"201801017\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\"}]
		String commetdata = "[{\"gid\":\"c8cb4aece819436899f4dd1eb7fa1733\",\"iid\":\"360dcd1d3e2043978933dcc0ee58df8d\",\"oid\":\"201801017\",\"odid\":\"6\",\"value\":\"%E9%9D%9E%E5%B8%B8%E5%A5%BD%E7%9A%84%E4%BA%A7%E5%93%81%EF%BC%81%EF%BC%81\",\"star\":\"3\",\"isanony\":\"1\"}]";

		// JSONArray ja=new JSONArray(commetdata);

		String d1 = "data=" + commetdata;
		String data = "?Token=111";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, d1);

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(jdata.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	public static void testExchangeList() {

		String url = "http://127.0.0.1:8888/interface/exchange/list";

		String json = "{\"userid\": \"t1\",\"pass\": \"123321\"}";

		String data = "?Token=111&pagenum=1";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				// JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(j.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	public static void testExchangeListDetail() {

		String url = "http://127.0.0.1:8888/interface/exchange/listDetail";

		String json = "{\"userid\": \"t1\",\"pass\": \"123321\"}";

		String data = "?Token=111&pagenum=1&id=73874fad756d47979c0c19357526c04b";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				// JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(j.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	/**
	 * 
	 * @param map
	 * @return
	 * @author zj
	 * @date 2017-11-14
	 */

	public static void testOrder() {

		String url = "http://127.0.0.1:8888/interface/order/showOrders";

		String json = "{\"userid\": \"t1\",\"pass\": \"123321\"}";

		String data = "?Token=111&state=5&pagenum=1";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				// JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(j.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	public static void testWuliu() {

		String url = "http://127.0.0.1:8888/interface/order/wuliuinfo";

		String json = "{\"userid\": \"t1\",\"pass\": \"123321\"}";

		String data = "?Token=111&id=201801013";

		String responsedata = "";
		try {
			responsedata = sendHttpData(url + data, "");

			JSONObject j = new JSONObject(responsedata);
			if (j.optInt("errCode") ==1) {
				// JSONObject jdata = new JSONObject(j.optString("data"));
				System.out.println(j.toString());
			}

			System.out.println("返回:" + responsedata);
			// System.out.println("解密:" + out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(responsedata);

	}

	private static Logger logger = Logger.getLogger(test.class);


	public static String sendHttpData(String url, String str) throws Exception {
		return sendHttpData("", url, str);
	}

	public static String sendHttpData(String auth, String url, String str) throws Exception {

		logger.info("HTTP Request URL:" + url + ",HTTP Request PARAM:" + str);
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("10.41.70.8", 80);
		// client.getParams().setAuthenticationPreemptive(true);

		PostMethod httpPost = new PostMethod(url);
		InputStream is = new java.io.ByteArrayInputStream(str.getBytes("utf-8"));
		client.setTimeout(60000);

		//httpPost.setRequestHeader("Content-type", "application/json");
		 httpPost.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

		httpPost.setRequestHeader("Accept", "application/json");
		httpPost.setRequestHeader("Connection", "close");
		httpPost.setRequestHeader(SysConst.AUTHORIZATION, auth);

		// httpPost.setRequestHeader("Authorization", "Basic YWRtaW46MTIz");
		httpPost.setRequestBody(is);

		String responseData = null;
		try {
			Exception exception = null;
			client.executeMethod(httpPost);
			int resStatusCode = httpPost.getStatusCode();
			if (resStatusCode == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpPost.getResponseBodyAsStream(), "utf-8"));
				logger.info("HTTP Request CHARSET:" + httpPost.getResponseCharSet());
				String res = null;
				StringBuffer sb = new StringBuffer();
				while ((res = br.readLine()) != null) {
					sb.append(res);
				}
				responseData = sb.toString();
			} else {
				logger.error("http请求失败 " + resStatusCode + ":" + httpPost.getStatusText());
				exception = new Exception("[SerialHttpSender] HttpErrorCode:" + resStatusCode);
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
			if (message != null && message.toLowerCase().indexOf("read timed") > -1) {
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

	public static void testWebSocket() {
		WebSocketContainer container = null;
		try {
			container = ContainerProvider.getWebSocketContainer();
		} catch (Exception ex) {
			System.out.println("error" + ex);
		}
		try {
			URI uri = URI.create("ws://192.168.100.121:9999/ttfp/websocket/af133baafb1740b5868b54718629d8a4");
			Session session = container.connectToServer(WebsocketClient.class, uri);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = "";
			try {
				do {
					input = br.readLine();
					if (!input.equals("exit"))
						session.getBasicRemote().sendText("javaclient"+input);
				} while (!input.equals("exit"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (DeploymentException | IOException e) {
			e.printStackTrace();
		}
	}

}
