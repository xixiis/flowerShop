package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

public class Business implements Serializable {
    private Integer id;//id
    private String user_id;//用户id
    private String img;//图片
    private String store;//商店名称
    private String email;//邮箱
    private Integer tel;//电话
    private String padding;//待处理订单
    private String success;//成功订单
    private Date create_time;//创建时间

    public Business() {
    }

    public Business(Integer id, String user_id, String img, String store, String email, Integer tel, String padding, String success, Date create_time) {
        this.id = id;
        this.user_id = user_id;
        this.img = img;
        this.store = store;
        this.email = email;
        this.tel = tel;
        this.padding = padding;
        this.success = success;
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
                ", tel=" + tel +
                ", padding='" + padding + '\'' +
                ", success='" + success + '\'' +
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

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}

