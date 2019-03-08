package com.xsx.blog.controller.admin;


import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xsx.blog.common.Constants;
import com.xsx.blog.common.LoginMsgConstants;
import com.xsx.blog.dto.SystemSettingDTO;
import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.request.LoginRequest;
import com.xsx.blog.response.LoginResponse;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.SystemService;
import com.xsx.blog.service.UserService;
import com.xsx.blog.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * 登录
 */
@RestController
@RequestMapping("/admin/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/login/{loginType}",method = RequestMethod.POST)
    public LoginResponse login(@PathVariable String loginType,@RequestBody LoginRequest loginRequest, HttpSession session){
        if(loginRequest == null || loginType == null){
            return new LoginResponse(false, LoginMsgConstants.PARAM_IS_NULL_ERROR);
        }
        if(Constants.SyetemSetting_Login.USER_PASS_VAIL.getValue().equals(loginType) && (StringUtils.isEmpty(loginRequest.getUsername())
                || StringUtils.isEmpty(loginRequest.getPassword()))){
            return new LoginResponse(false, LoginMsgConstants.USERNAME_OR_PASSWORD_IS_NULL);
        }else if(Constants.SyetemSetting_Login.MEAIL_VAIL.getValue().equals(loginType) && (StringUtils.isEmpty(loginRequest.getEmail()) || StringUtils.isEmpty(loginRequest.getCode()))){
            return new LoginResponse(false, LoginMsgConstants.EMAIL_CODE_IS_ERROR);
        }

        Result<UserInfo> result = userService.login(loginType,loginRequest);
        if(result.getSuccess()){
            //设置session
            SessionUtils.setSessionValue(session,result.getObject());
            return new LoginResponse(true,LoginMsgConstants.LOGIN_SUCCESS);
        }
        return new LoginResponse(false,result.getMsg());
    }

    @RequestMapping(value = "/loginType",method = RequestMethod.POST)
    public Result loginType(){
        Result result = new Result();
        SystemSettingDTO systemSettingDTO = systemService.getByType("loginType");
        result.setSuccess(true);
        result.setContent(systemSettingDTO.getValue());
        return result;
    }

    @RequestMapping(value = "/sendCode/{email}",method = RequestMethod.GET)
    public Result sendCode(@PathVariable String email){
        Result result = new Result();
       if(StringUtils.isEmpty(email)){
           result.setMsg("请输入邮箱");
           result.setSuccess(false);
           return result;
       }
       userService.sendCode(email,result);
       return result;
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
