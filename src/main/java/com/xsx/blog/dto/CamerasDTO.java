package com.xsx.blog.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/31 20:46
 */
public class CamerasDTO implements Serializable {

    private static final long serialVersionUID = -2446610011476993008L;
    private Integer id;

    private String title;

    private String tags;

    private Date createTime;

    private Date updateTime;

    private Integer statu;

    private Integer lookNum;

    private Integer zanNum;

    /**
     * 封面图
     */
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Integer getLookNum() {
        return lookNum;
    }

    public void setLookNum(Integer lookNum) {
        this.lookNum = lookNum;
    }

    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
