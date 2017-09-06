package edu.ccnt.mymall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //joda String ->date
    public static Date strToDate(String str,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }

    //data --> String
    public static String dateToStr(Date date,String formatStr){
       if(date == null){
           return StringUtils.EMPTY;
       }
       DateTime dateTime = new DateTime(date);
       return dateTime.toString(formatStr);
    }

    //joda String ->date
    public static Date strToDate(String str){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }

    //data --> String
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DATE_FORMAT);
    }
}
