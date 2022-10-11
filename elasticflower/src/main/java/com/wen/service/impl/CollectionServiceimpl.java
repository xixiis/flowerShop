package com.wen.service.impl;

import com.wen.dao.CollectionDao;
import com.wen.dao.FlowerDao;
import com.wen.dao.UserDao;
import com.wen.pojo.Collections;
import com.wen.pojo.Flower;
import com.wen.pojo.User;
import com.wen.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceimpl implements CollectionService {
    @Autowired
    FlowerDao flowerDao;
    @Autowired
    CollectionDao collectionDao;
    @Autowired
    UserDao userDao;

    /**
     * 根据用户id获取收藏夹内容
     * 并进行分页
     * @param id 用户id
     * @param start 从哪里开始,小标从第一页开始
     * @param count 一次输出多少条
     * @return
     */
    public List<Flower> getcollection(String id,Integer start,Integer count){
        //判空
        if (start <= 0 || count <= 0){
            return null;
        }
        //输出集合
        List<Flower> list = new ArrayList<>();
        //根据用户id取出数据库内容
        Collections collection = collectionDao.getcollectionById(id);
        //获取所有鲜花id，根据鲜花id取出数据
        String[] split = collection.getFlower_id().split(",");
        if (split[0] == null || split[0].equals("")){
            //收藏夹为空直接返回
            return null;
        }
        int f=(((start)*count)<=split.length)?count:(start)*count-split.length;//进行判断
        if (f <= 0){
            return null;
        }
        for (int i=(start-1)*count;i< (start-1)*count+f;i++){
            //根据鲜花id获取鲜花信息放入到list集合中
            Flower flowerById = flowerDao.getFlowerById(Integer.valueOf(split[i]));
            //取第一张图片作为主图
            String[] split1 = flowerById.getImg().split(",");
            flowerById.setImg(split1[0]);
            //放到list集合中
            list.add(flowerById);
        }
        return list;
    }

    @Override
    public String addCollection(String userid, String flowerid) {
        //根据用户id查collection表记录
        Collections collection=collectionDao.getcollectionById(userid);
        if (collection != null){//当存在该列时
            //取出库中鲜花栏
            String flower_id = collection.getFlower_id();
            System.out.println(flower_id);
            if (flower_id != null&&flower_id.contains(flowerid)){
                return "exist";//已存在
            }
            StringBuffer stringBuffer =new StringBuffer();
            if (flower_id == null || flower_id.equals("")) { //如果收藏夹中为空
                stringBuffer.append(flowerid);
            }else {
                //收藏夹不为空的情况
                stringBuffer.append(flower_id);
                stringBuffer.append(",").append(flowerid);
            }
            collection.setFlower_id(stringBuffer.toString());
            collectionDao.updatecollectionById(collection);
            return "ok";
        }else{//从用户表中查询再添加到表中
            //判断用户存不存在
            User userById = userDao.findUserById(userid);
            if (userById == null){
                return "usernotfound";//不存在返回错误代码
            }
            collectionDao.createCollection(userid);//创建数据列
            addCollection(userid,flowerid);//从新进入
            return "ok";
        }
    }

    @Override
    public String delCollection(String userid, String flowerid) {
        //根据用户id查collection表记录
        Collections collection=collectionDao.getcollectionById(userid);
        //取出库中鲜花栏
        String flower_id = collection.getFlower_id();
        if (flower_id != null) {
            StringBuffer stringBuffer=new StringBuffer();
            String[] split = flower_id.split(",");
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals(flowerid)) { //如果收藏夹中没有这个id
                    //跳过
                    continue;
                }
                stringBuffer.append(split[i]);
                stringBuffer.append(",");
            }
            if (stringBuffer.length() > 1){
                stringBuffer.deleteCharAt(stringBuffer.length()-1);
            }
            collection.setFlower_id(stringBuffer.toString());
            collectionDao.updatecollectionById(collection);
            return "ok";
        }else{
            return "warning";
        }
    }

    @Override
    public boolean containsCollection(String userid, String flowerid) {
        //根据用户id查collection表记录
        Collections collection=collectionDao.getcollectionById(userid);
        //取出库中鲜花栏
        String flower_id = collection.getFlower_id();
        if (flower_id.contains(flowerid)){
            return true;
        }
        return false;
    }

    @Override
    public Integer getcollectionpag(String id) {
        //根据用户id查collection表记录
        Collections collection=collectionDao.getcollectionById(id);
        String[] split = collection.getFlower_id().split(",");
        return split.length;
    }
}
