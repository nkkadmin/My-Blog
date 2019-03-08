package com.xsx.blog.service;


import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.model.Users;
import com.xsx.blog.request.LoginRequest;
import com.xsx.blog.result.Result;

public interface UserService {

    int insert(Users users);

    int update(Users users);

    Result<UserInfo> login(String loginType, LoginRequest loginRequest);

    void sendCode(String email,Result result);
}
