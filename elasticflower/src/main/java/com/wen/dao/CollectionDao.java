package com.wen.dao;

import com.wen.pojo.Collections;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionDao {
    /**
     * 获取用户收藏夹信息
     * @param userid
     * @return
     */
    Collections getcollectionById(String userid);

    /**
     * 通过id
     * 更新表
     * @param collection
     */
    void updatecollectionById(Collections collection);

    /**
     * 插入数据，当前flower_id为空
     * @param userid
     */
    void createCollection(String userid);
}
