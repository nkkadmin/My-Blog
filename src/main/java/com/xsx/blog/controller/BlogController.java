package com.xsx.blog.controller;


import com.xsx.blog.common.Constants;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.result.BlogResult;
import com.xsx.blog.util.StringUtils;
import com.xsx.blog.vo.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public BlogResult getBlog(@RequestBody BlogSearchRequest blogSearchRequest){

        if(blogSearchRequest == null || blogSearchRequest.getSelectMenu() == 0)
            return null;
        Page<Blog> page = blogService.findPage(blogSearchRequest);
        if(page != null || page.getContent() != null){
            for(Blog blog : page.getContent()){
                String content = StringUtils.delHTMLTag(blog.getContent());
                if(org.springframework.util.StringUtils.isEmpty(blog.getCoverPic())){
                    String[] img = StringUtils.getImgs(blog.getContent());
                    if(img != null && img.length > 0)
                        blog.setCoverPic(img[0]);
                    else
                        blog.setCoverPic(Constants.DEFAULT_PIC);
                }
                blog.setContent(content.length() >= 150 ? content.substring(0,150)+"..." : content);
            }
        }
        BlogResult result = new BlogResult();
        result.setObject(page);
        return result;
    }
}
