package com.wen.service;

import com.alibaba.fastjson.JSONArray;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowerimg;

import java.util.List;

public interface FlowerService {
    List<Flowerimg> getAll();

    void addflower(Flower flower, JSONArray objects);

    void delflower(Integer id);

    List<Flowerimg> getflowerById(Integer id);

    void updateFlower(Flower flower,JSONArray objects);
}
