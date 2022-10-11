package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车表
 */

public class Shop implements Serializable {
    private Integer id;//自增长id
    private String user_id;//用户id
    private String flowercount;//商品id和数量
    private Date update_time;
    private Date create_time;
    private Integer del;

    public Shop() {
    }

    public Shop(Integer id, String user_id, String flowercount, Date update_time, Date create_time, Integer del) {
        this.id = id;
        this.user_id = user_id;
        this.flowercount = flowercount;
        this.update_time = update_time;
        this.create_time = create_time;
        this.del = del;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", flowercount='" + flowercount + '\'' +
                ", update_time=" + update_time +
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

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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
