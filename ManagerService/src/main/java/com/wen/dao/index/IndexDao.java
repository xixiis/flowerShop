package com.wen.dao.index;

import com.wen.pojo.Index;
import com.wen.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexDao {
    /**
     * 增加鲜花
     * @param index
     */
     void addflower(Index index);

    List<Index> getAll();

    void delById(Integer id);

    String findImgByid(Integer id);

    Index getById(Integer id);

    void updateIndex(Index index);

    void addMenuIndex(Menu menu);

    List<Menu> getAllMenu();

    Menu getMenuById(Integer id);
}
