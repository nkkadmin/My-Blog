package com.xsx.blog.service;

/**
 * @Description:
 * @Date: 2019-03-02 23:39
 * @Auther: xieshengxiang
 */
public interface IMailService {

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);
}
