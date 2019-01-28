package com.xsx.blog.controller.index;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.result.BlogResult;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index/blog")
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

    @RequestMapping(value = "/info/{id}")
    public Blog getBlogById(@PathVariable Integer id){
        Blog blog = blogService.findOne(id);
        return blog;
    }


    /**
     * 根据菜单获取博客
     * @param blogSearchRequest
     * @return
     */
    @RequestMapping(value = "/getBlog",method = RequestMethod.POST)
    public PageInfo<BlogVo> getBlog(@RequestBody BlogSearchRequest blogSearchRequest){
        if(blogSearchRequest == null || blogSearchRequest.getMenuId() == null)
            return null;
        PageInfo<BlogVo> page = blogService.findPage(blogSearchRequest);
        return page;
    }
}
