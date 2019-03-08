package com.xsx.blog.common;

/**
 * @Description:
 * @Date: 2019-01-09 07:42
 * @Auther: xieshengxiang
 */
public class Constants {

    public static final String DEFAULT_PIC = "../img/default_pic.png";

    /**
     * fdfs服务器地址
     */
    public static final String FDFS_SERVER_URL = "http://39.96.176.80:8888/";

    /**
     * redis点赞key
     */
    public static final String REDIS_DIANZAN_MAP_FLAG = "dianzan:map";


    /**
     * redis浏览量key
     */
    public static final String REDIS_LOOK_MAP_FLAG = "look:map";

    /**
     * 登录邮件标题
     */
    public static final String SEND_MAIL_LOGIN_TITLE = "登录校验";

    /**
     * 邮件发生内容
     *
     */
    public static final String SNED_MAIL_LOGIN_CONTENT = "您正在登录【博客】后台管理，登录验证码为：code，请保管好您的验证码，3分钟内有效";

    public static final String SNED_CODE_REDIS_FLAG = "login:code";

    /**
     * 系统设置:登录设置
     */
    public static enum SyetemSetting_Login{

        USER_PASS_VAIL("账户密码校验","userpass"),
        MEAIL_VAIL("邮箱验证码校验","email"),
        BOTH_VAIL("两者双重校验","both");


        private String desc;
        private String value;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        SyetemSetting_Login(String desc, String value){
            this.desc = desc;
            this.value = value;
        }
    }
}
