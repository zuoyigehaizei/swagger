package com.example.demoswagger.controller;

import com.example.demoswagger.client.RabbitMQClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqController {

    @Autowired
    private RabbitMQClient client;

    //程序启动发送消息
    @RequestMapping("/abcde")
    public void rabbitTest() {
        for (int i = 0; i < 100; i++) {
            client.send(" mengxiangkai , ---------send" + i);
        }
    }
}
