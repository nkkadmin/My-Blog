package com.xsx.blog.controller.index;

import com.sun.tools.javac.util.List;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.vo.CameraCoverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: 2019-01-22 07:47
 * @Auther: xieshengxiang
 */
@RequestMapping("/index/camrea")
@RestController
public class IndexCameraController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private MenuService menuService;

    /**
     * 获取最新5个摄影作品封面
     * @return
     */
    public Result<List<CameraCoverVo>> searchTop5Cover(){

        BlogSearchRequest blogSearchRequest = new BlogSearchRequest();
        blogSearchRequest.setMenuId(2);
        blogSearchRequest.setPageNo(1);
        blogSearchRequest.setPageSize(5);
        blogService.findPage(blogSearchRequest);
        return null;
    }
}
