package com.xsx.blog.service;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.CameraIndexResult;
import com.xsx.blog.result.Result;
import com.xsx.blog.vo.AdminCameraVO;
import com.xsx.blog.vo.CameraVO;


/**
 * @description:
 * @author: xieshengxiang
 * @date: 2019/7/29 20:00
 */

public interface CameraService {


    public PageInfo<CameraVO> findPage(CamerasRequest request);

    public Result save(CamerasRequest camerasRequest);

    CameraIndexResult findPageIndex(CamerasRequest request);

    /**
     * 获取有效的数量
     * @return
     */
    Integer validCount();

    /**
     * 获取详情
     * @param id
     * @return
     */
    AdminCameraVO queryById(Integer id);

    /**
     * 变更状态
     * @param id
     * @return
     */
    Result changeStatu(Integer id, StatuEnum statuEnum);
}

