package com.xsx.blog.controller.admin;

import com.xsx.blog.dto.SystemSettingDTO;
import com.xsx.blog.request.SystemSettingRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: 2019-03-02 13:06
 * @Auther: xieshengxiang
 */
@RestController
@RequestMapping("/admin/systemSetting")
public class SystemSettingController {

    @Autowired
    private SystemService systemService;


    @RequestMapping("/edit")
    public Result edit(@RequestBody SystemSettingRequest systemSettingRequest){
        Result result = new Result();
        if(systemSettingRequest == null){
            result.setSuccess(false);
            result.setMsg("参数为空");
            return result;
        }
        boolean flag = systemService.saveOrUpdate(systemSettingRequest);
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping("/getByType/{type}")
    public Result<SystemSettingDTO> getByType(@PathVariable String type){
        Result<SystemSettingDTO> result = new Result<SystemSettingDTO>();
        if(type == null){
            result.setSuccess(false);
            result.setMsg("参数为空");
            return result;
        }
        try{
            SystemSettingDTO systemSettingDTO = systemService.getByType(type);
            result.setSuccess(true);
            result.setObject(systemSettingDTO);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
}
