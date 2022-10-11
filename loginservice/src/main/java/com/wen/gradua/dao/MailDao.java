package com.wen.gradua.dao;

import com.wen.gradua.pojo.MailSend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.Date;
import java.util.List;

@Mapper
public interface MailDao {
     /**
      * 插入数据
      * @param mail
      */
     void saveMail(MailSend mail);

     /**
      * 更新状态
      * @param msgid
      */
     void updateMailStatus(@Param("msgid") String msgid,@Param("status") Integer status);

     /**
      * 查看用户是否存在
      * @param name
      * @return
      */
     Integer existMail(@Param("msgid") String name);

     /**
      * 通过邮箱查看用户随机数是否存在
      * @param email
      * @return
      */
     List<String> existRandomByEmail(@Param("email") String email);

     /**
      * 更新用户随机数
      * @param name
      * @param result
      */
     void updateMailRandom(@Param("msgid") String name,@Param("random") String result);

     /**
      * 每十秒进行表扫描，发现有问题的进行重发，发现时间长的进行删除
      * @return
      */
     List<MailSend> fondFaultMsg(@Param("status") Integer status);

     /**
      * count赋值，重试次数
      * @param msgId
      * @param i
      */
     void saveCount(@Param("msgid") String msgId,@Param("count") int i,@Param("tryTime") Date tryTime);
}
