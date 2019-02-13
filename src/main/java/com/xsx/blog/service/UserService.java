package com.xsx.blog.service;


import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.model.Users;
import com.xsx.blog.request.LoginRequest;

public interface UserService {

    int insert(Users users);

    int update(Users users);

    UserInfo login(LoginRequest loginRequest);
}
