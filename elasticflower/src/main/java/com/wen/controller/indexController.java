package com.wen.controller;

import com.wen.pojo.Flower;
import com.wen.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class indexController {
    @Autowired
    FlowerService flowerService;
    /**
     * 查询季度最畅销的商品
     * @return
     */
    @RequestMapping("/BestSeller")
    public List<Flower> BestSeller(){
        return flowerService.BestSeller();
    }
}
