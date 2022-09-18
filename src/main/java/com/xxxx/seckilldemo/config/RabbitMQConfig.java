package com.xxxx.seckilldemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * TODO
 *生产者消费者模型
 * @author Daiquiri
 * @version 1.0
 * @date 2022/2/17 15:55
 */
@Configuration
public class RabbitMQConfig {
    private static final String QUEUE = "seckillQueue";
    private static final String EXCHANGE = "seckillExchange";
    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding binding01(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
    }
}
