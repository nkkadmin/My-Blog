package com.xsx.blog.controller;

import com.xsx.blog.common.Constants;
import com.xsx.blog.util.Base64Convert;
import com.xsx.blog.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2019/1/7 20:12
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {



    @RequestMapping(value = "/upload")
    public String findAll(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws Exception {
        Base64Convert base64Convert = new Base64Convert();

        String base64 = base64Convert.ioToBase64(multipartFile.getInputStream());
        if(!StringUtils.isEmpty(base64)){
            base64 = "data:image/jpeg;base64,"+base64;
        }
        String[] paths = FastDFSClient.upload(base64,request);
        String savePath = getFdfsServerUrl();
        for(String path : paths){
            savePath += path+"/";
        }
        if(savePath.endsWith("/")){
            savePath = savePath.substring(0,savePath.length()-1);
        }
        return savePath;
    }

    private String getFdfsServerUrl(){
        return Constants.FDFS_SERVER_URL;
    }

}
