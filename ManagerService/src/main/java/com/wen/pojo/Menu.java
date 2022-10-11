package com.wen.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Menu implements Serializable {
    //唯一id
    private Integer id;
    //名称
    private String name;
    //文章内容
    private String title;
    //url模糊查询路径
    private String to;
    //父亲ID
    private String from;

    public Menu() {
    }

    public Menu(Integer id, String name, String title, String to, String from) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.to = to;
        this.from = from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
