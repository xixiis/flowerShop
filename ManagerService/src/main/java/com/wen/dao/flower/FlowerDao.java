package com.wen.dao.flower;

import com.wen.pojo.Flower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlowerDao {
     List<Flower> getAll();

    void addFlower(Flower flower);

    void delflower(Integer id);

    Flower getflowerById(Integer id);

    String findImgByid(Integer id);

    void updateFlower(Flower flower);

}
