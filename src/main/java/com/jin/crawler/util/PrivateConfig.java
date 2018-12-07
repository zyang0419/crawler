package com.jin.crawler.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 统一获取配置文件信息
 *
 * @author Xingheng.Zhang
 * @date Created in 2018-11-8 9:58
 */
@Component
@ConfigurationProperties(prefix = "api-url.monitor")
public class PrivateConfig {

    /**
     * 获取方案关键词和排除词API地址
     */
    public static String path;




    /**
     * 用户最多新建方案数量
     */
    public static long number;

    public static String getPath() {
        return path;
    }

    @Value("${api-url.monitor.path}")
    public void setPath(String path) {
        PrivateConfig.path = path;
    }


    public static long getNumber() {
        return number;
    }

    @Value("${api-url.monitor.number}")
    public void setNumber(long number) {
        PrivateConfig.number = number;
    }


}
