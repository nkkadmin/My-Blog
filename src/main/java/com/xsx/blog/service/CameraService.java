package com.xsx.blog.service;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.vo.CameraVO;


/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:00
 */

public interface CameraService {


    public PageInfo<CameraVO> findPage(CamerasRequest request);

    public Result save(CamerasRequest camerasRequest);
}

