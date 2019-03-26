package com.xsx.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private DateUtils(){}


    public static String dateToStr(Date date){
        return dateToStr(date,"yyyy-MM-dd");
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToStr(Date date,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 返回日期的年份
     * @param date
     * @return
     */
    public static String dateYear(Date date){
        return dateToStr(date,"yyyy");
    }

    public static String currentDate(){
        return dateToStr(new Date(),"yyyy");
    }
}
