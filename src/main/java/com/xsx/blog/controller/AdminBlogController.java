package com.xsx.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.Constants;
import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.model.Blog;
import com.xsx.blog.model.Menu;
import com.xsx.blog.model.Tags;
import com.xsx.blog.request.BlogEditRequest;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import com.xsx.blog.util.FastDFSClient;
import com.xsx.blog.util.SessionUtils;
import com.xsx.blog.util.StringUtils;
import com.xsx.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/adminBlog")
public class AdminBlogController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private BlogService blogService;



    @RequestMapping(value = "/initData",method = RequestMethod.POST)
    public Result initData(){
        Result result = new Result();
        Map<String,Object> map = new HashMap<>();
        //获取全部有效菜单
       List<Menu> menus = menuService.findByStatu(StatuEnum.OK.getStatu());
       map.put("menus",menus);
        //获取全部有效标签
       List<Tags> tags = tagsService.findByStatu(StatuEnum.OK.getStatu());
       map.put("tags",tags);
       result.setContent(map);
       return result;
    }

    @RequestMapping(value = "/findAll")
    public PageInfo<BlogVo> findAll(@RequestBody BlogSearchRequest blogSearchRequest){
        PageInfo<BlogVo> page = blogService.findPage(blogSearchRequest);
        return page;
    }

    @RequestMapping(value = "/selectById")
    public Blog selectById(@RequestParam(name = "id") Integer id){
        return blogService.findOne(id);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result edit(@RequestBody BlogEditRequest blogEditRequest, HttpServletRequest request) throws Exception {
        UserInfo userInfo = SessionUtils.getLoginUser(request.getSession());
        blogEditRequest.setCreateUserId(userInfo.getId());
        //判断是否有图片
        if(blogEditRequest.getContent().contains("<img")){
            String content = blogEditRequest.getContent();
            //获取所有的base64
            List<String> base64List = StringUtils.getBase64(content);
            for(String base64 : base64List){
                String[] paths = FastDFSClient.upload(base64,request);
                String savePath = getFdfsServerUrl();
                for(String path : paths){
                    savePath += path+"/";
                }
                if(!org.springframework.util.StringUtils.isEmpty(savePath)){
                    savePath = savePath.substring(0,savePath.length()-1);
                }
                content = content.replace(base64,savePath);
            }
            blogEditRequest.setContent(content);
        }
        return blogService.saveOrUpdate(blogEditRequest);
    }

    private String getFdfsServerUrl(){
        return Constants.FDFS_SERVER_URL;
    }



    @RequestMapping(value = "/deleteById")
    public Boolean deleteById(@RequestParam(name = "id",required = true) Integer id){
        return blogService.deleteOne(id);
    }
}
