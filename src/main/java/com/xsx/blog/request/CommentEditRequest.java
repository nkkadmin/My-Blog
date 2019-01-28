package com.xsx.blog.request;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2019-01-10 07:17
 * @Auther: xieshengxiang
 */
public class CommentEditRequest implements Serializable {


    private Integer id;

    /**
     * 博客id
     */
    private Integer blogId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论id
     */
    private Integer pId;

    /**
     * 用户id
     */
    private Integer userId;

    private Integer statu;

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
