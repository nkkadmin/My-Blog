package com.xsx.blog.controller;

import com.xsx.blog.entity.Menu;
import com.xsx.blog.entity.Tags;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:00
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @RequestMapping(name = "/findAll")
    public Page<Tags> findAll(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        return tagsService.findPage(pageNo,pageSize);
    }

    @RequestMapping(name = "/findOne")
    public Tags findOne(@RequestParam(name = "id",required = true) Integer id){
        return tagsService.findOne(id);
    }

    @RequestMapping(name = "edit")
    public Boolean edit(@RequestBody Tags tag){
        return tagsService.save(tag);
    }

    @RequestMapping(name = "/deleteOne")
    public Boolean deleteOne(@RequestParam(name = "id",required = true) Integer id){
        return tagsService.deleteOne(id);
    }
}
