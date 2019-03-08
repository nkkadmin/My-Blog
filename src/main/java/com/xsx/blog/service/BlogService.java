package com.xsx.blog.service;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.model.Blog;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.Page;

import java.util.List;


/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface BlogService {

    public Blog findOne(Integer id);

    public Boolean save(BlogEditRequest blogEditRequest);

    public PageInfo<BlogVo> findPage(BlogSearchRequest blogSearchRequest);

    public Boolean deleteOne(Integer id);

    Integer count();

    /**
     * 点赞，成功返回当前的点赞量，失败返回-1
     * @param id
     * @return
     */
    int dianzan(Integer id);

    /**
     * 批处理
     * @param blogList
     * @return
     */
    Integer batchUpdateBlog(List<BlogDTO> blogList);
}
