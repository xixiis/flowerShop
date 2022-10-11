package com.wen.controller;

import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;
import com.wen.pojo.Order;
import com.wen.pojo.OrderFlower;
import com.wen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    AlipayController alipayController;

    /**
     * 获取用户的订单信息
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping("/getOrderById/{userid}")
    public List<OrderFlower> getOrderById(@PathVariable("userid") String userid) {
        //判空
        if (userid.equals("") || userid == null) {
            return null;
        }
        List<OrderFlower> objects = new ArrayList<>();
        objects.add(orderService.getOrderById(userid));

        return objects;
    }
    /**
     * 获取用户的所有订单信息
     *
     * @param userid 用户id
     * @return
     */
    @RequestMapping("/getALLOrderById/{userid}")
    public List<OrderFlower> getALLOrderById(@PathVariable("userid") String userid) {
        //判空
        if (userid.equals("") || userid == null) {
            return null;
        }
        return orderService.getALLOrderById(userid);
    }

    /**
     * 获取用户的所有订单信息
     * @return
     */
    @RequestMapping("/getALLOrder")
    public List<Order> getALLOrder() {
        return orderService.getALLOrder();
    }

    /**
     * 获取用户已经付款的订单信息
     * @param userid 用户id
     * @return
     */
    @RequestMapping("/getOrderPaymentById/{userid}")
    public List<OrderFlower> getOrderPaymentById(@PathVariable("userid") String userid) {
        //判空
        if (userid.equals("") || userid == null) {
            return null;
        }
        List<OrderFlower> objects = new ArrayList<>();
        objects.add(orderService.getOrderPaymentById(userid));
        return objects;
    }


    /**
     * 删除订单
     */
    @RequestMapping("/clearOrderById/{userid}")
    public String clearOrderById(@PathVariable("userid") String userid) {
        System.out.println(userid);
        return orderService.clearOrderById(userid);
    }

    /**
     * 删除单个商品
     *
     * @param userid   用户id
     * @param flowerid 商品id
     * @return
     */
    @RequestMapping("/delOneById")
    public String delOneById(@RequestParam("userid") String userid,
                             @RequestParam("flowerid") String flowerid) {

        return orderService.delOneById(userid, flowerid);
    }

    /**
     * 保存订单并发起支付
     *
     * @param orderid 订单id
     * @param address 地址
     * @param tel     电话
     * @param time    时间
     * @param remarks 备注
     * @return
     */
    @RequestMapping("/holdOrder")
    public Map<String, Object> holdOrder(@RequestParam("orderid") String orderid,
                                         @RequestParam("userid") String userid,
                                         @RequestParam("address") String address, @RequestParam("tel") String tel,
                                         @RequestParam("time") String time, @RequestParam("remarks") String remarks) {
        System.out.println(orderid + userid + address + tel + time + remarks);
        if (orderid.equals("") || address.equals("") || tel.equals("") || time.equals("")) {
            return null;
        }
        //保存订单
        String result = orderService.holdOrder(orderid, userid, address, tel, time, remarks);
        if (result.equals("ok")) {
            try {
                //获取订单价格
                OrderFlower orderById = orderService.getOrderById(userid);
                //获取商品名称
                List<Flowercount> flowercount = orderById.getFlowercount();
                Iterator<Flowercount> iterator = flowercount.iterator();
                StringBuffer stringBuffer = new StringBuffer();
                while (iterator.hasNext()) {
                    Flowercount next = iterator.next();
                    Flower flower = next.getFlower();
                    Integer count = next.getCount();
                    stringBuffer.append("花名："+flower.getName() +"数量：" + count);
                }
                Integer i = orderById.getSum().intValue();
                Map<String, Object> map = new HashMap<>();
                map.put("sum", i);
                map.put("name", stringBuffer.toString());
                //支付宝支付
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 更新用户订单付款成功
     * @param userid
     */
    @RequestMapping("/updateOrderState/{userid}")
    public void updateOrderState(@PathVariable("userid") String userid){
        orderService.updateOrderState(userid);
    }

}
