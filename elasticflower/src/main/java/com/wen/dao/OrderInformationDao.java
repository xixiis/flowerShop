package com.wen.dao;

import com.wen.pojo.OrderInformation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付订单信息表
 */
@Mapper
public interface OrderInformationDao {
    /**
     * 新增支付订单信息
     * @param orderInformation
     */
    void insertUserOrderInformation(OrderInformation orderInformation);

    OrderInformation findByOrderId(String order_id);

    void delByOrderId(String orderid);

    void updateOrderStatePaySuccess(String userid);
}
