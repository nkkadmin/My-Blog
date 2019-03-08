package com.xsx.blog.service;

import com.xsx.blog.dto.SystemSettingDTO;
import com.xsx.blog.request.SystemSettingRequest;

/**
 * @Description:系统设置
 * @Date: 2019-03-02 12:49
 * @Auther: xieshengxiang
 */
public interface SystemService {

    public SystemSettingDTO getByType(String type);

    public Boolean saveOrUpdate(SystemSettingRequest systemSettingRequest);
}
