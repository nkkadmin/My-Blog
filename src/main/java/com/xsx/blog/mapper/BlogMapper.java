package com.xsx.blog.mapper;

import java.util.List;

import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.model.Blog;
import com.xsx.blog.request.BlogSearchRequest;
import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    int insert(Blog blog);

    int insertSelective(Blog blog);

    Blog findById(@Param("id") Integer id);

    int update(Blog blog);

    List<BlogDTO> findAll(BlogSearchRequest blogSearchRequest);

    Integer count();

    Integer batchUpdateBlog(List<BlogDTO> blogList);

    /**
     * 获取所有年份
     * @return
     */
    List<String> groupByCreateTime();

    Integer validCount();
}