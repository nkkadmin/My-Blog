package com.xsx.blog.blog;

import com.xsx.blog.common.Constants;
import com.xsx.blog.service.IMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Date: 2019-03-02 23:44
 * @Auther: xieshengxiang
 */
public class IMailServiceTest extends BlogApplicationTests {


    @Autowired
    private IMailService iMailService;

    @Test
    public void sendTest(){
        iMailService.sendSimpleMail("15821292493@163.com", "登录验证码",Constants.SNED_MAIL_LOGIN_CONTENT);
    }
}
