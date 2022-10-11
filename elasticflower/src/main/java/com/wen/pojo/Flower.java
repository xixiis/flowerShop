package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 */
public class Flower implements Serializable {
    private Integer id;//商品id
    private String name;
    private String title;//商品介绍
    private String varieties;//商品属性
    private boolean isnew;//是否为新品
    private String sale;//商品显示折扣
    private BigDecimal price;//现在价格
    private BigDecimal oldprice;//折扣前价格
    private Integer count;//商品数量
    private String star;//商品评分
    private String store;//
    private String img;//商品图片
    private Integer sell;//商品销售个数
    private Date updatetime;//更新时间
    private Date createtime;//更新时间

    public Flower() {
    }

    public Flower(Integer id, String name, String title, String varieties, boolean isnew, String sale, BigDecimal price, BigDecimal oldprice, Integer count, String star, String store, String img, Integer sell, Date updatetime, Date createtime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.varieties = varieties;
        this.isnew = isnew;
        this.sale = sale;
        this.price = price;
        this.oldprice = oldprice;
        this.count = count;
        this.star = star;
        this.store = store;
        this.img = img;
        this.sell = sell;
        this.updatetime = updatetime;
        this.createtime = createtime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarieties() {
        return varieties;
    }

    public void setVarieties(String varieties) {
        this.varieties = varieties;
    }

    public boolean isIsnew() {
        return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
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

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", varieties='" + varieties + '\'' +
                ", isnew=" + isnew +
                ", sale='" + sale + '\'' +
                ", price=" + price +
                ", oldprice=" + oldprice +
                ", count=" + count +
                ", star='" + star + '\'' +
                ", store='" + store + '\'' +
                ", img='" + img + '\'' +
                ", sell=" + sell +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }
}
