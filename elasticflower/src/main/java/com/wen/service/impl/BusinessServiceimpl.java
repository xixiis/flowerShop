package com.wen.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wen.dao.BusinessDao;
import com.wen.dao.FlowerDao;
import com.wen.dao.OrderDao;
import com.wen.pojo.Business;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;
import com.wen.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusinessServiceimpl implements BusinessService {
    @Autowired
    BusinessDao businessDao;
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public List<Flowercount> PendingOrders(String id) {
        if (id == null){//判空
            return null;
        }
        //获取待处理订单的信息
        String padding = businessDao.findPaddingByUserID(id);
        if (padding == null){
            return null;
        }
        Map<String,Object> jsonObject = JSON.parseObject(padding);
        List<Flowercount> list=new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();//key是订单id
            Map<String,Object> value = (Map<String,Object>)next.getValue();
            Set<Map.Entry<String, Object>> entries1 = value.entrySet();
            Iterator<Map.Entry<String, Object>> iterator1 = entries1.iterator();
            while (iterator1.hasNext()){
                Map.Entry<String, Object> next1 = iterator1.next();
                String key1 = next1.getKey();//key是鲜花id
                Object value1 = next1.getValue();//value是鲜花数量
//                根据鲜花id取出鲜花信息
                Flower flowerById = flowerDao.getFlowerById(Integer.parseInt(key1));
                list.add(new Flowercount(flowerById,Integer.parseInt(value1.toString())));
            }

        }
        return list;
    }

    @Override
    public List<Flower> GoodsOnTheShelves(String store_id) {
        List<Flower> flowerByStore = flowerDao.getFlowerByStore(store_id);
        return flowerByStore;
    }

    @Override
    public List<Flowercount> OldOrders(String id) {
        if (id == null){//判空
            return null;
        }
        //获取待处理订单的信息
        String padding = businessDao.findSuccessByUserID(id);
        if (padding == null){
            return null;
        }
        Map<String,Object> jsonObject = JSON.parseObject(padding);
        List<Flowercount> list=new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();//key是订单id
            Map<String,Object> value = (Map<String,Object>)next.getValue();
            Set<Map.Entry<String, Object>> entries1 = value.entrySet();
            Iterator<Map.Entry<String, Object>> iterator1 = entries1.iterator();
            while (iterator1.hasNext()){
                Map.Entry<String, Object> next1 = iterator1.next();
                String key1 = next1.getKey();//key是鲜花id
                Object value1 = next1.getValue();//value是鲜花数量
//                根据鲜花id取出鲜花信息
                Flower flowerById = flowerDao.getFlowerById(Integer.parseInt(key1));
                list.add(new Flowercount(flowerById,Integer.parseInt(value1.toString())));
            }

        }
        return list;
    }
}
