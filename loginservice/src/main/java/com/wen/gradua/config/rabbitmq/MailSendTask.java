package com.wen.gradua.config.rabbitmq;

import com.wen.gradua.dao.MailDao;
import com.wen.gradua.pojo.MailSend;
import com.wen.gradua.service.MailService;
import com.wen.gradua.utils.MailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 定时任务处理错误信息
 */
@Component
public class MailSendTask {
    @Autowired
    MailDao mailDao;
    @Autowired
    MailService mailService;

    @Scheduled(cron = "0/10 * * * * ?") //十秒定时任务
    public void mailResendTask() {
        //查询有问题的信息
        List<MailSend> faultMsgs = mailDao.fondFaultMsg(MailConstants.FAILURE);
        faultMsgs.forEach(MailSend->{
            if (MailSend.getCount() > 3)
                //大于三次，没办法了
                System.out.println("这信息有毒！！！");
            //发送给自己信息
            //当重发的次数小于等于三次和举例上次重发超过一分钟
            if (MailSend.getCount() <= 3 && new Date().getTime() >= MailSend.getTryTime().getTime() ){
                //重新将存储的随机值发送，不多生成随机值
                mailService.saveMailRandom(MailSend.getMsgId(),MailSend.getEmail(),MailSend.getRandom());
                //重试次数加一,并且将尝试时间置于一分钟以后
                mailService.saveCount(MailSend.getMsgId(),MailSend.getCount()+1,new Date(System.currentTimeMillis()+1000*60*MailConstants.MSG_TIMEOUT));
            }
        });

    }
//    @Scheduled(cron = "0/10 * * * * ?") //十秒定时任务
////    @Scheduled(cron = "* * 1 * * *") //一天定时任务
//    public void mailTimeOverTask(){
//        System.out.println(new Date().getTime());
//    }

}
