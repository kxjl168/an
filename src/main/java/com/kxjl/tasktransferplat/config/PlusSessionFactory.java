package com.kxjl.tasktransferplat.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * mybatis plus sessionfactory ,及扫描目录
 * PlusSessionFactory.java.
 * 
 * @author zj
* @version 1.0.1 2019年1月28日
* @revision zj 2019年1月28日
* @since 1.0.1
 */
//@Configuration
public class PlusSessionFactory {

	@Bean
	@Primary
	public SqlSessionFactory myplusSessionFactory(DataSource dataSource) throws Exception {

		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

		// mapperLocations
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		try {
			List<Resource> resources = new ArrayList<>();
			resources.addAll(Arrays.asList(resolver.getResources("classpath:mappers/plus/*.xml")));

			Resource[] rs = resources.toArray(new Resource[resources.size()]);

			sqlSessionFactoryBean.setMapperLocations(rs);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// dataSource

		sqlSessionFactoryBean.setDataSource(dataSource);

		// SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();

		return sqlSessionFactoryBean.getObject();

	}

	@Bean
	@Primary
	public MapperScannerConfigurer plusmapperScannerConfigurer() {
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		// 可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
		scannerConfigurer.setBasePackage("com.ztgm.*.dao.plus");
		scannerConfigurer.setSqlSessionFactoryBeanName("myplusSessionFactory");
		return scannerConfigurer;
	}
}
