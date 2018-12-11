package com.xsx.blog.controller;

import com.xsx.blog.entity.Menu;
import com.xsx.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:00
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(name = "/findAll")
    public Page<Menu> findAll(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        return menuService.findPage(pageNo,pageSize);
    }

    @RequestMapping(name = "/findOne",method = RequestMethod.GET)
    public Menu findOne(@RequestParam(name = "id",required = true) Integer id){
        return menuService.findOne(id);
    }

    @RequestMapping(name = "/edit",method = RequestMethod.POST)
    public Boolean edit(@RequestBody Menu menu){
        return menuService.save(menu);
    }

    @RequestMapping(name = "/deleteOne",method = RequestMethod.GET)
    public Boolean deleteOne(@RequestParam(name = "id",required = true) Integer id){
        return menuService.deleteOne(id);
    }
}
