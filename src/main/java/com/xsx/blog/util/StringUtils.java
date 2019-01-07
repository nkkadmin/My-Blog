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
            if(srcs[i].startsWith("data:image/"))
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

    /**
     * 删除html标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    public static void main(String[] args) {
        String str = "<p>asdadsasasdsf阿道夫水电费水电费asdasd<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAACEQAAAS6CAYAAABZBy6NAAAMKWlDQ1BJQ0MgUHJvZmlsZQAASImVlwdUk8kWgOcvSUhIaIEISAm9CdKr1NAiCEgVbIQkkFBiTAgidmRRwbWgIoIVXRVRdC2ALCpiL4tg7w8LKsq6WLCh8iYJoKvnvXfePWf+/8udO3fuvZl/zgwA6jEc6vKII=\"></p>";
        System.out.println(delHTMLTag(str));

    }
}
