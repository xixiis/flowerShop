package com.wen.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wen.controller.AlipayController;
import com.wen.dao.*;
import com.wen.pojo.*;
import com.wen.service.OrderService;
import com.wen.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceimpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    ShopService shopService;
    @Autowired
    BusinessDao businessDao;
    @Autowired
    OrderInformationDao orderInformationDao;
    @Autowired
    AlipayController alipayController;


    /**
     * 对订单进行保存
     *
     * @param userid      用户id
     * @param flowercount 商品数量的json串
     * @return
     */
    @Override
    public String addOrder(String userid, String flowercount) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null) {
            //遍历map集合计算总金额
            BigDecimal sum = new BigDecimal(0);
            Map<String, Object> map = JSON.parseObject(flowercount);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                //遍历等到总价值
                Map.Entry<String, Object> next = iterator.next();
                BigDecimal priceById = flowerDao.getPriceById(Integer.parseInt(next.getKey()));//获取价格
                Integer value = Integer.parseInt(next.getValue().toString());//获取个数
                //个数乘以价格
                BigDecimal multiply = priceById.multiply(new BigDecimal(value));
                sum = sum.add(multiply);
                //同时删除购物车中的商品信息
                shopService.delShop(userid, next.getKey());
            }
            //生成订单id
            Calendar instance = Calendar.getInstance();
            String month = (instance.get(Calendar.MONTH) + 1) > 9 ? instance.get(Calendar.MONTH) + 1 + "" : "0" + instance.get(Calendar.MONTH) + 1;
            String day = instance.get(Calendar.DATE) > 9 ? instance.get(Calendar.DATE) + "" : "0" + instance.get(Calendar.DATE);
            long l = System.currentTimeMillis();
            String uuid = month+day+l;
            //存入数据
            orderDao.addOrder(new Order(null, uuid, userid, flowercount, sum, new Date(),null));
            return "ok";
        } else {
            return "usernotfound";//用户不存在
        }
    }

    @Override
    public OrderFlower getOrderById(String userid) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById == null) {
            return null;//用户不存在
        }
        //根据用户id获取正在进行时的订单信息
        Order order = orderDao.findOrderContinueByUserId(userid);
        if (order == null){
            return null;
        }
        return OrdertoOrderFlower(userid,userById,order);
    }
    @Override
    public OrderFlower getOrderPaymentById(String userid) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById == null) {
            return null;//用户不存在
        }
        //根据用户id获取已经下单的订单信息
        Order order = orderDao.findOrderPaymentByUserId(userid);
        if (order == null){
            return null;
        }
        return OrdertoOrderFlower(userid,userById,order);
    }


    public OrderFlower OrdertoOrderFlower(String userid,User userById,Order order){
        //将商品信息和数量取出
        String flowercount = order.getFlowercount();
        //原价总和
        BigDecimal oldprice =new BigDecimal(0);
//      原价计算过程
        StringBuffer stringBuffer=new StringBuffer();
        //定义数组存放商品信息和数量
        List<Flowercount> flowercounts=new ArrayList<>();
        Map<String,Object> map = JSON.parseObject(flowercount);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();//获取flowerid
            //根据鲜花id获取信息
            Flower flower = flowerDao.getFlowerById(Integer.parseInt(key));
            //仅保留第一张图片地址
            String img = flower.getImg();
            String[] split = img.split(",");
            flower.setImg(split[0]);
            //获取商品原价
            BigDecimal oldprice1 = flower.getOldprice();
            //获取商品数量
            Integer count = Integer.parseInt(next.getValue().toString());
            //计算原价
            oldprice = oldprice.add(oldprice1.multiply(new BigDecimal(count)));
            //计算原价
            stringBuffer.append(oldprice1).append("x").append(count).append("+");
            flowercounts.add(new Flowercount(flower,count));
        }
        if (stringBuffer.length()!=0){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }

        Date data=order.getCreate_time();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(data);
        OrderFlower orderFlower=new OrderFlower(userid,order.getOrder_id(),userById.getAddress(),userById.getTel(),stringBuffer.toString(),oldprice,order.getTotal_price(),format,flowercounts,order.getDel().toString());
        return orderFlower;
    }

    /**
     * 将当前订单删除
     * @param userid
     * @return
     */
    @Override
    public String clearOrderById(String userid) {
        if (userid.equals("") ||userid==null){
            return "warning";//为接收到信息
        }
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById == null) {
            return "usernotfound";//用户不存在
        }
        //更新表
        orderDao.clearOrderById(userid);
        return "ok";
    }

    @Override
    public String delOneById(String userid, String flowerid) {
        //判空
        if (userid.equals("") ||userid==null ||flowerid.equals("") || flowerid == null){
            return "warning";//为接收到信息
        }
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById == null) {
            return "usernotfound";//用户不存在
        }
        //找出现在正在结算的订单
        Order order = orderDao.findOrderContinueByUserId(userid);
        String flowercount = order.getFlowercount();
        //遍历map集合计算总金额
        BigDecimal sum = new BigDecimal(0);
        //迭代集合删除元素
        Map<String,Object> map = JSON.parseObject(flowercount);
        if (map.size() == 1){ //当结算界面只有一个订单时，直接删除订单
            clearOrderById(userid);
            return "tologin";
        }
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (next.getKey().equals(flowerid)){
                //放回购物车
                shopService.addShop(userid,flowerid,Integer.parseInt(next.getValue().toString()));
                //删除
                iterator.remove();
            }else {
                //重新计算价格
                BigDecimal priceById = flowerDao.getPriceById(Integer.parseInt(next.getKey()));//获取价格
                Integer value = Integer.parseInt(next.getValue().toString());//获取个数
                //个数乘以价格
                BigDecimal multiply = priceById.multiply(new BigDecimal(value));
                sum = sum.add(multiply);
            }
        }
        String s = JSON.toJSONString(map);
        order.setFlowercount(s);
        order.setTotal_price(sum);
        orderDao.updateOrder(order);
        return "ok";
    }

    @Override
    public String holdOrder(String orderid,String userid, String address, String tel, String time, String remarks) {
//        根据订单id查询订单信息
        Order order = orderDao.findOrderContinueByUserId(userid);
        //判空
        if (order == null||!order.getOrder_id().equals(orderid)){
            return "error";
        }
        //删除现有的订单
        if (orderInformationDao.findByOrderId(orderid)!=null){
            orderInformationDao.delByOrderId(orderid);
        }
        //保存订单
        orderInformationDao.insertUserOrderInformation(new OrderInformation(orderid,userid,address,tel,time,remarks,new Date(),0));
        return "ok";
    }

    @Override
    public void updateOrderState(String userid) {
        //        根据订单id查询订单信息
        Order order = orderDao.findOrderContinueByUserId(userid);
        //判空
        if (order == null){
            return;
        }
        //更新订单表
        orderDao.updateOrderStatePaySuccess(userid);
        //更新订单信息表
        orderInformationDao.updateOrderStatePaySuccess(userid);
        OrderInformation byOrderId = orderInformationDao.findByOrderId(order.getOrder_id());
        //更新售出商品表
        String flowercount = order.getFlowercount();
        Map<String,Object> map = JSON.parseObject(flowercount);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();//商品id
            Object value = next.getValue();//购买数量
            //更新用户的购买数量和库存管理
            Flower flowerById = flowerDao.getFlowerById(Integer.parseInt(key));
            Integer count = flowerById.getCount()- Integer.parseInt(value.toString());//库存减少
            //提醒商家发货去
//          根据商品id获取商家id
            String storeID = flowerById.getStore();
            if (!storeID.equals("自营")){
                //查询出待处理订单
                String padding = businessDao.findPaddingByID(storeID);
                if (!padding.equals("")){
                    Map<String,Object> maps = JSON.parseObject(padding);
                    String order_id = order.getOrder_id();//订单id
                    maps.put(order_id,new OrderBusiness(order_id,key,byOrderId.getAddress(),byOrderId.getTel(),Integer.parseInt(value.toString()),byOrderId.getRemarks(),byOrderId.getCreate_time()));
                    String s = JSON.toJSONString(maps);
                    businessDao.updatePadding(s,storeID);
                }


            }
            flowerDao.updateSellAdd(Integer.parseInt(key),count,Integer.parseInt(value.toString()));

        }
    }

    @Override
    public List<OrderFlower> getALLOrderById(String userid) {
        List<OrderFlower> orderFlowers =new ArrayList<>();
        //查询处理正在进行的订单之外的所有订单
        List<Order> orderByUserId = orderDao.findOrderByUserId(userid);
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById == null) {
            return null;//用户不存在
        }
        if (orderByUserId ==null){
            return null;
        }
        Iterator<Order> iterator = orderByUserId.iterator();
        while(iterator.hasNext()){
            Order next = iterator.next();
            OrderFlower orderFlower = OrdertoOrderFlower(userid, userById, next);
            orderFlowers.add(orderFlower);
        }
        return orderFlowers;
    }

    @Override
    public List<Order> getALLOrder() {
        return orderDao.getALLOrder();
    }


}
