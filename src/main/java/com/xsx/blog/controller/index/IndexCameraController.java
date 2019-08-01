package com.xsx.blog.controller.index;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Images;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.CameraIndexResult;
import com.xsx.blog.service.CameraService;
import com.xsx.blog.service.ImagesService;
import com.xsx.blog.util.DateUtils;
import com.xsx.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Description:
 * @Date: 2019-01-22 07:47
 * @Auther: xieshengxiang
 */
@RequestMapping("/index/camrea")
@RestController
public class IndexCameraController {

    @Autowired
    private CameraService cameraService;
    @Autowired
    private ImagesService imagesService;


    /**
     * 获取摄影作品
     * @return
     */
    @RequestMapping("/queryAll")
    public CameraIndexResult queryAll(@RequestBody CamerasRequest request){
        CameraIndexResult result = new CameraIndexResult();
        try {
            result = cameraService.findPageIndex(request);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    @RequestMapping("/queryImgByCamId/{camId}")
    public List<Images> queryImgByCamId(@PathVariable("camId") Integer camId){
        try {
            List<Images> images = imagesService.indexFindByCamId(camId);
            return images;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
