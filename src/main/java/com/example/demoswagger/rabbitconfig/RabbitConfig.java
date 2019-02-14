package com.example.demoswagger.rabbitconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";

    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory  = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }

    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange(EXCHANGE_B);
    }

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with("topic.message");
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with("topic.#");
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        //加载处理消息A的队列
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        //设置接收多个队列里面的消息，这里设置接收队列A
//        //假如想一个消费者处理多个队列里面的信息可以如下设置：
//        //container.setQueues(queueA(),queueB(),queueC());
//        container.setQueues(queueA());
//        container.setExposeListenerChannel(true);
//        //设置最大的并发的消费者数量
//        container.setMaxConcurrentConsumers(10);
//        //最小的并发消费者的数量
//        container.setConcurrentConsumers(1);
//        //设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//                 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
//                channel.basicQos(1);
//                byte[] body = message.getBody();
//                logger.info("接收处理队列A当中的消息:" + new String(body));
//                /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//        return container;
//    }


}
