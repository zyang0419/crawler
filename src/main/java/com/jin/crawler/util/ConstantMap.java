package com.jin.crawler.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

interface ConstantMap {
    /**
     * 平台 1新闻2微信3微博4论坛5问答6直播7视频8客户端9贴吧10博客99其他
     */
    Map<String, String> PLATFORM_MAP =
            new ImmutableMap.Builder<String, String>()
                    .put("1", "新闻")
                    .put("2", "微信")
                    .put("3", "微博")
                    .put("4", "论坛")
                    .put("5", "问答")
                    .put("6", "直播")
                    .put("7", "视频")
                    .put("8", "客户端")
                    .put("9", "贴吧")
                    .put("10", "博客")
                    .put("99", "其他")
                    .build();
    /**
     * 情感
     */
    Map<String, String> SEMANTIC_MAP =
            new ImmutableMap.Builder<String, String>()
                    .put("POSITIVE", "正面")
                    .put("NEGATIVE", "负面")
                    .put("NEUTRAL", "中性")
                    .build();


}