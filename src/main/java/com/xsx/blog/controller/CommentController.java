package com.xsx.blog.controller;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Comment;
import com.xsx.blog.request.CommentEditRequest;
import com.xsx.blog.request.CommentSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: 2019-01-10 07:21
 * @Auther: xieshengxiang
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/save")
    public Result save(@RequestBody CommentEditRequest commentEditRequest){
        Result result = new Result();
        result.setSuccess(commentService.saveOrderUpdate(commentEditRequest));
        return result;
    }

    @RequestMapping(value = "/getComment")
    public PageInfo<Comment> getCommentBy(@RequestBody CommentSearchRequest commentSearchRequest){
        PageInfo<Comment> page = commentService.queryAll(commentSearchRequest);
        return page;
    }
}
