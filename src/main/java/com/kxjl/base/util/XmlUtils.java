package com.kxjl.base.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

/**
 * mapper文件修改时自动刷新
 *
 * @project : tykj-common
 * @createTime : 2018年2月11日 : 下午10:54:25
 * @author : lukewei
 * @description :
 */
public class XmlUtils implements DisposableBean, InitializingBean, ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(XmlUtils.class);

	private SqlSessionFactory sqlSession;

	/**
	 *
	 * *Mapper.xml 文件路径
	 */
	private String basePackage = "classpath*:generator/generatorConfig.xml";
	// private String basePackage = "classpath*:**/*Mapper.xml";

	private HashMap<String, String> fileMapping = new HashMap<String, String>();

	private ScheduledExecutorService scheduledThreadPool;

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setSqlSession(SqlSessionFactory sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// this.context = (ConfigurableApplicationContext) applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		try {
			ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
			scheduledThreadPool.scheduleAtFixedRate(new Task(new Scanner()), 5, 15, TimeUnit.SECONDS);
		} catch (Exception e1) {
			logger.error("mapper文件修改刷新启动异常 : {}", e1);
		}
	}

	class Task implements Runnable {

		private Scanner scanner;

		public Task(Scanner scanner) {
			this.scanner = scanner;
		}

		@Override
		public void run() {
			try {
				List<Resource> resources = scanner.getChangedFiles();
				if (resources.size() > 0) {
					logger.debug("**********.xml文件改变,重新加载**************");
					scanner.reloadXML(resources);
					logger.debug("*****************加载完毕***********************");
				}
			} catch (Exception e) {
				logger.error("mapper文件修改重新加载失败: {}", e);
			}
		}

	}

	class Scanner {

		private String[] basePackages;

		public Scanner() {
			basePackages = StringUtils.tokenizeToStringArray(XmlUtils.this.basePackage,
					ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			try {
				scan();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 获取mapper.xml 资源
		 *
		 * @param basePackage
		 * @return
		 * @throws IOException
		 */
		public Resource[] getResource(String basePackage) throws IOException {
			return new PathMatchingResourcePatternResolver().getResources(basePackage);
		}

		/**
		 * 重新加载xml文件
		 *
		 * @param resources
		 * @throws Exception
		 */
		public void reloadXML(List<Resource> resources) throws Exception {

			Configuration configuration = sqlSession.getConfiguration();
			removeConfig(configuration);
			for (Resource resource : resources) {
				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(), configuration,
							resource.toString(), configuration.getSqlFragments());
					xmlMapperBuilder.parse();
				} catch (Exception e) {
					throw new NestedIOException("Failed to parse mapping resource: '" + resource + "'", e);
				} finally {
					ErrorContext.instance().reset();
				}
			}
		}

		/**
		 * 清除配置
		 *
		 * @param configuration
		 * @throws Exception
		 */
		private void removeConfig(Configuration configuration) throws Exception {

			clearValues(configuration, "mappedStatements");
			clearValues(configuration, "caches");
			clearValues(configuration, "resultMaps");
			clearValues(configuration, "parameterMaps");
			clearValues(configuration, "keyGenerators");
			clearValues(configuration, "sqlFragments");
			// 清理已加载的资源标识，方便让它重新加载。
			Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
			loadedResourcesField.setAccessible(true);
			((Set<?>) loadedResourcesField.get(configuration)).clear();
		}

		private void clearValues(Configuration configuration, String fieldName) throws Exception {

			Field field = configuration.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Map<?, ?> map = (Map<?, ?>) field.get(configuration);
			StrictMap<Object> newMap = new StrictMap<Object>();
			for (Object key : map.keySet()) {
				try {
					newMap.put((String) key, map.get(key));
				} catch (IllegalArgumentException ex) {
					newMap.put((String) key, ex.getMessage());
				}
			}
			field.set(configuration, newMap);
		}

		public void scan() throws IOException {

			if (!fileMapping.isEmpty()) {
				return;
			}
			for (String basePackage : basePackages) {
				Resource[] resources = getResource(basePackage);
				if (resources != null) {
					for (Resource resource : resources) {
						fileMapping.put(resource.getFilename(), getValue(resource));
					}
				}
			}
		}

		private String getValue(Resource resource) throws IOException {

			String contentLength = String.valueOf(resource.contentLength());
			String lastModified = String.valueOf(resource.lastModified());
			return new StringBuilder(contentLength).append(lastModified).toString();
		}

		public List<Resource> getChangedFiles() throws IOException {

			List<Resource> list = new ArrayList<Resource>();
			for (String basePackage : basePackages) {
				logger.debug("开始 刷新mybatis .xml 文件************************");
				Resource[] resources = getResource(basePackage);
				if (resources != null) {
					for (int i = 0; i < resources.length; i++) {
						String name = resources[i].getFilename();
						String value = fileMapping.get(name);
						String multi_key = getValue(resources[i]);
						if (!multi_key.equals(value)) {
							list.add(resources[i]);
							fileMapping.put(name, multi_key);
							logger.debug("{} 文件修改************************", name);
						}
					}
				}
			}
			logger.debug("mybatis .xml 文件刷新结束************************");
			return list;
		}
	}

	@Override
	public void destroy() throws Exception {

		if (scheduledThreadPool != null) {
			scheduledThreadPool.shutdownNow();
		}
	}

	/**
	 * 重写 org.apache.ibatis.session.Configuration.StrictMap 类 来自 MyBatis3.4.0版本，修改
	 * put 方法，允许反复 put更新。
	 */
	public static class StrictMap<V> extends HashMap<String, V> {

		private static final long serialVersionUID = -4950446264854982944L;

		@SuppressWarnings("unchecked")
		public V put(String key, V value) {
			remove(key);
			if (key.contains(".")) {
				final String shortKey = getShortName(key);
				if (super.get(shortKey) == null) {
					super.put(shortKey, value);
				} else {
					super.put(shortKey, (V) new Ambiguity(shortKey));
				}
			}
			return super.put(key, value);
		}

		private String getShortName(String key) {
			final String[] keyparts = key.split("\\.");
			return keyparts[keyparts.length - 1];
		}

		protected static class Ambiguity {

			private String subject;

			public Ambiguity(String subject) {
				this.subject = subject;
			}

			public String getSubject() {
				return subject;
			}
		}
	}
}
