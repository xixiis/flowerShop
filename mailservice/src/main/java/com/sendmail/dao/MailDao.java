package com.sendmail.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MailDao {
    /**
     * 更新用户重发次数
     * @param msgId 用户账号
     * @param i 已重试次数
     */
    void updateCount(@Param("msgid") String msgId,@Param("count") int i);
}
