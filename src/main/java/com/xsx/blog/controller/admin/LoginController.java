package com.xsx.blog.controller.admin;


import com.xsx.blog.common.LoginMsgConstants;
import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.request.LoginRequest;
import com.xsx.blog.response.LoginResponse;
import com.xsx.blog.service.UserService;
import com.xsx.blog.util.SessionUtils;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/admin/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpSession session){
        if(loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername()) || StringUtils.isEmpty(loginRequest.getPassword())){
            return new LoginResponse(false, LoginMsgConstants.USERNAME_OR_PASSWORD_IS_NULL);
        }
        UserInfo userInfo = userService.login(loginRequest);
        if(userInfo != null){
            //设置session
            SessionUtils.setSessionValue(session,userInfo);
            return new LoginResponse(true,LoginMsgConstants.LOGIN_SUCCESS);
        }
        return new LoginResponse(false,LoginMsgConstants.USERNAME_OR_PASSWORD_IS_ERROR);
    }

    /**
     * 获取当前登录用户
     * @return
     */
    @RequestMapping(value = "/currentLoginUser",method = RequestMethod.POST)
    public UserInfo currentLoginUser(HttpSession session){
        UserInfo userInfo = SessionUtils.getLoginUser(session);
        if(userInfo != null)
            userInfo.setPassword("**************");
        return userInfo;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public void logout(HttpSession session){
        SessionUtils.logout(session);
    }
}
