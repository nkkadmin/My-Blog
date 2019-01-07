package com.xsx.blog.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.xsx.blog.common.StatuEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 博客entity
 */
@Entity
@Table(name="blog")
public class Blog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title",length = 200)
    @NotNull
    private String title;

    @Column(name = "content",columnDefinition = "text")
    @NotNull
    private String content;

    /**
     * 创建者
     */
    @Column(name="create_user_id")
    private Integer createUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name="statu",length = 1,columnDefinition="tinyint default 1")
    @NotNull
    private Integer statu = StatuEnum.OK.getStatu();


    @OneToOne
    @JoinColumn(name = "tag_id")
    private Tags tags;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    /**
     * 用于排序字段，场景：最热，最新
     */
    @NotNull
    @Column(name = "sort_index")
    private Integer sortIndex = 1;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
}
