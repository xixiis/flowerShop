package com.wen.dao;

import com.wen.pojo.Flower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface FlowerDao {
    /**
     * 查找所有的鲜花信息
     * @return
     */
    List<Flower> FindAllFlower();

    /**
     * 根据id查找照片信息
     * @param flower
     * @return
     */
    String getImgById(Integer flower);

    /**
     * 获取信息根据id
     * @param flower
     * @return
     */
    Flower getFlowerById(Integer flower);

    /**
     * 获取鲜花的价格
     * @param parseInt
     * @return
     */
    BigDecimal getPriceById(int parseInt);

    /**
     * 更新鲜花出售个数
     * @param flowerid 鲜花id
     * @param sell 增加的个数
     */
    void updateSellAdd(@Param("flowerid") Integer flowerid,@Param("count") Integer count,@Param("sell") Integer sell);

    List<Flower> BestSeller();

    /**
     * 根据商家信息查询所有商家的商品id
     * @param storeID
     * @return
     */
    List<Integer> findAllFlowerIdByStoreId(String storeID);

    /**
     * 获取商店的鲜花信息
     * @param store_id
     * @return
     */
    List<Flower> getFlowerByStore(String store_id);

    /**
     * 根据传入进来的flowerid获取商家id
     * @param flower_id
     * @return
     */
    String getBusinessIdByFlowerId(String flower_id);
}
