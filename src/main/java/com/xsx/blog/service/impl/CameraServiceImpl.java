package com.xsx.blog.service.impl;

import com.xsx.blog.mapper.CamerasMapper;
import com.xsx.blog.mapper.ImagesMapper;
import com.xsx.blog.model.Cameras;
import com.xsx.blog.model.Images;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.CameraService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:00
 */
@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    private CamerasMapper camerasMapper;
    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public Result save(CamerasRequest camerasRequest) {
        Result result = checkParams(camerasRequest);
        if(!result.isSuccess()){
            return result;
        }
        //新增基本信息
        Cameras cameras = new Cameras();
        coverBaseModel(camerasRequest,cameras);
        int flag = camerasMapper.insertSelective(cameras);
        if(flag >= 1){
            //新增相册信息
            List<Images> imagesList = buildsImageModel(camerasRequest,cameras.getId());
            for(Images image : imagesList){
                imagesMapper.insertSelective(image);
            }
        }
        result.setSuccess(flag >= 1);
        result.setMsg(result.isSuccess() ? "编辑成功" : "编辑失败");
        return result;
    }

    private List<Images> buildsImageModel(CamerasRequest camerasRequest, Integer id) {
        for(Images image : camerasRequest.getImagesList()){
            image.setCarmeraId(id);
        }
        return camerasRequest.getImagesList();
    }

    private void coverBaseModel(CamerasRequest camerasRequest, Cameras cameras) {
        BeanUtils.copyProperties(camerasRequest,cameras);
    }

    /**
     * 参数校验
     * @param request
     * @return
     */
    private Result checkParams(CamerasRequest request) {
        Result result = new Result(false);
        if(StringUtils.isEmpty(request.getTitle())){
            result.setMsg("标题不能为空");
            return result;
        }
        if(StringUtils.isEmpty(request.getTags())){
            result.setMsg("标签不能为空");
            return result;
        }

        if(CollectionUtils.isEmpty(request.getImagesList())){
            result.setMsg("请上传图片");
            return result;
        }
        result.setSuccess(true);
        return result;
    }
}
