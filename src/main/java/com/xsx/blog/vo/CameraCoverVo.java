package com.xsx.blog.vo;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2019-01-22 07:49
 * @Auther: xieshengxiang
 */
public class CameraCoverVo implements Serializable {

    private String url;
    private Integer coverId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }
}
