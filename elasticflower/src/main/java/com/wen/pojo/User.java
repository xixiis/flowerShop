package com.wen.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */

public class User implements Serializable {
    private String id;//用户唯一id
    private String username;//账户
    private String password;//密码
    private String email;//邮箱
    private String address;//地址
    private String tel;//电话
    private String img;//用户头像
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Integer del;//是否删除
    private String perms;//用户权限，0表示普通用户，1表示商家用户

    public User() {
    }

    public User(String id, String username, String password, String email, String address, String tel, String img, Date createTime, Date updateTime, Integer del, String perms) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.tel = tel;
        this.img = img;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
