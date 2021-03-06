package com.xsx.blog.vo;

import com.xsx.blog.model.Images;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/8/1 21:17
 */
public class AdminCameraVO implements Serializable {

    private static final long serialVersionUID = -5581543293889617280L;

    private Integer id;

    private String title;

    private String tags;

    private List<Images> imagesList;

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

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }
}
