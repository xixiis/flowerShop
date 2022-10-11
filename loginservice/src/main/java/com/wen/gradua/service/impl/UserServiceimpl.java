package com.wen.gradua.service.impl;

import com.wen.gradua.dao.UserDao;
import com.wen.gradua.pojo.User;
import com.wen.gradua.service.UserService;
import com.wen.gradua.utils.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceimpl implements UserService {


    private final String STROAGED_PATH = "http://121.196.109.178/";

    @Autowired
    private UserDao userDao;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    Base64Utils base64Utils;

    @Autowired
    UUIDUtils uuidUtils;

    @Autowired
    FastDFSUtil fastDFSUtil;

    /**
     * 通过username查询id
     */
    @Override
    public String findIdByUsername(String username) {
        String idByUsername = userDao.findIdByUsername(username);
        if (idByUsername == null) {
            //如果是QQ邮箱，也支持不输入后缀
            String[] split = username.split("@");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(split[0]).append("@qq.com");
            idByUsername = userDao.findIdByUsername(stringBuffer.toString());
        }
        return idByUsername;
    }

    /**
     * 根据username查找用户是否删除
     * @param username
     * @return
     */
    @Override
    public boolean findDelByUsername(String username) {
        Integer del = userDao.findDelByUsername(username);
        if (del == null) {
            //如果是QQ邮箱，也支持不输入后缀
            String[] split = username.split("@");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(split[0]).append("@qq.com");
            del = userDao.findDelByUsername(stringBuffer.toString());
        }
        if (del == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过username查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);

    }


    /**
     * 用于保存用户注册信息
     * @return
     */
    @Override
    public String createRegister(User user1) {
        //从缓存中查询是否已经注册
        Object pass = redisUtils.get(user1.getUsername());
        //缓存中没有
        if (pass == null) {
            //从数据库中查询是否已经注册
            String idByUsername = userDao.findIdByUsername(user1.getUsername());
            //数据库没有
            if (idByUsername == null) {
                //使用重入锁
                ReentrantLock r =new ReentrantLock();
                try {
                    r.lock();
                    //将上传的密码解密
                    String password = base64Utils.decodeBase64(user1.getPassword(), 2);//        将密码解密
                    //封装到user中
                    User user = new User();
                    String uuid = uuidUtils.getUUID();//生成随机码id
                    user.setId(uuid); //id
                    user.setUsername(user1.getUsername()); //用户名
                    String md5password = MD5.SysMd5(user1.getUsername(), password);//md5加密密码
                    user.setPassword(md5password);//密码进行MD5加密
                    user.setEmail(user1.getEmail()); //email
                    user.setCreateTime(new Date()); //创建时间
                    user.setUpdateTime(new Date());//更新时间
                    //照片保存
                    if (user1.getImg() != null && user1.getImg() != "") {
                        String image = base64Utils.GenerateImage(user1.getImg());//保存到fastdfs
                        user.setImg(STROAGED_PATH + image);//头像地址
                    }
                    //保存至数据库
                    userDao.insertUser(user);
                    //查询用户id
                    String id = userDao.findIdByUsername(user1.getUsername());
                    //保存至缓存15天
                    redisUtils.setnx(user1.getUsername(),user1.getPassword(),60*60*24*15);
                    return id;
                }catch (Exception e){
                    return "error";
                }finally {
                    r.unlock();
                }
            }else{
                return "existUser";//用户已被注册
            }
        }else{
            return "existUser";//用户已被注册
        }
    }

    @Override
    public String findIdByEmail(String email) {
        return userDao.findIdByEmail(email);
    }


    @Override
    public String findImgByUsername(String username) {
        return userDao.findImgByUsername(username);
    }

    @Override
    public String resetPassword(String email, String password) {
        //查看用户是否存在
        String username = userDao.findUserByEmail(email);
        //用户不存在
        if (username == null) {
            return "userWarning";
        }
        //将密码解密
        String pass = base64Utils.decodeBase64(password, 2);//将密码进行解密
        String md5password = MD5.SysMd5(username, pass);//根据用户id进行md5
        //存储至数据库,new data为更新时间
        userDao.updatePasswordByUsername(username, md5password,new Date());

        //返回id
        return userDao.findIdByEmail(email);
    }

    @Override
    public User findUsernamePasswordByEmail(String email) {
        return userDao.findUsernamePasswordByEmail(email);
    }

    @Override
    public List<User> getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public String updateUserById(User user) {
        userDao.updateUserById(user);
        return "ok";
    }

    @Override
    public Integer findPeremByuserId(String user_id) {
        Integer perms = userDao.findPeremByuserId(user_id);
        if (perms == null){
            return -1;//返回-1表示该用户不存在，应该重新登录
        } else {
            //返回0或者1，0表示不为商家，1表示为商家
            return perms;
        }

    }

    @Override
    public List<User> getALLUser() {
        return userDao.getALLUser();
    }

    @Override
    public List<User> getALLUserINBusiness() {
        return userDao.getALLUserINBusiness();
    }


}

