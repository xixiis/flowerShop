package com.wen.service;

import com.wen.pojo.Order;
import com.wen.pojo.OrderFlower;

import java.util.List;

public interface OrderService {
    /**
     * 添加订单
     *
     * @param userid
     * @param s
     * @return
     */
    String addOrder(String userid, String s);

    /**
     * 根据用户id获取订单信息
     * @param userid
     * @return
     */
    OrderFlower getOrderById(String userid);

    OrderFlower getOrderPaymentById(String userid);

    /**
     * 清除订单信息
     * @param userid
     * @return
     */
    String clearOrderById(String userid);

    /**
     * 删除一个订单信息
     * @param userid
     * @param flowerid
     * @return
     */
    String delOneById(String userid, String flowerid);

    /**
     * 保存订单
     * @param orderid
     * @param address
     * @param tel
     * @param time
     * @param remarks
     * @return
     */
    String holdOrder(String orderid,String userid,  String address, String tel, String time, String remarks);

    /**
     * 更新订单状态
     * @param userid
     */
    void updateOrderState(String userid);

    /**
     * 获取处理正在下单的其它订单
     * @param userid
     * @return
     */
    List<OrderFlower> getALLOrderById(String userid);

    List<Order> getALLOrder();
}
