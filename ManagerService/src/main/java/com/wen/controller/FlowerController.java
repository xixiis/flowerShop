package com.wen.controller;

import com.alibaba.fastjson.JSONArray;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowerimg;
import com.wen.service.FlowerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/flower")
public class FlowerController {
    @Autowired
    FlowerService flowerService;

    /**
     * 获取所有的鲜花参数
     * @return
     */
    @RequestMapping("/getAll")
    public List<Flowerimg> getAll(){
        return flowerService.getAll();
    }

    /**
     * 新增花朵
     * @param flower
     * @return
     */
    @RequestMapping("/addflower")
    public String addflower(Flower flower,@RequestParam(value = "imgs",required = false)String imgs){

        if (flower == null){
            return "warning";
        }
        //获取多张图片信息
        JSONArray objects = JSONArray.parseArray(imgs);
        flowerService.addflower(flower,objects);
        return "ok";
    }

    /**
     * 通过id删除
     * @param id
     */
    @RequestMapping("/delone/{id}")
    public void delflower(@PathVariable("id") Integer id){
        if (id != null){
            flowerService.delflower(id);
        }
    }

    /**
     * 通过id
     * 获取信息
     * @param id
     * @return
     */
    @RequestMapping("/getflowerById/{id}")
    public List<Flowerimg> getflowerById(@PathVariable("id") Integer id){
        System.out.println(flowerService.getflowerById(id));
        return flowerService.getflowerById(id);
    }

    /**
     * 更新用户信息
     * @param flower
     */
    @RequestMapping("/updateFlower")
    public void updateFlower(Flower flower,@RequestParam(value = "imgs",required = false)String imgs){
        if (flower == null){
            return;
        }
        JSONArray objects = JSONArray.parseArray(imgs);
        flowerService.updateFlower(flower,objects);
    }

}
