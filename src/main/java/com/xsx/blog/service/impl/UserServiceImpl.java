package com.xsx.blog.service.impl;

import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.mapper.UsersMapper;
import com.xsx.blog.model.Users;
import com.xsx.blog.request.LoginRequest;
import com.xsx.blog.service.UserService;
import com.xsx.blog.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public int insert(Users users) {
        users.setPassword(MD5Utils.encoderByMd5(users.getUserName()+users.getPassword()));
        return usersMapper.insert(users);
    }

    @Override
    public int update(Users users) {
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    @Override
    public UserInfo login(LoginRequest loginRequest) {
        if(loginRequest.isEmpty()){
            return null;
        }
        Users users = usersMapper.login(loginRequest.getUsername(),MD5Utils.encoderByMd5(loginRequest.getUsername()+loginRequest.getPassword()));
        if(users == null){
            return null;
        }else{
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(users,userInfo);
            return userInfo;
        }

    }
}
