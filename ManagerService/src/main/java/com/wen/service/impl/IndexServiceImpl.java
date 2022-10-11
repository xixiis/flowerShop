package com.wen.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.wen.dao.index.IndexDao;
import com.wen.pojo.Index;
import com.wen.pojo.Menu;
import com.wen.service.IndexService;
import com.wen.utils.Base64Utils;
import com.wen.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    IndexDao indexDao;
    @Autowired
    Base64Utils base64Utils;
    @Autowired
    FastDFSUtil fastDFSUtil;

    @Override
    public void addflower(Index index) {
        //将图片存到fastdfs中
        try{
            String img = index.getImg();
            if (img !="" && img !=null){
                String s = base64Utils.GenerateImage(img);
                index.setImg(s);
            }
            indexDao.addflower(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Index> getAll() {
        return indexDao.getAll();
    }

    @Override
    public void delById(Integer id) {
        String img=indexDao.findImgByid(id);//查出图片
        if (!img.equals("") && StringUtils.isEmpty(img)){
            //根据文件的访问地址将把旧图片删除
            fastDFSUtil.deleteFile(img);
        }
        //删除数据库
        indexDao.delById(id);
    }

    @Override
    public Index getById(Integer id) {
        return indexDao.getById(id);
    }

    @Override
    public void updateIndex(Index index) {
        //当输入的照片不为空时，将旧照片删除，存入新照片
        if (!index.getImg().equals("")){
            String img=indexDao.findImgByid(index.getId());//查出图片
            if (!img.equals("") && StringUtils.isEmpty(img)){
                //根据文件的访问地址将把旧图片删除
                fastDFSUtil.deleteFile(img);
            }
            //把新图片存到fastdfs中
            String s = base64Utils.GenerateImage(index.getImg());
            index.setImg(s);
        }else{//当输入的照片为空时,不做修改
            String imgByid = indexDao.findImgByid(index.getId());
            index.setImg(imgByid);
        }
        System.out.println("3"+index);
        indexDao.updateIndex(index);
    }

    @Override
    public void addMenuIndex(Menu menu) {
        indexDao.addMenuIndex(menu);
    }

    @Override
    public List<Menu> getAllMenu() {
        return indexDao.getAllMenu();
    }

    @Override
    public Menu getMenuById(Integer id) {
        return indexDao.getMenuById(id);
    }
}
