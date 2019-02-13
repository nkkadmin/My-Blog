package com.xsx.blog.request;

import com.xsx.blog.util.MD5Utils;
import org.springframework.util.StringUtils;


import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEmpty(){
        if(StringUtils.isEmpty(this.username) || StringUtils.isEmpty(this.password)){
            return true;
        }
        return false;
    }
}
