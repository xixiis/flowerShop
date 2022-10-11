package com.wen.gradua.utils;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具包
 */
public class MD5 {    //添加MD5加密方法
    public static String  SysMd5(String username,String password) {
        String hashAlgorithmName = "MD5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(username);//以账号作为盐值
        int hashIterations = 5;//加密次数
        String newPassword= new SimpleHash(hashAlgorithmName,password,salt,hashIterations).toHex();
        return newPassword;
    }






}
