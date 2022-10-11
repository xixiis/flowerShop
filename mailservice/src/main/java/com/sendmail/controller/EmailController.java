package com.sendmail.controller;

import com.alibaba.druid.support.json.JSONUtils;
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
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class EmailController {
    @Autowired
    SendService sendService;

    @RabbitListener(queues = {MailConstants.MAIL_QUEUE_NAME})
    public void messageconsumer(String ordermsg, Channel channel,
                                CorrelationData correlationData,
                                @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            //收到短信
            MailSend mailSend = JSON.parseObject(ordermsg, MailSend.class);
//            System.out.println("mail的内容是"+mailSend.getEmail());
//            System.out.println("mail的随机数是"+mailSend.getRandom());
            //发送邮件
            sendService.SetHtmlEmail(mailSend.getEmail(),mailSend.getRandom());
            channel.basicAck(tag,true);
        } catch (IOException e) {
            //如果出现异常的情况下,根据实际的情况去进行重发
            //重发一次后，丢失，还是日记，存库根据自己的业务场景去决定
            //参数1:消息的tag 参数2: false 多条处理参数3: requeue 重发
            // false 不会重发，会把消息打入到死信队列
            // true 的会会死循环的重发，建议如果使用true的话，不要 加try/catch否则就会造成死循环
            channel.basicNack(tag,true,false);
        }
    }
}


