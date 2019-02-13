package com.xsx.blog.blog;

import com.xsx.blog.common.StatuEnum;
import com.xsx.blog.model.Users;
import com.xsx.blog.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserServiceTest extends BlogApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void insertTest(){
        Users users = new Users();
        users.setCreateTime(new Date());
        users.setUserName("admin");
        users.setStatu(StatuEnum.OK.getStatu());
        users.setPassword("admin123");
        int flag = userService.insert(users);
        System.out.println("insert result:" + (flag > 0));
    }
}
