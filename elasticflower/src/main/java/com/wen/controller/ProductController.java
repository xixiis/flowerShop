package com.wen.controller;

import com.wen.pojo.Flower;
import com.wen.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 处理商品界面
 */
@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    FlowerService flowerService;
    /**
     * 获取当前图像信息
     * @param flower
     * @return
     */
    @RequestMapping("/getImgById/{flower}")
    public List<String> getImgById(@PathVariable("flower") Integer flower){
        if (flower==null){
            return null;
        }
        return flowerService.getImgById(flower);
    }
    /**
     * 获取用户信息
     */
    @RequestMapping("/getFlowerById/{flower}")
    public List<Flower> getFlowerById(@PathVariable("flower") Integer flower){
        if (flower==null){
            return null;
        }
        return flowerService.getFlowerById(flower);
    }
    /**
     * 根据传入的id返回价格
     * 用于购物车计算总价
     * @param flowerid
     * @return
     */
    @RequestMapping("/calculPriceSum")
    public BigDecimal calculPriceSum(@RequestParam("userid") String userid,@RequestParam("flowerid") Integer... flowerid) {
        if (flowerid.equals("")){
            return null;
        }
        return flowerService.getPriceById(userid,flowerid);
    }

    /**
     * 根据传入进来的flowerid获取商家id
     * @param flower_id
     * @return
     */
    @PostMapping("/getBusinessIdByFlowerId/{id}")
    public String getBusinessIdByFlowerId(@PathVariable("id") String flower_id){
       return  flowerService.getBusinessIdByFlowerId(flower_id);
    }
}
