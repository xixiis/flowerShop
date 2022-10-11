package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

public class Flowerimg implements Serializable {
    private Flower flower;
    private List<String> img;

    public Flowerimg() {
    }

    public Flowerimg(Flower flower, List<String> img) {
        this.flower = flower;
        this.img = img;
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
