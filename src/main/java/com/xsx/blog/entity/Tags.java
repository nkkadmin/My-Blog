package com.xsx.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description:菜单
 * @Auther: xsx
 * @Date: 2018/12/9 19:39
 */
@Entity
@Table(name="tags")
public class Tags {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name",length = 50)
    @NotNull
    private String name;

    @Column(name="pid",columnDefinition = "tinyint default 0")
    private Integer pid = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name="statu",length = 1,columnDefinition="tinyint default 1")
    @NotNull
    private Integer statu = 1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}
