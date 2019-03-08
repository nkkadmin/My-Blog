package com.xsx.blog.controller.index;


import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.RedisService;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index/blog")
public class BlogController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private RedisService redisService;

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
        blogSearchRequest.setStatu(StatuEnum.OK.getStatu());
        if(blogSearchRequest == null || blogSearchRequest.getMenuId() == null)
            return null;
        PageInfo<BlogVo> page = blogService.findPage(blogSearchRequest);
        return page;
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value = "/dianZan/{id}")
    public Integer dianZan(@PathVariable Integer id){
        if(id == null)
            return null;
        redisService.addDianzan(id);
        return 1;
    }

    /**
     * 浏览
     * @param id
     * @return
     */
    @RequestMapping(value = "/look/{id}")
    public Integer look(@PathVariable Integer id){
        if(id == null)
            return null;
        redisService.addLook(id);
        return 1;
    }

    /**
     * 搜索
     * @param keyWork
     * @return
     */
    @RequestMapping(value = "/search/{keyWork}")
    public Result<PageInfo<BlogVo>> search(@PathVariable String keyWork){
        BlogSearchRequest blogSearchRequest = new BlogSearchRequest();
        blogSearchRequest.setBlogTitle(keyWork);
        blogSearchRequest.setMenuId(1);
        PageInfo<BlogVo> pageInfo = blogService.findPage(blogSearchRequest);
        Result<PageInfo<BlogVo>> result = new Result<PageInfo<BlogVo>>();
        result.setContent(pageInfo);
        return result;
    }

}
