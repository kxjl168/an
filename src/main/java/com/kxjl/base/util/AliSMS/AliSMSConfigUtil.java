package com.kxjl.base.util.AliSMS;


import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

@Component
public class AliSMSConfigUtil {

    public static String getProperty(String key) throws IOException {
        return getProperty(AliSMSInfo.PROPERTIES_DEFAULT, key);
    }

    public static Object setProperty(String propertyName, String propertyValue) throws URISyntaxException, IOException {
        return setProperty(AliSMSInfo.PROPERTIES_DEFAULT, propertyName, propertyValue);
    }

    public static void setProperties(Set<Map.Entry<String, Object>> data) throws IOException, URISyntaxException {
        setProperties(AliSMSInfo.PROPERTIES_DEFAULT, data);
    }

    // 读取Properties的全部信息
    public static Map<String, String> getAllProperties() throws IOException {
        Properties pps = new Properties();
        InputStream in = AliSMSConfigUtil.class.getClassLoader().getResourceAsStream(AliSMSInfo.PROPERTIES_DEFAULT);
        pps.load(in);
        in.close();
        // 得到配置文件的名字
        Enumeration<?> en = pps.propertyNames();
        Map<String, String> map = new HashMap<String, String>();
        while (en.hasMoreElements()) {
            String strKey = en.nextElement().toString();
            map.put(strKey, pps.getProperty(strKey));
        }
        return map;
    }

    public static String getProperty(String filePath, String key) throws IOException {
        Properties pps = new Properties();
        InputStream in = AliSMSConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
        pps.load(in);
        in.close();
        return pps.getProperty(key);
    }

    public static Object setProperty(String filePath, String propertyName, String propertyValue) throws URISyntaxException, IOException {
        Properties p = new Properties();
        InputStream in = AliSMSConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
        p.load(in);
        in.close();
        //设置属性值，如属性不存在新建
        Object o = p.setProperty(propertyName, propertyValue);
        //输出流
        OutputStream out = new FileOutputStream(new File(Objects.requireNonNull(AliSMSConfigUtil.class.getClassLoader().getResource(AliSMSInfo.PROPERTIES_DEFAULT)).toURI()));
        //设置属性头，如不想设置，请把后面一个用""替换掉
        p.store(out, "modify");
        //清空缓存，写入磁盘
        out.flush();
        //关闭输出流
        out.close();
        AliSMSInfo.initOrRefresh();
        return o;
    }

    public static void setProperties(String filePath, Set<Map.Entry<String, Object>> data) throws IOException, URISyntaxException {
        Properties p = new Properties();
        InputStream in = AliSMSConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
        p.load(in);
        in.close();
        //先遍历整个 people 对象
        for (Map.Entry<String, Object> entry : data) {
            //设置属性值，如属性不存在新建
            p.setProperty(entry.getKey(), entry.getValue().toString());
        }
        OutputStream out = new FileOutputStream(new File(Objects.requireNonNull(AliSMSConfigUtil.class.getClassLoader().getResource(AliSMSInfo.PROPERTIES_DEFAULT)).toURI()));
        //设置属性头，如不想设置，请把后面一个用""替换掉
        p.store(out, "modify");
        out.flush();//清空缓存，写入磁盘
        out.close();//关闭输出流
        //刷新缓存
        AliSMSInfo.initOrRefresh();
    }
}
