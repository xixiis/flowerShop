package com.wen.gradua.dao;

import com.wen.gradua.pojo.Business;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessDao {
    /**
     * 查找用户的基本信息
     * @param user_id
     * @return
     */
    public Business findAllBusinessMessage(String user_id);

    /**
     * 插入到商家表中
     * @param business
     */
    void createStore(Business business);

    void updateStore(Business business);
}
