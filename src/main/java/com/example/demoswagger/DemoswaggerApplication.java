package com.example.demoswagger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

//@ServletComponentScan
@SpringBootApplication   //相当于 3个注解
//@EnableAutoConfiguration
public class DemoswaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoswaggerApplication.class, args);
    }

}

