package com.xsx.blog.blog;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Tags;
import com.xsx.blog.request.TagRequest;
import com.xsx.blog.service.TagsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 20:18
 */
public class TagsServiceTest extends BlogApplicationTests {

    @Autowired
    private TagsService tagsService;
    @Test
    public void save(){
        for(int i = 0;i<10;i++){
            Tags tags = new Tags();
            tags.setName("标签"+i);
            tags.setCreateTime(new Date());
            tags.setStatu(1);
            tagsService.save(tags);
        }

    }

    @Test
    public void findOne(){
        System.out.println(tagsService.findOne(2));
    }

    @Test
    public void findAll(){
        TagRequest tagRequest = new TagRequest(0,10);
        PageInfo<Tags> page = tagsService.findPage(tagRequest);
        System.out.println(JSON.toJSON(page));

    }

    @Test
    public void delete(){
        tagsService.deleteOne(14);
    }

    @Test
    public void findByStatu(){
        tagsService.findByStatu(0);
    }
}
