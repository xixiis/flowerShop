package com.wen.controller;

import com.wen.config.ElasticSearchUtils;
import com.wen.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class EsController {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;


    /**
     * 添加索引
     * @return
     */
    @RequestMapping("/add")
    public String addEs(){
        return elasticSearchUtils.createIndex("wen");
    }

    /**
     * 删除索引
     * @return
     */
    @RequestMapping("/del")
    public boolean delEs(){
        try {
            return elasticSearchUtils.delIndex("flower");
        } catch (IOException e) {
            e.printStackTrace();return false;
        }
    }

    /**
     * 删除索引
     * @return
     */
    @RequestMapping("/getDocument")
    public String getDocument(String index,String id){
        try {
            return elasticSearchUtils.getDocument(index,id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询索引是否存在
     * @param name
     * @return
     */
    @RequestMapping("/getIndex")
    public boolean existEs(String name){
        try {
            return elasticSearchUtils.existIndex("wen");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据name进行模糊查询
     * @param value
     * @return
     */
    @RequestMapping("/matchQuery/{search}")
    public List<Map<String,Object>> matchQuery(@PathVariable("search") String value){
        if (value == null){
            return null;
        }
        try {
           return elasticSearchUtils.matchSearch("flower","name",value,value,"title");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 主页大家都爱
     * 进行精确查询
     * @param value
     * @return
     */
    @RequestMapping("/everyOneLove/{search}")
    public List<Map<String,Object>> everyOneLove(@PathVariable("search") String value){
        if (value == null){
            return null;
        }
        try {
            return elasticSearchUtils.matchSearch("flower","varieties",value,0,4,value,"name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
