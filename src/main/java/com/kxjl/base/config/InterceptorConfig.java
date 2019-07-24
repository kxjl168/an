package com.kxjl.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kxjl.base.interceptor.InterfaceTokenInterceptor;
import com.kxjl.base.interceptor.PrincipalInterceptor;



@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public PrincipalInterceptor getSecurityInterceptor() {
        return new PrincipalInterceptor();
    }
    
    @Bean
    public InterfaceTokenInterceptor getInterfaaceInterceptor() {
        return new InterfaceTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.addPathPatterns("/**");
        // 拦截配置
        addInterceptor.addPathPatterns("/manager/**");
        addInterceptor.addPathPatterns("/generator/**");
        //需要使用PrincipalFilter的spring权限管理的url
        addInterceptor.addPathPatterns("/privilege/**");
        
        

        
        registry.addInterceptor(getInterfaaceInterceptor())
        .addPathPatterns("/interface/**")
        .addPathPatterns("/api/**")
        .addPathPatterns("/api/*/*")
        .addPathPatterns("/interface/*/*/*");

    }
}