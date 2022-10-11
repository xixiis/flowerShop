package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单显示表
 */
public class OrderFlower implements Serializable {
    private String userid;//用户id
    private String orderid;//订单id
    private String address;//地址
    private String telephone;//电话
    private String oldcalcul;
    private BigDecimal oldprice;//折扣前总价格
    private BigDecimal sum;//总金额
    private String create_time;//订单创建时间
    private List<Flowercount> flowercount;//商品详情和数量
    private String state;//订单状态

    public OrderFlower() {
    }

    public OrderFlower(String userid, String orderid, String address, String telephone, String oldcalcul, BigDecimal oldprice, BigDecimal sum, String create_time, List<Flowercount> flowercount, String state) {
        this.userid = userid;
        this.orderid = orderid;
        this.address = address;
        this.telephone = telephone;
        this.oldcalcul = oldcalcul;
        this.oldprice = oldprice;
        this.sum = sum;
        this.create_time = create_time;
        this.flowercount = flowercount;
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderFlower{" +
                "userid='" + userid + '\'' +
                ", orderid='" + orderid + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", oldcalcul='" + oldcalcul + '\'' +
                ", oldprice=" + oldprice +
                ", sum=" + sum +
                ", create_time='" + create_time + '\'' +
                ", flowercount=" + flowercount +
                ", state='" + state + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOldcalcul() {
        return oldcalcul;
    }

    public void setOldcalcul(String oldcalcul) {
        this.oldcalcul = oldcalcul;
    }

    public BigDecimal getOldprice() {
        return oldprice;
    }

    public void setOldprice(BigDecimal oldprice) {
        this.oldprice = oldprice;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<Flowercount> getFlowercount() {
        return flowercount;
    }

    public void setFlowercount(List<Flowercount> flowercount) {
        this.flowercount = flowercount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
