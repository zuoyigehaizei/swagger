package com.example.demoswagger;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DongTaiProxy {

    //动态代理
    @Test
    public void dongTaiProxy() {
        //invocationHandler业务逻辑
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //需要代理的具体实现类，代理的方法参数
                method.invoke(new UserserviceImpl(), args);
                return null;
            }
        };
        Class[] interfaces = {UserService.class};
        //生成动态代理对象(类加载器，代理类的业务逻辑，代理的接口)
        UserService o = (UserService)Proxy.newProxyInstance(DongTaiProxy.class.getClassLoader(), interfaces, h);
        System.out.println(o.getClass());
        //调用方法
        o.b();
    }
}

//接口
interface UserService {
    void a(int x,int y);
    void b();
    void c();
}

//实现类（被代理类）
class UserserviceImpl implements  UserService{
    public void a(int x,int y) {
        System.out.println("方法a");
    }

    public void b() {
        System.out.println("方法b");
    }

    public void c() {
        System.out.println("方法c");
    }
}
