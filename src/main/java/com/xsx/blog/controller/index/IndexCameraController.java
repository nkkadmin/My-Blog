package com.xsx.blog.controller.index;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.MenuEnum;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.util.DateUtils;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.CameraCoverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    private BlogService blogService;
    @Autowired
    private MenuService menuService;


    /**
     * 获取摄影作品
     * @return
     */
    @RequestMapping("/searchList")
    public Result searchList(BlogSearchRequest blogSearchRequest){
        Result listResult = new Result<>();
        try {
            blogSearchRequest.setPageNo(1);
            blogSearchRequest.setPageSize(100);
            blogSearchRequest.setMenuId(2);
            PageInfo<BlogVo> pageInfo = blogService.findPage(blogSearchRequest);
            coverToResult(pageInfo,listResult);
        } catch (Exception e) {
            e.printStackTrace();
            listResult.setSuccess(false);
            listResult.setMsg(e.getMessage());
        }
        return listResult;
    }

    /**
     * 获取最新5个摄影作品封面
     * @return
     */
    @RequestMapping("/searchTop5Cover")
    public Result<List<CameraCoverVo>> searchTop5Cover(){
        Result<List<CameraCoverVo>> listResult = new Result<>();
       try{
           BlogSearchRequest blogSearchRequest = new BlogSearchRequest();
           Integer menuId = getCameraMenuId();
           blogSearchRequest.setMenuId(menuId);
           blogSearchRequest.setPageNo(1);
           blogSearchRequest.setPageSize(5);
           PageInfo<BlogVo> pageInfo = blogService.findPage(blogSearchRequest);
           coverToResult(pageInfo,listResult);
       }catch (Exception e){
           e.printStackTrace();
           listResult.setSuccess(false);
           listResult.setMsg(e.getMessage());
       }
       return listResult;
    }

    private Integer getCameraMenuId() throws Exception{
        Menu menu = menuService.findByName(MenuEnum.CEMARE_MENU.getName());
        if(menu != null)
            return menu.getId();
        else
            throw new Exception("获取菜单id失败");
    }

    /**
     * 封装数据返回前端
     * @param pageInfo
     * @param listResult
     */
    private void coverToResult(PageInfo<BlogVo> pageInfo, Result<List<CameraCoverVo>> listResult) {
        listResult.setSuccess(true);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            listResult.setMsg("返回结果为空");
        }else{
            Map<String,List<CameraCoverVo>> map = new LinkedHashMap<>();
            for(BlogVo blogVo : pageInfo.getList()){
                String year = DateUtils.dateYear(blogVo.getCreateTime());
                if(map.containsKey(year)){
                    map.get(year).add(coverToCameraVO(blogVo));
                }else{
                    List<CameraCoverVo> list = new ArrayList<>();
                    list.add(coverToCameraVO(blogVo));
                    map.put(year,list);
                }
            }
            listResult.setObject(map);
        }
    }

    /**
     * 转换成CameraCoverVo对象
     * @param blogVo
     * @return
     */
    private CameraCoverVo coverToCameraVO(BlogVo blogVo) {
        CameraCoverVo cameraCoverVo = new CameraCoverVo();
        cameraCoverVo.setCoverId(blogVo.getId());
        cameraCoverVo.setUrl(blogVo.getCoverPic());
        cameraCoverVo.setTitle(blogVo.getTitle());
        cameraCoverVo.setCreateTime(DateUtils.dateToStr(blogVo.getCreateTime()));
        return cameraCoverVo;
    }
}
