package com.example.demoswagger.controller;

import com.example.demoswagger.controller.annotatiion.MyAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

//定义aop切面
@Aspect
@Component
public class AopAspect {

    @Pointcut("execution(* com.example.demoswagger.controller.AspectController.*(..)) && @annotation(com.example.demoswagger.controller.annotatiion.MyAnnotation)")
    public void annotionTest() {
    }

    //环绕
//    @Around(value = "execution(* com.example.demoswagger.controller.AspectController.*(..))")
    @Around("annotionTest()")
    public Object huanraoAop(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("aop方法执行");
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        //获取方法上注解
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        System.out.println("方法规则拦截" + method.getName());
        //aop拦截放行
        return pjp.proceed();
    }
}
