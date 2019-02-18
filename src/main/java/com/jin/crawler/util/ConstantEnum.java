package com.jin.crawler.util;

import com.google.common.collect.ImmutableMap;

/**
 * @author Xingheng.Zhang
 * @date Created in 2018-12-27 17:14
 */
public enum ConstantEnum {


    NEWS("1", "新闻"),
    WECHAT("2", "微信"),
    WEIBO("3", "微博"),
    FORUM("4", "论坛"),
    QA("5", "问答"),
    LIVE("6", "直播"),
    VIDEO("7", "视频"),
    CLIENT("8", "客户端"),
    POSTBAR("9", "贴吧"),
    BLOG("10", "博客"),
    OTHER("99", "其他");

    private String name;
    private String index;

    // 构造方法
    private ConstantEnum(String index, String name) {
        this.index = index;
        this.name = name;
    }

    // 普通方法
    public static String getName(String index) {
        for (ConstantEnum ce : ConstantEnum.values()) {
            if (ce.getIndex() == index) {
                return ce.name;
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public static void main(String[] args) {
        System.out.println(ConstantEnum.POSTBAR.getIndex());
    }
}
