package com.xsx.blog.config;

import com.xsx.blog.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfigurer implements WebMvcConfigurer{

    @Autowired
    private LoginInterceptor loginInterceptor;

    private static List<String> passUrl = new ArrayList<>();

    static {
        passUrl.add("/admin/login.html");
        passUrl.add("/admin/login/login/*");
        passUrl.add("/admin/login/loginType");
        passUrl.add("/admin/login/sendCode/*");
        passUrl.add("/img/**");
        passUrl.add("/element-ui/**");
        passUrl.add("/editormd/**");
        passUrl.add("/js/**");
        passUrl.add("/css/**");
        passUrl.add("/blog/index.html");
        passUrl.add("/blog/blog.html");
        passUrl.add("/index/**");
        passUrl.add("/about/index.html");
        passUrl.add("/camera/index.html");
        passUrl.add("/camera/index_new.html");

        passUrl.add("/");
        passUrl.add("/gallery/**");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(passUrl);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/blog/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
