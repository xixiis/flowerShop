package com.wen.controller;

import com.wen.pojo.Business;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;
import com.wen.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class BusinessController {


    @Autowired
    BusinessService businessService;
    /**
     * 查询待处理订单
     */
    @RequestMapping("/PendingOrders/{id}")
    public List<Flowercount> PendingOrders(@PathVariable("id") String id){

        return businessService.PendingOrders(id);
    }

    /**
     * 查询待处理订单
     */
    @RequestMapping("/OldOrders/{id}")
    public List<Flowercount> OldOrders(@PathVariable("id") String id){

        return businessService.OldOrders(id);
    }



    /**
     * 查询我的上架商品
     * @param user_id 商家id
     * @return
     */
    @RequestMapping("/GoodsOnTheShelves/{id}")
    public List<Flower> GoodsOnTheShelves(@PathVariable("id") String user_id){
        if (user_id == "" || user_id == null){
            return null;
        }
        return businessService.GoodsOnTheShelves(user_id);
    }


    @RequestMapping(value="/run")
    public String factorysale(){
//        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("第一个",5);
        map.put("第二个",10);
        map.put("第三个",35);
        map.put("第四个",20);
//        dataList.add(objectObjectHashMap);
        StringBuffer sb = new StringBuffer("[");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            sb.append(",").append("{\"factory_name\":").append("\""+next.getKey()+"\"");
            sb.append(",").append("\"num\":").append("\""+next.getValue()+"\"").append("}");
        }
        sb.append("]");
        System.out.println(sb.toString().replaceFirst(",", ""));
        return sb.toString().replaceFirst(",", "");
    }

}
