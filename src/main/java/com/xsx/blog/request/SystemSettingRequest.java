package com.xsx.blog.request;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2019-03-02 12:55
 * @Auther: xieshengxiang
 */
public class SystemSettingRequest implements Serializable {

    private String type;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
