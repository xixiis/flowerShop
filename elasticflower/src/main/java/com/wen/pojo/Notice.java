package com.wen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


/**
 * 管理员公告
 */
public class Notice implements Serializable {
    private Integer id;
    private String notice;
    private String create_time;
    private Integer del;

    public Notice(Integer id, String notice, String create_time, Integer del) {
        this.id = id;
        this.notice = notice;
        this.create_time = create_time;
        this.del = del;
    }

    public Notice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", notice='" + notice + '\'' +
                ", create_time='" + create_time + '\'' +
                ", del=" + del +
                '}';
    }
}
