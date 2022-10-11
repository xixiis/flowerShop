package com.wen.pojo;

import lombok.*;

import java.io.Serializable;

/**
 * 收藏夹表
 */
public class Collections implements Serializable {
    private Integer id;//事件id
    private String user_id;//用户id
    private String flower_id;//鲜花id集合

    public Collections(Integer id, String user_id, String flower_id) {
        this.id = id;
        this.user_id = user_id;
        this.flower_id = flower_id;
    }

    public Collections() {
    }

    @Override
    public String toString() {
        return "Collections{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", flower_id='" + flower_id + '\'' +
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

    public String getFlower_id() {
        return flower_id;
    }

    public void setFlower_id(String flower_id) {
        this.flower_id = flower_id;
    }
}
