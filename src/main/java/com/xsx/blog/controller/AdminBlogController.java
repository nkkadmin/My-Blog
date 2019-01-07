package com.xsx.blog.controller;

import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.dto.FastDFSFile;
import com.xsx.blog.entity.Blog;
import com.xsx.blog.entity.Menu;
import com.xsx.blog.entity.Tags;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.request.PageRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.BlogService;
import com.xsx.blog.service.MenuService;
import com.xsx.blog.service.TagsService;
import com.xsx.blog.util.FastDFSClient;
import com.xsx.blog.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.Base64Utils;
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
    @Value("${fdfs_server_url}")
    private String fdfsServerUrl;


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
    public Page<Blog> findAll(@RequestBody BlogSearchRequest blogSearchRequest){
        Page<Blog> page = blogService.findPage(blogSearchRequest);
        return page;
    }

    @RequestMapping(value = "/selectById")
    public Blog selectById(@RequestParam(name = "id") Integer id){
        return blogService.findOne(id);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Boolean edit(@RequestBody Blog blog, HttpServletRequest request) throws Exception {
        //判断是否有图片
        if(blog.getContent().contains("<img")){
            String content = blog.getContent();
            //获取所有的base64
            List<String> base64List = StringUtils.getBase64(content);
            for(String base64 : base64List){
                String[] paths = upload(base64,request);
                String savePath = getFdfsServerUrl();
                for(String path : paths){
                    savePath += path+"/";
                }
                if(!org.springframework.util.StringUtils.isEmpty(savePath))
                    savePath = savePath.substring(0,savePath.length()-1);
                System.out.println("savePath:"+savePath);
                content = content.replace(base64,savePath);
            }
            blog.setContent(content);
        }
        return blogService.save(blog);
    }

    private String getFdfsServerUrl(){
        return "http://"+fdfsServerUrl+"/";
    }


    private String[] upload(String base64,HttpServletRequest request) throws Exception {
        String suffixStr = base64.split(",")[0];
        String data = base64.split(",")[1];
        String fileName = UUID.randomUUID().toString().replace("-","") + getSuffix(suffixStr.split(";")[0]);
        byte[] bs = Base64Utils.decodeFromString(data);

        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setName(fileName);
        fastDFSFile.setAuthor("xsx");
        fastDFSFile.setContent(bs);
        fastDFSFile.setExt(getSuffix(suffixStr.split(";")[0]));

        String[] files = FastDFSClient.upload(fastDFSFile);
        for(String file : files){
            System.out.println(file);
        }
        return files;
    }

    private static String getSuffix(String base64Str) throws Exception {
        String suffix = "";
        if("data:image/jpeg".equalsIgnoreCase(base64Str)){//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if("data:image/x-icon".equalsIgnoreCase(base64Str)){//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if("data:image/gif".equalsIgnoreCase(base64Str)){//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if("data:image/png".equalsIgnoreCase(base64Str)){//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else{
            throw new Exception("上传图片格式不合法");
        }
        return suffix;
    }



    @RequestMapping(value = "/deleteById")
    public Boolean deleteById(@RequestParam(name = "id",required = true) Integer id){
        return blogService.deleteOne(id);
    }
}
