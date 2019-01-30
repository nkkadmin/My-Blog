package com.xsx.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.Constants;
import com.xsx.blog.mapper.BlogMapper;
import com.xsx.blog.mapper.MenuMapper;
import com.xsx.blog.mapper.TagsMapper;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.LoggerService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import com.xsx.blog.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:56
 */
@Service
public class BlogServiceImpl extends LoggerService implements BlogService  {



    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public Blog findOne(Integer id) {
        return blogMapper.findById(id);
    }

    @Override
    public Boolean save(BlogEditRequest blogEditRequest) {
        Blog oldBlog = null;
        Blog blog = coverBlog(blogEditRequest);
        if(blog != null && blog.getId() != null){
            oldBlog = findOne(blog.getId());
            oldBlog.setTitle(blog.getTitle());
            oldBlog.setContent(blog.getContent());
            oldBlog.setMenuId(blog.getId());
            oldBlog.setTagId(blog.getTagId());
            oldBlog.setCoverPic(blog.getCoverPic());
        }else{
            oldBlog = blog;
            oldBlog.setCreateTime(new Date());
        }

        oldBlog.setUpdateTime(new Date());
        return blogMapper.insert(oldBlog) > 0;
    }

    private Blog coverBlog(BlogEditRequest blogEditRequest) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogEditRequest,blog);
        blog.setMenuId(blogEditRequest.getMenuId());
        blog.setTagId(blogEditRequest.getTagId());
        return blog;
    }

    @Override
    public PageInfo<BlogVo> findPage(BlogSearchRequest blogSearchRequest) {
        PageHelper.startPage(blogSearchRequest.startPage(),blogSearchRequest.getPageSize());
        List<Blog> list = blogMapper.findAll(blogSearchRequest);
        PageInfo<Blog> oldPage = new PageInfo<>(list);
        List<BlogVo> resultList = new ArrayList<>();
        for(Blog blog : list){
            BlogVo blogVo = new BlogVo();
            String content = com.xsx.blog.util.StringUtils.delHTMLTag(blog.getContent());
            if(org.springframework.util.StringUtils.isEmpty(blog.getCoverPic())){
                String[] img = com.xsx.blog.util.StringUtils.getImgs(blog.getContent());
                if(img != null && img.length > 0)
                    blog.setCoverPic(img[0]);
                else
                    blog.setCoverPic(Constants.DEFAULT_PIC);
            }
            blog.setContent(content.length() >= 150 ? content.substring(0,150)+"..." : content);
            BeanUtils.copyProperties(blog,blogVo);
            blogVo.setMenuName(menuMapper.findById(blog.getMenuId()).getName());
            blogVo.setTagName(tagsMapper.findById(blog.getTagId()).getName());
            resultList.add(blogVo);
        }
        PageInfo<BlogVo> page = new PageInfo<>();
        BeanUtils.copyProperties(oldPage,page);
        page.setList(resultList);
        return page;
    }

    @Override
    public Boolean deleteOne(Integer id) {
        Blog blog = blogMapper.findById(id);
        if(blog == null)
            return true;
        blog.setStatu(0);
        blogMapper.update(blog);
        return true;
    }

    @Override
    public Integer count() {
        return blogMapper.count();
    }


}
