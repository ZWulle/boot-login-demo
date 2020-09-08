package com.zwulle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import com.zwulle.entites.User;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;


/**
 * 消息消费者
 */
@Component

public class MsgConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SendEmail sendEmail;

    //    @Bean
//    public MappingJackson2MessageConverter mappingJackson2MessageConverter(){
//        return  new MappingJackson2MessageConverter();
//    }
    @RabbitListener(queuesToDeclare = @Queue("simple_queue"))
    public void msgConsumer(Message msg) throws Exception {
        //获取队列中的对象
        User user = (User) SerializationUtils.deserialize(msg.getBody());
        String message =
                "曾经有一份真诚的爱情放在我面前，我没有珍惜\n" +
                        "等到我失去的时候才后悔莫及 \n" +
                        "人世间最痛苦的事莫过于此……\n" +
                        "如果上天能够给我一个再来一次的机会\n" +
                        "我会对那个女孩子说三个字：\n" +
                        "我爱你\n" +
                        "如果非要在这份爱上加上一个期限 \n" +
                        "我希望是……一万年！\n " +
                        "姓名：" + user.getName() + "  邮箱：" + user.getEmail() + "  密码：" + user.getPassword() +
                        "\n" + "嘿嘿，不错！就是我咯！";
        //发送邮件
        sendEmail.sendEmail("1013034379@qq.com", user.getEmail(), message);
    }
}
