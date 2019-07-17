package com.kxjl.base.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/12/11.
 */
public class PropertiesUtil {
    private static Properties p;
    public static void init() {
        try{
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(""
            		+ "project.properties");
            p = new Properties();
            p.load(in);
        }catch (Exception e){

        }
    }

    public static String getProperty(String key) {
        if(p == null) {
            init();
        }
        return p.getProperty(key,"");
    }

    public static String getProperty(String key, String defaultValue) {
        if(p == null) {
            init();
        }
        return p.getProperty(key, defaultValue);
    }
}
