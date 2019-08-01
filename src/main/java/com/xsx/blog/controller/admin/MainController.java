package com.xsx.blog.controller.admin;

import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.CameraService;
import com.xsx.blog.service.CommentService;
import com.xsx.blog.vo.AdminMainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 19:00
 */
@RestController
@RequestMapping("/admin/main")
public class MainController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CameraService cameraService;

    @RequestMapping("/loadData")
    public AdminMainVO loadData(){
        AdminMainVO adminMainVO = new AdminMainVO();
        try {
            Integer articleCount = blogService.count();
            Integer commentCount = commentService.count();
            Integer cameraNum = cameraService.validCount();
            adminMainVO.setArticleNum(articleCount);
            adminMainVO.setCommentNum(commentCount);
            adminMainVO.setCameraNum(cameraNum);
            adminMainVO.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            adminMainVO.setSuccess(false);
        }
        return adminMainVO;
    }
}
