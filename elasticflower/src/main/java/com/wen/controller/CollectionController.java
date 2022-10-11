package com.wen.controller;

import com.wen.pojo.Flower;
import com.wen.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏夹控制器
 */
@RestController
@CrossOrigin
public class CollectionController {
    @Autowired
    CollectionService service;

    /**
     * 根据用户id获取收藏夹信息
     * @param search 用户id
     * @return
     */
    @RequestMapping("/getcollection")
    public List<Flower> getCollection(@RequestParam("search") String search,@RequestParam("start") Integer start){
        return service.getcollection(search,start,2);
    }

    /**
     * 添加商品到收藏夹
     * @param userid 用户的id
     * @param flowerid 鲜花的id
     * @return
     */
    @RequestMapping("/addCollection")
    public String addCollection(@RequestParam(value = "userid") String userid,@RequestParam(value = "flowerid") String flowerid){
        if (userid == null || flowerid == null || userid.equals("") || flowerid.equals("")){
            return "warning";
        }
        return service.addCollection(userid,flowerid);
    }


    /**
     * 删除商品到收藏夹
     * @param userid 用户的id
     * @param flowerid 鲜花的id
     * @return
     */
    @RequestMapping("/delCollection")
    public String delCollection(String userid,String flowerid){
        if (userid == null || flowerid == null || userid.equals("") || flowerid.equals("")){
            return "warning";
        }
        return service.delCollection(userid,flowerid);
    }

    /**
     * 在用户的收藏夹是否包含此商品
     * @param userid 用户id
     * @param flowerid 商品id
     * @return
     */
    @RequestMapping("/containsCollection")
    public boolean containsCollection(String userid,String flowerid){
        if (userid == null || flowerid == null || userid.equals("") || flowerid.equals("")){
            return false;
        }
        return service.containsCollection(userid,flowerid);
    }

    /**
     * 获取收藏夹有多少条数据
     * @param id
     * @return
     */
    @RequestMapping("/getcollectionpag/{search}")
    public Integer getcollectionpag(@PathVariable("search") String id){
        return  service.getcollectionpag(id);
    }


}
