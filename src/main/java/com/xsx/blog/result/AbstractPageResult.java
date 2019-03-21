package com.xsx.blog.result;

import java.io.Serializable;


/**
 * @Description:分页result
 * @Date: 2019-03-21 07:56
 * @Auther: xieshengxiang
 */
public class AbstractPageResult<T> extends AbstractResult<T> implements Serializable {

    private Integer pageNo;
    private Integer pageSize;
    private long total;
    private int pages;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

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


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
