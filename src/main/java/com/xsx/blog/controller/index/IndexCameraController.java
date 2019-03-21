package com.xsx.blog.controller.index;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xsx.blog.common.MenuEnum;
import com.xsx.blog.model.Menu;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.result.CameraIndexResult;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.util.DateUtils;
import com.xsx.blog.vo.BlogVo;
import com.xsx.blog.vo.CameraCoverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    private BlogService blogService;
    @Autowired
    private MenuService menuService;


    /**
     * 获取摄影作品
     * @return
     */
    @RequestMapping("/searchList")
    public CameraIndexResult searchList(@RequestBody BlogSearchRequest blogSearchRequest){
        CameraIndexResult result = new CameraIndexResult();
        try {
            blogSearchRequest.setMenuId(2);
            PageInfo<BlogVo> pageInfo = blogService.findPage(blogSearchRequest);
            coverToResult(pageInfo,result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }



    /**
     * 封装数据返回前端
     * @param pageInfo
     * @param result
     */
    private void coverToResult(PageInfo<BlogVo> pageInfo, CameraIndexResult result) {
        result.setSuccess(true);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            result.setMsg("返回结果为空");
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
            result.setObject(map);
            coverToResultPage(pageInfo, result);
        }
    }

    private void coverToResultPage(PageInfo<BlogVo> pageInfo, CameraIndexResult result) {
        result.setPageNo(pageInfo.getPageNum());
        result.setPages(pageInfo.getPageSize());
        result.setPages(pageInfo.getPages());
        result.setHasNextPage(pageInfo.isHasNextPage());
        result.setHasPreviousPage(pageInfo.isHasPreviousPage());
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
