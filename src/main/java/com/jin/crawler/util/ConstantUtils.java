package com.jin.crawler.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 * @author Xingheng.Zhang
 * @date Created in 2018-9-11 12:03
 */
public class ConstantUtils {


    public static final int ONE = 1;
    public static final int ZERO = 0;

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



}
