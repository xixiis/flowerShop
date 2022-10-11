package com.wen.gradua.service;

import com.wen.gradua.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 通过用户名查找用户的Id
     * 登录用
     * @return 返回id
     */
    String findIdByUsername(String username);

    /**
     * 登录时查询用
     * 通过username查询用户是否已经注销
     * @return 返回true即已经被注销
     */
    boolean findDelByUsername(String username);

    /**
     * shiro验证用
     * 通过用户username查询用户
     * @param username
     * @return 返回用户账号与密码
     */
    User findByUsername(String username);

    /**
     * 插入用户表
     * @param user
     * @return
     */
    String createRegister(User user);

    /**
     * 根据email查找用户信息
     * @param email
     * @return
     */

    String findIdByEmail(String email);

    /**
     * 根据username查找用户头像
     * @param username
     * @return
     */
    String findImgByUsername(String username);

    /**
     * 修改用户密码
     */
    String resetPassword(String email,String password);

    /**
     * 通过邮箱查询用户的用户名和密码
     * @param username
     * @return
     */
    User findUsernamePasswordByEmail(String username);

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    List<User> getUserById(String id);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    String updateUserById(User user);

    Integer findPeremByuserId(String user_id);

    List<User> getALLUser();

    List<User> getALLUserINBusiness();
}
