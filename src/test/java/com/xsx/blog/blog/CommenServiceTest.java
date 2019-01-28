package com.xsx.blog.blog;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Comment;
import com.xsx.blog.model.Tags;
import com.xsx.blog.request.CommentEditRequest;
import com.xsx.blog.request.CommentSearchRequest;
import com.xsx.blog.service.CommentService;
import com.xsx.blog.service.TagsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 20:18
 */
public class CommenServiceTest extends BlogApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    public void save() {
        CommentEditRequest commentEditRequest = new CommentEditRequest();
        commentEditRequest.setBlogId(1);
        commentEditRequest.setContent("测试评价");
        commentEditRequest.setpId(1);
        commentEditRequest.setUserId(1);
        commentEditRequest.setStatu(1);
        commentService.saveOrderUpdate(commentEditRequest);

    }


    @Test
    public void findAll() {
        CommentSearchRequest commentSearchRequest = new CommentSearchRequest();
        commentSearchRequest.setPageNo(1);
        commentSearchRequest.setPageSize(10);
        PageInfo<Comment> page = commentService.queryAll(commentSearchRequest);
        System.out.println(JSON.toJSON(page));

    }

}
