package com.xsx.blog.blog;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.service.MenuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 20:18
 */
public class MenuServiceTest extends BlogApplicationTests {

    @Autowired
    private MenuService menuService;
    @Test
    public void save(){
        for(int i = 0;i<10;i++){
            Menu menu = new Menu();
            menu.setName("摄影作品"+i);
            menu.setUrl("http://baidu.com");
            menu.setCreateTime(new Date());
            menuService.save(menu);
        }

    }

    @Test
    public void findOne(){
        System.out.println(menuService.findOne(2));
    }

    @Test
    public void findAll(){
        Page<Menu> page = menuService.findPage(0,10);
        System.out.println(JSON.toJSON(page));
        System.out.println("查询总页数:"+page.getTotalPages());
        System.out.println("查询总记录数:"+page.getTotalElements());
        System.out.println("查询当前第几页:"+page.getNumber()+1);
        System.out.println("查询当前页面的集合:"+page.getContent());
        System.out.println("查询当前页面的记录数:"+page.getNumberOfElements());
    }

    @Test
    public void delete(){
        menuService.deleteOne(1);
    }

}
