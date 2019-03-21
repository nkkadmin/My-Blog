package com.xsx.blog.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.mapper.MenuMapper;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.MenuRequest;
import com.xsx.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:56
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Menu findOne(Integer id) {
        return menuMapper.findById(id);
    }

    @Override
    public Boolean save(Menu menu) {
        if(menu.getId() == null){
            menu.setCreateTime(new Date());
            menu.setStatu(StatuEnum.OK.getStatu());
            return menuMapper.insert(menu) > 0;
        }else{
            Menu oldMenu = menuMapper.findById(menu.getId());
            oldMenu.setName(menu.getName());
            oldMenu.setSortIndex(menu.getSortIndex());
            oldMenu.setUrl(menu.getUrl());
            return menuMapper.update(oldMenu) > 0;
        }
    }

    @Override
    public PageInfo<Menu> findPage(MenuRequest menuRequest) {
        PageHelper.startPage(menuRequest.getPageNo(),menuRequest.getPageSize());
        List<Menu> list = menuMapper.findAll();
        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Boolean deleteOne(Integer id) {
        Menu menu = menuMapper.findById(id);
        if(menu == null)
            return true;
        menu.setStatu(0);
        menuMapper.update(menu);
        return true;
    }

    @Override
    public List<Menu> findByStatu(Integer statu) {
        return menuMapper.findByStatuOrderBySortIndexAsc(statu);
    }

    @Override
    public Menu findByName(String name) {
        return menuMapper.findByName(name);
    }

}
