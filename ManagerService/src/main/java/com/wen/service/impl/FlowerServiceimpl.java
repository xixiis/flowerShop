package com.wen.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wen.dao.flower.FlowerDao;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowerimg;
import com.wen.service.FlowerService;
import com.wen.utils.Base64Utils;
import com.wen.utils.ElasticSearchUtils;
import com.wen.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlowerServiceimpl implements FlowerService {

    @Autowired
    FlowerDao flowerDao;
    @Autowired
    Base64Utils base64Utils;
    @Autowired
    FastDFSUtil fastDFSUtil;
    @Autowired
    ElasticSearchUtils es;

    @Override
    public List<Flowerimg> getAll() {
        List<Flowerimg> flowerimgs= new ArrayList<>();
        List<Flower> all = flowerDao.getAll();//从数据库中获取出数据
        String[] split;
        //遍历商品
        for (Flower flower : all) {
            String img = flower.getImg();
            split = img.split(",");
            List<String> list =new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            Flowerimg flowerimg=new Flowerimg(flower,list);
            flowerimgs.add(flowerimg);//放到集合中
        }
        return flowerimgs;
    }

    @Override
    public void addflower(Flower flower, JSONArray objects) {
        //设置时间

        System.out.println(flower);
        //保存用户图片信息
        //设值
        flower.setImg(diskimg(objects));
        flower.setCreatetime(new Date());
        flower.setUpdatetime(new Date());
//        System.out.println(flower);
        flowerDao.addFlower(flower);//保存
    }

    @Override
    public void delflower(Integer id) {
        //遍历删除图像信息
        Flower flower = flowerDao.getflowerById(id);
        String img = flower.getImg();
        String[] split = img.split(",");
        for (int i = 0; i < split.length; i++) {
            fastDFSUtil.deleteFile(split[i]);
        }
        //删除es中的数据
        try {
            //如果存在就删除
            if (es.existDocument("flower",id.toString())){
                es.delDocument("flower",id.toString(),2);
            }
        } catch (IOException e) {
            System.out.println("找不到");
            return;
        }
        flowerDao.delflower(id);//删除数据库
    }

    @Override
    public List<Flowerimg> getflowerById(Integer id) {
        Flower flower = flowerDao.getflowerById(id);
        String img = flower.getImg();
        String[] split = img.split(",");
        List<String> list =new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        Flowerimg flowerimg=new Flowerimg(flower,list);
        List<Flowerimg> lists =new ArrayList<>();
        lists.add(flowerimg);
        return lists;
    }

    @Override
    public void updateFlower(Flower flower, JSONArray objects) {
        //当输入的照片不为空时，将旧照片删除，存入新照片
        if (objects.size() != 0){
            String img=flowerDao.findImgByid(flower.getId());//查出图片
            //删除原照片
            String[] split = img.split(",");
            for (int i = 0; i < split.length; i++) {
                fastDFSUtil.deleteFile(split[i]);
            }
            //把新图片存到fastdfs中
            flower.setImg(diskimg(objects));
        }else{//当输入的照片为空时,不做修改
            String imgByid = flowerDao.findImgByid(flower.getId());
            flower.setImg(imgByid);
        }
        flower.setUpdatetime(new Date());
        flowerDao.updateFlower(flower);

    }

    /**
     * 保存用户信息，转换为string
     * @param objects
     * @return
     */
    public String diskimg(JSONArray objects){
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < objects.size(); i++) {
            System.out.println(objects.get(i).toString());
            //进行存储
            stringBuffer.append(base64Utils.GenerateImage(objects.get(i).toString()));
            if (i != objects.size()-1){//使用,分隔，最后一个不加,
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }
}
