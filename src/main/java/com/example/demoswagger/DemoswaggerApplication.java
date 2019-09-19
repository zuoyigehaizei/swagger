package com.example.demoswagger;

import com.github.pagehelper.PageHelper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

//@ServletComponentScan
@SpringBootApplication   //相当于 3个注解
//@EnableAutoConfiguration
public class DemoswaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoswaggerApplication.class, args);
    }

    //pageHelper分页配置
//    @Bean
//    public PageHelper getPageHelper(){
//        PageHelper pageHelper=new PageHelper();
//        Properties properties=new Properties();
//        properties.setProperty("helperDialect","mysql");
//        properties.setProperty("reasonable","true");
//        properties.setProperty("supportMethodsArguments","true");
//        properties.setProperty("params","count=countSql");
//        pageHelper.setProperties(properties);
//        return pageHelper;
//    }

}

