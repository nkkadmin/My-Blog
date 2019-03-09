package com.xsx.blog.mapper;

import com.xsx.blog.model.SystemSetting;

public interface SystemSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemSetting record);

    int insertSelective(SystemSetting record);

    SystemSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemSetting record);

    int updateByPrimaryKey(SystemSetting record);

    SystemSetting getByType(String type);

    int updateByTypeSelective(SystemSetting systemSetting);
}