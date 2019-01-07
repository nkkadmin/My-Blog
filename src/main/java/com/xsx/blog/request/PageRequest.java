package com.xsx.blog.request;

import java.io.Serializable;

/**
 * 获取分页参数
 */
public class PageRequest implements Serializable {

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        if(pageNo < 1)
            return 0;
        else
            return pageNo - 1;
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
