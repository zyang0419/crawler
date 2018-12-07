package com.jin.crawler.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 * @author Xingheng.Zhang
 * @date Created in 2018-9-11 12:03
 */
public class ConstantUtils {


    public static final String ONE = "1";

    /**
     * 逻辑删除标识
     */
    public static final Integer LOGIC_DELETED = 1;
    /**
     * 只爬取多少天前到现在的信息
     */
    public static final Integer BEFORE_DAYS = 4;
    public static final String ALL = "ALL";
    public static final String ASC = "ASC";

    /**
     * 状态位
     */
    public static final String STATUS_VALID = "VALID";
    public static final String STATUS_INVALID = "INVALID";
    public static final String STATUS_SIMILAR = "SIMILAR";

    public static final String POSITIVE = "POSITIVE";
    public static final String NEGATIVE = "NEGATIVE";
    public static final String NEUTRAL = "NEUTRAL";

    public static final Map<String, String> SEMATIC_VALUE = new HashMap<String, String>();
    /**
     * 平台 1新闻2微信3微博4论坛5问答6直播7视频8客户端9贴吧10博客99其他
     */
    public static final Map<String, String> PLATFORM_VALUE = new HashMap<String, String>();
    static {
        SEMATIC_VALUE.put("POSITIVE", "正面");
        SEMATIC_VALUE.put("NEGATIVE", "负面");
        SEMATIC_VALUE.put("NEUTRAL", "中性");
        SEMATIC_VALUE.put("1", "新闻");
        SEMATIC_VALUE.put("2", "微信");
        SEMATIC_VALUE.put("3", "微博");
        SEMATIC_VALUE.put("4", "论坛");
        SEMATIC_VALUE.put("5", "问答");
        SEMATIC_VALUE.put("6", "直播");
        SEMATIC_VALUE.put("7", "视频");
        SEMATIC_VALUE.put("8", "客户端");
        SEMATIC_VALUE.put("9", "贴吧");
        SEMATIC_VALUE.put("10", "博客");
        SEMATIC_VALUE.put("99", "其他");
    }


}
