package com.wen.gradua.service;

import com.wen.gradua.pojo.Business;

import java.util.List;

public interface BusinessService {
    /**
     * 查找用户的基本信息
     * @param user_id
     * @return
     */
    List<Business> findAllBusinessMessage(String user_id);

    /**
     * 新增商家
     * @param business
     * @return
     */
    Integer createStore(Business business);

    /**
     * 更新用户的信息
     * @param business
     */
    void updateStore(Business business);
}
