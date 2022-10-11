package com.wen.controller;

import com.wen.pojo.Index;
import com.wen.pojo.Menu;
import com.wen.service.IndexService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页展示内容
 */
@CrossOrigin
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IndexService indexService;
//    主要组件实现逻辑

    /**
     * 主页显示添加
     * @param index
     * @return
     */
    @RequestMapping("/addIndex")
    public String addFlower(Index index){
        indexService.addflower(index);
        return "1";
    }
    /**
     * 主页显示
     * @return
     */
    @RequestMapping("/getAll")
    public List<Index> getAll(){
        return indexService.getAll();
    }

    /**
     * 删除一个
     * @param id
     * @return
     */
    @RequestMapping("/delOne/{id}")
    public String delOne(@PathVariable(name = "id") Integer id) {
        indexService.delById(id);
        return "1";
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/getById/{id}")
    public List<Index> getById(@PathVariable(name = "id") Integer id){
        List<Index> list =new ArrayList<>();
        list.add(indexService.getById(id));
        return list;
    }

    /**
     * 更新主页信息
     */
    @RequestMapping("/updateIndex")
    public String updateIndex(Index index){
        indexService.updateIndex(index);
        return "1";
    }

    //三级菜单逻辑
    /**
     * 插入菜单
     * @param menu
     * @return
     */
    @RequestMapping("/addMenuIndex")
    public String addMenuIndex(Menu menu){
        System.out.println(menu);
        indexService.addMenuIndex(menu);
        return "1";//无返回值，因为前端会主动刷新页面
    }


    /**
     * 主页显示
     * @return
     */
    @RequestMapping("/getAllMenu")
    public List<Menu> getAllMenu(){
        return indexService.getAllMenu();
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/getMenuById/{id}")
    public List<Menu> getMenuById(@PathVariable(name = "id") Integer id){
        List<Menu> list =new ArrayList<>();
        list.add(indexService.getMenuById(id));
        return list;
    }


}
