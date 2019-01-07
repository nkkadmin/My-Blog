package com.xsx.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    public static List<String> getBase64(String content){
        String[] srcs = getImgs(content);
        List<String> result = new ArrayList<>();
        for(int i = 0;i<srcs.length;i++){
            if(srcs[i].startsWith("data:image/png;base64,"))
                result.add(srcs[i]);
        }
        return result;
    }

    public static String[] getImgs(String content) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String str = "";
        String[] images = null;
        String regEx_img = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);

                if ("".equals(str)) {
                    str = tempSelected;
                } else {
                    String temp = tempSelected;
                    str = str + "#" + temp;
                }
            }
        }
        if (!"".equals(str)) {
            images = str.split("#");
        }
        return images;
    }

    public static void main(String[] args) {
        String str = "<p>asdadsasasdsf阿道夫水电费水电费asdasd<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAACEQAAAS6CAYAAABZBy6NAAAMKWlDQ1BJQ0MgUHJvZmlsZQAASImVlwdUk8kWgOcvSUhIaIEISAm9CdKr1NAiCEgVbIQkkFBiTAgidmRRwbWgIoIVXRVRdC2ALCpiL4tg7w8LKsq6WLCh8iYJoKvnvXfePWf+/8udO3fuvZl/zgwA6jEc6vKII=\"></p>";
        List<String> strings = getBase64(str);
        System.out.println(strings);
        for(String s : strings){
            System.out.println(str.contains(s));
            System.out.println(str.replace(s,"111"));
        }
    }
}
