package com.xsx.blog.util;

/**
 * @Description:
 * @Auther: xsx
 * @Date: 2019/1/7 20:14
 */
public class ImageUtils {

    private ImageUtils(){}

    public static String getSuffix(String base64Str) throws Exception {
        String suffix = "";
        if("data:image/jpeg".equalsIgnoreCase(base64Str)){//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if("data:image/x-icon".equalsIgnoreCase(base64Str)){//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if("data:image/gif".equalsIgnoreCase(base64Str)){//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if("data:image/png".equalsIgnoreCase(base64Str)){//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else{
            throw new Exception("上传图片格式不合法");
        }
        return suffix;
    }
}
