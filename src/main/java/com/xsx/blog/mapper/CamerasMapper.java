package com.xsx.blog.mapper;

import com.xsx.blog.dto.CamerasDTO;
import com.xsx.blog.model.Cameras;
import com.xsx.blog.request.CamerasRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CamerasMapper {


    List<CamerasDTO> findAll(@Param("request") CamerasRequest request);

    int deleteByPrimaryKey(Integer id);

    int insert(Cameras record);

    int insertSelective(Cameras record);

    Cameras selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cameras record);

    int updateByPrimaryKey(Cameras record);
}