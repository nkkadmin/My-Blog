package com.xsx.blog.request;

/**
 *
 * 博客搜索
 *
 */
public class BlogSearchRequest extends PageRequest {

    private String blogTitle;
    private Integer selectMenu;
    private Integer selectTag;

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Integer getSelectMenu() {
        return selectMenu;
    }

    public void setSelectMenu(Integer selectMenu) {
        this.selectMenu = selectMenu;
    }

    public Integer getSelectTag() {
        return selectTag;
    }

    public void setSelectTag(Integer selectTag) {
        this.selectTag = selectTag;
    }
}
