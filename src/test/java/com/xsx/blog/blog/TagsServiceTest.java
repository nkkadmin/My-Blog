package com.xsx.blog.blog;

import com.alibaba.fastjson.JSON;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.entity.Tags;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
            tagsService.save(tags);
        }

    }

    @Test
    public void findOne(){
        System.out.println(tagsService.findOne(2));
    }

    @Test
    public void findAll(){
        Page<Tags> page = tagsService.findPage(0,10);
        System.out.println(JSON.toJSON(page));
        System.out.println("查询总页数:"+page.getTotalPages());
        System.out.println("查询总记录数:"+page.getTotalElements());
        System.out.println("查询当前第几页:"+page.getNumber()+1);
        System.out.println("查询当前页面的集合:"+page.getContent());
        System.out.println("查询当前页面的记录数:"+page.getNumberOfElements());
    }

    @Test
    public void delete(){
        tagsService.deleteOne(1);
    }

}
