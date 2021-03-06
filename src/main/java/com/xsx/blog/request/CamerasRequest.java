package com.xsx.blog.request;

import com.xsx.blog.model.Images;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:03
 */
public class CamerasRequest extends PageRequest implements Serializable {

    private Integer id;

    private String title;

    private String tags;

    private Integer statu;

    private List<Images> imagesList;

    /**
     * 新增或者删除的图片
     */
    private List<OperImags> addOrDelImgs;

    /**
     * 封面变更
     */
    private ChangeCover changeCover;

    public ChangeCover getChangeCover() {
        return changeCover;
    }

    public void setChangeCover(ChangeCover changeCover) {
        this.changeCover = changeCover;
    }

    public List<OperImags> getAddOrDelImgs() {
        return addOrDelImgs;
    }

    public void setAddOrDelImgs(List<OperImags> addOrDelImgs) {
        this.addOrDelImgs = addOrDelImgs;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

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

    public static class ChangeCover implements Serializable {

        private Integer oldCover;
        private Integer newCover;

        public Integer getOldCover() {
            return oldCover;
        }

        public void setOldCover(Integer oldCover) {
            this.oldCover = oldCover;
        }

        public Integer getNewCover() {
            return newCover;
        }

        public void setNewCover(Integer newCover) {
            this.newCover = newCover;
        }
    }

    public static class OperImags implements Serializable {

        private static final long serialVersionUID = -2045382489628899841L;
        private Integer id;

        private String url;

        private String type;

        private Integer cover;

        public Integer getCover() {
            return cover;
        }

        public void setCover(Integer cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static enum OperImagType{
        REMOVE,ADD;

        public static boolean isAdd(String type){
            return OperImagType.ADD.name().equals(type);
        }

        public static boolean isRemove(String type){
            return OperImagType.REMOVE.name().equals(type);
        }
    }
}
