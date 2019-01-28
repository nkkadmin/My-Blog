package com.xsx.blog.result;

public class Result<T> extends AbstractResult {

    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
