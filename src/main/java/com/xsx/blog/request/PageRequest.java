package com.xsx.blog.request;

import java.io.Serializable;

/**
 * 获取分页参数
 */
public class PageRequest implements Serializable {

    private Integer pageNo;

    private Integer pageSize;

    public PageRequest() {
    }

    public PageRequest(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer startPage(){
        return (pageNo - 1) * pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
