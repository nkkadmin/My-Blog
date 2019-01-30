package com.xsx.blog.mapper;


import java.util.List;
import com.xsx.blog.model.Menu;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);

    Menu findById(Integer id);

    List<Menu> findAll();

    int update(Menu menu);

    List<Menu> findByStatuOrderBySortIndexAsc(Integer statu);
}