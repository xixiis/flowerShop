package com.wen.service;

import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;

import java.util.List;

public interface ShopService {

    /**
     * 添加商品
     * @param userid
     * @param flowerid
     * @param count
     */
    String addShop(String userid, String flowerid, Integer count);

    /**
     * 获取所有的购物车商品
     * @param userid
     * @return
     */
    List<Flowercount> getAllShop(String userid,Integer start,Integer count);

    /**
     * 删除购物车的某个商品
     * @param userid
     * @param toString
     * @return
     */
    String delShop(String userid, String toString);

    /**
     * 一共有多少个商品
     * @param userid
     * @return
     */
    Integer shoppingCount(String userid);

    /**
     * 商品进行加减操作
     * @param userid
     * @param flowerid
     * @param addReduce
     * @return
     */
    String shopAddOrReduce(String userid, String flowerid, String addReduce);

    /**
     * 根据商品id求得商品数量
     * @param userid
     * @param flowerid
     * @return
     */
    Integer getCountById(String userid, Integer flowerid);

    /**
     * 转去结账
     * @param userid
     * @param flowerid
     * @return
     */
    String toOrder(String userid, Integer[] flowerid);

    /**
     * 清空购物车所有
     * @param userid
     * @return
     */
    void delshopAll(String userid);
}
