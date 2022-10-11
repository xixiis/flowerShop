package com.wen.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 主页展示信息存放
 */
public class Index implements Serializable {
    private Integer id; //唯一id
    private String address; //图片存在的位置
    private String flag; //图片的名称
    private String bigflag;  //名称
    private String smallflag; //名称
    private String title; //描述
    private String img; //照片地址
    private String url; //模糊查询

    public Index() {
    }

    public Index(Integer id, String address, String flag, String bigflag, String smallflag, String title, String img, String url) {
        this.id = id;
        this.address = address;
        this.flag = flag;
        this.bigflag = bigflag;
        this.smallflag = smallflag;
        this.title = title;
        this.img = img;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBigflag() {
        return bigflag;
    }

    public void setBigflag(String bigflag) {
        this.bigflag = bigflag;
    }

    public String getSmallflag() {
        return smallflag;
    }

    public void setSmallflag(String smallflag) {
        this.smallflag = smallflag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Index{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", flag='" + flag + '\'' +
                ", bigflag='" + bigflag + '\'' +
                ", smallflag='" + smallflag + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
