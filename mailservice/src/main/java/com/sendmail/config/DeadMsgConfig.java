package com.sendmail.config;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sendmail.pojo.MailSend;
import com.sendmail.service.SendService;
import com.sendmail.utils.MailConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理死信队列信息
 */
@Component
public class DeadMsgConfig {

    @Autowired
    SendService sendService;


    @RabbitListener(queues = {"dead.order.queue"})
    public void deadMsg(String ordermsg, Channel channel,
                        CorrelationData correlationData,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try{
            //拿到信息
            MailSend mailSend = JSON.parseObject(ordermsg, MailSend.class);
            if (mailSend.getCount() > 3){
//                发送次数过多
                return;
            }
            //重新发送邮件
            sendService.SetHtmlEmail(mailSend.getEmail(),mailSend.getRandom());
            sendService.updateCount(mailSend.getMsgId(),mailSend.getCount()+1);
            channel.basicAck(tag,true);
        }catch (Exception e){
            //交给程序员处理
            System.out.println("报错了....");
        }
    }
}
