package com.xsx.blog.mapper;

import com.xsx.blog.model.Users;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {

    int insert(Users users);

    int insertSelective(Users users);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users users);

    int updateByPrimaryKey(Users users);

    Users login(@Param("userName") String userName,@Param("password") String password);
}