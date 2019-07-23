package com.kxjl.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.kxjl.base.convert.ExceptStringConverter;
import com.kxjl.base.filter.FreemarkerFilter;
import com.kxjl.base.filter.JspFilter;
import com.kxjl.base.filter.WebSiteMeshFilter;
import com.kxjl.base.websocket.ClientConnectSvr;
import com.kxjl.base.websocket.testServlert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Configuration
@PropertySource("classpath:project.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	
	
	
	/**
	 * 解决long类型js经度丢失的问题
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//定义Json转换器
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
				new ExceptStringConverter();
		//定义对象映射器
		ObjectMapper objectMapper = new ObjectMapper();
		//定义对象模型
		SimpleModule simpleModule = new SimpleModule();
		//添加对长整型的转换关系
		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		//将对象模型添加至对象映射器
		objectMapper.registerModule(simpleModule);
		//将对象映射器添加至Json转换器
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);

		//在转换器列表中添加自定义的Json转换器
		converters.add(jackson2HttpMessageConverter);
		//添加utf-8的默认String转换器
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}


	/**
	 * Freemarker 过滤器
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean freemarkerFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();

		FreemarkerFilter freemarkerFilter = new FreemarkerFilter();
		filter.setFilter(freemarkerFilter);
		filter.setEnabled(true);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("*.ftl");
		filter.setUrlPatterns(arrayList);
		return filter;
	}
	


/*	@Bean
	public ViewResolver getJspViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/templates");
		internalResourceViewResolver.setViewNames("*.jsp");
		//internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	@Bean
	public FreeMarkerViewResolver getFreeMarkerViewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setPrefix("/templates");
		//freeMarkerViewResolver.setSuffix(".html");
		freeMarkerViewResolver.setViewNames("*.ftl");
		freeMarkerViewResolver.setRequestContextAttribute("request");
		freeMarkerViewResolver.setOrder(0);
		freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");

		return freeMarkerViewResolver;
	}*/

/*	@Bean
	public FilterRegistrationBean jspFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();

		JspFilter jspFilter = new JspFilter();
		filter.setFilter(jspFilter);
		filter.setEnabled(true);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("*.jsp");
		filter.setUrlPatterns(arrayList);
		return filter;
	}*/

	/**
	 * 装饰器
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();

		WebSiteMeshFilter siteMeshFilter = new WebSiteMeshFilter();
		filter.setFilter(siteMeshFilter);
		filter.setEnabled(true);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("/*");
		filter.setUrlPatterns(arrayList);
		
		
		return filter;
	}

	@Value("${pool.corePoolSize}")
	private Integer corePoolSize;

	@Value("${pool.maxPoolSize}")
	private Integer maxPoolSize;

	@Value("${pool.queueCapacity}")
	private Integer queueCapacity;

	@Value("${pool.keepAliveSeconds}")
	private Integer keepAliveSeconds;

	@Bean(name = "Async")
	public Executor customAsync() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
		// 设置最大线程数
		threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
		// 设置队列容量
		threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
		// 设置线程活跃时间（秒）
		threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		// 设置默认线程名称
		threadPoolTaskExecutor.setThreadNamePrefix("Async-");
		threadPoolTaskExecutor.afterPropertiesSet();
		return threadPoolTaskExecutor;
	}

}
