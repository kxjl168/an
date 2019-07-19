package com.kxjl.base.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.kxjl.base.pojo.Manager;


import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrincipalFilter implements Filter {

	  private Locale locale;
	  
	
    public void init(FilterConfig filterConfig) throws ServletException {
        String localeStr = filterConfig.getInitParameter("locale");
        if (StringUtils.hasText(localeStr)) {
            locale = new Locale(localeStr);
        } else {
            locale = Locale.getDefault();
        }
        

		// autowire起作用
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            Map principal = (Map) subject.getPrincipal();
            
            
          
            servletRequest.setAttribute("principal",principal);
        }

    }

    @Override
    public void destroy() {

    }
}
