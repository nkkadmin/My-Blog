package com.xsx.blog.mapper;

import com.xsx.blog.model.Tags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagsMapper {
    int insert(Tags record);

    int insertSelective(Tags record);

    Tags findById(@Param("id") Integer id);

    List<Tags> findAll();

    int update(Tags tag);

    List<Tags> findByStatuOrderByCreateTimeAsc(Integer statu);
}