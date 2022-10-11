package com.wen.gradua.dao;

import com.wen.gradua.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 通过username用户名或email邮箱查找用户的Id
     * 登录用
     * @return 返回id
     */
    String findIdByUsername(String username);

    /**
     * 查询用户是否已经注销
     * 这里的username既可以是username也可以是email
     * @return 返回true即已经被注销
     */
    Integer findDelByUsername(String username);

    /**
     * shiro验证用
     * @param username
     * @return 返回账户和密码
     */
    User findByUsername(String username);

    /**
     * 创建新用户
     * @param user
     */
    void insertUser(User user);

    String findIdByEmail(@Param("email") String email);

    String findImgByUsername(@Param("username") String username);

    /**
     * 跟据提供的email查询username
     * @param email
     * @return username
     */
    String findUserByEmail(@Param("email") String email);

    /**
     * 更具用户名更新密码
     * @param username
     * @param password
     */
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password, @Param("updatetime")Date date);
    /**
     * 通过邮箱查询用户的用户名和密码
     * @param username
     * @return
     */
    User findUsernamePasswordByEmail(@Param("email") String username);

    List<User> getUserById(String id);

    void updateUserById(User user);

    Integer findPeremByuserId(String user_id);

    void SetPeremByuserId(@Param("perms") Integer perms,@Param("user_id") String user_id);

    List<User> getALLUser();

    List<User> getALLUserINBusiness();
}
