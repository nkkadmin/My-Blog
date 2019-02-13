package com.xsx.blog.util;

import com.xsx.blog.common.LoginMsgConstants;
import com.xsx.blog.dto.UserInfo;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static void setSessionValue(HttpSession session,Object value){
        session.setAttribute(LoginMsgConstants.LOGIN_SESSION_FLAG,value);
    }


    public static UserInfo getLoginUser(HttpSession session){
        return (UserInfo)session.getAttribute(LoginMsgConstants.LOGIN_SESSION_FLAG);
    }

    public static void logout(HttpSession session){
        session.removeAttribute(LoginMsgConstants.LOGIN_SESSION_FLAG);
    }
}
