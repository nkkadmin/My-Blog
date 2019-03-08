package com.xsx.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Comment;
import com.xsx.blog.request.CommentEditRequest;
import com.xsx.blog.request.CommentSearchRequest;

/**
 * @Description:
 * @Date: 2019-01-10 07:23
 * @Auther: xieshengxiang
 */
public interface CommentService {

    public Boolean saveOrderUpdate(CommentEditRequest commentEditRequest);

    public PageInfo<Comment> queryAll(CommentSearchRequest commentSearchRequest);

    Integer count();

    Integer delete(Integer commentId);

    Integer countByBlogId(Integer blogId);
}
