package com.jin.crawler.mongo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jin.crawler.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>
 * 博客爬取保存表
 * </p>
 *
 * @author jin
 * @since 2018-12-07
 */
@Data
@Document(collection = "blog")
public class BlogMongo {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 链接
     */
    @Indexed(unique = true)
    private String link;

    /**
     * 爬取时的页面时间
     */
    private String pageTime;

    /**
     * 博主名称
     */
    private String sourceName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除标识符1为逻辑删除，默认值为0
     */
    private Integer deleted;

    /**
     * 类型 1为新浪博客0为未知
     */
    private Integer type;

    /**
     * 项目方案id,即爬取关键词所属的方案
     */
    private String projectId;

    /**
     * 项目方案name,即爬取关键词所属的方案名称,实际中此名称是可以改变的,准确名称要根据project_id去实时获取
     */
    private String projectName;

    /**
     * 爬取关键字
     */
    private String keyWord;

    /**
     * 爬取摘要
     */
    private String description;


}
