package com.xsx.blog.service;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.dto.BlogDTO;
import com.xsx.blog.model.Blog;
import com.xsx.blog.vo.BlogVo;

/**
 * @Description:
 * @Date: 2019-02-27 22:46
 * @Auther: xieshengxiang
 */
public interface RedisService {

    /**
     * 分页获取缓存中的博客
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<BlogVo> finPage(Integer pageNo,Integer pageSize);

    /**
     * 将全部有效博客数据写入缓存
     */
    public void initBlog();

    /**
     * 将博客写入缓存
     * @return
     */
    public boolean addBlog(Blog blog);

    /**
     * 从缓存里获取博客文章
     * @param id
     * @return
     */
    public Blog getBlog(Integer id);

    /**
     * 删除缓存中的博客文章
     * @param blogId
     * @return
     */
    public boolean delBlogById(Integer... blogId);

    /**
     * 将点赞量写入缓存
     * @param blogId
     * @return
     */
    public boolean addDianzan(Integer blogId);

    /**
     * 将缓存点赞量同步到数据库中
     */
    public void synDianzanRedisToDB();

    /**
     * 将浏览量写入缓存
     * @param blogId
     * @return
     */
    public boolean addLook(Integer blogId);

    /**
     * 将缓存浏览量同步到数据库中
     */
    public void synLookNumRedisToDB();
}
