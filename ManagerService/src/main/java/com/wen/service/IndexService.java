package com.wen.service;

import com.wen.pojo.Index;
import com.wen.pojo.Menu;

import java.util.List;

public interface IndexService {

    void addflower(Index index);

    List<Index> getAll();

    void delById(Integer id);

    Index getById(Integer id);

    void updateIndex(Index index);

    void addMenuIndex(Menu menu);

    List<Menu> getAllMenu();

    Menu getMenuById(Integer id);
}
