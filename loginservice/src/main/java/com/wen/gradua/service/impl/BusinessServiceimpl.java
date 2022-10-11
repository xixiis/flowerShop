package com.wen.gradua.service.impl;

import com.wen.gradua.dao.BusinessDao;
import com.wen.gradua.dao.UserDao;
import com.wen.gradua.pojo.Business;
import com.wen.gradua.service.BusinessService;
import com.wen.gradua.utils.Base64Utils;
import com.wen.gradua.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessServiceimpl implements BusinessService {
    @Autowired
    BusinessDao businessDao;
    @Autowired
    UserDao userDao;
    @Autowired
    Base64Utils base64Utils;
    @Autowired
    FastDFSUtil fastDFSUtil;

    private final String STROAGED_PATH = "http://121.196.109.178/";

    @Override
    public List<Business> findAllBusinessMessage(String user_id) {
        Business business=businessDao.findAllBusinessMessage(user_id);
        List<Business> list = new ArrayList<>();list.add(business);
        return list;
    }

    @Override
    public Integer createStore(Business business) {

        //将图像保存到fastdfs中
        if (business.getImg() != null && business.getImg() != "") {
            String image = base64Utils.GenerateImage(business.getImg());//保存到fastdfs
            business.setImg(STROAGED_PATH + image);//头像地址
        }
        //保存到数据库
        businessDao.createStore(business);
        Business allBusinessMessage = businessDao.findAllBusinessMessage(business.getUser_id());
        //更新用户信息
        userDao.SetPeremByuserId(allBusinessMessage.getId(),business.getUser_id());
        return allBusinessMessage.getId();
    }

    @Override
    public void updateStore(Business business) {
        //将原图删除
        //将图像保存到fastdfs中
        Business allBusinessMessage = businessDao.findAllBusinessMessage(business.getUser_id());
        if ( business.getImg() != "") {
            fastDFSUtil.deleteFile(allBusinessMessage.getImg());//删除原图
            String image = base64Utils.GenerateImage(business.getImg());//保存到fastdfs
            business.setImg(STROAGED_PATH + image);//头像地址
        }else{
            business.setImg(allBusinessMessage.getImg());
        }
//      更新数据库
        businessDao.updateStore(business);
    }



}
