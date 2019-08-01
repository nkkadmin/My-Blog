package com.xsx.blog.vo;

import com.xsx.blog.result.Result;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2019/1/30 20:48
 */
public class AdminMainVO extends Result {

    /**
     * 文章数量
     */
    private Integer articleNum;
    /**
     * 评论数量
     */
    private Integer commentNum;

    /**
     * 摄影作品数量
     */
    private Integer cameraNum;

    public Integer getCameraNum() {
        return cameraNum;
    }

    public void setCameraNum(Integer cameraNum) {
        this.cameraNum = cameraNum;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
}
