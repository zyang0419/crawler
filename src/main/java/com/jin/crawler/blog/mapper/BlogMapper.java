package com.jin.crawler.blog.mapper;

import com.jin.crawler.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 博客爬取保存表 Mapper 接口
 * </p>
 *
 * @author jin
 * @since 2018-12-07
 */
@Component
public interface BlogMapper extends BaseMapper<Blog> {

}
