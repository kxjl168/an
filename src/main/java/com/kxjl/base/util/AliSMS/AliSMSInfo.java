package com.kxjl.base.util.AliSMS;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

@Component
public class AliSMSInfo {


    /**
     * 类路径下的属性文件名
     */
    static final String PROPERTIES_DEFAULT = "sms.properties";
    //sms
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String signName;
    public static String smsCode;
    public static String messageCode;
    public static String messageNormal;
    public static String smsOrderCode;
    public static String smsOrderCode2;
    public static String smsPhone;
    

    static {
        initOrRefresh();
    }

    /**
     * 初始化或更新缓存
     */
    static void initOrRefresh() {
        Properties p = new Properties();
        Reader reader = null;
        try {
            reader = new InputStreamReader(AliSMSInfo.class.getClassLoader().getResourceAsStream(PROPERTIES_DEFAULT));
            p.load(reader);
            accessKeyId = p.getProperty("accessKeyId");
            accessKeySecret = p.getProperty("accessKeySecret");
            signName =  p.getProperty("signName");
            smsCode = p.getProperty("smsCode");
            messageCode = p.getProperty("messageCode");
            messageNormal = p.getProperty("messageNormal");
            smsOrderCode= p.getProperty("smsOrderCode");
            smsOrderCode2= p.getProperty("smsOrderCode2");
        	
            smsPhone=p.getProperty("smsPhone");
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=reader){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
