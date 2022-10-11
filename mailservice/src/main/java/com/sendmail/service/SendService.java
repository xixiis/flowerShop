package com.sendmail.service;

public interface SendService {
    /**
     * 发送验证码
     * @param email
     * @return返回生成的验证码
     */
    void SetHtmlEmail(String email,String result);

    void updateCount(String msgId, int i);
}
