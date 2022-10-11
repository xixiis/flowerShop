package com.wen.dao;

import com.wen.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    /**
     * 根据用户id查询正在进行时的订单信息
     * @param userid
     * @return
     */
    Order findOrderContinueByUserId(@Param("userid") String userid);

    /**
     * 保存订单
     * @param order
     */
    void addOrder(Order order);

    /**
     * 清除订单列表，将del设为3为已删除
     * @param userid
     */
    void clearOrderById(String userid);

    /**
     * 更新订单
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 将del设为1表示已下单未签收状态
     * @param userid
     */
    void updateOrderStatePaySuccess(String userid);
    /**
     * 根据用户id查询已经下单时的订单信息
     * @param userid
     * @return
     */
    Order findOrderPaymentByUserId(String userid);

    List<Order> findOrderByUserId(String userid);

    List<Order> getALLOrder();
}
