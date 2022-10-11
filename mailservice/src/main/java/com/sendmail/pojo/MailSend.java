package com.sendmail.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 发送email的bean
 */

public class MailSend {
    private Integer id;  //自增id
    private String msgId;  //消息id
    private Integer status; //0 消息投递中 1投递成功 2 投递失败
    private String random; //随机数
    private String email; //邮箱
    private String routekey; //路由key
    private Integer count;  //重试次数
    private Date tryTime;  //尝试时间
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    public MailSend() {
    }

    public MailSend(Integer id, String msgId, Integer status, String random, String email, String routekey, Integer count, Date tryTime, Date createTime, Date updateTime) {
        this.id = id;
        this.msgId = msgId;
        this.status = status;
        this.random = random;
        this.email = email;
        this.routekey = routekey;
        this.count = count;
        this.tryTime = tryTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoutekey() {
        return routekey;
    }

    public void setRoutekey(String routekey) {
        this.routekey = routekey;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTryTime() {
        return tryTime;
    }

    public void setTryTime(Date tryTime) {
        this.tryTime = tryTime;
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

    @Override
    public String toString() {
        return "MailSend{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                ", status=" + status +
                ", random='" + random + '\'' +
                ", email='" + email + '\'' +
                ", routekey='" + routekey + '\'' +
                ", count=" + count +
                ", tryTime=" + tryTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
