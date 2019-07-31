package com.xsx.blog.request;


/**
 *
 * 博客搜索
 *
 */
public class BlogSearchRequest extends PageRequest {

    private String blogTitle;
    private Integer menuId;
    private Integer tagId;
    private Integer statu;
    private String year;

    public BlogSearchRequest() {
    }

    public BlogSearchRequest(Integer pageNo, Integer pageSize) {
        super(pageNo, pageSize);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setLikeYear(String year){
        this.year = year + "%";
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
