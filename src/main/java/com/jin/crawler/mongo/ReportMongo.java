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
 * 新闻爬取保存表
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@Data
@Document(collection = "report")
public class ReportMongo  {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 标题
     */
    private String title;

    /**
     *新闻摘要
     */
    private String description;

    /**
     * 内容
     */
    private String content;

    /**
     * 链接,同时根据此url防止重复爬取
     */
    @Indexed(unique = true)
    private String link;

    /**
     * 爬取时的页面时间
     */
    private String pageTime;

    /**
     * 来源媒体
     */
    private String sourceMedia;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除标识符1为逻辑删除，默认值为0
     */
    private Integer deleted;

    /**
     * 类型 1为搜狗新闻2为百度新闻0为未知
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

}
