package com.xsx.blog.blog;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.vo.BlogVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 20:18
 */
public class BlogServiceTest extends BlogApplicationTests {

    @Autowired
    private BlogService blogService;
    @Test
    public void save(){
        BlogEditRequest blogEditRequest = new BlogEditRequest();
        blogEditRequest.setContent("单元测试");
        blogEditRequest.setCoverPic("http://pic/xxx");
        blogEditRequest.setCreateTime(new Date());
        blogEditRequest.setCreateUserId(1);
        blogEditRequest.setMenuId(1);
        blogEditRequest.setSortIndex(4);
        blogEditRequest.setStatu(1);
        blogEditRequest.setTagId(1);
        blogEditRequest.setTitle("测试标题");
        blogService.saveOrUpdate(blogEditRequest);


    }

    @Test
    public void findOne(){
        System.out.println(JSON.toJSON(blogService.findOne(2)));
    }

    @Test
    public void findAll(){
        BlogSearchRequest blogSearchRequest = new BlogSearchRequest();
        blogSearchRequest.setPageNo(1);
        blogSearchRequest.setPageSize(10);
//        blogSearchRequest.setMenuId(1);
//        blogSearchRequest.setBlogTitle("测试标题");
        blogSearchRequest.setStatu(1);
        PageInfo<BlogVo> page = blogService.findPage(blogSearchRequest);
        System.out.println(JSON.toJSON(page));
    }



    @Test
    public void delete(){
        blogService.deleteOne(15);
    }

}
