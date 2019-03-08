package com.xsx.blog.dto;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2019-02-27 23:12
 * @Auther: xieshengxiang
 */
public class DianzanDTO implements Serializable {

    private Integer blogId;

    private Integer dianzanNum;


    public DianzanDTO(Integer blogId, Integer dianzanNum) {
        this.blogId = blogId;
        this.dianzanNum = dianzanNum;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getDianzanNum() {
        return dianzanNum;
    }

    public void setDianzanNum(Integer dianzanNum) {
        this.dianzanNum = dianzanNum;
    }
}
