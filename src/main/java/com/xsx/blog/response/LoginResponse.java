package com.xsx.blog.response;


import com.xsx.blog.result.Result;


public class LoginResponse extends Result {

    public LoginResponse(Boolean success,String message) {
        this.setSuccess(success);
        this.setMsg(message);
    }
}
