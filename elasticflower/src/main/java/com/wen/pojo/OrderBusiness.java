package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家页面商品展示表
 */

public class OrderBusiness implements Serializable {
    private String order_id;//订单id
    private String flower_id;//商品id
    private String address;//用户地址
    private String tel;//用户电话
    private Integer count;//订单数量
    private String remarks;//订单备注
    private Date time;//下单时间

    public OrderBusiness() {
    }

    public OrderBusiness(String order_id, String flower_id, String address, String tel, Integer count, String remarks, Date time) {
        this.order_id = order_id;
        this.flower_id = flower_id;
        this.address = address;
        this.tel = tel;
        this.count = count;
        this.remarks = remarks;
        this.time = time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFlower_id() {
        return flower_id;
    }

    public void setFlower_id(String flower_id) {
        this.flower_id = flower_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OrderBusiness{" +
                "order_id='" + order_id + '\'' +
                ", flower_id='" + flower_id + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", count=" + count +
                ", remarks='" + remarks + '\'' +
                ", time=" + time +
                '}';
    }
}
