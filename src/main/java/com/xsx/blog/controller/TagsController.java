package com.xsx.blog.controller;

import com.xsx.blog.entity.Tags;
import com.xsx.blog.request.PageRequest;
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

    @RequestMapping(value = "/findAll")
    public Page<Tags> findAll(@RequestBody PageRequest pageRequest){
        return tagsService.findPage(pageRequest.getPageNo(),pageRequest.getPageSize());
    }

    @RequestMapping(value = "/selectById")
    public Tags selectById(@RequestParam(name = "id",required = true) Integer id){
        return tagsService.findOne(id);
    }

    @RequestMapping(value = "edit")
    public Boolean edit(@RequestBody Tags tag){
        return tagsService.save(tag);
    }

    @RequestMapping(value = "/deleteById")
    public Boolean deleteById(@RequestParam(name = "id",required = true) Integer id){
        return tagsService.deleteOne(id);
    }
}
