package com.wen.dao;

import com.wen.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShopDao {

    void addShop(Shop shop);

    Shop findShopById(String user_id);

    void updateShop(Shop shop);
}
