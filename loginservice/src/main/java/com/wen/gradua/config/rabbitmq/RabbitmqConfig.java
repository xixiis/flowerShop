package com.wen.gradua.config.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.wen.gradua.dao.MailDao;
import com.wen.gradua.pojo.MailSend;
import com.wen.gradua.utils.MailConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class RabbitmqConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MailDao mailDao;
    //@ PostConstruct注解好多人以为是spring提供的。其实是Java自己的注解。
    //Java中该注解的说明: @PostConstruct该注解被用来修饰一 个非静态的void () 方法。
    // 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
    //并且只会被服务器执行一次。PostConstruct在构造函数之后执行， init () 方法之前执行。
    @PostConstruct
    public void regCallback(){
        //到中间件失败
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                String msgid = correlationData.getId();
//              ack为true表示收到信息
                if (!ack){
                    System.out.println("MQ队列应答失败...");
                    return;
                }
                try{
                    //更新数据表，status
                    mailDao.updateMailStatus(msgid,MailConstants.SUCCESS); //1表示投送成功
                }catch (Exception e){
                    mailDao.updateMailStatus(msgid,MailConstants.FAILURE); //2表示投送失败
                }
            }

        });
        //到路由key失败
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingkey)->{
            System.out.println("投送失败");
        });
    }

    //    发送信息
    public void sendMessage(MailSend mail) {
        rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME, JSON.toJSONString(mail),
                new CorrelationData(mail.getMsgId()));
    }
}
