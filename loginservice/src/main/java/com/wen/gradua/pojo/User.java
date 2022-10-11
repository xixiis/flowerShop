package com.wen.gradua.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */

public class User implements Serializable {
    private String id;//用户唯一id
    private String username;//账户
    private String password;//密码
    private String email;
    private String img;//用户头像
    private String address;//地址
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private String tel;//电话
    private Integer del;//是否删除
    private String perms;//用户权限

    public User() {
    }

    public User(String id, String username, String password, String email, String img, String address, Date createTime, Date updateTime, String tel, Integer del, String perms) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.img = img;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.tel = tel;
        this.del = del;
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", tel='" + tel + '\'' +
                ", del=" + del +
                ", perms='" + perms + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
