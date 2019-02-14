package com.example.demoswagger.client;

import com.example.demoswagger.rabbitconfig.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

//消息发送类
@Component
public class RabbitMQClient implements RabbitTemplate.ConfirmCallback{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    public RabbitMQClient(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
//    }

    //发送
    public void send(String message) {
//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, "topic.message", "abdlsjfkljfd");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, "topic.messages", "oooooooooo");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}
