package com.xsx.blog.controller;


import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.result.BlogResult;
import com.xsx.blog.vo.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private BlogService blogService;

    /**
     * 初始化数据
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public IndexVO index(){
        IndexVO indexVO = new IndexVO();
        List<Menu> menuList = menuService.findByStatu(StatuEnum.OK.getStatu());
        indexVO.setMenu(menuList);
        return indexVO;
    }


    /**
     * 根据菜单获取博客
     * @param blogSearchRequest
     * @return
     */
    @RequestMapping(value = "/getBlog",method = RequestMethod.POST)
    public BlogResult getBlog(@RequestBody BlogSearchRequest blogSearchRequest){

        if(blogSearchRequest == null || blogSearchRequest.getSelectMenu() == 0)
            return null;
        Page<Blog> page = blogService.findPage(blogSearchRequest);
        BlogResult result = new BlogResult();
        result.setObject(page);
        return result;
    }
}
