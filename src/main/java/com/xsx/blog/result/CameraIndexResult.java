package com.xsx.blog.result;


import java.util.List;
import java.util.TreeSet;

/**
 * @Description:
 * @Date: 2019-03-21 07:58
 * @Auther: xieshengxiang
 */
public class CameraIndexResult extends AbstractPageResult {

    /**
     * 将标签去重
     */
    private TreeSet<TagsVo> allInTags;

    /**
     * 数据
     */
    private List<CameraIndexVo> cameraIndexVos;

    public TreeSet<TagsVo> getAllInTags() {
        return allInTags;
    }

    public void setAllInTags(TreeSet<TagsVo> allInTags) {
        this.allInTags = allInTags;
    }

    public List<CameraIndexVo> getCameraIndexVos() {
        return cameraIndexVos;
    }

    public void setCameraIndexVos(List<CameraIndexVo> cameraIndexVos) {
        this.cameraIndexVos = cameraIndexVos;
    }

    /**
     * 标签对象
     */
    public static class TagsVo implements Comparable<TagsVo> {
        private Integer tagsId;

        private String cnTag;

        private String enTag;

        public Integer getTagsId() {
            return tagsId;
        }

        public void setTagsId(Integer tagsId) {
            this.tagsId = tagsId;
        }

        public String getCnTag() {
            return cnTag;
        }

        public void setCnTag(String cnTag) {
            this.cnTag = cnTag;
        }

        public String getEnTag() {
            return enTag;
        }

        public void setEnTag(String enTag) {
            this.enTag = enTag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TagsVo tagsVo = (TagsVo) o;

            if (tagsId != null ? !tagsId.equals(tagsVo.tagsId) : tagsVo.tagsId != null) return false;
            if (cnTag != null ? !cnTag.equals(tagsVo.cnTag) : tagsVo.cnTag != null) return false;
            return enTag != null ? enTag.equals(tagsVo.enTag) : tagsVo.enTag == null;
        }

        @Override
        public int hashCode() {
            int result = tagsId != null ? tagsId.hashCode() : 0;
            result = 31 * result + (cnTag != null ? cnTag.hashCode() : 0);
            result = 31 * result + (enTag != null ? enTag.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(TagsVo o) {
            return this.getTagsId() - o.getTagsId() ;
        }
    }

    public static class CameraIndexVo {

        private Integer id;

        private String title;

        private String url;

        /**
         * 英文版标签
         */
        private String enTags;

        private Integer lookNum;

        private Integer zanNum;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEnTags() {
            return enTags;
        }

        public void setEnTags(String enTags) {
            this.enTags = enTags;
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
    }
}
