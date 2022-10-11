package com.wen.service;

import com.wen.pojo.Flower;

import java.util.List;

public interface CollectionService {
    /**
     * 获取用户收藏夹信息
     * @param id 用户id
     * @param start 开始的位置
     * @param count 数量
     * @return
     */
    List<Flower> getcollection(String id,Integer start,Integer count);

    /**
     * 添加收藏
     * @param userid 用户id
     * @param flowerid 鲜花id
     * @return
     */
    String addCollection(String userid, String flowerid);

    /**
     * 从收藏夹中删除
     * @param userid 用户id
     * @param flowerid 鲜花商品id
     * @return
     */
    String delCollection(String userid, String flowerid);

    /**
     * 是否包含这个id
     * @param userid 用户id
     * @param flowerid 商品id
     * @return
     */
    boolean containsCollection(String userid, String flowerid);

    /**
     * 获取用户收藏商品数
     * @param id 用户id
     * @return
     */
    Integer getcollectionpag(String id);
}
