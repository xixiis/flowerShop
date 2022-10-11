package com.wen.gradua.utils;

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
    private final String STROAGED_PATH="http://121.196.109.178/group1/";

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
            //fastdfs工具类保存图片
            String s = fastdfsUtils.uploadFile(data, "jpg");
            System.out.println("成功");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取头像失败");
            return "error";
        }
    }

//    处理前端密码解密加密
    //加密一次
    public  String encodeBase64(String mingwen) {
        String code = "";
        if (mingwen == null || mingwen.equals("")) {
        } else {
            BASE64Encoder encoder = new BASE64Encoder();
            try {
                code = encoder.encode(mingwen.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return code;
    }
    //解密一次
    public  String decodeBase64(String mi) {
        String mingwen = "";
        if (mi == null || mi.equals("")) {
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                byte[] by = decoder.decodeBuffer(mi);
                mingwen = new String(by,"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mingwen;
    }

    // 提供加密N次
    public  String encodeBase64(String mingwen, int times) {
        int num = (times <= 0) ? 1 : times;//当输入值小于1时置为1
        String code = "";
        if (mingwen == null || mingwen.equals("")) {
        } else {
            code = mingwen;
            for (int i = 0; i < num; i++) {
                code = encodeBase64(code);
            }
        }
        return code;
    }

    // 对应提供解密N次
    public  String decodeBase64(String mi, int times) {
        int num = (times <= 0) ? 1 : times;
        String mingwen = "";
        if (mi == null || mi.equals("")) {
        } else {
            mingwen = mi;
            for (int i = 0; i < num; i++) {
                mingwen = decodeBase64(mingwen);
            }
        }
        return mingwen;
    }
//    版权声明：本文为CSDN博主「码农的无聊生活」的原创文章


}
