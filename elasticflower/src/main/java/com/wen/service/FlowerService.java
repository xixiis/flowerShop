package com.wen.service;

import com.wen.pojo.Flower;

import java.math.BigDecimal;
import java.util.List;

public interface FlowerService {
    /**
     * 根据鲜花的id获取图片
     * @param flower
     * @return
     */
    List<String> getImgById(Integer flower);

    /**
     * 根据id获取全部信息
     * @param flower
     * @return
     */
    List<Flower> getFlowerById(Integer flower);

    /**
     * 根据id获取价格
     * @param userid
     * @param flowerid
     * @return
     */
    BigDecimal getPriceById(String userid,Integer... flowerid);

    /**
     * 获取销售榜单前四名
     * @return
     */
    List<Flower> BestSeller();

    String getBusinessIdByFlowerId(String flower_id);
}
