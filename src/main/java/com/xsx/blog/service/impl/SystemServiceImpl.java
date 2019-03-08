package com.xsx.blog.service.impl;

import com.xsx.blog.dto.SystemSettingDTO;
import com.xsx.blog.mapper.SystemSettingMapper;
import com.xsx.blog.model.SystemSetting;
import com.xsx.blog.request.SystemSettingRequest;
import com.xsx.blog.service.SystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Date: 2019-03-02 12:56
 * @Auther: xieshengxiang
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemSettingMapper systemSettingMapper;


    @Override
    public SystemSettingDTO getByType(String type) {
        SystemSetting systemSetting = systemSettingMapper.getByType(type);
        if(systemSetting == null)
            return null;
        SystemSettingDTO systemSettingDTO = new SystemSettingDTO();
        BeanUtils.copyProperties(systemSetting,systemSettingDTO);
        return systemSettingDTO;
    }

    @Override
    public Boolean saveOrUpdate(SystemSettingRequest systemSettingRequest) {
        try{
            SystemSetting systemSetting = systemSettingMapper.getByType(systemSettingRequest.getType());
            if(systemSetting == null){
                systemSetting = new SystemSetting();
                systemSetting.setType(systemSettingRequest.getType());
                systemSetting.setValue(systemSettingRequest.getValue());
                systemSettingMapper.insertSelective(systemSetting);
            }else{
                systemSetting.setValue(systemSettingRequest.getValue());
                systemSettingMapper.updateByTypeSelective(systemSetting);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
