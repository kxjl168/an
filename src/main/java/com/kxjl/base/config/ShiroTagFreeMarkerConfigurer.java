package com.kxjl.base.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;

/**
 * ftl模板 ${ctx} 前缀变量注入 FreemarkerConfiguration.java.
 * 
 * @author zj
 * @version 1.0.1 2018年12月20日
 * @revision zj 2018年12月20日
 * @since 1.0.1
 */

@Component
public class ShiroTagFreeMarkerConfigurer implements InitializingBean {

	@Autowired
	private Configuration configuration;

	@Autowired
	private FreeMarkerViewResolver resolver;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 加上这句后，可以在页面上使用shiro标签
		configuration.setSharedVariable("shiro", new ShiroTags());
		// 加上这句后，可以在页面上用${context.contextPath}获取contextPath
		resolver.setRequestContextAttribute("context");
	}
}
