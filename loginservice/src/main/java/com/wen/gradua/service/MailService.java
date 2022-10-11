package com.wen.gradua.service;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MailService {
     String saveMail(String email,String name);

     void saveMailRandom(String email,String name,String random);

    /**
     * 通过邮箱查看用户随机数是否存在
     * @param email
     * @return
     */
    List<String> existRandomByEmail(@Param("email") String email);

    /**
     * 设置count为i
     * @param msgId
     * @param i
     */
    void saveCount(String msgId, int i, Date tryTime);
}
