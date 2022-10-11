package com.sendmail.service.impl;

import com.sendmail.dao.MailDao;
import com.sendmail.pojo.EmailModel;
import com.sendmail.service.SendService;
import com.sendmail.utils.MailSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
@Service
public class SendServiceimpl implements SendService {
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private MailSendUtils mailSendUtils;
    @Autowired
    private MailDao mailDao;
    /**
     * 用户端调用发送验证码的方法
     * @param email
     */
    public void SetHtmlEmail(String email,String result) {
        //定义返回值
        String myreturn="";
        //创建新线程发送信息
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    //将随机数发送到用户邮箱
                    EmailModel emailModel=new EmailModel();
                    emailModel.setEmailTheme("309不懂科技");//邮箱主体
                    emailModel.setRecieverName("");//收件人姓名
                    emailModel.setEmailContent("您的验证码内容是"+ result);//邮件内容
                    emailModel.setRecieverEmailAddress(email);//邮箱地址
                    // 发送信息
                    mailSendUtils.sendEmailAsSysExceptionHtml(emailModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("邮箱发送失败.");
                }
            }
        };
//        Callable callable=new Callable() {
//            @Override
//            public Object call() throws Exception {
//
//                return result;
//            }
//        };
        //将线程放入到线程池中
        taskExecutor.submit(runnable);
//        try {
//            //将验证码放入到返回值中
//            myreturn=(String)submit.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return myreturn;
    }

    @Override
    public void updateCount(String msgId, int i) {
        mailDao.updateCount(msgId,i);
    }
}
