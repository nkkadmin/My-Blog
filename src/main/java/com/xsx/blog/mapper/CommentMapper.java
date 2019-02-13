package com.xsx.blog.mapper;

import com.xsx.blog.model.Comment;
import com.xsx.blog.request.CommentSearchRequest;

import java.util.List;

public interface CommentMapper {
    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> findAll(CommentSearchRequest commentSearchRequest);

    Integer count();

    Comment findOne(Integer commentId);

    Integer update(Comment comment);
}