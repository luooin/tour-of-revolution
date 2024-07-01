package com.hzw.utils;

import cn.hutool.core.date.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    public static String TimeToDate(String currentTime ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间戳
        //long l = System.currentTimeMillis();
        //如果你数据库存储的时间戳类型为string，就需要将string字符串转为long类型
        long l = Long.parseLong(currentTime);
        return sdf.format(l);
    }
    public static String defaultFormatYMD(LocalDate date) {
        if(null != date) {
            // 创建一个 DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 使用 formatter 将 LocalDate 格式化为字符串
            return date.format(formatter);
        } else {
            return null;
        }
    }
    public static String defaultFormatYMD(LocalDateTime date) {
        if(null != date) {
            // 创建一个 DateTimeFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 使用 formatter 将 LocalDate 格式化为字符串
            return date.format(formatter);
        } else {
            return null;
        }
    }

    public static String currentTimeYMD(){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期转换为字符串
        return currentDate.format(formatter);
    }
    public static String currentTimeYMDS(){
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        // 将日期转换为字符串
        return currentDate.format(formatter);
    }
    public static String currentDateTime(){
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒");
        // 将日期转换为字符串
        return currentDate.format(formatter);
    }
}
