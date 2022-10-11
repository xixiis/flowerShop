package com.wen.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wen.dao.FlowerDao;
import com.wen.dao.OrderDao;
import com.wen.dao.ShopDao;
import com.wen.dao.UserDao;
import com.wen.pojo.*;
import com.wen.service.OrderService;
import com.wen.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopServiceimpl implements ShopService {
    @Autowired
    UserDao userDao;
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderService orderService;
    @Override
    public String addShop(String userid, String flowerid, Integer count) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null){
            //查询鲜花id是否存在
            Flower flowerById = flowerDao.getFlowerById(Integer.valueOf(flowerid));
            if (flowerById != null){
                //将用户鲜花插入到shop表中
                Shop shopById = shopDao.findShopById(userid);//从购物车表中获取
                if (shopById == null){
                    //新用户第一次添加商品
                    Map<String,Integer> map =new HashMap<>();
                    map.put(flowerid,count);
                    String flower_count = JSON.toJSONString(map);//转换为json类型
                    shopDao.addShop(new Shop(null,userid,flower_count,new Date(),new Date(),null));//添加到数据库
                }else{
                    //在数据库中已经存在此列
                    String flowercount = shopById.getFlowercount();//这里是json类型，需要转换为map在添加
                    Map map = JSON.parseObject(flowercount);
                    if (map.containsKey(flowerid)){//如果已经有这个了，就增加
                        Integer i = (Integer)map.get(flowerid);
                        count = count +i;
                    }
                    System.out.println(flowerid+"/"+count);
                    map.put(flowerid,count);
                    String flower_count = JSON.toJSONString(map);//转换为json类型
                    shopDao.updateShop(new Shop(null,userid,flower_count,new Date(),null,null));//添加到数据库
                }
                return "ok";//正确返回
            }else{
                return "warning";//鲜花商品不存在
            }
        }else{
            return "usernotfound";//用户不存在
        }
    }

    /**
     * 获取所有的购物车鲜花
     * @param userid
     * @return
     */
    @Override
    public List<Flowercount> getAllShop(String userid,Integer start,Integer count) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null){
            List<Flowercount> list =new ArrayList<>();
            Shop shopById = shopDao.findShopById(userid);
            String flowercount = shopById.getFlowercount();
            //这里是json类型，需要转换为map
            Map map = JSON.parseObject(flowercount);
            //循环添加到list中
            Set<Map.Entry<String,Integer>> set = map.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Integer> next = iterator.next();
                String flowerid = next.getKey();//获取flowerid
//              根据id查询内容
                Flower flower = flowerDao.getFlowerById(Integer.valueOf(flowerid));
                //取第一个图像作为主图
                String[] split = flower.getImg().split(",");
                flower.setImg(split[0]);
                //获取数量
                Integer counts = next.getValue();
                //添加到列表中
                list.add(new Flowercount(flower,counts));
            }
            //对list进行分页
            List<Flowercount> pageList = getPageList(list, start, count);
            return pageList;
        }else{
            return null;//用户不存在
        }
    }

    @Override
    public String delShop(String userid, String flowerid) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null) {
            //从数据库中获取这个人的购物车信息
            Shop shopById = shopDao.findShopById(userid);
            //取出flowercount这一列
            String flowercount = shopById.getFlowercount();
            Map<String,Object> map =JSON.parseObject(flowercount);
            //包含这一列的话进行迭代器删除
            if (map.containsKey(flowerid)){
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, Object> next = iterator.next();
                    if (next.getKey().equals(flowerid)){
                        iterator.remove();
                    }
                }
                String flower_count = JSON.toJSONString(map);
                shopDao.updateShop(new Shop(null,userid,flower_count,new Date(),null,null));
                return "ok";
            }else{
                return "warning";
            }

        }else{
            return "usernotfound";//用户不存在
        }

    }

    @Override
    public Integer shoppingCount(String userid) {
        System.out.println(userid);
        //从数据库中获取这个人的购物车信息
        Shop shopById = shopDao.findShopById(userid);
        String flowercount = shopById.getFlowercount();
        JSONObject jsonObject = JSON.parseObject(flowercount);
        return jsonObject.size();
    }

    @Override
    public String shopAddOrReduce(String userid, String flowerid, String addReduce) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null) {
            //从数据库中获取这个人的购物车信息
            Shop shopById = shopDao.findShopById(userid);
            String flowercount = shopById.getFlowercount();
            Map<String,Object> map = JSON.parseObject(flowercount);
            //如果这个商品存在
            if (map.containsKey(flowerid)){
                Integer count = Integer.parseInt(map.get(flowerid).toString());
                if (addReduce.equals("+")){
                    count = count +1;
                }else if (addReduce.equals("-")){
                    if (count > 1){
                        count = count -1;
                    }
                }else{
                    return "warning";//addreduce错误
                }
                map.put(flowerid,count);
                String s = JSON.toJSONString(map);
                shopDao.updateShop(new Shop(null,userid,s,new Date(),null,null));
                return "ok";
            }else{
                return "warning";//鲜花商品在收藏夹中不存在
            }

        }else{
            return "usernotfound";//用户不存在
        }

    }

    @Override
    public Integer getCountById(String userid, Integer flowerid) {
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null) {
            //从数据库中获取这个人的购物车信息
            Shop shopById = shopDao.findShopById(userid);
            String flowercount = shopById.getFlowercount();
            Map<String,Object> map = JSON.parseObject(flowercount);
            if (map.containsKey(flowerid)){
                return Integer.parseInt(map.get(flowerid).toString());
            }else {
                return null;
            }
        }else {
            return null;//用户不存在
        }
    }

    @Override
    public String toOrder(String userid, Integer[] flowerid) {
        //一次只允许一个订单执行
        Order order=orderDao.findOrderContinueByUserId(userid);
        if (order != null){
            //当前还有订单未解决
            return "existorder";
        }
        Map<String,Object> orderMap =new HashMap<>();
        //查询用户是否存在
        User userById = userDao.findUserById(userid);
        if (userById != null) {
            //获取用户的所有购物表信息
            Shop shop = shopDao.findShopById(userid);
            //获取商品和数量信息
            String flowercount = shop.getFlowercount();
            Map<String,Object> map = JSON.parseObject(flowercount);
            //遍历用户下单的商品，在map中取出数量
            for (Integer id : flowerid) {
                //获取单个商品数量
                Integer count =Integer.parseInt(map.get(id.toString()).toString());
                //放入到新表order中
                orderMap.put(id.toString(),count);
            }
            String s = JSON.toJSONString(orderMap);
            //调用保存订单
            String result = orderService.addOrder(userid, s);
            return result;
        }else {
            return "usernotfound";//用户不存在
        }
    }

    @Override
    public void delshopAll(String userid) {
         shopDao.updateShop(new Shop(null,userid,"",new Date(),null,null));
    }

    /**
     * 使用代码进行分页
     * @param listPage 要进行分页的原list
     * @param pageNum 开始的页数
     * @param pageSize 每页的条数
     * @return 封装好的list
     */
    public List<Flowercount>  getPageList(List<?> listPage ,Integer pageNum,Integer  pageSize){
        if (listPage == null) {
            return null;
        }
        if (listPage.size() == 0) {
            return null;
        }
        Integer count = listPage.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = listPage.subList(fromIndex, toIndex);
        return pageList;
    }

}
