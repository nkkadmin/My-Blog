package com.xsx.blog.vo;


import com.xsx.blog.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * 封装首页返回值
 */
public class IndexVO implements Serializable {

    private List<Menu> menu;

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
