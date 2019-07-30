package com.xsx.blog.service.impl;

import com.xsx.blog.mapper.ImagesMapper;
import com.xsx.blog.model.Images;
import com.xsx.blog.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/30 20:46
 */
@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public List<Images> findByCamId(Integer camId) {
        return imagesMapper.findByCamId(camId);
    }
}
