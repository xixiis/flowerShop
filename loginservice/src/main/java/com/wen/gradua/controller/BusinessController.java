package com.wen.gradua.controller;

import com.wen.gradua.pojo.Business;
import com.wen.gradua.service.BusinessService;
import com.wen.gradua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class BusinessController {

    @Autowired
    UserService userService;
    @Autowired
    BusinessService businessService;
    /**
     * 查询用户是否为商家
     * @param user_id 用户id username
     * @return 返回用户是否已经注册
     */
    @PostMapping("/UserIsBusiness/{id}")
    public Integer UserIsBusiness(@PathVariable("id") String user_id){
        if (user_id == null){
            return -1;//用户输入为空跳转到登录界面
        }
        //返回正确值
        return userService.findPeremByuserId(user_id);
    }
    /**
     * 根据用户id取出商家的基本信息
     */
    @RequestMapping("/findAllBusinessMessage/{id}")
    public List<Business> findAllBusinessMessage(@PathVariable("id") String user_id){
        if (user_id == null){
            return null;//用户输入为空跳转到登录界面
        }
        return businessService.findAllBusinessMessage(user_id);
    }

    /**
     * 创建为商家
     * @return
     */
    @RequestMapping("/createStore")
    public Integer createStore(@RequestParam("user_id") String user_id,
                              @RequestParam("store") String store,
                              @RequestParam("email") String email,
                              @RequestParam("tel") String tel,
                              @RequestParam("img") String img

    ){

        if (user_id.equals("")||store.equals("")||email.equals("")||tel.equals("")){
            return 0;//缺少输入
        }
        if (user_id==null||store==null||email==null||tel==null){
            return 0;//缺少输入
        }
        return businessService.createStore(new Business(null,user_id,img,store,email,tel,new Date()));
    }

    /**
     * 更新用户信息
     * @param user_id
     * @param store
     * @param email
     * @param tel
     * @param img
     */
    @RequestMapping("/updateStore")
    public void updateStore(@RequestParam("user_id") String user_id,
                            @RequestParam("store") String store,
                            @RequestParam("email") String email,
                            @RequestParam("tel") String tel,
                            @RequestParam("img") String img){
        if (user_id.equals("")||store.equals("")||email.equals("")||tel.equals("")){
            return;//缺少输入
        }
        if (user_id==null||store==null||email==null||tel==null){
            return;//缺少输入
        }
        businessService.updateStore(new Business(null,user_id,img,store,email,tel,new Date()));
    }


}
