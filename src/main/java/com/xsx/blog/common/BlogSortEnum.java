package com.xsx.blog.common;


public enum BlogSortEnum {

    HOT(9999),//最火
    TO(8888);//推荐

    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    BlogSortEnum(Integer sort){
        this.sort = sort;
    }
}
