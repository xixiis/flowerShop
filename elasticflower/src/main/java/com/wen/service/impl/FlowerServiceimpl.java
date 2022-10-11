package com.wen.service.impl;

import com.wen.dao.FlowerDao;
import com.wen.pojo.Flower;
import com.wen.service.FlowerService;
import com.wen.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class FlowerServiceimpl implements FlowerService {
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    ShopService shopService;

    @Override
    public List<String> getImgById(Integer flower) {
        String imgs=flowerDao.getImgById(flower);//找出所以的照片信息
        String[] split = imgs.split(",");
        List<String> list =new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return list;
    }

    @Override
    public List<Flower> getFlowerById(Integer flower) {
        List<Flower> list =new ArrayList<>();
        list.add(flowerDao.getFlowerById(flower));
        return list;
    }

    @Override
    public BigDecimal getPriceById(String userid,Integer... flowerid) {
        //商品总价格
        BigDecimal sum =new BigDecimal(0);
        //遍历每一个传过来的商品id
        for (Integer flower : flowerid) {
            System.out.println(flower);
            //获取单个商品价格
            BigDecimal price = flowerDao.getPriceById(flower);
            //获取购物车商品个数
            Integer count=shopService.getCountById(userid,flower);
            BigDecimal one =new BigDecimal(count);
            BigDecimal multiply = price.multiply(one);//乘操作
            sum = sum.add(multiply);
        }
        //获取商品个数
        return sum;
    }

    @Override
    public List<Flower> BestSeller() {
        List<Flower> list = flowerDao.BestSeller();
        Iterator<Flower> iterator = list.iterator();
        while(iterator.hasNext()){
            Flower next = iterator.next();
            String[] img = next.getImg().split(",");
            next.setImg(img[0]);
        }
        return list;
    }

    @Override
    public String getBusinessIdByFlowerId(String flower_id) {
       return  flowerDao.getBusinessIdByFlowerId(flower_id);
    }
}
