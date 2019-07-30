package com.xsx.blog.service;

import com.xsx.blog.model.Images;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/30 20:46
 */
public interface ImagesService {

    /**
     * 根据cameraId获取图片
     * @param camId
     * @return
     */
    List<Images> findByCamId(Integer camId);

}
