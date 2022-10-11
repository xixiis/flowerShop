package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 商品和数量表
 */
public class Flowercount implements Serializable {
    private Flower flower;
    private Integer count;//数量

    public Flowercount(Flower flower, Integer count) {
        this.flower = flower;
        this.count = count;
    }

    public Flowercount() {
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Flowercount{" +
                "flower=" + flower +
                ", count=" + count +
                '}';
    }
}
