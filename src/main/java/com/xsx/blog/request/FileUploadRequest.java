package com.xsx.blog.request;

import java.io.Serializable;

/**
 * @description: 文件上传参数
 * @author: xieshengxiang
 * @date: 2019/8/3 13:58
 */
public class FileUploadRequest implements Serializable {
    private static final long serialVersionUID = 7893109787180635083L;
    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
