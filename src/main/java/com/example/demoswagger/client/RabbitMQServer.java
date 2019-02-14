package com.example.demoswagger.client;

import com.example.demoswagger.rabbitconfig.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//接收消息端
@Component
@RabbitListener(queues = "QUEUE_A")
public class RabbitMQServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //监听队列获取消息
    @RabbitHandler
    public void receive(String message) {
        logger.info("接收处理队列A当中的消息： " + message);
    }
}

