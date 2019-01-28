package com.xsx.blog.vo;

import java.util.List;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2019-01-26 10:53
 * @Auther: xieshengxiang
 */
public class Page<T> implements Serializable {

    private Integer pageNo;
    private Integer pageSize;
    private Integer totals; //总记录数
    private Integer totalPage; //总页数
    private List<T> list;

    public Integer getPageNo() {
        return pageNo;
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

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
