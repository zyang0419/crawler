package com.jin.crawler.news.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jin.crawler.news.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jin
 * @since 2018-12-06
 */
@Component
@DS("monitor")
public interface NewsMapper extends BaseMapper<News> {

}
