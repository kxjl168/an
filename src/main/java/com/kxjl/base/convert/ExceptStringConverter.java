/**
 * @(#)ExceptStringConverter.java 2019/3/14 11:46
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.base.convert;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author 单肙
 * @version 1.0.0 2019/3/14 11:46
 * @since 1.0.0
 */
public class ExceptStringConverter extends MappingJackson2HttpMessageConverter {

    /**
     * 重写writeInternal方法，当输入类型是String时不进行处理
     * @param object
     * @param type
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        Class objectClass = object.getClass();
        if (!objectClass.equals(String.class)) {
            super.writeInternal(object, type, outputMessage);
        } else {
            String json = object.toString();
            Object parseObject = JSON.parse(json);

            if (parseObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) parseObject;
                super.writeInternal(jsonObject, JSONObject.class, outputMessage);
            } else if (parseObject instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) parseObject;
                super.writeInternal(jsonArray, JSONArray.class, outputMessage);
            } else {
                throw new IOException("找不到对应的类型！");
            }
        }
    }
}
