package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;
import com.wen.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ShopController {

    @Autowired
    ShopService shopService;
    @Autowired
    CollectionController collectionController;

    /**
     * 添加商品到购物车
     *
     * @param userid   用户id
     * @param flowerid 鲜花id
     * @param count    鲜花数量
     */
    @RequestMapping("/addShop")
    public String addShop(@RequestParam("userid") String userid, @RequestParam("flowerid") Integer flowerid, @RequestParam("count") Integer count) {

        return shopService.addShop(userid, flowerid.toString(), count);
    }

    /**
     * 根据用户id获取所有购物车信息
     *
     * @param userid
     * @return
     */
    @RequestMapping("/getAllShop")
    public List<Flowercount> getAllShop(@RequestParam("userid") String userid,
                                        @RequestParam("start") Integer start,
                                        @RequestParam("count") Integer count) {
        return shopService.getAllShop(userid, start, count);
    }

    /**
     * 购物车中删除
     *
     * @param userid
     * @param flowerid
     * @return
     */
    @RequestMapping("/delShop")
    public String delShop(@RequestParam("userid") String userid,
                          @RequestParam("flowerid") Integer flowerid) {
        return shopService.delShop(userid, flowerid.toString());
    }
    /**
     * 购物车中删除所有
     *
     * @param userid
     * @param
     * @return
     */
    @RequestMapping("/delshopAll/{id}")
    public void delshopAll(@PathVariable("id") String userid) {
        shopService.delshopAll(userid);
    }

    /**
     * 购物车有多少条信息
     */
    @RequestMapping("/shoppingCount/{userid}")
    public Integer shoppingCount(@PathVariable("userid") String userid) {
        return shopService.shoppingCount(userid);
    }

    /**
     * 控制购物车值加一还是减一
     *
     * @param userid    用户id
     * @param flower    鲜花id
     * @param addReduce "+"为加一，"-"为减一
     * @return
     */
    @RequestMapping("/shopAddOrReduce")
    public String shopAddOrReduce(@RequestParam("userid") String userid
            , @RequestParam("flower") String flower, @RequestParam("addreduce") String addReduce) {
        shopService.shopAddOrReduce(userid, flower, addReduce);
        return null;
    }

    /**
     * 从购物车中加入收藏夹
     */
    @RequestMapping("/shopToCollection")
    public String shopToCollection(@RequestParam("userid") String userid
            , @RequestParam("flowerid") String flowerid) {
        //加入到收藏夹中
        collectionController.addCollection(userid, flowerid);
        //从购物车中删除
        shopService.delShop(userid, flowerid);
        return "ok";
    }

    /**
     * 将购物车中商品进行订单打包
     * @param userid 用户id
     * @param flowerid 商品集合
     * @return
     */
    @RequestMapping("/toOrder")
    public String toOrder(@RequestParam("userid") String userid,@RequestParam("flowerid") Integer... flowerid){
        if (userid == null || flowerid == null){
            return "warning";
        }
        return shopService.toOrder(userid,flowerid);
    }



}


