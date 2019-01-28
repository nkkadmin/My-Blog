package com.xsx.blog.request;


/**
 * @Description:
 * @Date: 2019-01-10 07:55
 * @Auther: xieshengxiang
 */
public class CommentSearchRequest extends PageRequest {

    private Integer blogId;

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}
