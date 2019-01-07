package com.xsx.blog.common;

/**
 * 状态
 */
public enum StatuEnum {

    OK(1),
    DELETE(0);

    private Integer statu;

    StatuEnum(Integer statu){
        this.statu = statu;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}
