package com.wen.config;

import com.wen.dao.FlowerDao;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowerimg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每十秒中从数据库更新最新值
 */
@Component
public class MySQLGetTask {
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    ElasticSearchUtils es;
    private  boolean flag= false;

    @Scheduled(cron = "0/10 * * * * ?") //十秒定时任务
    public void ScheduledMysql(){
        if (flag == false){
            try {
                //如果不存在这个表则先创建表
                if (!es.existIndex("flower")){
                    String flower = es.createIndex("flower");
                    System.out.println("flower等于"+flower);
                    if (flower != null){
                        flag = true;
                    }
                }else{
                    flag=true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        //从数据库中获取所有flower数据
        List<Flower> flower = flowerDao.FindAllFlower();
//        更新es
        for (Flower flower1 : flower) {

            //获取id
            Integer id = flower1.getId();

            //是否为新品
            if (new Date().getTime()-flower1.getCreatetime().getTime() < 1000*60*60*15){
                flower1.setIsnew(true);//15内为新品
            }else{
                flower1.setIsnew(false);
            }

            String img = flower1.getImg();//获取照片信息
            String[] split = img.split(",");
            flower1.setImg(split[0]);//只需要第一张图片作为封面图


            //折扣比例    现价/原价*100%
            BigDecimal subtract = flower1.getPrice().divide(flower1.getOldprice(),2, BigDecimal.ROUND_HALF_UP);//相减
            double v = 100-subtract.doubleValue()*100;
            flower1.setSale("-"+v+ "%");


            try {
                //是否存在该id
                if (es.existDocument("flower", id.toString())){
                    //存在的话就更新
                    result = es.updateDocument("flower", id.toString(), flower1, 2);
                }else{
                    //不存在就插入
                    result = es.addDocument("flower", id.toString(),flower1, 2);
                }
//                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
