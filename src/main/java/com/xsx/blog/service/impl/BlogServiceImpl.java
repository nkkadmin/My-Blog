package com.xsx.blog.service.impl;

import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.entity.Tags;
import com.xsx.blog.repository.BlogRepository;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.LoggerService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
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
    private BlogRepository blogRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private TagsService tagsService;

    @Override
    public Blog findOne(Integer id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Boolean save(BlogEditRequest blogEditRequest) {
        Blog oldBlog = null;
        Blog blog = coverBlog(blogEditRequest);
        if(blog != null && blog.getId() != null){
            oldBlog = findOne(blog.getId());
            oldBlog.setTitle(blog.getTitle());
            oldBlog.setContent(blog.getContent());
            oldBlog.setMenu(blog.getMenu());
            oldBlog.setTags(blog.getTags());
            oldBlog.setCoverPic(blog.getCoverPic());
        }else{
            oldBlog = blog;
            oldBlog.setCreateTime(new Date());
        }

        oldBlog.setUpdateTime(new Date());
        return blogRepository.save(oldBlog).getId() != null;
    }

    private Blog coverBlog(BlogEditRequest blogEditRequest) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogEditRequest,blog);
        Menu menu = menuService.findOne(blogEditRequest.getMenuId());
        blog.setMenu(menu);
        Tags tags = tagsService.findOne(blogEditRequest.getTagId());
        blog.setTags(tags);
        return blog;
    }

    @Override
    public Page<Blog> findPage(BlogSearchRequest blogSearchRequest) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(blogSearchRequest.getPageNo(),blogSearchRequest.getPageSize(),sort);
        Specification<BlogSearchRequest> specification = new Specification<BlogSearchRequest>() {
            @Override
            public Predicate toPredicate(Root<BlogSearchRequest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> requestParamList = new ArrayList<>();
                if(!StringUtils.isEmpty(blogSearchRequest.getBlogTitle())){
                    Predicate p = criteriaBuilder.like(root.get("title").as(String.class),"%" + blogSearchRequest.getBlogTitle() + "%");
                    requestParamList.add(p);
                }

                Join<BlogSearchRequest,Tags> tagsJoin = root.join("tags",JoinType.INNER);
                if(!StringUtils.isEmpty(blogSearchRequest.getSelectTag())){
                    Predicate p = criteriaBuilder.equal(tagsJoin.get("id").as(String.class),blogSearchRequest.getSelectTag());
                    requestParamList.add(p);
                }

                Join<BlogSearchRequest, Menu> standardJoin = root.join("menu",JoinType.INNER);
                if(blogSearchRequest.getSelectMenu() !=null){
                    Predicate p = criteriaBuilder.equal(standardJoin.get("id").as(String.class),blogSearchRequest.getSelectMenu());
                    requestParamList.add(p);
                }

                return criteriaBuilder.and(requestParamList.toArray(new Predicate[0]));
            }
        };
        Page<Blog> page = blogRepository.findAll(specification,pageable);
        return page;
    }

    @Override
    public Boolean deleteOne(Integer id) {
        Blog menu = blogRepository.findById(id).get();
        menu.setStatu(0);
        Blog m = blogRepository.save(menu);
        return true;
    }

    @Override
    public List<Blog> findByStatu(Integer statu) {
        return null;
    }

}
