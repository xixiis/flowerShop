package com.sendmail.utils;

import com.alibaba.fastjson.JSON;
import com.sendmail.pojo.MailSend;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 建立死信队列，并声明mail队列绑定关系
 */
@Component
public class mailQueryUtils {
    @Bean
    public FanoutExchange deadfanoutExchange(){
        return new FanoutExchange("dead_fanout_exchange",true,false);
    }

    @Bean
    public Queue queue(){
        return new Queue("dead.order.queue",true);
    }
    @Bean
    public Binding bindingDead(){
        return BindingBuilder.bind(queue()).to(deadfanoutExchange());
    }

    //  声明交换机
    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME,true,false);
    }
    //声明队列
    @Bean
    public Queue emailQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","dead_fanout_exchange");
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true,false,true,map);
    }
    //绑定关系
    @Bean
    Binding mailBinding(){
        return BindingBuilder.bind(emailQueue()).to(mailExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
