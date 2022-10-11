package com.wen.dao;


import com.wen.pojo.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;

@Mapper
public interface BusinessDao {
    /**
     * 查找用户的基本信息
     * @param user_id
     * @return
     */
    public Business findAllBusinessMessage(String user_id);

    /**
     * 更新用户的预处理信息
     * @param padding
     */
    void updatePadding(@Param("padding") String padding,@Param("id") String store);

    /**
     * 取出用户的预处理信息
     * @param id
     */
    String findPaddingByUserID(String id);

    /**
     *
     * @param id
     * @return
     */
    String findPaddingByID(String id);

    /**
     * 获取已完成的订单
     * @param id
     * @return
     */
    String findSuccessByUserID(String id);
}
