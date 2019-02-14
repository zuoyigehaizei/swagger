package com.example.demoswagger.config;

import com.example.demoswagger.interceptor.ActionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {



    //web层配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ActionInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }



}
