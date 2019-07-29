package com.xsx.blog.controller.admin;

import com.github.pagehelper.Page;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Cameras;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.request.CamerasRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.CameraService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: 2019-01-13 21:21
 * @Auther: xieshengxiang
 */
@RestController
@RequestMapping("/admin/camera")
public class CameraController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CameraService cameraService;

    @RequestMapping(value = "/findAll")
    public PageInfo<BlogVo> findAll(@RequestBody BlogSearchRequest blogSearchRequest){
        Integer cameraMenuId = getCameraMenuId();
        if(cameraMenuId == null)
            return null;
        blogSearchRequest.setMenuId(cameraMenuId);
        PageInfo<BlogVo> page = blogService.findPage(blogSearchRequest);
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

}
