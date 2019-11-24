package com.kxjl.base.util.sendpost;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;


/**
 * HttpClient实现get请求
 */
@Component
public class HttpSendGet {
	

    private static final Log log = LogFactory.getLog(HttpSendGet.class);

    /**
     * HTTP HEAD CONTENT TYPE
     */
    private final static String CONTENT_TYPE = "application/json;charset=utf-8";

    public static SendPostResponse doGet(String url) throws Exception {

        SendPostResponse wzResponse = new SendPostResponse();


        
        
        
        
        org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(url);
        
       // httpGet.addHeader("Cookie","__jsluid_h=84afde715fdbc92649e2752812322a03; __jsl_clearance=1571311752.313|0|ncJptyiSa2K2pwxKIZ2CKnr23dg%3D");
       // httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
       // httpGet.addHeader("Referer","http://www.gsxt.gov.cn/%7B7E9CABC416BD0A16E58A6CC73461885A35480BF8532E7B182F84563944FCE28D6B16432017BCFFE3EE3C202D6E88CB6034DB8B744055F62134649344514A9600BD3ABD3ABD2B9611AA2DAA2DAA2DAA2DAA2DAA2D0186567EC5784B32DB63BB897A2FC69076A45711AA17AC2B29C0DED39566FE1CEF3DA049575A581B070A4934DD2E68D354D354D354-1571311349322%7D");
        //httpGet.addHeader("Accept-Encoding","gzip, deflate");
        //httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
       
        
        httpGet.addHeader("Cookie", "__cfduid=dec8c480a104a1731c2f7ea36e3951ec91571826177; _ga=GA1.2.630177117.1571826181; _gid=GA1.2.851512316.1571826181; adwin=adwin; _gat_gtag_UA_37874049_5=1; __atuvc=2%7C43; __atuvs=5db02a049f05f4a1001");
		
//        httpGet.setEntity(new StringEntity(json,"utf-8"));
        HttpClient httpClient = HttpClientManager.getHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        wzResponse.setStatusCode(statusCode);
        //请求成功获取返回数据
        if (statusCode == 200) {
            HttpEntity reqHttpEntity = httpResponse.getEntity();
            wzResponse.setData(EntityUtils.toString(reqHttpEntity, "UTF-8"));
            EntityUtils.consume(reqHttpEntity);
        }
        httpGet.releaseConnection();
        httpGet.abort();
        return wzResponse;
    }

}
