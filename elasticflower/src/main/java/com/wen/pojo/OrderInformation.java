package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息表
 */
public class OrderInformation implements Serializable {
    private String order_id;//订单id
    private String user_id;//用户id
    private String address;//地址
    private String tel;//电话
    private String time;//时间
    private String remarks;//客户留言
    private Date create_time;//创建时间
    private Integer state;//0正常下单 1已付钱 2完成订单 3未完成异常

    public OrderInformation() {
    }

    public OrderInformation(String order_id, String user_id, String address, String tel, String time, String remarks, Date create_time, Integer state) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.address = address;
        this.tel = tel;
        this.time = time;
        this.remarks = remarks;
        this.create_time = create_time;
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "order_id='" + order_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", time='" + time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", create_time=" + create_time +
                ", state=" + state +
                '}';
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
