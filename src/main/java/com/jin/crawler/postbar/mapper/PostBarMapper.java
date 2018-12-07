package com.jin.crawler.postbar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jin.crawler.postbar.entity.PostBar;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 贴吧爬取保存表 Mapper 接口
 * </p>
 *
 * @author jin
 * @since 2018-12-05
 */
@Component
public interface PostBarMapper extends BaseMapper<PostBar> {

}
