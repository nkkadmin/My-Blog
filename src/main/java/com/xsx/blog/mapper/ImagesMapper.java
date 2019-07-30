package com.xsx.blog.mapper;

import com.xsx.blog.model.Images;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImagesMapper {

    /**
     * 根据cameraId获取图片
     * @param camId
     * @return
     */
    List<Images> findByCamId(@Param("camId") Integer camId);

    int deleteByPrimaryKey(Integer id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}