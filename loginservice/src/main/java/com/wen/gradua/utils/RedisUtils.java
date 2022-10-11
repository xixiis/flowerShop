package com.wen.gradua.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置过期时间，以秒为单位
     * @param key
     * @param time
     * @return 返回true表示设置成功
     */
    public boolean expireOfSeconds(String key,long time){
        if (time>0){
            redisTemplate.expire(key,time, TimeUnit.SECONDS);
        }
        return true;
    }
    /**
     * 设置过期时间，以小时为单位
     * @param key
     * @param time
     * @return 返回true表示设置成功
     */
    public boolean expireOfHours(String key,long time){
        if (time>0){
            redisTemplate.expire(key,time, TimeUnit.HOURS);
        }
        return true;
    }

    /**
     * 获取过期时间
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * @param key 可以传一个或多个值
     */
    public void del(String... key){
        if (key !=null && key.length !=0){
            for (int i = 0; i < key.length; i++) {
                redisTemplate.delete(key[i]);
            }
        }
    }

//    =============================================String=======================================================
    /**
     * 一般情况下缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * 一般情况下存入String
     * @param key
     * @param value
     * @return 返回true保存成功
     */
    public boolean set(String key ,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 可加入秒为单位的时间
     * @param key
     * @param value
     * @param time 以秒为单位
     * @return
     */
    public boolean set(String key,Object value,long time){
        try{
            if (time>0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key, value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 没有才插入
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(String key,Object value){
        try{
            redisTemplate.opsForValue().setIfAbsent(key,value,null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean setnx(String key,Object value,long time){
        try{
            if (time>0){
                redisTemplate.opsForValue().setIfAbsent(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key, value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增或递减
     *
     * @param key   键
     * @param delta 要增加几(大于0)/要减少几(小于0)
     * @return
     */
    public long incrOrdecr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
//========================================hash================================================

    /**
     * 获得hash的值
     * @param key 键 不能为空
     * @param item 项 不能为空
     * @return 获得的值
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取hash键中所有键值对
     * @param key 键不能为空
     * @return map键值集合
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key,Map<String,Object> map){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key,Map<String,Object> map,long time){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            if (time > 0) {
                expireOfSeconds(key, time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expireOfSeconds(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增递减 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

//==================================set=========================================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功存储个数
     */
    public long sSetTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expireOfSeconds(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================List(列表)=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List <Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean RSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean RSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expireOfSeconds(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expireOfSeconds(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean RSet(String key, List <Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LSet(String key, List <Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean RSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expireOfSeconds(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean LSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0)
                expireOfSeconds(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


//    =========================================zset====================================

    /**
     * 获取个数
     * @param key 键
     * @return 返回值
     */
    public long ZSize(String key){
        return  redisTemplate.opsForZSet().size(key);
    }

    /**
     * 将数据放入zset缓存
     *
     * @param key    键
     * @param values 值
     * @return 成功个数
     */
    public boolean Zadd(String key,Integer scores,int score, Object... values) {
        try {
//            DefaultTypedTuple<String> tuple1 = new DefaultTypedTuple<String>("p2", 1.1);
            return redisTemplate.opsForZSet().add(key,values,score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 加分减分
     *
     * @param key 键
     * @return
     */
    public Double ZincrementScore(String key,Object value,int inc) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key,value,inc);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param value 值 可以是多个
     * @return 移除的个数
     */
    public long ZsetRemove(String key, Object value) {
        try {
            Long count = redisTemplate.opsForZSet().remove(key, value);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取指定元素的排名，从小到大排序
     * @param key
     * @param value
     * @return
     */
    public long Zrank(String key,Object value){
        try {
            return redisTemplate.opsForZSet().rank(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 获取指定元素的排名，从大到小排序
     * @param key
     * @param value
     * @return
     */
    public long ZreverseRank(String key,Object value){
        try {
            return redisTemplate.opsForZSet().reverseRank(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /**
     * 返回集合内元素的排名，以及分数（从小到大）
     * @param key
     * @param min max 最小值最大值
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> ZrangeWithScores(String key, long min, long max){
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 返回集合内元素在指定分数范围内的排名（从小到大）
     * @param key
     * @param min max 最小值最大值
     * @return
     */
    public Set<Object> ZrangeByScore(String key,int min,int max){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 返回集合内元素在指定分数范围内的排名（从小到大）
     * 带偏移量和个数
     * 从index下标开始，个位数为count
     * @param key
     * @param min max 最小值最大值
     * @return
     */
    public Set<Object> ZrangeByScore(String key,int min,int max,int index,int count){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max,index,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 返回集合内指定分数范围的成员个数
     * @param key
     * @param min max 最小值最大值
     * @return
     */
    public long Zcount(String key,int min,int max){
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /**
     * 获得指定元素的分数
     * @param key
     * @param  value 指定元素
     * @return 分数
     */
    public Double Zcount(String key,Object value){
        try {
            return redisTemplate.opsForZSet().score(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }

    }

    /**
     * 删除指定索引范围的元素
     * @param key
     * @param  min max 指定范围
     * @return 分数
     */
    public long ZremoveRange(String key,int min,int max){
        try {
            return redisTemplate.opsForZSet().removeRange(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /**
     *删除指定分数范围内的元素
     * @param key
     * @param  min max 指定范围
     * @return 分数
     */
    public long ZremoveRangeByScore(String key,int min,int max){
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    /**
     *按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
     * @param key
     * @param  min max 指定范围
     * @return 分数
     */
    public Set<Object> ZrangeAll(String key,int min,int max){
        try {
            return redisTemplate.opsForZSet().range(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }









}





