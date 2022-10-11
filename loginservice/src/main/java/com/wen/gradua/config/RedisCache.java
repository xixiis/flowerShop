//package com.wen.gradua.config;
//
//
//import com.wen.gradua.utils.ApplicationContextUtils;
//import org.apache.ibatis.cache.Cache;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.util.DigestUtils;
//
////分布式缓存替换掉mybatis原来的cache方式
//
///**
// * redis二级缓存
// */
//public class RedisCache implements Cache {
//    private final String id;
//
//    //必须存在的构造方法
//    public RedisCache(String id){
//        this.id =id;
//    }
//    //返回cache的唯一标识
//    @Override
//    public String getId() {
//        return this.id;
//    }
//
//
//
//    //缓存放入值
//    @Override
//    public void putObject(Object key, Object value) {
//        getRedisTemplate().opsForHash().put(id.toString(),getKeyToMd5(key.toString()),value);
//    }
//
//    //缓存拿出值
//    @Override
//    public Object getObject(Object key) {
//        return getRedisTemplate().opsForHash().get(id.toString(),getKeyToMd5(key.toString()));
//    }
//
//    //mybatis的保留方法，现在还没有实现，后续版本可能会实现
//    @Override
//    public Object removeObject(Object o) {
//        return null;
//    }
//
////    清空缓存
//    @Override
//    public void clear() {
//        System.out.println("清空缓存~~~");
//        getRedisTemplate().delete(id.toString());
//    }
//
//    //计算缓存数量
//    @Override
//    public int getSize() {
//        return 0;
//    }
//
////封装RedisTemplate
//    private RedisTemplate getRedisTemplate(){
//        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
////    md5加密,原来的key相对较长，进行MD5加密
//    private String getKeyToMd5(String key){
//        return DigestUtils.md5DigestAsHex(key.getBytes());
//    }
//}
