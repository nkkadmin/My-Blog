package com.xsx.blog.service;

import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface BlogService {

    public Blog findOne(Integer id);

    public Boolean save(Blog blog);

    public Page<Blog> findPage(BlogSearchRequest blogSearchRequest);

    public Boolean deleteOne(Integer id);

    List<Blog> findByStatu(Integer statu);

}
