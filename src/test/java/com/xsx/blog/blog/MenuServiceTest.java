package com.xsx.blog.blog;



import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import java.util.List;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.MenuRequest;
import com.xsx.blog.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

       for(int i = 0;i<14;i++){
           Menu menu = new Menu();
           menu.setName("测试"+i);
           menu.setSortIndex(3);
           menu.setUrl("http://baidu.com");
           menu.setCreateTime(new Date());
           menu.setStatu(1);
           menuService.save(menu);
       }

    }

    @Test
    public void findOne(){
        System.out.println(menuService.findOne(2));
    }

    @Test
    public void findAll(){
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setPageNo(1);
        menuRequest.setPageSize(10);
        PageInfo<Menu> page = menuService.findPage(menuRequest);
        System.out.println(JSON.toJSON(page));
    }

    @Test
    public void findByStatu(){
        List<Menu> list = menuService.findByStatu(1);
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void delete(){
        menuService.deleteOne(13);
    }

}
