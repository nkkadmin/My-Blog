package com.xsx.blog.common;

/**
 * @Description:
 * @Date: 2019-01-23 07:04
 * @Auther: xieshengxiang
 */
public enum MenuEnum {

    TECHNOLOGY_MENU("技术博客"),
    CEMARE_MENU("摄影作品"),
    ABOUT_MENU("关于我");


    MenuEnum(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
