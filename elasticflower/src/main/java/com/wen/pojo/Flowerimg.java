package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;
/**
 * 商品和商品图片表
 */
public class Flowerimg implements Serializable {
    private Flower flower;
    private List<String> img;//图片集合

    public Flowerimg() {
    }

    public Flowerimg(Flower flower) {
        this.flower = flower;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Flowerimg{" +
                "flower=" + flower +
                ", img=" + img +
                '}';
    }
}
