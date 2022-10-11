package com.wen.gradua.service.impl;

import com.wen.gradua.config.rabbitmq.RabbitmqConfig;
import com.wen.gradua.dao.MailDao;
import com.wen.gradua.pojo.MailSend;
import com.wen.gradua.service.MailService;
import com.wen.gradua.utils.MailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class MailServiceimpl implements MailService {
    @Autowired
    MailDao mailDao;
    @Autowired
    RabbitmqConfig rabbitmqConfig;

    @Override
    public String saveMail(String email,String name) {
        // 获取六位随机数
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        //生成对象
        MailSend mailSend = new MailSend();
        mailSend.setMsgId(name);
        mailSend.setEmail(email);
        mailSend.setRandom(result);
        mailSend.setCreateTime(new Date());
        mailSend.setRoutekey(MailConstants.MAIL_ROUTING_KEY_NAME);
        mailSend.setStatus(MailConstants.DELIVERING); //投递中
        mailSend.setTryTime(new Date(System.currentTimeMillis()+1000*60*MailConstants.MSG_TIMEOUT));
        mailSend.setUpdateTime(new Date());
        mailSend.setCount(1);
        if (mailDao.existMail(name) == null){
            //保存到数据库
            mailDao.saveMail(mailSend);
        }else{
            //更新用户随机数
            mailDao.updateMailRandom(name,result);
        }
        //发送验证码
        rabbitmqConfig.sendMessage(mailSend);

        //返回随机数
        return result;

    }

    @Override
    public void saveMailRandom(String name,String email,String random) {
        MailSend mail = new MailSend();
        mail.setMsgId(name);
        mail.setEmail(email);
        mail.setRandom(random);
        System.out.println("id"+mailDao.existMail(name));
        //重新发送
        rabbitmqConfig.sendMessage(mail);
    }

    @Override
    public List<String> existRandomByEmail(String email) {
        return mailDao.existRandomByEmail(email);
    }

    @Override
    public void saveCount(String msgId, int i,Date tryTime) {
        mailDao.saveCount(msgId,i,tryTime);
    }
}
