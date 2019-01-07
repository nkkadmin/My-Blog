package com.xsx.blog.service;

import com.xsx.blog.entity.Tags;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:55
 */
public interface TagsService {

    public Tags findOne(Integer id);

    public boolean save(Tags tag);

    public Page<Tags> findPage(Integer pageNo,Integer pageSize);

    public boolean deleteOne(Integer id);

    List<Tags> findByStatu(Integer statu);
}
