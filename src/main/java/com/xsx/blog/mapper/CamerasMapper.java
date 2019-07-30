package com.xsx.blog.mapper;

import com.xsx.blog.model.Cameras;

public interface CamerasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cameras record);

    int insertSelective(Cameras record);

    Cameras selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cameras record);

    int updateByPrimaryKey(Cameras record);
}