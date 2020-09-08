package com.zwulle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件发送服务类
 */
@RestController
public class SendEmail {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * @param msgFrom 发送方
     * @param msgTo   接收方
     * @param msg     邮件内容
     * @return 字符串
     */
    public String sendEmail(String msgFrom, String msgTo, String msg) {
//        String msgFrom,String msgTo
        //从队列中获取消息

        //实例一个邮箱实体类
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        //发送者
        mailMsg.setFrom(msgFrom);
        //接收者
        mailMsg.setTo(msgTo);
        //邮件标题
        mailMsg.setSubject("注册案例-测试邮件-包雪双");
        //邮件内容
        mailMsg.setText(msg);
        //发送
        try {
            javaMailSender.send(mailMsg);
            return "success";
        } catch (Exception e) {
//            System.out.println("错误");
            e.printStackTrace();
            return "fail";
        }
    }
}
