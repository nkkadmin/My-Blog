package com.xsx.blog.request;


/**
 * @Description:
 * @Date: 2019-01-10 07:55
 * @Auther: xieshengxiang
 */
public class CommentSearchRequest extends PageRequest {

    private Integer blogId;

    private Integer isAdmin = 0; //是否是后台请求

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}
