package com.wen.gradua.pojo;

import lombok.*;

import java.util.Date;

public class Business {
    private Integer id;//id
    private String user_id;//用户id
    private String img;//图片
    private String store;//商店名称
    private String email;//邮箱
    private String tel;//电话
    private Date create_time;//创建时间

    public Business() {
    }

    public Business(Integer id, String user_id, String img, String store, String email, String tel, Date create_time) {
        this.id = id;
        this.user_id = user_id;
        this.img = img;
        this.store = store;
        this.email = email;
        this.tel = tel;
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", img='" + img + '\'' +
                ", store='" + store + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", create_time=" + create_time +
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
