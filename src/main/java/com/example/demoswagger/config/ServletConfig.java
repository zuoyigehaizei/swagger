package com.example.demoswagger.config;

import com.example.demoswagger.filterHandler.RequestFilter;
import com.example.demoswagger.interceptor.ActionInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class ServletConfig {

    //过滤器配置
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestFilter());
        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("sessionFilter");
        return registration;
    }

    @Bean
    public InterceptorRegistry interceptorRegistry() {
        InterceptorRegistry interceptorRegistry = new InterceptorRegistry();
        interceptorRegistry.addInterceptor(actionInterceptor());
        return interceptorRegistry;
    }

    @Bean
    public Filter requestFilter() {
        return new RequestFilter();
    }

    //拦截器配置
    @Bean
    public HandlerInterceptor actionInterceptor() {
        return new ActionInterceptor();
    }
}
