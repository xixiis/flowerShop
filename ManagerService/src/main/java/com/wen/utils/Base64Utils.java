package com.wen.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * base64工具类
 */
@Slf4j
@Component
public class Base64Utils {
    private final String STROAGED_PATH="http://www.jiuxiangheni.com/";

    @Autowired
    FastDFSUtil fastdfsUtils;

    //图片转化成base64字符串
    public  String GetImageStr(String imgFile) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
    //base64字符串转化成图片并保存到fastdfs中
    public String GenerateImage(String imgStr) {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {//图像数据为空
            log.error("图像数据为空");
            return "error";
        }
        //去掉base64字符串的开头部分
        String r3 = imgStr.substring(imgStr.indexOf(",")+1);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] data = decoder.decodeBuffer(r3);
            for(int i =0 ; i < data.length ;i++) {
                if(data[i] < 0) {
                    data[i] += 256;
                }
            }
            String s = fastdfsUtils.uploadFile(data, "jpg");
            return STROAGED_PATH+s;//返回网络地址
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取头像失败");
            return "error";
        }
    }
}
