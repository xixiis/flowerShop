package com.wen.pojo;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Flower implements Serializable {
    private Integer id; //商品id
    private String name; //商品名称
    private String varieties; //商品属性
    private String img; //商品图片
    private String title; //商品详情文章
    private BigDecimal oldprice; //商品旧价格
    private BigDecimal price; //商品价格
    private String star; //商品星级
    private String store; //商品所属店铺
    private Integer count; //商品数量
    private Date updatetime; //更新时间
    private Date createtime;//创建时间

    public Flower() {
    }

    public Flower(Integer id, String name, String varieties, String img, String title, BigDecimal oldprice, BigDecimal price, String star, String store, Integer count, Date updatetime, Date createtime) {
        this.id = id;
        this.name = name;
        this.varieties = varieties;
        this.img = img;
        this.title = title;
        this.oldprice = oldprice;
        this.price = price;
        this.star = star;
        this.store = store;
        this.count = count;
        this.updatetime = updatetime;
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", varieties='" + varieties + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", oldprice=" + oldprice +
                ", price=" + price +
                ", star='" + star + '\'' +
                ", store='" + store + '\'' +
                ", count=" + count +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVarieties() {
        return varieties;
    }

    public void setVarieties(String varieties) {
        this.varieties = varieties;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
