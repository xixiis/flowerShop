package com.wen.dao;

import com.wen.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //根据id查询该用户是否存在
    public User findUserById(String id);
}
