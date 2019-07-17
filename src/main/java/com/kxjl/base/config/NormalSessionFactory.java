package com.kxjl.base.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 普通 mybatis sessionfactory 及扫描目录 NormalSessionFactory.java.
 * 
 * @author zj
 * @version 1.0.1 2019年1月28日
 * @revision zj 2019年1月28日
 * @since 1.0.1
 */
//@Configuration
public class NormalSessionFactory {

	@Bean
	public SqlSessionFactoryBean normalSqlSessionFactory(DataSource dataSource) throws Exception {

		org.mybatis.spring.SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		// mapperLocations
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		try {

			List<Resource> resources = new ArrayList<>();
			resources.addAll(Arrays.asList(resolver.getResources("classpath:mappers/base/*.xml")));
			resources.addAll(Arrays.asList(resolver.getResources("classpath:mappers/*.xml")));

			Resource[] rs = resources.toArray(new Resource[resources.size()]);

			sqlSessionFactoryBean.setMapperLocations(rs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// dataSource

		sqlSessionFactoryBean.setDataSource(dataSource);

		// SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();

		return sqlSessionFactoryBean;

	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		// 可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
		scannerConfigurer.setBasePackage("com.ztgm.base.dao,com.ztgm.*.dao.normal");
		scannerConfigurer.setSqlSessionFactoryBeanName("normalSqlSessionFactory");
		return scannerConfigurer;
	}
}
