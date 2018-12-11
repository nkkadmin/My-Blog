package com.xsx.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2018/12/9 17:22
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "hello")
    public String hello(){
        return "hello blog!";
    }
}
