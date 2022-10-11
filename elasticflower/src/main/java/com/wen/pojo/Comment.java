package com.wen.pojo;

import lombok.*;

import java.io.Serializable;


public class Comment implements Serializable {
    private Integer id;
    private String order_id;
    private String user_id;
    private String comment;

    public Comment() {
    }

    public Comment(Integer id, String order_id, String user_id, String comment) {
        this.id = id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", order_id='" + order_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
