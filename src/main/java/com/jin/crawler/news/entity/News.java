package com.jin.crawler.news.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jin.crawler.common.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jin
 * @since 2018-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class News extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    /**
     * 新闻来源
     */
    private String source;

    /**
     * 文章情感语义分析:正面positve,反面negative中性neutral
     */
    private String semantic;

    private String description;

    private String content;

    private Integer hits;

    private Date createTm;

    private String publishTm;

    private String url;

    private String newsImage;

    /**
     * 状态位 VALID有效 INVALID无效
     */
    private String status;
    /**
     * 平台 1新闻2微信3微博4论坛5问答6直播7视频8客户端9贴吧10博客99其他
     */
    private String platform;

    private Long referId;

    private String similarId;

    private Integer similarNumber;

    /**
     * 项目ID
     */
    private String projectId;


}
