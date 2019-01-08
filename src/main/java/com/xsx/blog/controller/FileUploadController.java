package com.xsx.blog.controller;

import com.xsx.blog.dto.FastDFSFile;
import com.xsx.blog.entity.Blog;
import com.xsx.blog.request.BlogSearchRequest;
import com.xsx.blog.util.Base64Convert;
import com.xsx.blog.util.FastDFSClient;
import com.xsx.blog.util.ImageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2019/1/7 20:12
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${fdfs_server_url}")
    private String fdfsServerUrl;

    @RequestMapping(value = "/upload")
    public String findAll(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws Exception {
        Base64Convert base64Convert = new Base64Convert();

        String base64 = base64Convert.ioToBase64(multipartFile.getInputStream());
        if(!StringUtils.isEmpty(base64))
            base64 = "data:image/jpeg;base64,"+base64;
        String[] paths = FastDFSClient.upload(base64,request);
        String savePath = getFdfsServerUrl();
        for(String path : paths){
            savePath += path+"/";
        }
        return savePath;
    }

    private String getFdfsServerUrl(){
        return "http://"+fdfsServerUrl+"/";
    }

}
