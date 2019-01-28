package com.xsx.blog.service;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.MenuRequest;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface MenuService {

    public Menu findOne(Integer id);

    public Boolean save(Menu menu);

    public PageInfo<Menu> findPage(MenuRequest menuRequest);

    public Boolean deleteOne(Integer id);

    List<Menu> findByStatu(Integer statu);
}
