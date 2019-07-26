package com.kxjl;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import java.util.TimeZone;

import javax.servlet.ServletRegistration;

import org.apache.catalina.Context;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kxjl.base.websocket.ClientConnectSvr;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true) 
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableAsync        //开启异步任务
@EnableTransactionManagement// 启注解事务管理
@MapperScan("com.kxjl.*.dao")//将项目中对应的mapper类的路径加进来就可以了
@PropertySource("classpath:project.properties")
@EnableScheduling
//@ServletComponentScan("com.kxjl.base.websocket")
public class GAVIDEOApplication {


    public static void main(String[] args) {
    	
    	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    	
        SpringApplication.run(GAVIDEOApplication.class, args);
        System.out.println("===========启动完成！=============");
    }
    

    
    @Value("${httpPort}")
    private Integer httpPort;

    @Value("${server.port}")
    private Integer httpsPort;

    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }

   // 放开 未https
    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
              SecurityCollection collection = new SecurityCollection();
               collection.addPattern("/*");
                securityConstraint.addCollection(collection);
              //  context.addConstraint(securityConstraint);
            }
        };
       
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

   @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(httpPort);
     //  connector.setSecure(false);//同时启用httP, https 不跳转
    //   connector.setRedirectPort(httpsPort);

        return connector;
    }

}
