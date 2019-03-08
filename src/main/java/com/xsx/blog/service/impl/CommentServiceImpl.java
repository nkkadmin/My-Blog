package com.xsx.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.mapper.CommentMapper;
import com.xsx.blog.model.Comment;
import com.xsx.blog.request.CommentEditRequest;
import com.xsx.blog.request.CommentSearchRequest;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Date: 2019-01-10 07:23
 * @Auther: xieshengxiang
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private BlogService blogService;


    @Override
    public Boolean saveOrderUpdate(CommentEditRequest commentEditRequest) {
        if(commentEditRequest == null){
            return false;
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEditRequest,comment);
        if(comment.getId() == null){
            comment.setCreateTime(new Date());
            comment.setStatu(StatuEnum.OK.getStatu());
        }
        comment.setBlogId(commentEditRequest.getBlogId());
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public PageInfo<Comment> queryAll(CommentSearchRequest commentSearchRequest) {
        PageHelper.startPage(commentSearchRequest.startPage(),commentSearchRequest.getPageSize());
        List<Comment> list = commentMapper.findAll(commentSearchRequest);
        return new PageInfo<>(list);
    }

    @Override
    public Integer count() {
        return commentMapper.count();
    }

    @Override
    public Integer delete(Integer commentId) {
        if(commentId == null){
            return 0;
        }
        Comment comment = commentMapper.findOne(commentId);
        if(comment != null){
            comment.setStatu(StatuEnum.DELETE.getStatu());
            return commentMapper.update(comment);
        }
        return null;
    }

    @Override
    public Integer countByBlogId(Integer blogId) {
        return commentMapper.countByBlogId(blogId);
    }
}
