package com.xsx.blog.service;

import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.Result;

/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:00
 */
public interface CameraService {

    public Result save(CamerasRequest camerasRequest);
}
