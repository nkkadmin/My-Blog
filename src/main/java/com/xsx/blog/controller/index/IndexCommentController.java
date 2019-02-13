package com.xsx.blog.controller.index;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.model.Comment;
import com.xsx.blog.request.CommentEditRequest;
import com.xsx.blog.request.CommentSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.CommentService;
import com.xsx.blog.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Date: 2019-01-10 07:21
 * @Auther: xieshengxiang
 */
@RestController
@RequestMapping(value = "/index/comment")
public class IndexCommentController {

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

    /**
     * 删除评论
     * @return
     */
    @RequestMapping(value ="/delete",method = RequestMethod.POST)
    public Result delete(@RequestParam("commentId") Integer commentId, HttpSession session){
        if(commentId == null){
            return new Result(false);
        }
        UserInfo userInfo = SessionUtils.getLoginUser(session);
        if(userInfo == null){
            return new Result(false);
        }
        Integer flag = commentService.delete(commentId);
        return new Result(flag > 0);
    }
}
