package com.jin.crawler.util;

import com.google.common.base.Splitter;

import java.util.Map;

/**
 * @author Xingheng.Zhang
 * @date Created in 2018-12-5 14:04
 */
public class CrawlerUtils {


    /**
     *
     *获取url中指定的参数值
     *@author Xingheng.Zhang
     *@date 2018-12-5 14:05
     *@param
     *@return^
     */
    public static String getParam(String url, String name) {
        String params = url.substring(url.indexOf("?") + 1, url.length());
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(params);
        return split.get(name);
    }

    /**
     *
     * 处理不规则时间 如30分钟前
     *@author Xingheng.Zhang
     *@date 2018-12-6 9:55
     *@param
     *@return
     */
    public static String handleTime(String time){
        if(time.endsWith("小时前")){
            time = time.substring(0,time.indexOf("小时前"));
            time = DateUtils.format(DateUtils.beforeHours(-Integer.parseInt(time)));
            System.out.println("time_hour====="+time);
        }else if(time.endsWith("分钟前")){
            time = time.substring(0,time.indexOf("分钟前"));
            time = DateUtils.format(DateUtils.beforeMinutes(-Integer.parseInt(time)));
            System.out.println("time_minute====="+time);
        } else if(time.endsWith("秒前")){
            time = time.substring(0,time.indexOf("秒前"));
            time = DateUtils.format(DateUtils.beforeSeconds(-Integer.parseInt(time)));
            System.out.println("time_second====="+time);
        } else if(time.indexOf("年") != -1){
            time = time.replace("年","-").replace("月","-").replace("日","");
            System.out.println("time_年月日====="+time);
        } else {
            time = time + " 00:00:00";
            System.out.println("time====="+time);
        }
        return time;
    }


    public static void main(String[] args) {
        String str = "2018年12月05日 21:56";
        System.out.println(str.replace("年","-").replace("月","-").replace("日",""));
       /* String string="河南省人民政府   25分钟前";
        for(String a:string.split("\\s+")){
            System.out.println(a);
        }*/
    }
}
