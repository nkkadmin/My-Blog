package com.xsx.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private DateUtils(){}


    public static String dateToStr(Date date){
        return dateToStr(date,"yyyy-MM-hh");
    }

    public static String dateToStr(Date date,String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}
