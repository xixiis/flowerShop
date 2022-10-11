package com.wen.gradua.controller;

import com.wen.gradua.utils.FastDFSUtil;
import com.wen.gradua.utils.StringAndBytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理编写markdown文本的controller
 */
@RestController
@CrossOrigin
@RequestMapping("/editor")
public class EditorController {
    private final String STROAGED_PATH="http://121.196.109.178/";
    @Autowired
    FastDFSUtil fastDFSUtil;
    @Autowired

    StringAndBytes stringAndBytes;

    //保存方法
    @PostMapping("/save")
    public boolean save(String markdown,String id){
        if (id == null){
            return false;
        }
        //将String类型转换为byte[]数组类型
        byte[] bytes = stringAndBytes.stringToBytes(markdown);
        //保存到fastdfs文档中
        String uploadFile = fastDFSUtil.uploadFile(bytes, ".md");
        //将用于id和数据保存到数据库表中
        System.out.println(STROAGED_PATH+uploadFile);
        return true;
    }
}
