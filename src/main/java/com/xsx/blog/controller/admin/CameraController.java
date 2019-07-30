package com.xsx.blog.controller.admin;

import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Cameras;
import com.xsx.blog.model.Images;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.CameraService;
import com.xsx.blog.service.ImagesService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.CameraVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Date: 2019-01-13 21:21
 * @Auther: xieshengxiang
 */
@RestController
@RequestMapping("/admin/camera")
public class CameraController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private CameraService cameraService;
    @Autowired
    private ImagesService imagesService;

    @RequestMapping(value = "/findAll")
    public PageInfo<CameraVO> findAll(@RequestBody CamerasRequest request){
        PageInfo<CameraVO> page = cameraService.findPage(request);
        return page;
    }

    private Integer getCameraMenuId(){
        Integer menuId = null;
        List<Menu> menuList = menuService.findByStatu(1);
        for(Menu menu : menuList){
            if("摄影作品".equalsIgnoreCase(menu.getName())){
                menuId = menu.getId();
                break;
            }
        }
        return menuId;
    }


    @PostMapping("/save")
    public Result save(@RequestBody CamerasRequest camerasRequest){
        return cameraService.save(camerasRequest);
    }

    @GetMapping("/queryImgByCamId/{camId}")
    public List<Images> queryImgByCamId(@PathVariable Integer camId){
        if(camId == null){
            return new ArrayList<>();
        }
        return imagesService.findByCamId(camId);
    }

}
