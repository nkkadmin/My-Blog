package com.xsx.blog.interceptor;

import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.util.SessionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    //未登录标识
    private final static String UNLOGIN_FLAG = "unlogin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("请求url:"+request.getRequestURL());
        UserInfo userInfo = SessionUtils.getLoginUser(request.getSession());
        if(userInfo == null){
            if(isAjaxRequest(request)){
                ServletOutputStream out = response.getOutputStream();
                //返回给前端未登录标识
                out.print(UNLOGIN_FLAG);
                out.flush();
                out.close();
            }else{
                response.sendRedirect("/admin/login.html");
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("x-requested-with");
        if (header != null && "XMLHttpRequest".equals(header)){
            return true;
        }
        return false;
    }
}
