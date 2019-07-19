
package com.kxjl.video.util;


import javax.swing.text.html.parser.Entity;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/29 14:48
 * @since 1.0.0
 */
public class ParamUtil {

    /**
     * 检查入参是否为空
     * @param map
     */
    public static void checkArgsNull (Map<String, String> map) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry == null || entry.getValue() == null) {
                throw new RuntimeException("必传入参为空！");
            }
        }
    }

    /**
     * 检查入参是否为空
     * @param o
     */
    public static void checkArgsNull (Object o) {
        if (o == null) {
            throw new RuntimeException("必传入参为空！");
        }
        if (o instanceof List) {
            List list = (List) o;
            if (list.size() == 0) {
                throw new RuntimeException("所传list为空");
            }
        }
    }


}
