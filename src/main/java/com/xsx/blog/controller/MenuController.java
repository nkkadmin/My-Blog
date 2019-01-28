package com.xsx.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.PageRequest;
import com.xsx.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:00
 */
@RestController
@RequestMapping(value = "/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/findAll")
    public PageInfo<Menu> findAll(@RequestBody PageRequest pageRequest){
        PageInfo<Menu> pageInfo = menuService.findPage(pageRequest.getPageNo(),pageRequest.getPageSize());
        return pageInfo;
    }

    @RequestMapping(value = "/selectById")
    public Menu selectById(@RequestParam(name = "id") Integer id){
        return menuService.findOne(id);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Boolean edit(@RequestBody Menu menu){
        return menuService.save(menu);
    }


    @RequestMapping(value = "/deleteById")
    public Boolean deleteById(@RequestParam(name = "id",required = true) Integer id){
        return menuService.deleteOne(id);
    }
}
