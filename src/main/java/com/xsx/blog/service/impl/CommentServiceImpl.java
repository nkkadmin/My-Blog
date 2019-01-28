package com.xsx.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        }
        comment.setBlogId(commentEditRequest.getBlogId());
        return commentMapper.insert(comment) > 1;
    }

    @Override
    public PageInfo<Comment> queryAll(CommentSearchRequest commentSearchRequest) {
        PageHelper.startPage(commentSearchRequest.getPageNo(),commentSearchRequest.getPageSize());
        List<Comment> list = commentMapper.findAll(commentSearchRequest);
        return new PageInfo<>(list);
    }
}