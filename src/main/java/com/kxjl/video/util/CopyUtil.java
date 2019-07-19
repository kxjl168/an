
package com.kxjl.video.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 * @author 单肙
 * @version 1.0.0 2019/1/29 11:19
 * @since 1.0.0
 */
public class CopyUtil {

    private static final Logger logger = LoggerFactory.getLogger(CopyUtil.class);

    /**
     * 浅拷贝，同名属性拷贝
     * @param src
     * @param target
     * @param <T>
     * @param <V>
     */
    public static <T, V> void copySameName(T src, V target) {
        //获取class对象
        Class srcClass = src.getClass();
        Class targetClass = target.getClass();

        //获得类属性
        Field[] srcFields = srcClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        //设置可达性
        AccessibleObject.setAccessible(srcFields, true);
        AccessibleObject.setAccessible(targetFields, true);

        //进行拷贝
        for (Field srcField : srcFields) {
            String srcFieldName = srcField.getName();
            for (Field targetField : targetFields) {
                String targetFieldName = targetField.getName();
                if (srcFieldName.equals(targetFieldName)) {
                    try {
                        targetField.set(target, srcField.get(src));
                    } catch (Exception e) {
                        logger.error(srcClass.getName() + "." + srcFieldName + "与"
                                + targetClass.getName() + "." + targetFieldName + "参数同名但类型不同，拷贝失败！", e);
                    }
                }
            }
        }
    }
}
