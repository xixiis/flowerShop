package com.wen.gradua.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 生成uuid随机码工具类
 */
@Component
public class UUIDUtils {
    public String getUUID(){
        UUID uuid=UUID.randomUUID();//生成uuid
        String aa = uuid.toString();//转换为string对象
        StringBuffer newuuid =new StringBuffer();//创建一个Stringbuffer对象
        String[] splituuid=aa.split("-");//把生成随机码的-去掉
        newuuid.append("jxhn_");//加入id前缀
        for (String string : splituuid) {//遍历以拼接到StringBuffer中
            newuuid.append(string);
        }
        String theuuid=newuuid.substring(0,20);//截取stringbuffer,剩余20长度
        return theuuid;
    }

}
