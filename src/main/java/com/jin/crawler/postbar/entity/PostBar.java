package com.jin.crawler.postbar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jin.crawler.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 贴吧爬取保存表
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PostBar extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

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
    private String link;

    /**
     * 爬取时的页面时间
     */
    private String pageTime;

    /**
     * 贴吧名称
     */
    private String postBarName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除标识符1为逻辑删除，默认值为0
     */
//    private Integer deleted;

    /**
     * 类型 1为百度贴吧0为未知
     */
    private Integer type;

    /**
     * 唯一值,防止重复爬取.百度贴吧使用pid值
     */
    private String uuid;

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
