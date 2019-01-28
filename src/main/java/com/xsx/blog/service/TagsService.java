package com.xsx.blog.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Tags;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface TagsService {

    public Tags findOne(Integer id);

    public boolean save(Tags tag);

    public PageInfo<Tags> findPage(Integer pageNo, Integer pageSize);

    public boolean deleteOne(Integer id);

    List<Tags> findByStatu(Integer statu);
}
