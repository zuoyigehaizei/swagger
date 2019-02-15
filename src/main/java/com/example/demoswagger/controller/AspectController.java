package com.example.demoswagger.controller;

import com.example.demoswagger.controller.annotatiion.MyAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AspectController {

    @GetMapping
    //自定义注解被修饰的才能被aop拦截
    @MyAnnotation
    public String aopTest01() {
        System.out.println("我是aop执行了");
        return "aop";
    }

//    @MyAnnotation
    @GetMapping("/aaaaa")
    public void apiAnnotion() {
        System.out.println("自定义注解aop执行了");
    }
}
