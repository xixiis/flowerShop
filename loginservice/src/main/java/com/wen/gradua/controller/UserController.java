package com.wen.gradua.controller;


import com.wen.gradua.pojo.User;
import com.wen.gradua.service.MailService;
import com.wen.gradua.service.UserService;
import com.wen.gradua.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 处理用户登录的页面功能
 */
@Slf4j
@CrossOrigin
@RestController //返回json字符串，不返回页面
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    MailService mailService;

    /**
     * 查看用户图片显式地显示在前端
     * @param username 用户id
     * @return
     */
    @PostMapping("/img")
    public String img(@RequestParam("username") String username){
        //判定用户输入
        if (username == null){
            return null;
        }
        //加锁
        ReentrantLock reentrantLock =new ReentrantLock();
        //先从redis中获取
        Object img = redisUtils.hget("img", username);
        if (img == null){
            try {
                reentrantLock.lock();
                //数据库中查询
                String result = userService.findImgByUsername(username);
                if (result != null){
                    //放入到redis中,保存15天
                    Map<String,Object> map=new HashMap();
                    map.put(username,result);
                    redisUtils.hmset("img",map,15*60*3600);
                    return result;
                }else{
                    return null;
                }
            }finally {
                reentrantLock.unlock();
            }

        }else{
            return (String)img;
        }
    }


    /**
     * 用户登录
     * @param username 用户id
     * @param password 密码
     * @param remember 是否记住我
     * @return 错误代码或者成功
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password") String password,boolean remember) {
        //查询用户是否上传数据
        if (username == null || password == null || username.equals("") || password.equals("") || username == "" || password == "") {
            return "userEmptyWarning";
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //开启记住我功能
        if (remember == true){
            token.setRememberMe(true);
        }
        try {
            subject.login(token);//执行登录方法，如果没有异常就说明ok，这里需要try/catch
            //通过用户名查询用户是否已经被注销，del=1
            if (userService.findDelByUsername(username)) {
                return "userDelWarning";
            }
            String id = userService.findIdByUsername(username);
            return id;//没有报错的情况，返回正确界面
        } catch (UnknownAccountException uae) {//不存在账户报错
            return "userWarning";
        } catch (IncorrectCredentialsException ice) {//密码错误
            return "passwordWarning";
        } catch (LockedAccountException lae) {//用户被锁定了
            return "warning";
        } catch (AuthenticationException ae) {//认证异常
            return "warning";
        }
    }

    /**
     * 处理注册请求的程序
     *
     * @param user 用户全部信息
     * @return 错误信息，无错误信息即成功
     */
    @PostMapping("/register")
    public String register(User user) {
        //        当用户信息没有上传时
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return "emptyError"; //前端没有拦截到，用户未输入内容
        }
        //将用户数据持久化
        return userService.createRegister(user);
    }


    /**
     * 获取验证码的方法
     * //     * @param username
     * @param email 用户邮箱
     * @param name 用户账号
     * @return  返回用户验证码
     */
    @PostMapping("/getEmail")
    public String getEmail(@RequestParam("email") String email,@RequestParam("name") String name) {
        //当用户email为空时，返回null
        if (email == null) {
            return "emptyEmail";
        }
        if (name == null) {
            return "nameEmail";
        }
        if (userService.findIdByEmail(email) != null){
            //该邮箱已被注册
            return "existEmail";
        }
        /**
         * 调用service层的方法执行发送邮箱验证码的功能
         */
        //发送至rabbitmq中间件
        return mailService.saveMail(email,name);
    }

    /**
     * 忘记密码请求
     * @param email  接收邮箱
     * @return 返回验证码至前端
     */
    @PostMapping("/forgetEmail")
    public String forgetEmail(@RequestParam("email") String email) {
        //当用户email为空时，返回null
        if (email == null) {
            return "emptyForget";
        }
        String id=userService.findIdByEmail(email);
        if (id == null){
            //该邮箱未被注册
            return "NoExistForget";
        }
        /**
         * 调用service层的方法执行发送邮箱验证码的功能
         */
//        //重复发送，只发一个随机数
//        List<String> integers = mailService.existRandomByEmail(email);
//        if (integers != null){
//            String one = integers.get(0);//获取第一个随机数
//            //发送至rabbitmq中间件
//            mailService.saveMailRandom(email,id,one); //发送
//            return one;//返回随机数
//        }

        return mailService.saveMail(email,id); //第一次发送;

    }

    /**
     * 重置密码请求
     * @param email  接收邮箱
     * @return 返回验证码至前端
     */
    @PostMapping("/reset")
    public String resetPassword(@RequestParam("email") String email,@RequestParam("password") String password) {
        //查询用户是否上传数据
        if (email == null || password == null || email.equals("") || password.equals("") || email == "" || password == "") {
            return "EmptyWarning";
        }
        return userService.resetPassword(email,password);
    }
    /**
     * 获取用户信息
     */
    @RequestMapping("/getUserById/{id}")
    public List<User> getUserById(@PathVariable("id") String id){
       return  userService.getUserById(id);
    }

    /**
     * 获取用户信息
     */
    @RequestMapping("/getALLUser")
    public List<User> getALLUser(){
        return  userService.getALLUser();
    }

    /**
     * 获取用户中为商家的信息
     */
    @RequestMapping("/getALLUserINBusiness")
    public List<User> getALLUserINBusiness(){
        return  userService.getALLUserINBusiness();
    }

    /**
     * 更新用户信息
     * @param user
     */
    @RequestMapping("/updateUserById")
    public void updateUserById(User user){
        System.out.println(user);
          userService.updateUserById(user);
    }

}