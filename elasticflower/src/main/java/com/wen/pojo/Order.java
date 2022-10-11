package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单表
 */
public class Order implements Serializable {
    private Integer id;//自增id
    private String Order_id;//订单id
    private String user_id;//用户id
    private String flowercount;//商品和数量的json串
    private BigDecimal total_price;//商品总价格
    private Date create_time;//创建时间
    private Integer del;//是否以过期删除 0正常未付钱 1已付钱未收货 2完成订单 3已删除 4异常

    public Order(Integer id, String order_id, String user_id, String flowercount, BigDecimal total_price, Date create_time, Integer del) {
        this.id = id;
        Order_id = order_id;
        this.user_id = user_id;
        this.flowercount = flowercount;
        this.total_price = total_price;
        this.create_time = create_time;
        this.del = del;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", Order_id='" + Order_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", flowercount='" + flowercount + '\'' +
                ", total_price=" + total_price +
                ", create_time=" + create_time +
                ", del=" + del +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFlowercount() {
        return flowercount;
    }

    public void setFlowercount(String flowercount) {
        this.flowercount = flowercount;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}
