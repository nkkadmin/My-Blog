package com.xsx.blog.controller;

import com.xsx.blog.common.Constants;
import com.xsx.blog.request.FileUploadRequest;
import com.xsx.blog.util.Base64Convert;
import com.xsx.blog.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    /**
     * base64方式上传
     * @param fileUploadRequest
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadBase64",method = RequestMethod.POST)
    public String findAll(@RequestBody FileUploadRequest fileUploadRequest, HttpServletRequest request) throws Exception {
        String base64 = fileUploadRequest.getBase64();
        if(StringUtils.isEmpty(base64)){
            return null;
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

    @GetMapping("/delFile")
    public void delFile(@RequestParam("filePath") String filePath){
        if(StringUtils.isEmpty(filePath)){
            return;
        }
        filePath = filePath.replace(getFdfsServerUrl(),"");
        String group = filePath.substring(0,filePath.indexOf("/"));
        String fileName = filePath.substring(group.length()+1,filePath.length());
        try{
            FastDFSClient.deleteFile(group, fileName);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private String getFdfsServerUrl(){
        return Constants.FDFS_SERVER_URL;
    }

}
