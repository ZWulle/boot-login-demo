package com.zwulle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwulle.entites.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class MsgProduce {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void msgProduce(User u) throws Exception {
        //向队列发送消息
        rabbitTemplate.convertAndSend("simple_queue", u);

    }
}
